package com.simplicity.resourceserver.security;

import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    public boolean hasAccess(long parameter) {
        return parameter == 1L;
    }
}
