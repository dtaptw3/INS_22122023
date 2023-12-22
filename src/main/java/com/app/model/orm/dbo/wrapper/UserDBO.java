package com.app.model.orm.dbo.wrapper;

import javax.persistence.Entity;

abstract class SystemUserAbstract {

	Integer developer = 1;

	public void setRights() {
		System.out.println("inside abstract method of setRights. current stat " + developer);
	}

}

final class SystemUser {

}

@Entity(name = "Users") 
public class UserDBO extends SystemUserAbstract {
	
	@PrimaryKey
	private Integer id;
	private String username;
	private String password;
	private String name;
	private String status;
	private String sessionID;
	private String linkID;
	private byte[] profileimage; 

	private String loginstatus; 
	private String usertype;

	private String email;

	public UserDBO(String name, String loginstatus, String usertype) {
		this.name = name;
		this.loginstatus = loginstatus;
		this.usertype = usertype;
	}

	public UserDBO(Integer id, String username, String password, String name, String status, String sessionID,
			String linkID, byte[] profileimage, String loginstatus, String usertype, String email) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.status = status;
		this.sessionID = sessionID;
		this.linkID = linkID;
		this.profileimage = profileimage;
		this.loginstatus = loginstatus;
		this.usertype = usertype;
		this.email = email;
	}

	public UserDBO() {
	}

	public UserDBO(int id, String username, String password, String name, String status, String sessionID,
			String linkID, byte[] profileimage) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.status = status;
		this.sessionID = sessionID;
		this.profileimage = profileimage;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public byte[] getProfileimage() {
		return profileimage;
	}

	public void setProfileimage(byte[] profileimage) {
		this.profileimage = profileimage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLinkID() {
		return linkID;
	}

	public void setLinkID(String linkID) {
		this.linkID = linkID;
	}

	public String getLoginstatus() {
		return loginstatus;
	}

	public void setLoginstatus(String loginstatus) {
		this.loginstatus = loginstatus;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
