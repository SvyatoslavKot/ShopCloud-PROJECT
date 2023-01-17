package com.example.shopclient_module.app.grpc;

import com.example.grpc.ClientServiceOuterClass;
import com.example.shopclient_module.app.domain.Role;
import com.example.shopclient_module.app.dto.UserDto;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GRpcClientMapper {

    public ClientServiceOuterClass.ProtoUserDto toProtoUserDto (UserDto userDto) {
        ClientServiceOuterClass.ProtoUserDto protoUserDto = ClientServiceOuterClass.ProtoUserDto.newBuilder()
                        .setFirstName(userDto.getFirstName())
                        .setLastName( userDto.getLastName() != null ? userDto.getLastName() : " ")
                        .setMail(userDto.getMail())
                        .setRole(userDto.getRole().name())
                        .setAddress(userDto.getAddress() != null ? userDto.getAddress() : " ")
                        .setBucketId(userDto.getBucketId() != null ? userDto.getBucketId() : 0)
                        .build();
        return protoUserDto;
    }

    public UserDto fromProto (ClientServiceOuterClass.ProtoUserDto protoUser) {
        UserDto userDto = new UserDto();
        userDto.setFirstName(protoUser.getFirstName());
        userDto.setLastName(protoUser.getLastName());
        userDto.setMail(protoUser.getMail());
        userDto.setPassword(protoUser.getPassword());
        userDto.setAddress(protoUser.getAddress());
        userDto.setRole(Role.valueOf(protoUser.getRole().toUpperCase(Locale.ROOT)));
        userDto.setBucketId(protoUser.getBucketId());
        userDto.setMatchingPassword(protoUser.getMatchingPassword());
        return  userDto;
    }

    public ClientServiceOuterClass.ProtoListOfUser toProtoUserList (List<UserDto> userList) {
        List<ClientServiceOuterClass.ProtoUserDto> protoUserList = new ArrayList<>();
        if (!userList.isEmpty()){
            for (UserDto userDto : userList) {
                protoUserList.add(toProtoUserDto(userDto));
            }
        }
        return ClientServiceOuterClass.ProtoListOfUser.newBuilder()
                .addAllUsers(protoUserList)
                .build();
    }


    public ClientServiceOuterClass.ProtoHttpStatus protoStatusOK () {
        return ClientServiceOuterClass.ProtoHttpStatus.newBuilder()
                .setCode(String.valueOf(HttpStatus.OK.value()))
                .setValue(String.valueOf(HttpStatus.OK.series()))
                .setPhrase(HttpStatus.OK.getReasonPhrase())
                .build();
    }

    public ClientServiceOuterClass.ProtoHttpStatus protoStatusBAD_REQUEST (Exception e) {
        return ClientServiceOuterClass.ProtoHttpStatus.newBuilder()
                .setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .setValue(String.valueOf(e.getMessage()))
                .setPhrase(HttpStatus.BAD_GATEWAY.getReasonPhrase())
                .build();
    }
}
