package com.app.service;

import java.util.List;

import com.app.model.orm.dbo.wrapper.UserDBO;

public interface UserService {

	List<UserDBO> fetchAllUsers();

	UserDBO selectByUser(UserDBO currentUser);
	
}
