package com.substring.auth.dtos;

import java.util.UUID;

public class RoleDto {
	private UUID id;
	private String name;
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public RoleDto(UUID id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	
	
}
