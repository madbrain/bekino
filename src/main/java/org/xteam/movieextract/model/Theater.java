package org.xteam.movieextract.model;

import org.springframework.data.annotation.Id;

public class Theater {

	@Id
	private String id;
	
	private String name;
	
	private String allocineCode;

	public Theater(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAllocineCode() {
		return allocineCode;
	}

	public void setAllocineCode(String allocineCode) {
		this.allocineCode = allocineCode;
	}
	
}
