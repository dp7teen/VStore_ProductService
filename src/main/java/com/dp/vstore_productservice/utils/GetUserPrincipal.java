package com.dp.vstore_productservice.utils;

import com.dp.vstore_productservice.security.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class GetUserPrincipal {
    public static UserPrincipal getUserPrincipal() {
        return (UserPrincipal)  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
