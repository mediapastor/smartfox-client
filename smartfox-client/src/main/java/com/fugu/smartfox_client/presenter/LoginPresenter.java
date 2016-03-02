package com.fugu.smartfox_client.presenter;

import com.fugu.smartfox_client.Client;
import com.fugu.smartfox_client.Connector;
import com.fugu.smartfox_client.model.User;
import com.fugu.smartfox_client.util.Util;
import com.fugu.smartfox_client.view.LoginView;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sfs2x.client.SmartFox;
import sfs2x.client.requests.LoginRequest;

public class LoginPresenter {

	private final User user;
	private final LoginView view;
	private final SmartFox sfs = Client.getSmartFox();

	private final static String DEFAULT_SERVER_ADDRESS = "192.168.1.153";
	private final static String DEFAULT_SERVER_PORT = "9933";
	
	public LoginPresenter(User user, LoginView view) {
		this.user = user;
		this.view = view;
		attachEvents();
	}
	
	public void attachEvents() {
		view.loginBtn.setOnAction(e -> login(e));
	}
	
	private void login(ActionEvent e) {
		
//		Platform.runLater(() ->sfs.connect(DEFAULT_SERVER_ADDRESS, Integer.parseInt(DEFAULT_SERVER_PORT)));
		sfs.connect(DEFAULT_SERVER_ADDRESS, Integer.parseInt(DEFAULT_SERVER_PORT));
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
