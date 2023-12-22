package com.app.model.orm.dbo.wrapper;

import java.util.List;

public class NetworkProviderDBO {
	// i.e TPA(third party admin)
	@TSNO
	private Integer id;
	private String name;
	private String status = "A";
	
	
	List<AreaOfCoverDBO> areaofcover;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
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


	public List<AreaOfCoverDBO> getAreaofcover() {
		return areaofcover;
	}


	public void setAreaofcover(List<AreaOfCoverDBO> areaofcover) {
		this.areaofcover = areaofcover;
	}

}
