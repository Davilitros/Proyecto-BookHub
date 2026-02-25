package com.Bookhub.entity;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Person {

    private String username;

    private String email;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}