package com.rohithkankipati.projects.Sked.util;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.rohithkankipati.projects.Sked.entity.UserEntity;
import com.rohithkankipati.projects.Sked.repository.UserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	UserEntity userEntity = userRepository.findByUserName(username)
		.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

	return buildUserDetails(userEntity);
    }

    public UserDetails loadUserById(String userId) throws UsernameNotFoundException {
	UserEntity userEntity = userRepository.findById(userId)
		.orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userId));

	return buildUserDetails(userEntity);
    }

    private UserDetails buildUserDetails(UserEntity userEntity) {
	UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(userEntity.getUserName());
	builder.password(userEntity.getPassword());

	// Map each UserRole to a SimpleGrantedAuthority
	var authorities = userEntity.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
		.collect(Collectors.toList());

	builder.authorities(authorities);
	return builder.build();
    }

}
