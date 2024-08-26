package com.exalt.training.springsecurity.config;
import com.exalt.training.springsecurity.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import com.exalt.training.springsecurity.service.UserService;

/**
 * Custom filter for processing JWT authentication and setting the security context.
 * This filter runs once per request and checks the validity of the JWT token in the request.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JWTService jwtService; // JWTService interface used to create tokens
    private final UserService userService; // UserService interface used to create tokens


    /**
     * Handles the filtering logic for incoming requests by validating the JWT token and setting
     * the authentication in the security context if the token is valid.
     *
     * @param request     The HTTP request.
     * @param response    The HTTP response.
     * @param filterChain The filter chain to pass the request and response further down the chain.
     * @throws ServletException In case of servlet-related errors.
     * @throws IOException      In case of IO-related errors.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader =request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if(!StringUtils.hasLength(authHeader) || !org.apache.commons.lang3.StringUtils.startsWith(authHeader, "Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        jwt=authHeader.substring(7);
        userEmail = jwtService.extractUserName(jwt);
        if(StringUtils.hasLength(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails =userService.userDetailsService().loadUserByUsername(userEmail);

            if(jwtService.isTokenValid(jwt, userDetails)){
                SecurityContext securityContext= SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);
            }
        }
        filterChain.doFilter(request,response);
    }
}
