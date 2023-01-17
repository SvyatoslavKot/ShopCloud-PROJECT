package com.example.shopclient_module.app.grpc;

import com.example.grpc.ClientServiceGrpc;
import com.example.grpc.ClientServiceOuterClass;
import com.example.shopclient_module.app.dto.UserDto;
import com.example.shopclient_module.app.exception.RegistrationException;
import com.example.shopclient_module.app.service.ClientService;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@GRpcService
public class GRpcUserService extends ClientServiceGrpc.ClientServiceImplBase {

    @Autowired
    private ClientService clientService;
    private GRpcClientMapper clientMapper = new GRpcClientMapper();

    @Override
    @Transactional
    public void registrationClient(ClientServiceOuterClass.ProtoUserDto request, StreamObserver<ClientServiceOuterClass.ProtoResponseEntityString> responseObserver) {
        UserDto userDto = clientMapper.fromProto(request);
        ClientServiceOuterClass.ProtoHttpStatus protoHttpStatus = null;
        String msg = null;

                log.info("Request User ->{}", userDto);
        try{
            UserDto registrationClient = clientService.registrationClient(userDto);
            msg = "success";
            protoHttpStatus = clientMapper.protoStatusOK();
        }catch (RegistrationException e){
            msg = e.getMessage();
            protoHttpStatus = clientMapper.protoStatusBAD_REQUEST(e);
        }

        ClientServiceOuterClass.ProtoResponseEntityString protoResponseEntity = ClientServiceOuterClass.ProtoResponseEntityString.newBuilder()
                .setMessage(msg)
                .setHttpStatus(protoHttpStatus)
                .build();

        responseObserver.onNext(protoResponseEntity);
        responseObserver.onCompleted();
    }

    @Override
    @Transactional
    public void findByMail(ClientServiceOuterClass.ProtoMessageString request, StreamObserver<ClientServiceOuterClass.ProtoResponseEntityUser> responseObserver) {
        log.info("gRPC get By Id Start");
        var mail = request.getMessage();
        ClientServiceOuterClass.ProtoUserDto protoUserDto = null;
        ClientServiceOuterClass.ProtoHttpStatus protoHttpStatus = null;

        try{
            UserDto userDto = clientService.findByMail(mail);
            protoUserDto = clientMapper.toProtoUserDto(userDto);
            protoHttpStatus = clientMapper.protoStatusOK();
        }catch (RuntimeException e){
            protoHttpStatus = clientMapper.protoStatusBAD_REQUEST(e);
            protoUserDto = ClientServiceOuterClass.ProtoUserDto.newBuilder().build();

        }
        log.info("protUser -> {}", protoUserDto);
        log.info("status -> {}", protoHttpStatus);

        ClientServiceOuterClass.ProtoResponseEntityUser protoResponseEntity = ClientServiceOuterClass.ProtoResponseEntityUser.newBuilder()
                .setBody(protoUserDto)
                .setHttpStatus(protoHttpStatus)
                .build();
        responseObserver.onNext(protoResponseEntity);
        responseObserver.onCompleted();
    }

    @Override
    @Transactional
    public void findAllUserDto(ClientServiceOuterClass.ProtoSearchParam request, StreamObserver<ClientServiceOuterClass.ProtoResponseEntityAllUsers> responseObserver) {
        var title = request.getTitle();
        ClientServiceOuterClass.ProtoHttpStatus protoHttpStatus = null;
        ClientServiceOuterClass.ProtoListOfUser protoListOfUser = null;
        List<UserDto> userList = null;
        try{
            userList = clientService.findAllClient();
            protoHttpStatus = clientMapper.protoStatusOK();
            protoListOfUser = clientMapper.toProtoUserList(userList);
        }catch (RuntimeException e) {
            protoHttpStatus = clientMapper.protoStatusBAD_REQUEST(e);
            protoListOfUser = ClientServiceOuterClass.ProtoListOfUser.newBuilder().build();
        }

        ClientServiceOuterClass.ProtoResponseEntityAllUsers response = ClientServiceOuterClass.ProtoResponseEntityAllUsers.newBuilder()
                .setHttpStatus(protoHttpStatus)
                .setBody(protoListOfUser)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void updateProfile(ClientServiceOuterClass.ProtoUserDto request, StreamObserver<ClientServiceOuterClass.ProtoResponseEntityString> responseObserver) {
        super.updateProfile(request, responseObserver);
    }
}
