package org.drm.util;

public class Authentication 
{
	String userName;
	String accessToken ;
	String status ;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Authentication(String userName, String accessToken, String status) {
		super();
		this.userName = userName;
		this.accessToken = accessToken;
		this.status = status;
	}
	
	
}
