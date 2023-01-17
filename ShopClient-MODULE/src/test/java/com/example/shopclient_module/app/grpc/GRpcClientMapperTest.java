package com.example.shopclient_module.app.grpc;

import com.example.grpc.ClientServiceOuterClass;
import com.example.shopclient_module.app.domain.Role;
import com.example.shopclient_module.app.domain.Status;
import com.example.shopclient_module.app.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.SpringBootDependencyInjectionTestExecutionListener;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GRpcClientMapperTest {

    private GRpcClientMapper mapper = new GRpcClientMapper();

    private UserDto userDto = new UserDto("testName",
            "testLastName",
            "testMail",
            "pass",
            "pass",
            Status.ACTIVE,
            Role.USER,
            "testAddress",
            1l );
    private UserDto userDtoWithNull = new UserDto("testName",
            null,
            "testMail",
            null,
            null,
            null,
            Role.USER,
            null,
            null );
    private ClientServiceOuterClass.ProtoUserDto protoUserDto = ClientServiceOuterClass.ProtoUserDto.newBuilder()
            .setFirstName("testProtoName")
            .setLastName("testProtoLastName")
            .setMail("testProtoMail")
            .setRole(Role.USER.name())
            .setAddress("testProtoAddress")
            .setBucketId(2l)
            .build();

    private List<UserDto> userDtoList = new ArrayList<>(List.of(
            userDto, userDtoWithNull
    ));

    @Test
    void toProtoUserDto() {
        ClientServiceOuterClass.ProtoUserDto testProtoUserDto = mapper.toProtoUserDto(userDto);
        assertNotNull(testProtoUserDto);
        assertEquals(testProtoUserDto.getMail(), userDto.getMail());
        assertEquals(testProtoUserDto.getFirstName(), userDto.getFirstName());
        assertEquals(testProtoUserDto.getLastName(), userDto.getLastName());
        assertEquals(testProtoUserDto.getAddress(), userDto.getAddress());
        assertEquals(testProtoUserDto.getRole(), userDto.getRole().name());
        assertEquals(testProtoUserDto.getBucketId(), userDto.getBucketId());
    }
    @Test
    void toProtoUserDtoWithNull() {
        ClientServiceOuterClass.ProtoUserDto testProtoUserDto = mapper.toProtoUserDto(userDtoWithNull);
        assertNotNull(testProtoUserDto);
        assertEquals(testProtoUserDto.getMail(), userDtoWithNull.getMail());
        assertEquals(testProtoUserDto.getFirstName(), userDtoWithNull.getFirstName());
        assertEquals(testProtoUserDto.getRole(), userDtoWithNull.getRole().name());
        assertNotEquals(testProtoUserDto.getLastName(), userDtoWithNull.getLastName());
        assertEquals(testProtoUserDto.getLastName(), " ");
        assertNotEquals(testProtoUserDto.getAddress(), userDtoWithNull.getAddress());
        assertEquals(testProtoUserDto.getAddress(), " ");
        assertNotEquals(testProtoUserDto.getBucketId(), userDtoWithNull.getBucketId());
        assertEquals(testProtoUserDto.getBucketId(), 0);
    }

    @Test
    void fromProto() {
        UserDto  testUserDto = mapper.fromProto(protoUserDto);
        assertNotNull(testUserDto);
        assertEquals(testUserDto.getMail(), protoUserDto.getMail());
        assertEquals(testUserDto.getFirstName(), protoUserDto.getFirstName());
        assertEquals(testUserDto.getLastName(), protoUserDto.getLastName());
        assertEquals(testUserDto.getAddress(), protoUserDto.getAddress());
        assertEquals(testUserDto.getRole().name(), protoUserDto.getRole());
        assertEquals(testUserDto.getBucketId(), protoUserDto.getBucketId());
    }

    @Test
    void toProtoUserList() {
        ClientServiceOuterClass.ProtoListOfUser protoUserList = mapper.toProtoUserList(userDtoList);
        ClientServiceOuterClass.ProtoUserDto proto = protoUserList.getUsersList().stream()
                .filter(protoUserDto1 -> protoUserDto1.getMail().equals(userDto.getMail())).findFirst().get();

        assertNotNull(protoUserList);
        assertEquals(protoUserList.getUsersList().size(), 2);
        assertNotNull(proto);
        assertEquals(proto.getFirstName(), userDto.getFirstName());
    }

    @Test
    void protoStatusOK() {
        ClientServiceOuterClass.ProtoHttpStatus protoHttpStatusOK =  mapper.protoStatusOK();

        assertEquals(protoHttpStatusOK.getCode(), String.valueOf(HttpStatus.OK.value()));
        assertEquals(protoHttpStatusOK.getPhrase(), HttpStatus.OK.getReasonPhrase().toString());
        assertEquals(protoHttpStatusOK.getValue(), String.valueOf(HttpStatus.OK.series()));
    }

    @Test
    void protoStatusBAD_REQUEST() {
        ClientServiceOuterClass.ProtoHttpStatus statusBad = mapper.protoStatusBAD_REQUEST(new RuntimeException("testException"));

        assertNotNull(statusBad);
        assertEquals(statusBad.getCode(), String.valueOf(HttpStatus.BAD_REQUEST.value()));
        assertEquals(statusBad.getPhrase(), HttpStatus.BAD_GATEWAY.getReasonPhrase());
        assertEquals(statusBad.getValue(), "testException");
    }
}