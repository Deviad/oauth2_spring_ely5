package com.example.springdemo.controllers.v1;

import com.example.springdemo.api.v1.model.UserWithInfoDTO;
import com.example.springdemo.persistence.services.UserService;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping(UserController.BASE_URL)
public class UserController {
    public static final String BASE_URL = "/api/v1/users";
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserWithInfoDTO createNewUser(@RequestBody UserWithInfoDTO userWithInfoDTO) {
        return userService.createNewUser(userWithInfoDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserWithInfoDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/username/{username}")
    @ResponseStatus(HttpStatus.OK)
    public UserWithInfoDTO getUserById(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserWithInfoDTO> getAllUsers(@RequestParam Optional<Integer> offset, @RequestParam Optional<Integer> limit) {
        return userService.getAllUsers(offset, limit);
    }

    @GetMapping(value = "/me", produces = "application/json")
    public Map<String, Object> user(OAuth2Authentication user) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put(
                "user",
                user.getUserAuthentication()
                        .getPrincipal());
        userInfo.put("authorities", AuthorityUtils.authorityListToSet( user.getUserAuthentication()
                .getAuthorities())); return userInfo;
    }
}
