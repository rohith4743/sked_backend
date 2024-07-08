package com.rohithkankipati.projects.Sked.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.rohithkankipati.projects.Sked.util.CustomUserDetailsService;
import com.rohithkankipati.projects.Sked.util.JwtTokenUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String header = request.getHeader("Authorization");
	    if (header != null && header.startsWith("Bearer ")) {
	          String jwt = header.substring(7);
	          String userId = request.getHeader("X-User-ID");
	          
	          if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	              UserDetails userDetails = userDetailsService.loadUserById(userId);
	              
	              if (jwtTokenUtil.validateToken(jwt, userDetails.getUsername())) {
	                  UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
	                          userDetails, null, userDetails.getAuthorities());
	                  SecurityContextHolder.getContext().setAuthentication(authentication);
	              }
	          }
	      }
	      filterChain.doFilter(request, response);

	}

}
