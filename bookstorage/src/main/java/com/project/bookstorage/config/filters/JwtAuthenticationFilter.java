package com.project.bookstorage.config.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.project.bookstorage.exceptions.UserDoesNotExistException;
import com.project.bookstorage.models.UserEntity;
import com.project.bookstorage.services.UserService;
import com.project.bookstorage.services.auth.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
                String authorizationHeader = request.getHeader("Authorization");
                if(StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
                    String jwt = authorizationHeader.split(" ")[1];

                    String username = jwtService.extractUsername(jwt);

                    UserEntity user = userService.findOneByUsername(username).orElseThrow(() -> new UserDoesNotExistException());
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        username, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
                filterChain.doFilter(request, response);
    }
    
}
