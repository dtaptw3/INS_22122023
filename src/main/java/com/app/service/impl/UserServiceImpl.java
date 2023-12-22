package com.app.service.impl;

import java.util.List;

import javax.enterprise.context.RequestScoped;

import org.apache.log4j.Logger;

import com.app.model.orm.dbo.wrapper.UserDBO;
import com.app.service.UserService;
import com.app.utilities.QueryBuilder;

@RequestScoped
public class UserServiceImpl implements UserService {

	volatile Logger log = Logger.getLogger(UserServiceImpl.class);

	@Override
	public List<UserDBO> fetchAllUsers() {

		try {
			return (List<UserDBO>) new QueryBuilder().select("*", "Users").executeFor(UserDBO.class);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		return null;
	}

	@Override
	public UserDBO selectByUser(UserDBO currentUser) {
		try {

			List<UserDBO> userlist = new QueryBuilder().select(currentUser).where("username", currentUser.getUsername())
					.and("password", currentUser.getPassword()).executeFor(UserDBO.class);

			if (userlist != null && userlist.size() > 0) {
				return userlist.get(0);
			}

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
		
		return null;
	}

}
