package com.blogApp.blog.Security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

    private  final UserDetailsService userDetailsService;

    private final JwtTokenHelper jwtTokenHelper;

    @Autowired
    public JwtAuthenticationFilter(UserDetailsService userDetailsService,
                                   JwtTokenHelper jwtTokenHelper) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenHelper = jwtTokenHelper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // public url
        String requestURI = request.getRequestURI();
        if ("/api/auth/**".equals(requestURI) || "/api/users/new/**".equals(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        //get token
        String requestHeader = request.getHeader("Authorization");

        //Authorization = Bearer djnwodnovieo

        logger.info("Header : {}",requestHeader);

        String email = null;
        String token = null;

        if(requestHeader!=null && requestHeader.startsWith("Bearer ")){
            // looks good
            token = requestHeader.substring(7);

            try {
                email = jwtTokenHelper.getUsernameFromToken(token);
            }
            catch (IllegalArgumentException e){
                logger.info("Illegal Argument while fetching username !");
                e.printStackTrace();
            }
            catch (ExpiredJwtException e){
                logger.info("Given jwt Token has expired");
                e.printStackTrace();
            }
            catch (MalformedJwtException e){
                logger.info("Invalid Jwt token");
                e.printStackTrace();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else {
            logger.info("Invalid Header Value !");
        }

        // now validate token
        if(email!=null && SecurityContextHolder.getContext().getAuthentication()==null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            Boolean validateToken = jwtTokenHelper.validateToken(token,userDetails);

            if(Boolean.TRUE.equals(validateToken)){
                // everything is alright and we have to authenticate
                UsernamePasswordAuthenticationToken authenticationToken = new
                        UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            else {
                logger.info("Validation for Jwt token has failed !");
            }
        }
        else {
            logger.info("Username is null or context is not null");
        }

        filterChain.doFilter(request,response);

    }
}
