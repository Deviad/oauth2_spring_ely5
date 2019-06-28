package com.simplicity.resourceserver.api.v1.mapper;

import com.simplicity.resourceserver.api.v1.model.UserDTO;
import com.simplicity.resourceserver.api.v1.model.UserWithInfoDTO;
import com.simplicity.resourceserver.persistence.domain.User;
import com.simplicity.resourceserver.persistence.domain.UserInfo;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-06-28T23:42:36+0300",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 11 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO userToUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setUsername( user.getUsername() );
        userDTO.setPassword( user.getPassword() );

        return userDTO;
    }

    @Override
    public UserWithInfoDTO userToUserWithinfoDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserWithInfoDTO userWithInfoDTO = new UserWithInfoDTO();

        userWithInfoDTO.setName( userUserInfoName( user ) );
        userWithInfoDTO.setTelephone( userUserInfoTelephone( user ) );
        userWithInfoDTO.setSurname( userUserInfoSurname( user ) );
        userWithInfoDTO.setUsername( user.getUsername() );
        userWithInfoDTO.setPassword( user.getPassword() );

        return userWithInfoDTO;
    }

    @Override
    public User userWithInfoDTOtoUser(UserWithInfoDTO userWithInfoDTO) {
        if ( userWithInfoDTO == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( userWithInfoDTO.getUsername() );
        user.setPassword( userWithInfoDTO.getPassword() );
        user.setUserInfo( userWithInfoDTOToUserInfo( userWithInfoDTO ) );

        return user;
    }

    @Override
    public UserInfo userWithInfoDTOtoUserinfo(UserWithInfoDTO userWithInfoDTO) {
        if ( userWithInfoDTO == null ) {
            return null;
        }

        UserInfo userInfo = new UserInfo();

        userInfo.setName( userWithInfoDTO.getName() );
        userInfo.setTelephone( userWithInfoDTO.getTelephone() );
        userInfo.setSurname( userWithInfoDTO.getSurname() );

        return userInfo;
    }

    private String userUserInfoName(User user) {
        if ( user == null ) {
            return null;
        }
        UserInfo userInfo = user.getUserInfo();
        if ( userInfo == null ) {
            return null;
        }
        String name = userInfo.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String userUserInfoTelephone(User user) {
        if ( user == null ) {
            return null;
        }
        UserInfo userInfo = user.getUserInfo();
        if ( userInfo == null ) {
            return null;
        }
        String telephone = userInfo.getTelephone();
        if ( telephone == null ) {
            return null;
        }
        return telephone;
    }

    private String userUserInfoSurname(User user) {
        if ( user == null ) {
            return null;
        }
        UserInfo userInfo = user.getUserInfo();
        if ( userInfo == null ) {
            return null;
        }
        String surname = userInfo.getSurname();
        if ( surname == null ) {
            return null;
        }
        return surname;
    }

    protected UserInfo userWithInfoDTOToUserInfo(UserWithInfoDTO userWithInfoDTO) {
        if ( userWithInfoDTO == null ) {
            return null;
        }

        UserInfo userInfo = new UserInfo();

        return userInfo;
    }
}
