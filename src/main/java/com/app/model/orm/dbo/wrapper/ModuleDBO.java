package com.app.model.orm.dbo.wrapper;

import java.util.Date;

import javax.persistence.Entity;

@Entity(name = "Modules")
public class ModuleDBO {

	public ModuleDBO() {
	}

	public ModuleDBO(Integer code, String name, String label, Integer parent, String createdby, Date createdon,
			Integer visibility, Integer status) {
		super();
		this.code = code;
		this.name = name;
		this.label = label;
		this.parent = parent;
		this.createdby = createdby;
		this.createdon = createdon;
		this.visibility = visibility;
		this.status = status;
	}

	@PrimaryKey
	private Integer code;
	private String name;
	private String label;
	private Integer parent;
	private String createdby;
	private Date createdon;
	private Integer visibility;
	private Integer status;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Date getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public Integer getVisibility() {
		return visibility;
	}

	public void setVisibility(Integer visibility) {
		this.visibility = visibility;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public Integer getParent() {
		return parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}

}
