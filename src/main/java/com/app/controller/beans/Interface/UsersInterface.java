package com.app.controller.beans.Interface;



import java.io.Serializable;

import com.app.model.orm.dbo.wrapper.UserDBO;

public interface UsersInterface extends Serializable {

	public void setUsers(UserDBO user);

}
 