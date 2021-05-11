package com.website.persocoach.security.jwt;

import com.website.persocoach.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class JwtFilterRequest extends OncePerRequestFilter {
    @Autowired private JwtUtils jwtUtils;
    @Autowired private UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
//        String authorizationHeader = request.getHeader("Authorization");
//        String username = null;
//        String password = null;
//        String jwtToken = null;
//
//        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
//            jwtToken = authorizationHeader.substring(7);
//            username = jwtUtils.extractUsername(jwtToken);
//        }
//
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//            UserDetails currentUserDetails = this.userDetailsServiceImpl.loadUserByUsername(username);
//
//            if (jwtUtils.validateToken(jwtToken, currentUserDetails)) {
//
//                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
//                        new UsernamePasswordAuthenticationToken(
//                        currentUserDetails, null, currentUserDetails.getAuthorities());
//                usernamePasswordAuthenticationToken
//                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//            }
//        }

        try{
            String jwt = parseJwt(request);
            if(jwt != null && jwtUtils.validateToken(jwt)){
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }catch (Exception e){
            System.out.println("error doInternal");
        }
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request){
        String headerAuth = request.getHeader("Authorization");

        if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")){
            return headerAuth.substring(7, headerAuth.length());
        }
        return null;
    }
}
