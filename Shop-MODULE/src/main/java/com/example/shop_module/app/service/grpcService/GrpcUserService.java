package com.example.shop_module.app.service.grpcService;

import com.example.grpc.ClientServiceGrpc;
import com.example.grpc.ClientServiceOuterClass;
import com.example.shop_module.app.dto.UserDTO;
import com.example.shop_module.app.exceptions.NoConnectedToGRpsServer;
import com.example.shop_module.app.exceptions.ResponseMessageException;
import com.example.shop_module.app.gRPC.mapper.UserGRpcMapper;
import com.example.shop_module.app.service.abstraction.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class GrpcUserService implements UserService {

    @Autowired
    private ClientServiceGrpc.ClientServiceBlockingStub stub;
    private UserGRpcMapper userMapper = new UserGRpcMapper();

    @Override
    public ResponseEntity registrationClient(UserDTO userDTO) throws ResponseMessageException {
        ClientServiceOuterClass.ProtoUserDto protoUserDto = userMapper.toProtoUserDto(userDTO);
        ClientServiceOuterClass.ProtoResponseEntityString protoResponseEntity = null;
        try{
            protoResponseEntity = stub.registrationClient(protoUserDto);
        }catch (RuntimeException e){
            throw new NoConnectedToGRpsServer();
        }
        log.info(protoResponseEntity.getMessage());

        if (protoResponseEntity.getHttpStatus().getCode().equals(String.valueOf(HttpStatus.OK.value()))){
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(protoResponseEntity.getHttpStatus().getValue(),
                HttpStatus.resolve(Integer.parseInt(protoResponseEntity.getHttpStatus().getCode())));
    }

    @Override
    @Transactional
    public ResponseEntity findByMail(String mail) throws RuntimeException {
        ClientServiceOuterClass.ProtoMessageString request = ClientServiceOuterClass.ProtoMessageString.newBuilder()
                .setMessage(mail)
                .build();
        ClientServiceOuterClass.ProtoResponseEntityUser protoResponseEntity = null;

        try{
            protoResponseEntity = stub.findByMail(request);
        }catch (RuntimeException e){
            throw new NoConnectedToGRpsServer();
        }

        if (protoResponseEntity.getHttpStatus().getCode().equals(protoResponseEntity.getHttpStatus().getCode())) {
            UserDTO user = userMapper.fromProtoUserDto(protoResponseEntity.getBody());
            return new ResponseEntity(user, HttpStatus.OK);
        }
        return new ResponseEntity(protoResponseEntity.getHttpStatus().getValue(),
                HttpStatus.resolve(Integer.parseInt(protoResponseEntity.getHttpStatus().getCode())));
    }

    @Override
    public ResponseEntity findAllUserDto() {
        ClientServiceOuterClass.ProtoSearchParam request = ClientServiceOuterClass.ProtoSearchParam.newBuilder()
                .setTitle("")
                .build();
        ClientServiceOuterClass.ProtoResponseEntityAllUsers protoResponseEntity = null;
        try{
            protoResponseEntity = stub.findAllUserDto(request);
        }catch (RuntimeException e) {
            throw new NoConnectedToGRpsServer();
        }

        if (protoResponseEntity.getHttpStatus().getCode().equals(String.valueOf(HttpStatus.OK.value()))){
            List<UserDTO> userList = userMapper.toDtoList(protoResponseEntity.getBody());
            log.info("userList ->{}", userList);
            return new ResponseEntity(userList,HttpStatus.OK);
        }
        return new ResponseEntity(protoResponseEntity.getHttpStatus().getValue(),
                HttpStatus.resolve(Integer.parseInt(protoResponseEntity.getHttpStatus().getCode())));
    }

    @Override
    public void updateProfile(UserDTO userDTO) {

    }
}
