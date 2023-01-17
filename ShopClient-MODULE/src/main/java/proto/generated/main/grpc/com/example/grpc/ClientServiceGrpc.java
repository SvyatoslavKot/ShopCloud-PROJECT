package com.example.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.51.0)",
    comments = "Source: ClientService.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ClientServiceGrpc {

  private ClientServiceGrpc() {}

  public static final String SERVICE_NAME = "com.example.grpc.ClientService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.example.grpc.ClientServiceOuterClass.ProtoUserDto,
      com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityString> getRegistrationClientMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "registrationClient",
      requestType = com.example.grpc.ClientServiceOuterClass.ProtoUserDto.class,
      responseType = com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityString.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.grpc.ClientServiceOuterClass.ProtoUserDto,
      com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityString> getRegistrationClientMethod() {
    io.grpc.MethodDescriptor<com.example.grpc.ClientServiceOuterClass.ProtoUserDto, com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityString> getRegistrationClientMethod;
    if ((getRegistrationClientMethod = ClientServiceGrpc.getRegistrationClientMethod) == null) {
      synchronized (ClientServiceGrpc.class) {
        if ((getRegistrationClientMethod = ClientServiceGrpc.getRegistrationClientMethod) == null) {
          ClientServiceGrpc.getRegistrationClientMethod = getRegistrationClientMethod =
              io.grpc.MethodDescriptor.<com.example.grpc.ClientServiceOuterClass.ProtoUserDto, com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityString>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "registrationClient"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.grpc.ClientServiceOuterClass.ProtoUserDto.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityString.getDefaultInstance()))
              .setSchemaDescriptor(new ClientServiceMethodDescriptorSupplier("registrationClient"))
              .build();
        }
      }
    }
    return getRegistrationClientMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.grpc.ClientServiceOuterClass.ProtoMessageString,
      com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityUser> getFindByMailMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "findByMail",
      requestType = com.example.grpc.ClientServiceOuterClass.ProtoMessageString.class,
      responseType = com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityUser.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.grpc.ClientServiceOuterClass.ProtoMessageString,
      com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityUser> getFindByMailMethod() {
    io.grpc.MethodDescriptor<com.example.grpc.ClientServiceOuterClass.ProtoMessageString, com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityUser> getFindByMailMethod;
    if ((getFindByMailMethod = ClientServiceGrpc.getFindByMailMethod) == null) {
      synchronized (ClientServiceGrpc.class) {
        if ((getFindByMailMethod = ClientServiceGrpc.getFindByMailMethod) == null) {
          ClientServiceGrpc.getFindByMailMethod = getFindByMailMethod =
              io.grpc.MethodDescriptor.<com.example.grpc.ClientServiceOuterClass.ProtoMessageString, com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityUser>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "findByMail"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.grpc.ClientServiceOuterClass.ProtoMessageString.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityUser.getDefaultInstance()))
              .setSchemaDescriptor(new ClientServiceMethodDescriptorSupplier("findByMail"))
              .build();
        }
      }
    }
    return getFindByMailMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.grpc.ClientServiceOuterClass.ProtoSearchParam,
      com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityAllUsers> getFindAllUserDtoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "findAllUserDto",
      requestType = com.example.grpc.ClientServiceOuterClass.ProtoSearchParam.class,
      responseType = com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityAllUsers.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.grpc.ClientServiceOuterClass.ProtoSearchParam,
      com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityAllUsers> getFindAllUserDtoMethod() {
    io.grpc.MethodDescriptor<com.example.grpc.ClientServiceOuterClass.ProtoSearchParam, com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityAllUsers> getFindAllUserDtoMethod;
    if ((getFindAllUserDtoMethod = ClientServiceGrpc.getFindAllUserDtoMethod) == null) {
      synchronized (ClientServiceGrpc.class) {
        if ((getFindAllUserDtoMethod = ClientServiceGrpc.getFindAllUserDtoMethod) == null) {
          ClientServiceGrpc.getFindAllUserDtoMethod = getFindAllUserDtoMethod =
              io.grpc.MethodDescriptor.<com.example.grpc.ClientServiceOuterClass.ProtoSearchParam, com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityAllUsers>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "findAllUserDto"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.grpc.ClientServiceOuterClass.ProtoSearchParam.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityAllUsers.getDefaultInstance()))
              .setSchemaDescriptor(new ClientServiceMethodDescriptorSupplier("findAllUserDto"))
              .build();
        }
      }
    }
    return getFindAllUserDtoMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.example.grpc.ClientServiceOuterClass.ProtoUserDto,
      com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityString> getUpdateProfileMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "updateProfile",
      requestType = com.example.grpc.ClientServiceOuterClass.ProtoUserDto.class,
      responseType = com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityString.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.example.grpc.ClientServiceOuterClass.ProtoUserDto,
      com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityString> getUpdateProfileMethod() {
    io.grpc.MethodDescriptor<com.example.grpc.ClientServiceOuterClass.ProtoUserDto, com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityString> getUpdateProfileMethod;
    if ((getUpdateProfileMethod = ClientServiceGrpc.getUpdateProfileMethod) == null) {
      synchronized (ClientServiceGrpc.class) {
        if ((getUpdateProfileMethod = ClientServiceGrpc.getUpdateProfileMethod) == null) {
          ClientServiceGrpc.getUpdateProfileMethod = getUpdateProfileMethod =
              io.grpc.MethodDescriptor.<com.example.grpc.ClientServiceOuterClass.ProtoUserDto, com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityString>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "updateProfile"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.grpc.ClientServiceOuterClass.ProtoUserDto.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityString.getDefaultInstance()))
              .setSchemaDescriptor(new ClientServiceMethodDescriptorSupplier("updateProfile"))
              .build();
        }
      }
    }
    return getUpdateProfileMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ClientServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ClientServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ClientServiceStub>() {
        @java.lang.Override
        public ClientServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ClientServiceStub(channel, callOptions);
        }
      };
    return ClientServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ClientServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ClientServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ClientServiceBlockingStub>() {
        @java.lang.Override
        public ClientServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ClientServiceBlockingStub(channel, callOptions);
        }
      };
    return ClientServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ClientServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ClientServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ClientServiceFutureStub>() {
        @java.lang.Override
        public ClientServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ClientServiceFutureStub(channel, callOptions);
        }
      };
    return ClientServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ClientServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void registrationClient(com.example.grpc.ClientServiceOuterClass.ProtoUserDto request,
        io.grpc.stub.StreamObserver<com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityString> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegistrationClientMethod(), responseObserver);
    }

    /**
     */
    public void findByMail(com.example.grpc.ClientServiceOuterClass.ProtoMessageString request,
        io.grpc.stub.StreamObserver<com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityUser> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFindByMailMethod(), responseObserver);
    }

    /**
     */
    public void findAllUserDto(com.example.grpc.ClientServiceOuterClass.ProtoSearchParam request,
        io.grpc.stub.StreamObserver<com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityAllUsers> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getFindAllUserDtoMethod(), responseObserver);
    }

    /**
     */
    public void updateProfile(com.example.grpc.ClientServiceOuterClass.ProtoUserDto request,
        io.grpc.stub.StreamObserver<com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityString> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateProfileMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getRegistrationClientMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.example.grpc.ClientServiceOuterClass.ProtoUserDto,
                com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityString>(
                  this, METHODID_REGISTRATION_CLIENT)))
          .addMethod(
            getFindByMailMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.example.grpc.ClientServiceOuterClass.ProtoMessageString,
                com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityUser>(
                  this, METHODID_FIND_BY_MAIL)))
          .addMethod(
            getFindAllUserDtoMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.example.grpc.ClientServiceOuterClass.ProtoSearchParam,
                com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityAllUsers>(
                  this, METHODID_FIND_ALL_USER_DTO)))
          .addMethod(
            getUpdateProfileMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.example.grpc.ClientServiceOuterClass.ProtoUserDto,
                com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityString>(
                  this, METHODID_UPDATE_PROFILE)))
          .build();
    }
  }

  /**
   */
  public static final class ClientServiceStub extends io.grpc.stub.AbstractAsyncStub<ClientServiceStub> {
    private ClientServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ClientServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ClientServiceStub(channel, callOptions);
    }

    /**
     */
    public void registrationClient(com.example.grpc.ClientServiceOuterClass.ProtoUserDto request,
        io.grpc.stub.StreamObserver<com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityString> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegistrationClientMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void findByMail(com.example.grpc.ClientServiceOuterClass.ProtoMessageString request,
        io.grpc.stub.StreamObserver<com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityUser> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFindByMailMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void findAllUserDto(com.example.grpc.ClientServiceOuterClass.ProtoSearchParam request,
        io.grpc.stub.StreamObserver<com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityAllUsers> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getFindAllUserDtoMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateProfile(com.example.grpc.ClientServiceOuterClass.ProtoUserDto request,
        io.grpc.stub.StreamObserver<com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityString> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateProfileMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ClientServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<ClientServiceBlockingStub> {
    private ClientServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ClientServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ClientServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityString registrationClient(com.example.grpc.ClientServiceOuterClass.ProtoUserDto request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegistrationClientMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityUser findByMail(com.example.grpc.ClientServiceOuterClass.ProtoMessageString request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFindByMailMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityAllUsers findAllUserDto(com.example.grpc.ClientServiceOuterClass.ProtoSearchParam request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getFindAllUserDtoMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityString updateProfile(com.example.grpc.ClientServiceOuterClass.ProtoUserDto request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateProfileMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ClientServiceFutureStub extends io.grpc.stub.AbstractFutureStub<ClientServiceFutureStub> {
    private ClientServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ClientServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ClientServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityString> registrationClient(
        com.example.grpc.ClientServiceOuterClass.ProtoUserDto request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegistrationClientMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityUser> findByMail(
        com.example.grpc.ClientServiceOuterClass.ProtoMessageString request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFindByMailMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityAllUsers> findAllUserDto(
        com.example.grpc.ClientServiceOuterClass.ProtoSearchParam request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getFindAllUserDtoMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityString> updateProfile(
        com.example.grpc.ClientServiceOuterClass.ProtoUserDto request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateProfileMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REGISTRATION_CLIENT = 0;
  private static final int METHODID_FIND_BY_MAIL = 1;
  private static final int METHODID_FIND_ALL_USER_DTO = 2;
  private static final int METHODID_UPDATE_PROFILE = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ClientServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ClientServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REGISTRATION_CLIENT:
          serviceImpl.registrationClient((com.example.grpc.ClientServiceOuterClass.ProtoUserDto) request,
              (io.grpc.stub.StreamObserver<com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityString>) responseObserver);
          break;
        case METHODID_FIND_BY_MAIL:
          serviceImpl.findByMail((com.example.grpc.ClientServiceOuterClass.ProtoMessageString) request,
              (io.grpc.stub.StreamObserver<com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityUser>) responseObserver);
          break;
        case METHODID_FIND_ALL_USER_DTO:
          serviceImpl.findAllUserDto((com.example.grpc.ClientServiceOuterClass.ProtoSearchParam) request,
              (io.grpc.stub.StreamObserver<com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityAllUsers>) responseObserver);
          break;
        case METHODID_UPDATE_PROFILE:
          serviceImpl.updateProfile((com.example.grpc.ClientServiceOuterClass.ProtoUserDto) request,
              (io.grpc.stub.StreamObserver<com.example.grpc.ClientServiceOuterClass.ProtoResponseEntityString>) responseObserver);
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

  private static abstract class ClientServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ClientServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.example.grpc.ClientServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ClientService");
    }
  }

  private static final class ClientServiceFileDescriptorSupplier
      extends ClientServiceBaseDescriptorSupplier {
    ClientServiceFileDescriptorSupplier() {}
  }

  private static final class ClientServiceMethodDescriptorSupplier
      extends ClientServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ClientServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (ClientServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ClientServiceFileDescriptorSupplier())
              .addMethod(getRegistrationClientMethod())
              .addMethod(getFindByMailMethod())
              .addMethod(getFindAllUserDtoMethod())
              .addMethod(getUpdateProfileMethod())
              .build();
        }
      }
    }
    return result;
  }
}
