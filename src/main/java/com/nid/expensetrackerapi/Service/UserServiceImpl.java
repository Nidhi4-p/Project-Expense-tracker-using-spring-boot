package com.nid.expensetrackerapi.Service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nid.expensetrackerapi.entity.User;
import com.nid.expensetrackerapi.entity.UserModel;
import com.nid.expensetrackerapi.exceptions.ItemAlreadyExistsException;
import com.nid.expensetrackerapi.exceptions.ResourceNotFoundException;
import com.nid.expensetrackerapi.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Override
	public User createUser(UserModel user) {
		if(userRepo.existsByEmail(user.getEmail()))
		{
			throw new ItemAlreadyExistsException("Email already exists: "+user.getEmail());
		}
		User newUser = new User(); 
		BeanUtils.copyProperties(user, newUser);
		newUser.setPassword(bcryptEncoder.encode(newUser.getPassword()));
		return userRepo.save(newUser);
	}
	@Override
	public User readUser() {
		Long id = getLoggedInUser().getId();
		return userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found for id : " + id));
	}
	@Override
	public User updateUser(UserModel user) {
		User existingUser = readUser();
		existingUser.setName(user.getName()!=null ? user.getName() : existingUser.getName());
		existingUser.setEmail(user.getEmail()!=null ? user.getEmail() : existingUser.getEmail());
		existingUser.setPassword(user.getPassword()!=null ? bcryptEncoder.encode(user.getPassword()) : existingUser.getPassword());
		existingUser.setAge(user.getAge()!=null ? user.getAge() : existingUser.getAge());
		return userRepo.save(existingUser);
	}
	@Override
	public void deleteUser() {
		
		User existingUser = readUser();
		userRepo.delete(existingUser);
		
	}
	@Override
	public User getLoggedInUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		return userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found for the email : " + email));
	}

}
