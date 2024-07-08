package com.rohithkankipati.projects.Sked.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rohithkankipati.projects.Sked.dto.UserDTO;
import com.rohithkankipati.projects.Sked.entity.UserEntity;
import com.rohithkankipati.projects.Sked.exception.SkedException;
import com.rohithkankipati.projects.Sked.model.UserRole;
import com.rohithkankipati.projects.Sked.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UserDTO createAccount(UserDTO userDTO) {
	if (userRepository.existsByUserName(userDTO.getUserName())) {
	    throw new SkedException("create_user.username.exists", HttpStatus.BAD_REQUEST);
	}
	if (userRepository.existsByEmail(userDTO.getEmail())) {
	    throw new SkedException("create_user.email.exists", HttpStatus.BAD_REQUEST);
	}
	if (userRepository.existsByMobileNumber(userDTO.getMobileNumber())) {
	    throw new SkedException("create_user.phone.exists", HttpStatus.BAD_REQUEST);
	}

	if (userDTO.getRoles() == null || userDTO.getRoles().isEmpty()) {
	    userDTO.setRoles(Collections.singleton(UserRole.USER));
	}

	UserEntity userEntity = new UserEntity();
	userEntity.fromUserDTO(userDTO);
	userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));

	userEntity = userRepository.save(userEntity);
	return userDTO.fromEntity(userEntity);
    }

    public UserDTO login(String identifier, String password) {

	Optional<UserEntity> userOptional = userRepository.findByUsernameOrEmailOrPhoneNumber(identifier);

	if (userOptional.isPresent() && passwordEncoder.matches(password, userOptional.get().getPassword())) {
	    return userOptional.get().toUserDTO();
	}
	throw new SkedException("login.failed", HttpStatus.BAD_REQUEST);
    }

    public boolean checkUsernameExists(String username) {
	return userRepository.existsByUserName(username);
    }

    public boolean changePassword(String userId, String oldPassword, String newPassword) {
	return false;
    }

    public boolean forgetPassword(String email) {
	return false;
    }

    public UserDTO getProfile(String userName) {

	Optional<UserEntity> userOptional = userRepository.findByUserName(userName);
	if (userOptional.isPresent()) {
	    UserEntity userEntity = userOptional.get();
	    UserDTO userDTO = new UserDTO();
	    userDTO.setEmail(userEntity.getEmail());
	    userDTO.setFirstName(userEntity.getFirstName());
	    userDTO.setLastName(userEntity.getLastName());
	    userDTO.setMobileNumber(userEntity.getMobileNumber());
	    userDTO.setUserName(userName);
	    return userDTO;
	}
	throw new SkedException("profile.not_found", HttpStatus.BAD_REQUEST);
    }

    public boolean logout(String userId) {
	return false;
    }
}