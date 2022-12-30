/*
 * Copyright 2022 Webull
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.webull.openapi.quotes.internal.grpc;

import com.google.protobuf.InvalidProtocolBufferException;
import com.webull.openapi.execption.ClientException;
import com.webull.openapi.grpc.BaseGrpcClient;
import com.webull.openapi.grpc.StatefulResponseObserver;
import com.webull.openapi.grpc.auth.SignatureCallCredentials;
import com.webull.openapi.grpc.exception.ErrorResponseException;
import com.webull.openapi.grpc.lifecycle.GrpcHandler;
import com.webull.openapi.grpc.lifecycle.SubStreamObserver;
import com.webull.openapi.grpc.lifecycle.proxy.HandlerProxyFactory;
import com.webull.openapi.grpc.retry.SynchronousGrpcRetryable;
import com.webull.openapi.logger.Logger;
import com.webull.openapi.logger.LoggerFactory;
import com.webull.openapi.quotes.api.QuotesApiClient;
import com.webull.openapi.quotes.domain.AskBid;
import com.webull.openapi.quotes.domain.Bar;
import com.webull.openapi.quotes.domain.Broker;
import com.webull.openapi.quotes.domain.Instrument;
import com.webull.openapi.quotes.domain.Order;
import com.webull.openapi.quotes.domain.Quote;
import com.webull.openapi.quotes.domain.Snapshot;
import com.webull.openapi.quotes.domain.Tick;
import com.webull.openapi.quotes.internal.common.ArgNames;
import com.webull.openapi.quotes.internal.grpc.lifecycle.ApiLoggingHandler;
import com.webull.openapi.quotes.internal.grpc.lifecycle.channel.ApiGrpcChannel;
import com.webull.openapi.quotes.internal.grpc.lifecycle.channel.GrpcChannel;
import com.webull.openapi.quotes.internal.grpc.lifecycle.channel.GrpcStreamChannelPoolMap;
import com.webull.openapi.quotes.internal.grpc.lifecycle.channel.GrpcStreamEndpoint;
import com.webull.openapi.quotes.internal.grpc.lifecycle.channel.SingletonChannelPool;
import com.webull.openapi.quotes.internal.grpc.lifecycle.channel.StreamObserverFactory;
import com.webull.openapi.quotes.internal.grpc.lifecycle.proxy.ApiHandlerProxyFactory;
import com.webull.openapi.quotes.internal.grpc.proto.Api;
import com.webull.openapi.quotes.internal.grpc.proto.Gateway;
import com.webull.openapi.quotes.internal.grpc.proto.QuoteGrpc;
import com.webull.openapi.quotes.subsribe.lifecycle.ReplyMessage;
import com.webull.openapi.retry.RetryPolicy;
import com.webull.openapi.utils.Assert;
import com.webull.openapi.utils.CollectionUtils;
import com.webull.openapi.utils.StringUtils;
import io.grpc.CallCredentials;
import io.grpc.stub.StreamObserver;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class GrpcQuotesApiClient extends BaseGrpcClient<Gateway.ClientResponse> implements QuotesApiClient {

    private static final Logger logger = LoggerFactory.getLogger(GrpcQuotesApiClient.class);

    private static final GrpcStreamChannelPoolMap<Gateway.ClientRequest, Gateway.ClientResponse> channelPoolMap =
            new GrpcStreamChannelPoolMap<>();

    private final long connectTimeoutMillis;
    private final long readTimeoutMillis;

    private final SingletonChannelPool<Gateway.ClientRequest, Gateway.ClientResponse> channelPool;
    private final ApiConnectionManager connectionManager = new ApiConnectionManager();
    private final CallCredentials credentials = new SignatureCallCredentials(appKey, appSecret, host, port,
            "/openapi.Quote/StreamRequest", null);

    protected GrpcQuotesApiClient(String appKey,
                                  String appSecret,
                                  String host,
                                  int port,
                                  long connectTimeoutMillis,
                                  long readTimeoutMillis,
                                  RetryPolicy retryPolicy,
                                  boolean enableTls,
                                  List<GrpcHandler> handlers) {
        this(appKey, appSecret, host, port, connectTimeoutMillis, readTimeoutMillis, retryPolicy, enableTls, handlers,
                ApiHandlerProxyFactory.getInstance());
    }

    protected GrpcQuotesApiClient(String appKey,
                                  String appSecret,
                                  String host,
                                  int port,
                                  long connectTimeoutMillis,
                                  long readTimeoutMillis,
                                  RetryPolicy retryPolicy,
                                  boolean enableTls,
                                  List<GrpcHandler> handlers,
                                  HandlerProxyFactory<Gateway.ClientResponse> handlerProxyFactory) {
        super(appKey, appSecret, host, port, retryPolicy, enableTls, handlers, handlerProxyFactory);

        Assert.nonnegative("connectTimeoutMillis", connectTimeoutMillis);
        Assert.nonnegative("readTimeoutMillis", readTimeoutMillis);
        this.connectTimeoutMillis = connectTimeoutMillis;
        this.readTimeoutMillis = readTimeoutMillis;

        GrpcStreamEndpoint<Gateway.ClientRequest, Gateway.ClientResponse> endpoint =
                new GrpcStreamEndpoint<>(this.appKey, this.host, this.port, new ApiStreamObserverFactory());
        this.channelPool = channelPoolMap.get(endpoint);
    }

    private Gateway.ClientResponse doRequest(final Gateway.ClientRequest request) {
        // acquire for a channel
        GrpcChannel<Gateway.ClientRequest, Gateway.ClientResponse> grpcChannel =
                this.channelPool.acquire(connectTimeoutMillis, TimeUnit.MILLISECONDS);
        // exchange message
        try (ReplyMessage<Gateway.ClientResponse> replyMessage = grpcChannel.exchange(request)) {
            Gateway.ClientResponse response = replyMessage.get(readTimeoutMillis, TimeUnit.MILLISECONDS);
            if (response.getCode() != 0) {
                throw new ErrorResponseException(response.getCode(), response.getMsg(), response.getRequestId());
            }
            return response;
        }
    }

    @Override
    public List<Instrument> getInstruments(Set<String> symbols, String category) {
        Assert.notEmpty(ArgNames.SYMBOLS, symbols);
        Assert.notBlank(ArgNames.CATEGORY, category);
        Api.InstrumentRequest tickerRequest = Api.InstrumentRequest.newBuilder()
                .setSymbols(String.join(",", symbols))
                .setCategory(category)
                .build();
        Gateway.ClientRequest request = Gateway.ClientRequest.newBuilder()
                .setType(Gateway.MsgType.Payload)
                .setRequestId(UUID.randomUUID().toString())
                .setPath("/instrument/list")
                .setPayload(tickerRequest.toByteString())
                .build();
        Gateway.ClientResponse response = this.doRequest(request);
        try {
            List<Api.Instrument> instrumentResponses = Api.InstrumentResponse.parseFrom(response.getPayload()).getResultList();
            return instrumentResponses.stream().map(r -> {
                Instrument instrument = new Instrument();
                instrument.setName(r.getName());
                instrument.setSymbol(r.getSymbol());
                instrument.setInstrumentId(r.getInstrumentId());
                instrument.setExchangeCode(r.getExchangeCode());
                instrument.setCurrency(r.getCurrency());
                return instrument;
            }).collect(Collectors.toList());
        } catch (InvalidProtocolBufferException e) {
            throw new ClientException("Deserialize instrument response error.", e);
        }
    }

    @Override
    public List<Bar> getBars(String symbol, String category, String timespan, int count) {
        Assert.notBlank(ArgNames.SYMBOL, symbol);
        Assert.notBlank(ArgNames.CATEGORY, category);
        Assert.notBlank(ArgNames.TIMESPAN, timespan);
        Assert.nonnegative(ArgNames.COUNT, count);
        Api.BarsRequest barsRequest = Api.BarsRequest.newBuilder()
                .setSymbol(symbol)
                .setCategory(category)
                .setTimespan(timespan)
                .setCount(String.valueOf(count))
                .build();
        Gateway.ClientRequest request = Gateway.ClientRequest.newBuilder()
                .setType(Gateway.MsgType.Payload)
                .setRequestId(UUID.randomUUID().toString())
                .setPath("/market-data/bars")
                .setPayload(barsRequest.toByteString())
                .build();
        Gateway.ClientResponse response = this.doRequest(request);
        try {
            List<Api.Bar> barResponses = Api.BarsResponse.parseFrom(response.getPayload()).getResultList();
            return barResponses.stream().map(r -> {
                Bar bar = new Bar();
                bar.setTime(r.getTime());
                bar.setOpen(r.getOpen());
                bar.setClose(r.getClose());
                bar.setHigh(r.getHigh());
                bar.setLow(r.getLow());
                bar.setVolume(r.getVolume());
                return bar;
            }).collect(Collectors.toList());
        } catch (InvalidProtocolBufferException e) {
            throw new ClientException("Deserialize bar response error.", e);
        }
    }

    @Override
    public Quote getQuote(String symbol, String category) {
        Assert.notBlank(ArgNames.SYMBOL, symbol);
        Assert.notBlank(ArgNames.CATEGORY, category);
        Api.QuoteRequest quoteRequest = Api.QuoteRequest.newBuilder()
                .setSymbol(symbol)
                .setCategory(category)
                .build();
        Gateway.ClientRequest request = Gateway.ClientRequest.newBuilder()
                .setType(Gateway.MsgType.Payload)
                .setRequestId(UUID.randomUUID().toString())
                .setPath("/market-data/quotes")
                .setPayload(quoteRequest.toByteString())
                .build();
        Gateway.ClientResponse response = this.doRequest(request);
        try {
            Api.QuoteResponse quoteResponse = Api.QuoteResponse.parseFrom(response.getPayload());
            Quote quote = new Quote();
            quote.setSymbol(quoteResponse.getSymbol());
            quote.setInstrumentId(quoteResponse.getInstrumentId());
            List<AskBid> asks = quoteResponse.getAsksList().stream().map(this::assembleAskBid).collect(Collectors.toList());
            quote.setAsks(asks);
            List<AskBid> bids = quoteResponse.getBidsList().stream().map(this::assembleAskBid).collect(Collectors.toList());
            quote.setBids(bids);
            return quote;
        } catch (InvalidProtocolBufferException e) {
            throw new ClientException("Deserialize quote response error.", e);
        }
    }

    private AskBid assembleAskBid(Api.AskBid from) {
        AskBid result = new AskBid();
        result.setPrice(from.getPrice());
        result.setSize(from.getSize());
        List<Order> orders = from.getOrderList().stream().map(this::assembleOrder).collect(Collectors.toList());
        result.setOrders(orders);
        List<Broker> brokers = from.getBrokerList().stream().map(this::assembleBroker).collect(Collectors.toList());
        result.setBrokers(brokers);
        return result;
    }

    private Order assembleOrder(Api.Order from) {
        Order result = new Order();
        result.setMpid(from.getMpid());
        result.setSize(from.getSize());
        return result;
    }

    private Broker assembleBroker(Api.Broker from) {
        Broker result = new Broker();
        result.setBid(from.getBid());
        result.setName(from.getName());
        return result;
    }

    @Override
    public List<Snapshot> getSnapshots(Set<String> symbols, String category) {
        Assert.notEmpty(ArgNames.SYMBOLS, symbols);
        Assert.notBlank(ArgNames.CATEGORY, category);
        Api.SnapshotRequest snapshotsRequest = Api.SnapshotRequest.newBuilder()
                .setSymbols(String.join(",", symbols))
                .setCategory(category)
                .build();
        Gateway.ClientRequest request = Gateway.ClientRequest.newBuilder()
                .setType(Gateway.MsgType.Payload)
                .setRequestId(UUID.randomUUID().toString())
                .setPath("/market-data/snapshot")
                .setPayload(snapshotsRequest.toByteString())
                .build();
        Gateway.ClientResponse response = this.doRequest(request);
        try {
            List<Api.Snapshot> snapshotResponses = Api.SnapshotResponse.parseFrom(response.getPayload()).getResultList();
            return snapshotResponses.stream().map(r -> {
                Snapshot snapshot = new Snapshot();
                snapshot.setSymbol(r.getSymbol());
                snapshot.setInstrumentId(r.getInstrumentId());
                snapshot.setTradeTime(r.getTradeTime());
                snapshot.setPrice(r.getPrice());
                snapshot.setOpen(r.getOpen());
                snapshot.setHigh(r.getHigh());
                snapshot.setLow(r.getLow());
                snapshot.setPreClose(r.getPreClose());
                snapshot.setVolume(r.getVolume());
                snapshot.setChange(r.getChange());
                snapshot.setChangeRatio(r.getChangeRatio());
                return snapshot;
            }).collect(Collectors.toList());
        } catch (InvalidProtocolBufferException e) {
            throw new ClientException("Deserialize snapshot response error.", e);
        }
    }

    @Override
    public List<Tick> getTicks(String symbol, String category, int count) {
        Assert.notBlank(ArgNames.SYMBOL, symbol);
        Assert.notBlank(ArgNames.CATEGORY, category);
        Assert.nonnegative(ArgNames.COUNT, count);
        Api.TickRequest quoteRequest = Api.TickRequest.newBuilder()
                .setSymbol(symbol)
                .setCategory(category)
                .setCount(String.valueOf(count))
                .build();
        Gateway.ClientRequest request = Gateway.ClientRequest.newBuilder()
                .setType(Gateway.MsgType.Payload)
                .setRequestId(UUID.randomUUID().toString())
                .setPath("/market-data/tick")
                .setPayload(quoteRequest.toByteString())
                .build();
        Gateway.ClientResponse response = this.doRequest(request);
        try {
            Api.TickResponse ticksResponse = Api.TickResponse.parseFrom(response.getPayload());
            return ticksResponse.getResultList().stream().map(r -> {
                Tick tick = new Tick();
                tick.setSymbol(ticksResponse.getSymbol());
                tick.setInstrumentId(ticksResponse.getInstrumentId());
                tick.setTime(r.getTime());
                tick.setPrice(r.getPrice());
                tick.setVolume(r.getVolume());
                tick.setSide(r.getSide());
                return tick;
            }).collect(Collectors.toList());
        } catch (InvalidProtocolBufferException e) {
            throw new ClientException("Deserialize tick response error.", e);
        }
    }

    @Override
    public String getToken() {
        Gateway.ClientRequest request = Gateway.ClientRequest.newBuilder()
                .setType(Gateway.MsgType.Payload)
                .setRequestId(UUID.randomUUID().toString())
                .setPath("/market-data/streaming/token")
                .build();
        Gateway.ClientResponse response = this.doRequest(request);
        try {
            Api.TokenResponse tokenResponse = Api.TokenResponse.parseFrom(response.getPayload());
            return tokenResponse.getToken();
        } catch (InvalidProtocolBufferException e) {
            throw new ClientException("Deserialize token response error.", e);
        }
    }

    @Override
    public void subscribe(String token, Set<String> symbols, String category, Set<String> subTypes) {
        Assert.notBlank(ArgNames.TOKEN, token);
        Assert.notEmpty(ArgNames.SYMBOLS, symbols);
        Assert.notBlank(ArgNames.CATEGORY, category);
        Assert.notEmpty(ArgNames.SUBTYPES, subTypes);
        Api.SubscribeRequest subscribeRequest = Api.SubscribeRequest.newBuilder()
                .setToken(token)
                .addAllSymbols(symbols)
                .setCategory(category)
                .addAllSubTypes(subTypes)
                .build();
        Gateway.ClientRequest request = Gateway.ClientRequest.newBuilder()
                .setType(Gateway.MsgType.Payload)
                .setRequestId(UUID.randomUUID().toString())
                .setPath("/market-data/streaming/subscribe")
                .setPayload(subscribeRequest.toByteString())
                .build();
        this.doRequest(request);
    }

    @Override
    public void unsubscribe(String token, Set<String> symbols, String category, Set<String> subTypes, Boolean unsubscribeAll) {
        Assert.notBlank(ArgNames.TOKEN, token);
        Api.SubscribeRequest.Builder builder = Api.SubscribeRequest.newBuilder().setToken(token);
        if (CollectionUtils.isNotEmpty(symbols)) {
            builder.addAllSymbols(symbols);
        }
        if (StringUtils.isNotBlank(category)) {
            builder.setCategory(category);
        }
        if (CollectionUtils.isNotEmpty(subTypes)) {
            builder.addAllSubTypes(subTypes);
        }
        if (unsubscribeAll != null) {
            builder.setUnsubscribeAll(String.valueOf(unsubscribeAll));
        }
        Api.SubscribeRequest subscribeRequest = builder.build();
        Gateway.ClientRequest request = Gateway.ClientRequest.newBuilder()
                .setType(Gateway.MsgType.Payload)
                .setRequestId(UUID.randomUUID().toString())
                .setPath("/market-data/streaming/unsubscribe")
                .setPayload(subscribeRequest.toByteString())
                .build();
        this.doRequest(request);
    }

    @Override
    public void shutdown(long timeout, TimeUnit timeUnit) throws InterruptedException {
        this.channelPool.close();
        super.shutdown(timeout, timeUnit);
    }

    @Override
    public void shutdownNow() {
        this.channelPool.close();
        super.shutdownNow();
    }

    private class ApiConnectionManager implements SubStreamObserver<Gateway.ClientResponse> {

        @Override
        public void onReady() {
            channelPool.onConnected(new ApiGrpcChannel());
        }

        @Override
        public void onNext(Gateway.ClientResponse value) {
            Gateway.MsgType msgType = value.getType();
            if (Gateway.MsgType.Payload == msgType) {
                channelPool.acquireIfExist().ifPresent(channel -> channel.receive(value));
            }
        }

        @Override
        public void onError(Throwable cause) {
            channelPool.remove().ifPresent(GrpcChannel::close);
        }

        @Override
        public void onCompleted() {
            channelPool.remove().ifPresent(GrpcChannel::close);
        }
    }

    private class ApiStreamObserverFactory implements StreamObserverFactory<Gateway.ClientRequest, Gateway.ClientResponse> {

        private volatile StreamObserver<Gateway.ClientResponse> responseStreamObserver;
        private volatile QuoteGrpc.QuoteStub stub;

        @Override
        public StreamObserver<Gateway.ClientRequest> createRequestObserver() {
            StreamObserver<Gateway.ClientResponse> streamObserver = this.createResponseObserver();
            logger.debug("Start to grpc stream request...");
            return this.stub.streamRequest(streamObserver);
        }

        @Override
        public StreamObserver<Gateway.ClientResponse> createResponseObserver() {
            // response stream observer should only init once!
            if (this.responseStreamObserver == null) {
                synchronized (this) {
                    if (this.responseStreamObserver == null) {
                        buildChannel();
                        this.stub = QuoteGrpc.newStub(channel).withCallCredentials(credentials);

                        // add connection manager
                        subObservers.addFirst(connectionManager);
                        // add logging handler
                        subObservers.addFirst(ApiLoggingHandler.getInstance());

                        StatefulResponseObserver<Gateway.ClientRequest, Gateway.ClientResponse> observer = new StatefulResponseObserver<>(subObservers);
                        observer.setRetryable(new SynchronousGrpcRetryable(channelPool::tryAcquire, retryPolicy));
                        this.responseStreamObserver = observer;
                    }
                }
            }
            return this.responseStreamObserver;
        }
    }
}
