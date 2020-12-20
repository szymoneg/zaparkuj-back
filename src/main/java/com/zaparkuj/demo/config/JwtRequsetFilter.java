package com.zaparkuj.demo.config;

import com.zaparkuj.demo.services.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequsetFilter extends OncePerRequestFilter {

    @Autowired
    private UserService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        final String requestTokenHeader = httpServletRequest.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")){
            jwtToken = requestTokenHeader.substring(7);
            try{
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            }catch (IllegalArgumentException e){
                System.out.println("Unable to get JWT token");
            }catch (ExpiredJwtException e){
                System.out.println("JWT token has expired");
            }
        }else {
            logger.warn("JWT token does not begin with Bearer String");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){

            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

            if (jwtTokenUtil.validateToken(jwtToken,userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
