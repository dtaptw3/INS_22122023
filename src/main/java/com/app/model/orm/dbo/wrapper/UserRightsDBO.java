package com.app.model.orm.dbo.wrapper;



public class UserRightsDBO {
	
	
	private String modcode;
	
	private String modname;

	private String modtype;

	private String modsequence;

	private Integer parent;

	// docrights
	private Integer access = 0;
	private Integer add = 0;
	private Integer modify = 0;
	private Integer delete = 0;
	private Integer search = 0;
	private Integer print = 0;
	private Integer approve = 0;
	private Integer email = 0;
	private Integer export = 0;
	
	public String getModcode() {
		return modcode;
	}
	public void setModcode(String modcode) {
		this.modcode = modcode;
	}
	public String getModname() {
		return modname;
	}
	public void setModname(String modname) {
		this.modname = modname;
	}
	public String getModtype() {
		return modtype;
	}
	public void setModtype(String modtype) {
		this.modtype = modtype;
	}
	public String getModsequence() {
		return modsequence;
	}
	public void setModsequence(String modsequence) {
		this.modsequence = modsequence;
	}
	public Integer getParent() {
		return parent;
	}
	public void setParent(Integer parent) {
		this.parent = parent;
	}
	public Integer getAccess() {
		return access;
	}
	public void setAccess(Integer access) {
		this.access = access;
	}
	public Integer getAdd() {
		return add;
	}
	public void setAdd(Integer add) {
		this.add = add;
	}
	public Integer getModify() {
		return modify;
	}
	public void setModify(Integer modify) {
		this.modify = modify;
	}
	public Integer getDelete() {
		return delete;
	}
	public void setDelete(Integer delete) {
		this.delete = delete;
	}
	public Integer getSearch() {
		return search;
	}
	public void setSearch(Integer search) {
		this.search = search;
	}
	public Integer getPrint() {
		return print;
	}
	public void setPrint(Integer print) {
		this.print = print;
	}
	public Integer getApprove() {
		return approve;
	}
	public void setApprove(Integer approve) {
		this.approve = approve;
	}
	public Integer getEmail() {
		return email;
	}
	public void setEmail(Integer email) {
		this.email = email;
	}
	public Integer getExport() {
		return export;
	}
	public void setExport(Integer export) {
		this.export = export;
	}

	

}
