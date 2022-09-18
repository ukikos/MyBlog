package com.vsu.myblog.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static String getCurrentUserUsername() {
        if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
            return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } else {
            throw new SecurityException("Пользователь не авторизован");
        }
    }

}
