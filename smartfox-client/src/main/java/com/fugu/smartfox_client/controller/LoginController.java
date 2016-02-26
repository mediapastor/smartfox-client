package com.fugu.smartfox_client.controller;

import java.io.IOException;

import com.fugu.smartfox_client.Client;
import com.fugu.smartfox_client.SFSController;
import com.fugu.smartfox_client.Util.Util;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sfs2x.client.SmartFox;
import sfs2x.client.core.BaseEvent;
import sfs2x.client.core.IEventListener;
import sfs2x.client.core.SFSEvent;
import sfs2x.client.requests.LoginRequest;

public class LoginController implements IEventListener {
	
	private SmartFox sfsClient;
	private final static String DEFAULT_SERVER_ADDRESS = "192.168.50.5";
	private final static String DEFAULT_SERVER_PORT = "9933";
	
	private String username;
	private String password;
	
	@FXML private TextField usernameField;
	@FXML private PasswordField passwordField;
	@FXML private Text actionTarget;
	
	/**
	 * initialize smartfox 
	 * 
	 */
	public LoginController() {		
		initSmartFox();
	}
	
	public void initSmartFox() {
		sfsClient = SFSController.getSFSClient();
		
		// Add event listeners
		sfsClient.addEventListener(SFSEvent.CONNECTION, this);
		sfsClient.addEventListener(SFSEvent.CONNECTION_LOST, this);
		sfsClient.addEventListener(SFSEvent.CONNECTION_RETRY, this);
		sfsClient.addEventListener(SFSEvent.CONNECTION_RESUME, this);
		sfsClient.addEventListener(SFSEvent.HANDSHAKE, this);
		sfsClient.addEventListener(SFSEvent.SOCKET_ERROR, this);
		sfsClient.addEventListener(SFSEvent.LOGIN, this);
		sfsClient.addEventListener(SFSEvent.LOGIN_ERROR, this);
		sfsClient.addEventListener(SFSEvent.ROOM_JOIN, this);
		sfsClient.addEventListener(SFSEvent.ROOM_JOIN_ERROR, this);
		sfsClient.addEventListener(SFSEvent.EXTENSION_RESPONSE, this);
	}
	
	/**
	 * JavaFX LoginController login button click event, action to connect to smartfox
	 * 
	 * @param actionEvent
	 */
	@FXML
	public void handleLogin(ActionEvent actionEvent) {	
		// start connection
		SFSController.connect(DEFAULT_SERVER_ADDRESS, Integer.parseInt(DEFAULT_SERVER_PORT));
		System.out.println("Connecting to IP " + DEFAULT_SERVER_ADDRESS + " Port " + DEFAULT_SERVER_PORT);
	}
	
	/**
	 * smartfox event dispatcher
	 */
	@Override
	public void dispatch(BaseEvent event)  {

		switch(event.getType()) {		
			case SFSEvent.CONNECTION:
				onConnection(event);
				break;
			case SFSEvent.CONNECTION_LOST:
				onConnectionLost(event);
				break;
			case SFSEvent.CONNECTION_RETRY:
				System.out.println("Connection retry");
				break;
			case SFSEvent.CONNECTION_RESUME:
				System.out.println("Connection resume");
				break;
			case SFSEvent.LOGIN:
				onLogin(event);
				break;
			case SFSEvent.LOGIN_ERROR:
				onLoginError(event);
				break;
		}
	}
	
	/**
	 * invoke when player successfully connect to smartfox
	 * 
	 * @param event
	 */
	private void onConnection(BaseEvent event) {
		// console log connection mode if connection success
		if (event.getArguments().get("success").equals(true)) {				
			System.out.println("The connection mode " + sfsClient.getConnectionMode());
			
			// start login
			this.username = usernameField.getText(); // "tim";
			this.password = passwordField.getText(); // "1234";
			sfsClient.send(new LoginRequest(username, password, "MyExtension"));		
			System.out.println("Now login......");
			
		} else {
			System.out.println("Connection error");
		}	

	}
	
	/**
	 * invoke when player lost connection to smartfox
	 * 
	 * @param event
	 * @throws IOException 
	 */
	private void onConnectionLost(BaseEvent event) {
		// console log connection lost reason
		String reason = (String) event.getArguments().get("reason");
		System.out.println("Lobby Connection lost :" + reason);	
		
		Platform.runLater(() -> redirectToLoginPage());		
	}
	
	/**
	 * invoke when successfully login smartfox
	 * 
	 * @param event
	 */
	private void onLogin(BaseEvent event) {
		System.out.println("login success!");
		
		Platform.runLater(() -> redirectToLobbyPage());	
	}
	
	/**
	 * invoke when smartfox login error occurs
	 * 
	 * @param event
	 */
	private void onLoginError(BaseEvent event) {
		String loginErrorMessage = (String) event.getArguments().get("errorMessage");
		System.out.println("login error : " + loginErrorMessage);
		
		Platform.runLater(() -> actionTarget.setText("Wrong username or password"));
	}
	
	/**
	 * back to login controller when connection lost
	 */
	private void redirectToLoginPage() {
		
		// back to JavaFX login UI
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/login.fxml"));
		GridPane root;
		try {
			root = (GridPane) loader.load();
			Stage mainStage = Client.primaryStage;
			mainStage.getScene().setRoot(root); //we dont need to change whole sceene, only set new root.
		} catch (IOException e) {
			Util.logException(e);
		}
		
		System.out.println("redirected to login page......");
	}
	
	private void redirectToLobbyPage() {
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
