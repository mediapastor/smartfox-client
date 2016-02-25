package com.fugu.smartfox_client.controller;

import java.io.IOException;

import com.fugu.smartfox_client.Client;
import com.fugu.smartfox_client.SFSController;
import com.fugu.smartfox_client.Util.Util;
import com.smartfoxserver.v2.exceptions.SFSException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sfs2x.client.SmartFox;
import sfs2x.client.core.BaseEvent;
import sfs2x.client.core.IEventListener;
import sfs2x.client.core.SFSEvent;
import sfs2x.client.requests.CreateRoomRequest;
import sfs2x.client.requests.JoinRoomRequest;
import sfs2x.client.requests.RoomSettings;

public class LobbyController implements IEventListener {
	
	private SmartFox sfsClient;
	
	public LobbyController() {
		
		initSmartFox();
	}

	public void initSmartFox() {
		sfsClient = SFSController.getSFSClient();
		
		// Add event listeners
		sfsClient.addEventListener(SFSEvent.CONNECTION_LOST, this);
		sfsClient.addEventListener(SFSEvent.CONNECTION_RETRY, this);
		sfsClient.addEventListener(SFSEvent.CONNECTION_RESUME, this);
//		sfsClient.addEventListener(SFSEvent.ROOM_JOIN, this);
		sfsClient.addEventListener(SFSEvent.ROOM_JOIN_ERROR, this);
		sfsClient.addEventListener(SFSEvent.EXTENSION_RESPONSE, this);
	}
	

	
	/**
	 * disconnect from smartfox
	 * 
	 * @param actionEvent
	 */
	public void handleDisconnect(ActionEvent actionEvent) {
		
		sfsClient.disconnect();
		System.out.println("JavaFX handleDisconnect done");
	}
	
	/**
	 * create new smartfox room
	 * 
	 * @param actionEvent
	 */
	public void handleCreateRoom(ActionEvent actionEvent) {
		System.out.println("Creating new room!");
		
		RoomSettings settings = new RoomSettings("hangman");
		settings.setMaxUsers(2);
		settings.isGame();
		settings.setGroupId("game");
		settings.setMaxUsers(0);
//		settings.setExtension(extension);
		
		sfsClient.send(new CreateRoomRequest(settings, true, sfsClient.getLastJoinedRoom()));
	}
	
	public void handleJoinRoom(ActionEvent actionEvent) {
		sfsClient.send( new JoinRoomRequest("hangman") );
	}

	/**
	 * smartfox event dispatcher
	 */
	@Override
	public void dispatch(BaseEvent event) throws SFSException {

		switch(event.getType()) {
				
			case SFSEvent.CONNECTION_LOST:				
				onConnectionLost(event);
				break;
//			case SFSEvent.ROOM_JOIN:
//				onRoomJoin(event);
//				break;
			case SFSEvent.ROOM_JOIN_ERROR:
				onRoomJoinError(event);
				break;
		}
	}
	
	/**
	 * invoke when player lost connection
	 * 
	 * @param event
	 * @throws IOException 
	 */
	private void onConnectionLost(BaseEvent event) {
		String reason = (String) event.getArguments().get("reason");
		System.out.println("Lobby Connection lost :" + reason);	
		try {
			goToLogin();
		} catch (IOException e) {
			Util.logException(e);
		}
	}
	

	
	/**
	 * evoke when player try to join a room and failed
	 * 
	 * @param event
	 */
	private void onRoomJoinError(BaseEvent event) {
		String errorMessage = (String) event.getArguments().get("errorMessage");
		System.out.println("Room joining failed :" + errorMessage);
	}
	
	private void goToLogin() throws IOException {	
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("login.fxml"));
		GridPane root = (GridPane) loader.load();
		
		Stage mainStage = Client.primaryStage;
		mainStage.getScene().setRoot(root); //we dont need to change whole sceene, only set new root.
		
		System.out.println("going to login......");
	}
}
