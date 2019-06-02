package com.example.springdemo.api.v1.mapper;

import com.example.springdemo.api.v1.model.UserWithInfoDTO;
import com.example.springdemo.persistence.domain.User;
import com.example.springdemo.api.v1.model.UserDTO;
import com.example.springdemo.persistence.domain.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mappings({
            @Mapping(target= "userUrl", ignore = true),
//            @Mapping(source = "userInfo.name", target = "name"),
//            @Mapping(source = "userInfo.surname", target = "surname"),
//            @Mapping(source = "userInfo.telephone", target = "telephone")
    })
    UserDTO userToUserDTO(User user);

    @Mappings({
            @Mapping(target= "userUrl", ignore = true),
            @Mapping(source = "userInfo.name", target = "name"),
            @Mapping(source = "userInfo.surname", target = "surname"),
            @Mapping(source = "userInfo.telephone", target = "telephone")
    })
    UserWithInfoDTO userToUserWithinfoDTO(User user);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "userInfo.user", ignore = true),
            @Mapping(target = "roles", ignore = true),
    })
    User userWithInfoDTOtoUser(UserWithInfoDTO userWithInfoDTO);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "user", ignore = true),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "surname", target = "surname"),
            @Mapping(source = "telephone", target= "telephone")
    })
    UserInfo userWithInfoDTOtoUserinfo(UserWithInfoDTO userWithInfoDTO);
}
