package org.drm.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="Authentication_token")
@Entity
public class Authentication 
{
	@Id
	@GeneratedValue
	private long id; 
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	String userName;
	String accessToken ;
	String status ;
	
	public Authentication() {}
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
