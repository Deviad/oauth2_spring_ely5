package com.example.springdemo.persistence.services;
import com.example.springdemo.api.v1.model.UserDTO;
import com.example.springdemo.api.v1.model.UserWithInfoDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserWithInfoDTO> getAllUsers(Optional<Integer> offset, Optional<Integer> limit);
    UserWithInfoDTO getUserById(Long id);
    UserWithInfoDTO getUserByUsername(String username);
    UserWithInfoDTO createNewUser(UserWithInfoDTO userWithInfoDTO);
    UserDTO saveUserByDTO(Long id, UserDTO userDTO);
    UserDTO patchUser(Long id, UserDTO userDTO);
    void deleteCustomerById(Long id);
}
