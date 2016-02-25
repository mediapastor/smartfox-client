package com.fugu.smartfox_client.controller;

import java.io.IOException;

import com.fugu.smartfox_client.Client;
import com.fugu.smartfox_client.SFSController;
import com.fugu.smartfox_client.Util.Util;

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
import sfs2x.client.entities.SFSRoom;
import sfs2x.client.requests.JoinRoomRequest;
import sfs2x.client.requests.LoginRequest;

public class LoginController implements IEventListener {
	
	private SmartFox sfsClient;
	private final static String DEFAULT_SERVER_ADDRESS = "localhost";
	private final static String DEFAULT_SERVER_PORT = "9933";
	
	private String username;
	private String password;
	
	@FXML private TextField usernameField;
	@FXML private PasswordField passwordField;
	@FXML private Text actionTarget;
	
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
	 * smartfox event dispatcher
	 */
	@Override
	public void dispatch(BaseEvent event)  {

		switch(event.getType()) {
		
			case SFSEvent.CONNECTION:
				
				if (event.getArguments().get("success").equals(true)) {				
					// Login as guest in current zone
					System.out.println("The connection mode " + sfsClient.getConnectionMode());
					
					login();
				} else {
					System.out.println("Connection error");
				}
				break;
				
			case SFSEvent.CONNECTION_LOST:
				
//				String responseError = (String) event.getArguments().get("errorMessage");
				System.out.println("Connection lost");
				break;
				
			case SFSEvent.CONNECTION_RETRY:
				System.out.println("Connection retry");
				break;
			case SFSEvent.CONNECTION_RESUME:
				System.out.println("Connection resume");
				break;
			case SFSEvent.LOGIN:
				
				System.out.println("login success!");
//				System.out.println(event.getArguments().toString());
				joinRoom();
				
			case SFSEvent.LOGIN_ERROR:
				
//				String loginError = (String) event.getArguments().get("errorMessage");
//				System.out.println("An error occurred while attempting to login: " + loginError + " ; all arguments " + event.getArguments().toString());
				actionTarget.setText("Wrong username or password");
				break;
				
			case SFSEvent.ROOM_JOIN:
				
				System.out.println("Joined room");
				try {
					goToLobby();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
				System.out.println("The current room is " + sfsClient.getLastJoinedRoom().getName());
				onRoomJoin(event);
				break;
				
			case SFSEvent.ROOM_JOIN_ERROR:
				
				System.out.println("Join room error" + event.getArguments().get("errorMessage"));
				break;	
		}
	}
	
	/**
	 * connect to smartfox
	 * 
	 * @param actionEvent
	 */
	@FXML
	public void handleLogin(ActionEvent actionEvent) {
		
		connect(DEFAULT_SERVER_ADDRESS, DEFAULT_SERVER_PORT);
		System.out.println("Connecting to IP " + DEFAULT_SERVER_ADDRESS + " Port " + DEFAULT_SERVER_PORT);
	}
	
	private void connect(String serverIP, String serverPort) {
		// if the user have entered port number it uses it...
		if (serverPort.length() > 0) {
			int serverPortValue = Integer.parseInt(serverPort);
			// tries to connect to the server
			sfsClient.connect(serverIP, serverPortValue);
		}
		// ...otherwise uses the default port number
		else {
			sfsClient.connect(serverIP, 9933);
		}
	}
	
	private void login(){
		this.username = usernameField.getText(); // "tim";
		this.password = passwordField.getText(); // "1234";
		sfsClient.send(new LoginRequest(username, password, "MyExtension"));
		
		System.out.println("Now login......");
	}
	
	private void joinRoom() {
		sfsClient.send( new JoinRoomRequest("The Lobby") );
		
		System.out.println("joining room......");
		
	}
	
	/**
	 * evoke when player joins a room
	 * 
	 * @param event
	 */
	private void onRoomJoin(BaseEvent event) {
		SFSRoom room = (SFSRoom) event.getArguments().get("room");
		System.out.println("Room joined successfully :" + room.getName());
	}

	
	private void goToLobby() throws IOException {	
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("lobby.fxml"));
		GridPane root = (GridPane) loader.load();
		
		Stage mainStage = Client.primaryStage;
		mainStage.getScene().setRoot(root); //we dont need to change whole sceene, only set new root.
		
		System.out.println("going to lobby......");
	}

	
}
