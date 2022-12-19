package com.webull.openapi.quotes.internal.grpc.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.51.0)",
    comments = "Source: gateway.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class QuoteGrpc {

  private QuoteGrpc() {}

  public static final String SERVICE_NAME = "openapi.Quote";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientRequest,
      com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientResponse> getStreamRequestMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StreamRequest",
      requestType = com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientRequest.class,
      responseType = com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientRequest,
      com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientResponse> getStreamRequestMethod() {
    io.grpc.MethodDescriptor<com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientRequest, com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientResponse> getStreamRequestMethod;
    if ((getStreamRequestMethod = QuoteGrpc.getStreamRequestMethod) == null) {
      synchronized (QuoteGrpc.class) {
        if ((getStreamRequestMethod = QuoteGrpc.getStreamRequestMethod) == null) {
          QuoteGrpc.getStreamRequestMethod = getStreamRequestMethod =
              io.grpc.MethodDescriptor.<com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientRequest, com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "StreamRequest"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QuoteMethodDescriptorSupplier("StreamRequest"))
              .build();
        }
      }
    }
    return getStreamRequestMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientRequest,
      com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientResponse> getRequestMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Request",
      requestType = com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientRequest.class,
      responseType = com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientRequest,
      com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientResponse> getRequestMethod() {
    io.grpc.MethodDescriptor<com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientRequest, com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientResponse> getRequestMethod;
    if ((getRequestMethod = QuoteGrpc.getRequestMethod) == null) {
      synchronized (QuoteGrpc.class) {
        if ((getRequestMethod = QuoteGrpc.getRequestMethod) == null) {
          QuoteGrpc.getRequestMethod = getRequestMethod =
              io.grpc.MethodDescriptor.<com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientRequest, com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Request"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientResponse.getDefaultInstance()))
              .setSchemaDescriptor(new QuoteMethodDescriptorSupplier("Request"))
              .build();
        }
      }
    }
    return getRequestMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static QuoteStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<QuoteStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<QuoteStub>() {
        @java.lang.Override
        public QuoteStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new QuoteStub(channel, callOptions);
        }
      };
    return QuoteStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static QuoteBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<QuoteBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<QuoteBlockingStub>() {
        @java.lang.Override
        public QuoteBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new QuoteBlockingStub(channel, callOptions);
        }
      };
    return QuoteBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static QuoteFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<QuoteFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<QuoteFutureStub>() {
        @java.lang.Override
        public QuoteFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new QuoteFutureStub(channel, callOptions);
        }
      };
    return QuoteFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class QuoteImplBase implements io.grpc.BindableService {

    /**
     */
    public io.grpc.stub.StreamObserver<com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientRequest> streamRequest(
        io.grpc.stub.StreamObserver<com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientResponse> responseObserver) {
      return io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall(getStreamRequestMethod(), responseObserver);
    }

    /**
     */
    public void request(com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientRequest request,
        io.grpc.stub.StreamObserver<com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRequestMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getStreamRequestMethod(),
            io.grpc.stub.ServerCalls.asyncBidiStreamingCall(
              new MethodHandlers<
                com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientRequest,
                com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientResponse>(
                  this, METHODID_STREAM_REQUEST)))
          .addMethod(
            getRequestMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientRequest,
                com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientResponse>(
                  this, METHODID_REQUEST)))
          .build();
    }
  }

  /**
   */
  public static final class QuoteStub extends io.grpc.stub.AbstractAsyncStub<QuoteStub> {
    private QuoteStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected QuoteStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new QuoteStub(channel, callOptions);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientRequest> streamRequest(
        io.grpc.stub.StreamObserver<com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientResponse> responseObserver) {
      return io.grpc.stub.ClientCalls.asyncBidiStreamingCall(
          getChannel().newCall(getStreamRequestMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public void request(com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientRequest request,
        io.grpc.stub.StreamObserver<com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRequestMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class QuoteBlockingStub extends io.grpc.stub.AbstractBlockingStub<QuoteBlockingStub> {
    private QuoteBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected QuoteBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new QuoteBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientResponse request(com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRequestMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class QuoteFutureStub extends io.grpc.stub.AbstractFutureStub<QuoteFutureStub> {
    private QuoteFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected QuoteFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new QuoteFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientResponse> request(
        com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRequestMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REQUEST = 0;
  private static final int METHODID_STREAM_REQUEST = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final QuoteImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(QuoteImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REQUEST:
          serviceImpl.request((com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientRequest) request,
              (io.grpc.stub.StreamObserver<com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_STREAM_REQUEST:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.streamRequest(
              (io.grpc.stub.StreamObserver<com.webull.openapi.quotes.internal.grpc.proto.Gateway.ClientResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class QuoteBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    QuoteBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.webull.openapi.quotes.internal.grpc.proto.Gateway.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Quote");
    }
  }

  private static final class QuoteFileDescriptorSupplier
      extends QuoteBaseDescriptorSupplier {
    QuoteFileDescriptorSupplier() {}
  }

  private static final class QuoteMethodDescriptorSupplier
      extends QuoteBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    QuoteMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (QuoteGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new QuoteFileDescriptorSupplier())
              .addMethod(getStreamRequestMethod())
              .addMethod(getRequestMethod())
              .build();
        }
      }
    }
    return result;
  }
}
