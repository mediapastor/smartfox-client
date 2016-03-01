package com.fugu.smartfox_client.view;

import com.fugu.smartfox_client.model.User;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class LoginView extends GridPane {
	
	private final User model;
	
	// Label
	public Label usernameLbl = new Label("username");
	public Label passwordLbl = new Label("password");
	
	// Text
	public Text title = new Text("SmartFox Client");
	public Text errorMessage = new Text("");
	
	// TextField
	public TextField usernameFld = new TextField();
	public TextField passwordFld = new TextField();
	
	// Button
	public Button loginBtn = new Button("Login");
	
	public LoginView(User user) {
		this.model = user;
		layoutForm();
		bindFieldsToModel();
	}
	
	private void layoutForm() {
		this.setHgap(5);
		this.setVgap(5);
		this.setAlignment(Pos.CENTER);
		
		// add title and input fields
		this.add(title, 0, 0, 2, 1);
		this.add(usernameLbl, 0, 1);
		this.add(usernameFld, 1, 1);
		this.add(passwordLbl, 0, 2);
		this.add(passwordFld, 1, 2);
		
		// add button and set it to max width
		this.add(loginBtn, 0, 3, 2, 1);
		loginBtn.setMaxWidth(Double.MAX_VALUE);
		
		
		// add error message and set it to max width
		this.add(errorMessage, 0, 4, 2, 1);
		errorMessage.maxWidth(Double.MAX_VALUE);
		
		this.getStylesheets().add("css/login.css");
	}
	
	public void bindFieldsToModel() {
        usernameFld.textProperty().bindBidirectional(model.usernameProperty());
        passwordFld.textProperty().bindBidirectional(model.passwordProperty());
	}
	
}
