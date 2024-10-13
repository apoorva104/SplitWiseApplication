package com.Splitwise.security;


import com.Splitwise.exception.ExceptionMsg;
import com.Splitwise.exception.SWException;
import com.Splitwise.services.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JWTHelper jwtUtil;

    // Add endpoints to exclude from token validation
    private final List<String> excludedUrls = List.of("/user/signup", "/user/login");

    public JwtAuthenticationFilter(JWTHelper jwtUtil) {
        this.jwtUtil = jwtUtil; // Initialize JWT utility
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestPath = request.getRequestURI();
        // Check if the request is for an excluded URL
        if (excludedUrls.stream().anyMatch(requestPath::contains)) {
            chain.doFilter(request, response); // Skip JWT validation for these URLs
            return;
        }
        final String authorizationHeader = request.getHeader("Authorization");


        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                username = jwtUtil.extractUsername(jwt);
            } catch (Exception e) {

                throw new SWException(ExceptionMsg.INVALID_TOKEN_CODE,ExceptionMsg.INVALID_TOKEN_MESSAGE);
            }

        }
        else {
            // No token present in header
            throw new SWException(ExceptionMsg.JWT_TOKEN_NOT_FOUND_CODE,ExceptionMsg.JWT_TOKEN_NOT_FOUND_MESSAGE);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
            else {
                throw new RuntimeException("Invalid JWT Token");
            }
        }
        chain.doFilter(request, response);
    }

}
