package com.ExamHub.ExamHub.utils.helpers;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityUtils {



    public static Optional<String> getCurrentLoginUser(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrinciple(securityContext.getAuthentication()));
    }

    private static  String extractPrinciple(Authentication authentication){


        if(authentication == null) return null;
        if(authentication.getPrincipal() instanceof UserDetails)
        {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        if(authentication.getPrincipal() instanceof String) return  (String) authentication.getPrincipal();
        return null;
    }

}
