package com.nid.expensetrackerapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nid.expensetrackerapi.Service.UserService;
import com.nid.expensetrackerapi.entity.User;
import com.nid.expensetrackerapi.entity.UserModel;

import javax.validation.Valid;

@RestController
public class UserConroller {
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/profile")
	public ResponseEntity<User> readUser()
	{
		return new ResponseEntity<User> (userService.readUser(),HttpStatus.OK);
	}
	@PutMapping("profile")
	public ResponseEntity<User> updateUser(@RequestBody UserModel user)
	{
		return new ResponseEntity<User> (userService.updateUser(user),HttpStatus.OK);
	}
	
	@DeleteMapping("deactivate")
	public ResponseEntity<HttpStatus> deleteUser()
	{
		userService.deleteUser();
		return new ResponseEntity<HttpStatus> (HttpStatus.NO_CONTENT);
	}
	

}
