package com.simplicity.resourceserver.controllers.v1;

import com.simplicity.resourceserver.api.v1.model.UserWithInfoDTO;
import com.simplicity.resourceserver.configs.CustomOauth2Request;
import com.simplicity.resourceserver.persistence.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @GetMapping(value = "/userinfo", produces = "application/json")
    @PreAuthorize("hasRole('USER')")
    public Map<String, Object> user(OAuth2Authentication user) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put(
                "user",
                user.getUserAuthentication()
                        .getPrincipal());
        userInfo.put("roles", ((CustomOauth2Request) user.getOAuth2Request()).getRoles());
        userInfo.put("authorities", AuthorityUtils.authorityListToSet(user.getUserAuthentication()
                .getAuthorities()));
        return userInfo;


    }
//
//    @GetMapping(value = "/me")
//    @PreAuthorize("hasRole('ROLE_USER')")
//    public ResponseEntity<Principal> get(final Principal principal) {
//        return ResponseEntity.ok(principal);
//    }


//
//        final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) user.getDetails();
//        final OAuth2AccessToken accessToken = tokenStore.readAccessToken(details.getTokenValue());
//        System.out.println(accessToken);
//        return accessToken.getAdditionalInformation();
}
