package com.fugu.smartfox_client.presenter;

import com.fugu.smartfox_client.Client;
import com.fugu.smartfox_client.SFSController;
import com.fugu.smartfox_client.model.User;
import com.fugu.smartfox_client.util.Util;
import com.fugu.smartfox_client.view.LoginView;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sfs2x.client.SmartFox;

public class LoginPresenter {

	private final User model;
	private final LoginView view;

	private final static String DEFAULT_SERVER_ADDRESS = "192.168.50.5";
	private final static String DEFAULT_SERVER_PORT = "9933";
	
	public LoginPresenter(User model, LoginView view) {
		this.model = model;
		this.view = view;
		attachEvents();
	}
	
	public void attachEvents() {
		view.loginBtn.setOnAction(e -> login());
	}
	
	private void login() {
		SFSController.connect(DEFAULT_SERVER_ADDRESS, Integer.parseInt(DEFAULT_SERVER_PORT));
	}
	
	private void error() {
		view.errorMessage.setText("Wrong username or password");
	}
	
	private void redirectToLobby() {
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/lobby.fxml"));
		
		// go to lobby controller
		try {
			GridPane root = (GridPane) loader.load();
			Stage mainStage = Client.primaryStage;
			mainStage.getScene().setRoot(root); //we dont need to change whole scene, only set new root.
		} catch (Exception e) {
			Util.logException(e);;
		}
		
		System.out.println("redirected to lobby UI......");
	}
}
