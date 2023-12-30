package com.ExamHub.ExamHub.config;

import com.ExamHub.ExamHub.utils.helpers.JwtUtil;
import com.ExamHub.service.examService.CustomUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            String requestTokenHeader = request.getHeader("Authorization");
            String username = null;
            String jwtToken = null;

            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {

                jwtToken = requestTokenHeader.substring(7);

                if (this.jwtUtil.isTokenExpired(jwtToken)) {
                    System.out.println("Token is Expired");
                }

                username = this.jwtUtil.extractUsername(jwtToken);
                UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                } else {
                    System.out.println("Token is not validated");
                }
            }

            filterChain.doFilter(request, httpServletResponse);

        } catch (ExpiredJwtException e) {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> data = new HashMap<>();
            data.put("timestamp", new Date());
            data.put("message", e.getMessage());
            ((HttpServletResponse) httpServletResponse)
                    .getOutputStream().println(objectMapper.writeValueAsString(data));
            ((HttpServletResponse) httpServletResponse).setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
        }
    }
}
