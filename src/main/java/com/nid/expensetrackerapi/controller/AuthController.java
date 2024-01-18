package com.nid.expensetrackerapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*	;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.nid.expensetrackerapi.Service.UserService;
import com.nid.expensetrackerapi.entity.AuthModel;
import com.nid.expensetrackerapi.entity.JwtResponse;
import com.nid.expensetrackerapi.entity.User;
import com.nid.expensetrackerapi.entity.UserModel;
import com.nid.expensetrackerapi.security.CustomUserDetailsService;
import com.nid.expensetrackerapi.util.JwtTokenUtil;

import javax.validation.Valid;

@RestController
public class AuthController {
	
	@Autowired
	private UserService userservice;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private CustomUserDetailsService customuserdetails;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody AuthModel model) throws Exception
	{
		authenticate(model.getEmail(),model.getPassword());
		//generate JWT token
		final UserDetails userDetails = customuserdetails.loadUserByUsername(model.getEmail());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return new ResponseEntity<JwtResponse>(new JwtResponse(token), HttpStatus.OK);
		
	}
	private void authenticate(String email, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
		}catch(DisabledException e) {
			throw new Exception("User disabled");
		}catch(BadCredentialsException b) {
			throw new Exception("Bad Credentials");
		}
		
	}
	@PostMapping("/register")
	public ResponseEntity<User> save(@Valid @RequestBody UserModel user)
	{
		return new ResponseEntity<User> (userservice.createUser(user),HttpStatus.CREATED);
	}

}
