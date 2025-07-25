package com.webull.openapi.example.quotes;

import com.webull.openapi.common.dict.Category;
import com.webull.openapi.common.dict.SubscribeType;
import com.webull.openapi.example.config.Env;
import com.webull.openapi.execption.ClientException;
import com.webull.openapi.execption.ServerException;
import com.webull.openapi.grpc.exception.UserCancelledException;
import com.webull.openapi.logger.Logger;
import com.webull.openapi.logger.LoggerFactory;
import com.webull.openapi.quotes.subsribe.QuotesSubsClient;
import com.webull.openapi.quotes.subsribe.message.ConnAck;
import com.webull.openapi.quotes.subsribe.message.MarketData;
import com.webull.openapi.serialize.JsonSerializer;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class QuotesSubscribe {

    private static final Logger logger = LoggerFactory.getLogger(QuotesSubscribe.class);

    public static void main(String[] args) {
        Set<String> symbols = new HashSet<>();
        symbols.add("AAPL");

        Set<String> subTypes = new HashSet<>();
        subTypes.add(SubscribeType.SNAPSHOT.name());

        try (QuotesSubsClient client = QuotesSubsClient.builder()
                .appKey(Env.APP_KEY)
                .appSecret(Env.APP_SECRET)
                .regionId(Env.REGION_ID)
                .onMessage(QuotesSubscribe::handleMarketData)
                .addSubscription(symbols, Category.US_STOCK.name(), subTypes)
                .build()) {

            // subscribe blocking.
            subscribeBlocking(client);

            // subscribe asynchronously.
//             subscribeAsync(client);

            // subscribe by reactive stream.
//             subscribeRx(client);

        } catch (ClientException ex) {
            logger.error("Client error", ex);
        } catch (ServerException ex) {
            logger.error("Sever error", ex);
        } catch (Exception ex) {
            logger.error("Unknown error", ex);
        }
    }

    private static void handleMarketData(MarketData marketData) {
        // your code...
        logger.info("Received market data: {}", JsonSerializer.toJson(marketData));
    }


    private static void subscribeBlocking(QuotesSubsClient client) {
        client.connectBlocking();
        client.subscribeBlocking();
        logger.info("Subscribe completed.");
    }


    private static void subscribeAsync(QuotesSubsClient client) {
        CompletableFuture<ConnAck> connectFuture = client.connectAsync();
        CompletableFuture<Void> subscribeFuture = connectFuture.thenCompose(ack -> client.subscribeAsync());

        // your code...

        try {
            subscribeFuture.get();
            logger.info("Subscribe completed.");
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException ex) {
            Throwable cause = ex.getCause();
            if (cause instanceof ClientException) {
                throw (ClientException) cause;
            } else if (cause instanceof ServerException) {
                throw (ServerException) cause;
            } else {
                throw new ClientException("Unknown error", cause);
            }
        }
    }


    private static void subscribeRx(QuotesSubsClient client)  {
        Flowable<Object> subscribeFlow = client.connectRx().flatMapPublisher(ack -> client.subscribeRx());
        subscribeFlow.ignoreElements().subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable disposable) {
                logger.info("Start to subscribing.");
            }

            @Override
            public void onComplete() {
                logger.info("Subscribe completed.");
            }

            @Override
            public void onError(@NonNull Throwable ex) {
                if (!(ex instanceof UserCancelledException)) {
                    logger.error("Subscribe error", ex);
                }
            }
        });
        try {
            // subscribe for 60 seconds.
            Thread.sleep(60 * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
