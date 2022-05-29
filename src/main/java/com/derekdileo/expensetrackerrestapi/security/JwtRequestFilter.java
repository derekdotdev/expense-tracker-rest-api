package com.derekdileo.expensetrackerrestapi.security;

import com.derekdileo.expensetrackerrestapi.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtRequestFilter extends OncePerRequestFilter {

    // Runtime injection of JwtTokenUtil and CustomUserDetailsService dependencies
    // Constructor-based was interfering with Bean in WebSecurityConfig on line 34.
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");

        String jwtToken = null;
        String username = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {

            // Remove 'bearer ' from token
            jwtToken = requestTokenHeader.substring(7);

            // Get username from jtwToken
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                throw new RuntimeException("Jwt token has expired");
            }

        }

        // Validate Jwt token
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Get user details
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

            // Validate Jwt Token
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

                // Create auth token
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                // set auth object to security context
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }

        }
        // Continue Spring Security filter chain
        filterChain.doFilter(request, response);

    }
}
