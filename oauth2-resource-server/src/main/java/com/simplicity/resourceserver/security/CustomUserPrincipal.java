package com.simplicity.authserver.security;

import com.simplicity.authserver.persistence.domain.Privilege;
import com.simplicity.authserver.persistence.domain.Role;
import com.simplicity.authserver.persistence.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class CustomUserPrincipal implements UserDetails {

    private static final long serialVersionUID = 1L;

    private final User user;

    public CustomUserPrincipal(User user) {
        this.user = user;
    }


    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    private List<String> getPrivileges(Collection<Role> roles) {

        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final List<GrantedAuthority> authorities = new ArrayList<>();
        final Collection<Role> roles = user.getRoles();
        for (String privilege : getPrivileges(roles)) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }


    public Set<String> getRoles() {
        Set<String> roleNames = new LinkedHashSet<>();

        final Set<Role> roles = new LinkedHashSet<>(user.getRoles());
        for (Role role : roles) {
            roleNames.add(role.getName());
        }
        return roleNames;
    }



//    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (String privilege : privileges) {
//            authorities.add(new SimpleGrantedAuthority(privilege));
//        }
//        return authorities;
//    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User getUser() {
        return user;
    }

}
