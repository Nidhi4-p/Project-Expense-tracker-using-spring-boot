package com.nid.expensetrackerapi.Service;

import com.nid.expensetrackerapi.entity.User;
import com.nid.expensetrackerapi.entity.UserModel;

public interface UserService {
	
	User createUser(UserModel user);
	
	User readUser();
	
	User updateUser(UserModel user);
	
	void deleteUser();
	
	User getLoggedInUser();

}
