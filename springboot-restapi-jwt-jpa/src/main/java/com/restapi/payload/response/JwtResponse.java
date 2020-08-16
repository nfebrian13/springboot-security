package com.restapi.payload.response;

import java.util.List;

public class JwtResponse {

	private long id;
	private String username;
	private String email;
	private List<String> roles;
	private String accessToken;
	private String tokenType;

	public JwtResponse(long id, String username, String email, List<String> roles, String accessToken) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
		this.accessToken = accessToken;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

}
