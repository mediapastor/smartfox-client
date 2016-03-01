package com.fugu.smartfox_client.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
	
	private final StringProperty username = new SimpleStringProperty(this, "username", null);
	private final StringProperty password = new SimpleStringProperty(this, "password", null);
	
	public User(String username, String password) {
		this.username.set(username);
		this.password.set(password);		
	}
	
	public User() {
		this(null, null);
	}
	
	/* username property */
	public final String getUsername() {
		return username.get();
	}
	
	public final void setUsername(String username) {
		usernameProperty().set(username);
	}
	

	public final StringProperty usernameProperty() {
		return username;
	}
	
	/* password property */
	public final String getPassword() {
		return password.get();
	}
	
	public final void setPassword(String password) {
		passwordProperty().set(password);
	}
	

	public final StringProperty passwordProperty() {
		return password;
	}
	
	/* Domain specific business rules */
	
	
	@Override
	public String toString() {
		return "[username=" + username.get() +
		", password=" + password.get() + "]";
	}
}
