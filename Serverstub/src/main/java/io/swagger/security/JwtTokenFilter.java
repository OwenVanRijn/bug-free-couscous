package io.swagger.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = jwtTokenProvider.resolveToken(request);

        try {
            if (jwt != null && jwtTokenProvider.validateToken(jwt)) { //check if token is valid
                Authentication auth = jwtTokenProvider.getAuthentication(jwt); //retrieve user
                SecurityContextHolder.getContext().setAuthentication(auth); //apply user to security context of request
            }
        } catch (ResponseStatusException e) {
            SecurityContextHolder.clearContext();   // if token is invalid, clear security context
            //response.sendError(e.getRawStatusCode(), e.getMessage()); //getRawStatusCode() werkt niet
            return;
        }

        filterChain.doFilter(request, response); //move to next filter
    }
}
