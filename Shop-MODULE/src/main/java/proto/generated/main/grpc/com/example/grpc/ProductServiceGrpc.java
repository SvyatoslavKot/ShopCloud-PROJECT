package com.example.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.51.0)",
    comments = "Source: ProductService.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ProductServiceGrpc {

  private ProductServiceGrpc() {}

  public static final String SERVICE_NAME = "com.example.grpc.ProductService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.example.grpc.ProductServiceOuterClass.MessageId,
      com.example.grpc.ProductServiceOuterClass.ProtoProduct> getGetByIdMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getById",
      requestType = com.example.grpc.ProductServiceOuterClass.MessageId.class,
      responseType = com.example.grpc.ProductServiceOuterClass.ProtoProduct.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.grpc.ProductServiceOuterClass.MessageId,
      com.example.grpc.ProductServiceOuterClass.ProtoProduct> getGetByIdMethod() {
    io.grpc.MethodDescriptor<com.example.grpc.ProductServiceOuterClass.MessageId, com.example.grpc.ProductServiceOuterClass.ProtoProduct> getGetByIdMethod;
    if ((getGetByIdMethod = ProductServiceGrpc.getGetByIdMethod) == null) {
      synchronized (ProductServiceGrpc.class) {
        if ((getGetByIdMethod = ProductServiceGrpc.getGetByIdMethod) == null) {
          ProductServiceGrpc.getGetByIdMethod = getGetByIdMethod =
              io.grpc.MethodDescriptor.<com.example.grpc.ProductServiceOuterClass.MessageId, com.example.grpc.ProductServiceOuterClass.ProtoProduct>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getById"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.grpc.ProductServiceOuterClass.MessageId.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.grpc.ProductServiceOuterClass.ProtoProduct.getDefaultInstance()))
              .setSchemaDescriptor(new ProductServiceMethodDescriptorSupplier("getById"))
              .build();
        }
      }
    }
    return getGetByIdMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.grpc.ProductServiceOuterClass.MessageFindAll,
      com.example.grpc.ProductServiceOuterClass.ProtoListOfProduct> getGetAllProductMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getAllProduct",
      requestType = com.example.grpc.ProductServiceOuterClass.MessageFindAll.class,
      responseType = com.example.grpc.ProductServiceOuterClass.ProtoListOfProduct.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.grpc.ProductServiceOuterClass.MessageFindAll,
      com.example.grpc.ProductServiceOuterClass.ProtoListOfProduct> getGetAllProductMethod() {
    io.grpc.MethodDescriptor<com.example.grpc.ProductServiceOuterClass.MessageFindAll, com.example.grpc.ProductServiceOuterClass.ProtoListOfProduct> getGetAllProductMethod;
    if ((getGetAllProductMethod = ProductServiceGrpc.getGetAllProductMethod) == null) {
      synchronized (ProductServiceGrpc.class) {
        if ((getGetAllProductMethod = ProductServiceGrpc.getGetAllProductMethod) == null) {
          ProductServiceGrpc.getGetAllProductMethod = getGetAllProductMethod =
              io.grpc.MethodDescriptor.<com.example.grpc.ProductServiceOuterClass.MessageFindAll, com.example.grpc.ProductServiceOuterClass.ProtoListOfProduct>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getAllProduct"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.grpc.ProductServiceOuterClass.MessageFindAll.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.grpc.ProductServiceOuterClass.ProtoListOfProduct.getDefaultInstance()))
              .setSchemaDescriptor(new ProductServiceMethodDescriptorSupplier("getAllProduct"))
              .build();
        }
      }
    }
    return getGetAllProductMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.grpc.ProductServiceOuterClass.ProtoProduct,
      com.example.grpc.ProductServiceOuterClass.MessageRequest> getAddProductMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "addProduct",
      requestType = com.example.grpc.ProductServiceOuterClass.ProtoProduct.class,
      responseType = com.example.grpc.ProductServiceOuterClass.MessageRequest.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.grpc.ProductServiceOuterClass.ProtoProduct,
      com.example.grpc.ProductServiceOuterClass.MessageRequest> getAddProductMethod() {
    io.grpc.MethodDescriptor<com.example.grpc.ProductServiceOuterClass.ProtoProduct, com.example.grpc.ProductServiceOuterClass.MessageRequest> getAddProductMethod;
    if ((getAddProductMethod = ProductServiceGrpc.getAddProductMethod) == null) {
      synchronized (ProductServiceGrpc.class) {
        if ((getAddProductMethod = ProductServiceGrpc.getAddProductMethod) == null) {
          ProductServiceGrpc.getAddProductMethod = getAddProductMethod =
              io.grpc.MethodDescriptor.<com.example.grpc.ProductServiceOuterClass.ProtoProduct, com.example.grpc.ProductServiceOuterClass.MessageRequest>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "addProduct"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.grpc.ProductServiceOuterClass.ProtoProduct.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.grpc.ProductServiceOuterClass.MessageRequest.getDefaultInstance()))
              .setSchemaDescriptor(new ProductServiceMethodDescriptorSupplier("addProduct"))
              .build();
        }
      }
    }
    return getAddProductMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.grpc.ProductServiceOuterClass.MessageAddToBucket,
      com.example.grpc.ProductServiceOuterClass.MessageRequest> getAddProductToBucketMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "addProductToBucket",
      requestType = com.example.grpc.ProductServiceOuterClass.MessageAddToBucket.class,
      responseType = com.example.grpc.ProductServiceOuterClass.MessageRequest.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.grpc.ProductServiceOuterClass.MessageAddToBucket,
      com.example.grpc.ProductServiceOuterClass.MessageRequest> getAddProductToBucketMethod() {
    io.grpc.MethodDescriptor<com.example.grpc.ProductServiceOuterClass.MessageAddToBucket, com.example.grpc.ProductServiceOuterClass.MessageRequest> getAddProductToBucketMethod;
    if ((getAddProductToBucketMethod = ProductServiceGrpc.getAddProductToBucketMethod) == null) {
      synchronized (ProductServiceGrpc.class) {
        if ((getAddProductToBucketMethod = ProductServiceGrpc.getAddProductToBucketMethod) == null) {
          ProductServiceGrpc.getAddProductToBucketMethod = getAddProductToBucketMethod =
              io.grpc.MethodDescriptor.<com.example.grpc.ProductServiceOuterClass.MessageAddToBucket, com.example.grpc.ProductServiceOuterClass.MessageRequest>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "addProductToBucket"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.grpc.ProductServiceOuterClass.MessageAddToBucket.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.grpc.ProductServiceOuterClass.MessageRequest.getDefaultInstance()))
              .setSchemaDescriptor(new ProductServiceMethodDescriptorSupplier("addProductToBucket"))
              .build();
        }
      }
    }
    return getAddProductToBucketMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.grpc.ProductServiceOuterClass.MessageRemoveFromBucket,
      com.example.grpc.ProductServiceOuterClass.MessageRequest> getRemoveProductFromBucketMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "removeProductFromBucket",
      requestType = com.example.grpc.ProductServiceOuterClass.MessageRemoveFromBucket.class,
      responseType = com.example.grpc.ProductServiceOuterClass.MessageRequest.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.grpc.ProductServiceOuterClass.MessageRemoveFromBucket,
      com.example.grpc.ProductServiceOuterClass.MessageRequest> getRemoveProductFromBucketMethod() {
    io.grpc.MethodDescriptor<com.example.grpc.ProductServiceOuterClass.MessageRemoveFromBucket, com.example.grpc.ProductServiceOuterClass.MessageRequest> getRemoveProductFromBucketMethod;
    if ((getRemoveProductFromBucketMethod = ProductServiceGrpc.getRemoveProductFromBucketMethod) == null) {
      synchronized (ProductServiceGrpc.class) {
        if ((getRemoveProductFromBucketMethod = ProductServiceGrpc.getRemoveProductFromBucketMethod) == null) {
          ProductServiceGrpc.getRemoveProductFromBucketMethod = getRemoveProductFromBucketMethod =
              io.grpc.MethodDescriptor.<com.example.grpc.ProductServiceOuterClass.MessageRemoveFromBucket, com.example.grpc.ProductServiceOuterClass.MessageRequest>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "removeProductFromBucket"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.grpc.ProductServiceOuterClass.MessageRemoveFromBucket.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.grpc.ProductServiceOuterClass.MessageRequest.getDefaultInstance()))
              .setSchemaDescriptor(new ProductServiceMethodDescriptorSupplier("removeProductFromBucket"))
              .build();
        }
      }
    }
    return getRemoveProductFromBucketMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.grpc.ProductServiceOuterClass.MessageFindAllWithParam,
      com.example.grpc.ProductServiceOuterClass.ProtoPageableResponse> getGetAllByParamMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getAllByParam",
      requestType = com.example.grpc.ProductServiceOuterClass.MessageFindAllWithParam.class,
      responseType = com.example.grpc.ProductServiceOuterClass.ProtoPageableResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.grpc.ProductServiceOuterClass.MessageFindAllWithParam,
      com.example.grpc.ProductServiceOuterClass.ProtoPageableResponse> getGetAllByParamMethod() {
    io.grpc.MethodDescriptor<com.example.grpc.ProductServiceOuterClass.MessageFindAllWithParam, com.example.grpc.ProductServiceOuterClass.ProtoPageableResponse> getGetAllByParamMethod;
    if ((getGetAllByParamMethod = ProductServiceGrpc.getGetAllByParamMethod) == null) {
      synchronized (ProductServiceGrpc.class) {
        if ((getGetAllByParamMethod = ProductServiceGrpc.getGetAllByParamMethod) == null) {
          ProductServiceGrpc.getGetAllByParamMethod = getGetAllByParamMethod =
              io.grpc.MethodDescriptor.<com.example.grpc.ProductServiceOuterClass.MessageFindAllWithParam, com.example.grpc.ProductServiceOuterClass.ProtoPageableResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "getAllByParam"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.grpc.ProductServiceOuterClass.MessageFindAllWithParam.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.grpc.ProductServiceOuterClass.ProtoPageableResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProductServiceMethodDescriptorSupplier("getAllByParam"))
              .build();
        }
      }
    }
    return getGetAllByParamMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ProductServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProductServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProductServiceStub>() {
        @java.lang.Override
        public ProductServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProductServiceStub(channel, callOptions);
        }
      };
    return ProductServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ProductServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProductServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProductServiceBlockingStub>() {
        @java.lang.Override
        public ProductServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProductServiceBlockingStub(channel, callOptions);
        }
      };
    return ProductServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ProductServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProductServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProductServiceFutureStub>() {
        @java.lang.Override
        public ProductServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProductServiceFutureStub(channel, callOptions);
        }
      };
    return ProductServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ProductServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getById(com.example.grpc.ProductServiceOuterClass.MessageId request,
        io.grpc.stub.StreamObserver<com.example.grpc.ProductServiceOuterClass.ProtoProduct> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetByIdMethod(), responseObserver);
    }

    /**
     */
    public void getAllProduct(com.example.grpc.ProductServiceOuterClass.MessageFindAll request,
        io.grpc.stub.StreamObserver<com.example.grpc.ProductServiceOuterClass.ProtoListOfProduct> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAllProductMethod(), responseObserver);
    }

    /**
     */
    public void addProduct(com.example.grpc.ProductServiceOuterClass.ProtoProduct request,
        io.grpc.stub.StreamObserver<com.example.grpc.ProductServiceOuterClass.MessageRequest> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAddProductMethod(), responseObserver);
    }

    /**
     */
    public void addProductToBucket(com.example.grpc.ProductServiceOuterClass.MessageAddToBucket request,
        io.grpc.stub.StreamObserver<com.example.grpc.ProductServiceOuterClass.MessageRequest> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAddProductToBucketMethod(), responseObserver);
    }

    /**
     */
    public void removeProductFromBucket(com.example.grpc.ProductServiceOuterClass.MessageRemoveFromBucket request,
        io.grpc.stub.StreamObserver<com.example.grpc.ProductServiceOuterClass.MessageRequest> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRemoveProductFromBucketMethod(), responseObserver);
    }

    /**
     */
    public void getAllByParam(com.example.grpc.ProductServiceOuterClass.MessageFindAllWithParam request,
        io.grpc.stub.StreamObserver<com.example.grpc.ProductServiceOuterClass.ProtoPageableResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAllByParamMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetByIdMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.example.grpc.ProductServiceOuterClass.MessageId,
                com.example.grpc.ProductServiceOuterClass.ProtoProduct>(
                  this, METHODID_GET_BY_ID)))
          .addMethod(
            getGetAllProductMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.example.grpc.ProductServiceOuterClass.MessageFindAll,
                com.example.grpc.ProductServiceOuterClass.ProtoListOfProduct>(
                  this, METHODID_GET_ALL_PRODUCT)))
          .addMethod(
            getAddProductMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.example.grpc.ProductServiceOuterClass.ProtoProduct,
                com.example.grpc.ProductServiceOuterClass.MessageRequest>(
                  this, METHODID_ADD_PRODUCT)))
          .addMethod(
            getAddProductToBucketMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.example.grpc.ProductServiceOuterClass.MessageAddToBucket,
                com.example.grpc.ProductServiceOuterClass.MessageRequest>(
                  this, METHODID_ADD_PRODUCT_TO_BUCKET)))
          .addMethod(
            getRemoveProductFromBucketMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.example.grpc.ProductServiceOuterClass.MessageRemoveFromBucket,
                com.example.grpc.ProductServiceOuterClass.MessageRequest>(
                  this, METHODID_REMOVE_PRODUCT_FROM_BUCKET)))
          .addMethod(
            getGetAllByParamMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.example.grpc.ProductServiceOuterClass.MessageFindAllWithParam,
                com.example.grpc.ProductServiceOuterClass.ProtoPageableResponse>(
                  this, METHODID_GET_ALL_BY_PARAM)))
          .build();
    }
  }

  /**
   */
  public static final class ProductServiceStub extends io.grpc.stub.AbstractAsyncStub<ProductServiceStub> {
    private ProductServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProductServiceStub(channel, callOptions);
    }

    /**
     */
    public void getById(com.example.grpc.ProductServiceOuterClass.MessageId request,
        io.grpc.stub.StreamObserver<com.example.grpc.ProductServiceOuterClass.ProtoProduct> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetByIdMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAllProduct(com.example.grpc.ProductServiceOuterClass.MessageFindAll request,
        io.grpc.stub.StreamObserver<com.example.grpc.ProductServiceOuterClass.ProtoListOfProduct> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAllProductMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void addProduct(com.example.grpc.ProductServiceOuterClass.ProtoProduct request,
        io.grpc.stub.StreamObserver<com.example.grpc.ProductServiceOuterClass.MessageRequest> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAddProductMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void addProductToBucket(com.example.grpc.ProductServiceOuterClass.MessageAddToBucket request,
        io.grpc.stub.StreamObserver<com.example.grpc.ProductServiceOuterClass.MessageRequest> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAddProductToBucketMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void removeProductFromBucket(com.example.grpc.ProductServiceOuterClass.MessageRemoveFromBucket request,
        io.grpc.stub.StreamObserver<com.example.grpc.ProductServiceOuterClass.MessageRequest> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRemoveProductFromBucketMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAllByParam(com.example.grpc.ProductServiceOuterClass.MessageFindAllWithParam request,
        io.grpc.stub.StreamObserver<com.example.grpc.ProductServiceOuterClass.ProtoPageableResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAllByParamMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ProductServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<ProductServiceBlockingStub> {
    private ProductServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProductServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.example.grpc.ProductServiceOuterClass.ProtoProduct getById(com.example.grpc.ProductServiceOuterClass.MessageId request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetByIdMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.example.grpc.ProductServiceOuterClass.ProtoListOfProduct getAllProduct(com.example.grpc.ProductServiceOuterClass.MessageFindAll request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAllProductMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.example.grpc.ProductServiceOuterClass.MessageRequest addProduct(com.example.grpc.ProductServiceOuterClass.ProtoProduct request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAddProductMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.example.grpc.ProductServiceOuterClass.MessageRequest addProductToBucket(com.example.grpc.ProductServiceOuterClass.MessageAddToBucket request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAddProductToBucketMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.example.grpc.ProductServiceOuterClass.MessageRequest removeProductFromBucket(com.example.grpc.ProductServiceOuterClass.MessageRemoveFromBucket request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRemoveProductFromBucketMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.example.grpc.ProductServiceOuterClass.ProtoPageableResponse getAllByParam(com.example.grpc.ProductServiceOuterClass.MessageFindAllWithParam request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAllByParamMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ProductServiceFutureStub extends io.grpc.stub.AbstractFutureStub<ProductServiceFutureStub> {
    private ProductServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProductServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProductServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.grpc.ProductServiceOuterClass.ProtoProduct> getById(
        com.example.grpc.ProductServiceOuterClass.MessageId request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetByIdMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.grpc.ProductServiceOuterClass.ProtoListOfProduct> getAllProduct(
        com.example.grpc.ProductServiceOuterClass.MessageFindAll request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAllProductMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.grpc.ProductServiceOuterClass.MessageRequest> addProduct(
        com.example.grpc.ProductServiceOuterClass.ProtoProduct request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAddProductMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.grpc.ProductServiceOuterClass.MessageRequest> addProductToBucket(
        com.example.grpc.ProductServiceOuterClass.MessageAddToBucket request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAddProductToBucketMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.grpc.ProductServiceOuterClass.MessageRequest> removeProductFromBucket(
        com.example.grpc.ProductServiceOuterClass.MessageRemoveFromBucket request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRemoveProductFromBucketMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.grpc.ProductServiceOuterClass.ProtoPageableResponse> getAllByParam(
        com.example.grpc.ProductServiceOuterClass.MessageFindAllWithParam request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAllByParamMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_BY_ID = 0;
  private static final int METHODID_GET_ALL_PRODUCT = 1;
  private static final int METHODID_ADD_PRODUCT = 2;
  private static final int METHODID_ADD_PRODUCT_TO_BUCKET = 3;
  private static final int METHODID_REMOVE_PRODUCT_FROM_BUCKET = 4;
  private static final int METHODID_GET_ALL_BY_PARAM = 5;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ProductServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ProductServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_BY_ID:
          serviceImpl.getById((com.example.grpc.ProductServiceOuterClass.MessageId) request,
              (io.grpc.stub.StreamObserver<com.example.grpc.ProductServiceOuterClass.ProtoProduct>) responseObserver);
          break;
        case METHODID_GET_ALL_PRODUCT:
          serviceImpl.getAllProduct((com.example.grpc.ProductServiceOuterClass.MessageFindAll) request,
              (io.grpc.stub.StreamObserver<com.example.grpc.ProductServiceOuterClass.ProtoListOfProduct>) responseObserver);
          break;
        case METHODID_ADD_PRODUCT:
          serviceImpl.addProduct((com.example.grpc.ProductServiceOuterClass.ProtoProduct) request,
              (io.grpc.stub.StreamObserver<com.example.grpc.ProductServiceOuterClass.MessageRequest>) responseObserver);
          break;
        case METHODID_ADD_PRODUCT_TO_BUCKET:
          serviceImpl.addProductToBucket((com.example.grpc.ProductServiceOuterClass.MessageAddToBucket) request,
              (io.grpc.stub.StreamObserver<com.example.grpc.ProductServiceOuterClass.MessageRequest>) responseObserver);
          break;
        case METHODID_REMOVE_PRODUCT_FROM_BUCKET:
          serviceImpl.removeProductFromBucket((com.example.grpc.ProductServiceOuterClass.MessageRemoveFromBucket) request,
              (io.grpc.stub.StreamObserver<com.example.grpc.ProductServiceOuterClass.MessageRequest>) responseObserver);
          break;
        case METHODID_GET_ALL_BY_PARAM:
          serviceImpl.getAllByParam((com.example.grpc.ProductServiceOuterClass.MessageFindAllWithParam) request,
              (io.grpc.stub.StreamObserver<com.example.grpc.ProductServiceOuterClass.ProtoPageableResponse>) responseObserver);
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
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class ProductServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ProductServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.example.grpc.ProductServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ProductService");
    }
  }

  private static final class ProductServiceFileDescriptorSupplier
      extends ProductServiceBaseDescriptorSupplier {
    ProductServiceFileDescriptorSupplier() {}
  }

  private static final class ProductServiceMethodDescriptorSupplier
      extends ProductServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ProductServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (ProductServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ProductServiceFileDescriptorSupplier())
              .addMethod(getGetByIdMethod())
              .addMethod(getGetAllProductMethod())
              .addMethod(getAddProductMethod())
              .addMethod(getAddProductToBucketMethod())
              .addMethod(getRemoveProductFromBucketMethod())
              .addMethod(getGetAllByParamMethod())
              .build();
        }
      }
    }
    return result;
  }
}
