package com.fugu.smartfox_client.controller;

import java.io.IOException;

import com.fugu.smartfox_client.Client;
import com.fugu.smartfox_client.SFSController;
import com.smartfoxserver.v2.exceptions.SFSException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sfs2x.client.SmartFox;
import sfs2x.client.core.BaseEvent;
import sfs2x.client.core.IEventListener;
import sfs2x.client.core.SFSEvent;
import sfs2x.client.requests.JoinRoomRequest;
import sfs2x.client.requests.LoginRequest;

public class LoginController implements IEventListener {
	
	private SmartFox sfsClient;
	private final static String DEFAULT_SERVER_ADDRESS = "192.168.50.5";
	private final static String DEFAULT_SERVER_PORT = "9933";
	
	private String username;
	private String password;
	
	@FXML private TextField firstName;	
	
	public LoginController() {
		
		initSmartFox();
	}
	
	public void initSmartFox() {
		sfsClient = SFSController.getSFSClient();
		
		// Add event listeners
		sfsClient.addEventListener(SFSEvent.CONNECTION, this);
		sfsClient.addEventListener(SFSEvent.CONNECTION_LOST, this);
		sfsClient.addEventListener(SFSEvent.LOGIN, this);
		sfsClient.addEventListener(SFSEvent.LOGIN_ERROR, this);
		sfsClient.addEventListener(SFSEvent.ROOM_JOIN, this);
		sfsClient.addEventListener(SFSEvent.ROOM_JOIN_ERROR, this);
		sfsClient.addEventListener(SFSEvent.HANDSHAKE, this);
		sfsClient.addEventListener(SFSEvent.SOCKET_ERROR, this);
		sfsClient.addEventListener(SFSEvent.EXTENSION_RESPONSE, this);
	}


	/**
	 * smartfox event dispatcher
	 */
	@Override
	public void dispatch(BaseEvent event) throws SFSException {

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
				
				String responseError = (String) event.getArguments().get("errorMessage");
				System.out.println("An error occurred while attempting to create the Room: " + responseError);
				break;
				
			case SFSEvent.LOGIN:
				
				System.out.println("login success!");
				System.out.println(event.getArguments().toString());
				joinRoom();
				
			case SFSEvent.LOGIN_ERROR:
				
				String loginError = (String) event.getArguments().get("errorMessage");
				System.out.println("An error occurred while attempting to login: " + loginError + " ; all arguments " + event.getArguments().toString());
				break;
				
			case SFSEvent.ROOM_JOIN:
				try {
					goToLobby();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("The current room is " + sfsClient.getLastJoinedRoom().getName());
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
	public void handleConnect(ActionEvent actionEvent) {
		
		connect(DEFAULT_SERVER_ADDRESS, DEFAULT_SERVER_PORT);
		System.out.println("Connecting to IP " + DEFAULT_SERVER_ADDRESS + " Port " + DEFAULT_SERVER_PORT);
		System.out.println(actionEvent.toString());
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
		this.username = "tim";
		this.password = "1234";
		sfsClient.send(new LoginRequest(username, password, "MyExtension"));
		
		System.out.println("Now login......");
	}
	
	private void joinRoom() {
		sfsClient.send( new JoinRoomRequest("The Lobby") );
		
		System.out.println("joining room......");
		
	}

	
	private void goToLobby() throws IOException {	
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("lobby.fxml"));
		GridPane root = (GridPane) loader.load();
		
		Stage mainStage = Client.primaryStage;
		mainStage.getScene().setRoot(root); //we dont need to change whole sceene, only set new root.
		
		System.out.println("going to lobby......");
	}

	
}
