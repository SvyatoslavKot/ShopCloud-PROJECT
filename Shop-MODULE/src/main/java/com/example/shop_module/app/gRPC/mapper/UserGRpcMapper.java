package com.example.shop_module.app.gRPC.mapper;

import com.example.grpc.ClientServiceOuterClass;
import com.example.shop_module.app.dto.UserDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


public class UserGRpcMapper {

    public UserDTO fromProtoUserDto(ClientServiceOuterClass.ProtoUserDto protoUserDto){
        UserDTO user = new UserDTO();
        user.setFirstName(protoUserDto.getFirstName());
        user.setLastName(protoUserDto.getLastName());
        user.setMail(protoUserDto.getMail());
        user.setAddress(protoUserDto.getAddress());
        return user;
    }

    public ClientServiceOuterClass.ProtoUserDto toProtoUserDto(UserDTO userDTO) {
        return ClientServiceOuterClass.ProtoUserDto.newBuilder()
                .setFirstName(userDTO.getFirstName() != null ? userDTO.getFirstName() : "")
                .setLastName(userDTO.getLastName() != null ? userDTO.getLastName() : "")
                .setMail(userDTO.getMail() != null ? userDTO.getMail() : "")
                .setPassword(userDTO.getPassword() != null ? userDTO.getPassword() : "")
                .setMatchingPassword(userDTO.getMatchingPassword() != null ? userDTO.getMatchingPassword() : "")
                .build();
    }

    public List<UserDTO> toDtoList (ClientServiceOuterClass.ProtoListOfUser protoListOfUser) {
        List<UserDTO> userDTOList = new ArrayList<>();
        if (!protoListOfUser.getUsersList().isEmpty()){
            for (ClientServiceOuterClass.ProtoUserDto protoUserDto : protoListOfUser.getUsersList()){
                userDTOList.add(fromProtoUserDto(protoUserDto));
            }
        }
        return userDTOList;
    }
}
