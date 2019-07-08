package com.simplicity.resourceserver.security;

import com.simplicity.resourceserver.configs.CustomOauth2Request;
import com.simplicity.resourceserver.configs.CustomTokenConverter;
import com.simplicity.resourceserver.persistence.services.UserService;
import lombok.SneakyThrows;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;

public class CustomSecurityExpressionRoot implements MethodSecurityExpressionOperations {
    protected final Authentication authentication;
    private AuthenticationTrustResolver trustResolver;
    private RoleHierarchy roleHierarchy;
    private Set<String> roles;
    private String defaultRolePrefix = "ROLE_";

    public final boolean permitAll = true;
    public final boolean denyAll = false;
    private PermissionEvaluator permissionEvaluator;
    public final String read = "read";
    public final String write = "write";
    public final String create = "create";
    public final String delete = "delete";
    public final String admin = "administration";

    private Object filterObject;
    private Object returnObject;

    private UserService userService;


    public CustomSecurityExpressionRoot(Authentication authentication) {
        if (authentication == null) {
            throw new IllegalArgumentException("Authentication object cannot be null");
        }
        this.authentication = authentication;
    }

    @Override
    public final boolean hasAuthority(String authority) {
        throw new RuntimeException("method hasAuthority() not allowed");
    }


    @Override
    public final boolean hasAnyAuthority(String... authorities) {
        return hasAnyAuthorityName(null, authorities);
    }

    @Override
    public final boolean hasRole(String role) {
        return hasAnyRole(role);
    }

    @Override
    public final boolean hasAnyRole(String... roles) {
        return hasAnyRoleName(defaultRolePrefix, roles);
    }

    public boolean isOwner(String userName) {
       return userName.equals(this.authentication.getPrincipal());
    }

    public boolean isStateVerified(OAuth2Authentication auth) {
        final String state = this.getState(authentication);
        final String stateFromCustomAuth = ((CustomOauth2Request) auth.getOAuth2Request()).getState();

        return state.equals(stateFromCustomAuth);
    }


    private boolean hasAnyAuthorityName(String prefix, String... roles) {
        final Set<String> roleSet = getAuthoritySet();

        for (final String role : roles) {
            final String defaultedRole = getRoleWithDefaultPrefix(prefix, role);
            if (roleSet.contains(defaultedRole)) {
                return true;
            }
        }

        return false;
    }


    private boolean hasAnyRoleName(String prefix, String... roles) {
        final Set<String> roleSet = getRoleSet();

        for (final String role : roles) {
            final String defaultedRole = getRoleWithDefaultPrefix(prefix, role);
            if (roleSet.contains(defaultedRole)) {
                return true;
            }
        }

        return false;
    }


    @SneakyThrows
    private String getState(Authentication authentication) {
        byte[] hmacData;
        final long unixTime = Instant.now().getEpochSecond();
        long adhocTime = unixTime / 60;

        String rawState = adhocTime + (String) authentication.getPrincipal();
        SecretKeySpec secretKey = new SecretKeySpec(rawState.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKey);
        hmacData = mac.doFinal(rawState.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hmacData);
    }


    @Override
    public final Authentication getAuthentication() {
        return authentication;
    }

    @Override
    public final boolean permitAll() {
        return true;
    }

    @Override
    public final boolean denyAll() {
        return false;
    }

    @Override
    public final boolean isAnonymous() {
        return trustResolver.isAnonymous(authentication);
    }

    @Override
    public final boolean isAuthenticated() {
        return !isAnonymous();
    }

    @Override
    public final boolean isRememberMe() {
        return trustResolver.isRememberMe(authentication);
    }

    @Override
    public final boolean isFullyAuthenticated() {
        return !trustResolver.isAnonymous(authentication) && !trustResolver.isRememberMe(authentication);
    }

    public CustomUserPrincipal getPrincipal() {
        return (CustomUserPrincipal)authentication.getPrincipal();
    }

    public void setTrustResolver(AuthenticationTrustResolver trustResolver) {
        this.trustResolver = trustResolver;
    }

    public void setRoleHierarchy(RoleHierarchy roleHierarchy) {
        this.roleHierarchy = roleHierarchy;
    }

    public void setDefaultRolePrefix(String defaultRolePrefix) {
        this.defaultRolePrefix = defaultRolePrefix;
    }


    private Set<String> getRoleSet() {
        if (roles == null) {
            roles = new HashSet<>();
            roles.addAll(CustomTokenConverter.getOAuth2RequestFromAuthentication().getRoles());
        }
        return roles;
    }

    private Set<String> getAuthoritySet() {
        if (roles == null) {
            roles = new HashSet<>();
            Collection<? extends GrantedAuthority> userAuthorities = authentication.getAuthorities();

            if (roleHierarchy != null) {
                userAuthorities = roleHierarchy.getReachableGrantedAuthorities(userAuthorities);
            }

            roles = AuthorityUtils.authorityListToSet(userAuthorities);
        }

        return roles;
    }

    @Override
    public boolean hasPermission(Object target, Object permission) {
        return permissionEvaluator.hasPermission(authentication, target, permission);
    }

    @Override
    public boolean hasPermission(Object targetId, String targetType, Object permission) {
        return permissionEvaluator.hasPermission(authentication, (Serializable) targetId, targetType, permission);
    }

    public void setPermissionEvaluator(PermissionEvaluator permissionEvaluator) {
        this.permissionEvaluator = permissionEvaluator;
    }

    private static String getRoleWithDefaultPrefix(String defaultRolePrefix, String role) {
        if (role == null) {
            return role;
        }
        if ((defaultRolePrefix == null) || (defaultRolePrefix.length() == 0)) {
            return role;
        }
        if (role.startsWith(defaultRolePrefix)) {
            return role;
        }
        return defaultRolePrefix + role;
    }

    @Override
    public Object getFilterObject() {
        return this.filterObject;
    }

    @Override
    public Object getReturnObject() {
        return this.returnObject;
    }

    @Override
    public Object getThis() {
        return this;
    }

    @Override
    public void setFilterObject(Object obj) {
        this.filterObject = obj;
    }

    @Override
    public void setReturnObject(Object obj) {
        this.returnObject = obj;
    }
}
