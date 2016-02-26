package com.fugu.smartfox_client.controller;

import java.io.IOException;

import com.fugu.smartfox_client.Client;
import com.fugu.smartfox_client.SFSController;
import com.fugu.smartfox_client.Util.Util;
import com.smartfoxserver.v2.exceptions.SFSException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sfs2x.client.SmartFox;
import sfs2x.client.core.BaseEvent;
import sfs2x.client.core.IEventListener;
import sfs2x.client.core.SFSEvent;
import sfs2x.client.entities.SFSRoom;
import sfs2x.client.requests.CreateRoomRequest;
import sfs2x.client.requests.RoomSettings;

public class LobbyController implements IEventListener {
	
	private SmartFox sfsClient;
//	private List<Room> roomList;
	
	@FXML private volatile HBox hBox;
	
	public LobbyController() {
		
		initSmartFox();

	}

	public void initSmartFox() {
		sfsClient = SFSController.getSFSClient();
		
		// Add event listeners
		sfsClient.addEventListener(SFSEvent.ROOM_JOIN, this);
		sfsClient.addEventListener(SFSEvent.ROOM_JOIN_ERROR, this);
		sfsClient.addEventListener(SFSEvent.ROOM_ADD, this);
		sfsClient.addEventListener(SFSEvent.ROOM_CREATION_ERROR, this);
		sfsClient.addEventListener(SFSEvent.EXTENSION_RESPONSE, this);
		
		// list all rooms
//		roomList = sfsClient.getRoomList();
	}
	
	/**
	 * initialize after FXML is built
	 */
	@FXML
	protected void initialize() {
		// add updated rooms to the list
		sfsClient.getRoomList().forEach(room -> {
			Button btn = new Button(room.getName());
			String roomName = room.getName();
			btn.setOnAction(event -> handleBtnJoinRoom(event, roomName));
			hBox.getChildren().add(btn);
		});	
	}
	
	/**
	 * disconnect from smartfox
	 * 
	 * @param actionEvent
	 */
	@FXML
	public void handleDisconnect(ActionEvent actionEvent) {
		sfsClient.disconnect();
		Platform.runLater(() -> redirectToLoginPage());
		System.out.println("JavaFX handleDisconnect done");
	}
	
	/**
	 * create new smartfox room
	 * 
	 * @param actionEvent
	 */
	@FXML
	public void handleCreateRoom(ActionEvent actionEvent) {
		System.out.println("Creating new room!");
		
		RoomSettings settings = new RoomSettings("hangman");
		settings.setMaxUsers(2);
		settings.isGame();
		settings.setGroupId("default");
		
		sfsClient.send(new CreateRoomRequest(settings, true, sfsClient.getLastJoinedRoom()));
	}
	
	/**
	 * invoke when player tries to join a room
	 * 
	 * @param actionEvent
	 */
	public void handleJoinRoom(ActionEvent actionEvent) {
//		SFSController.joinRoom("The Lobby");
	}

	public void handleBtnJoinRoom(ActionEvent event, String roomName) {
		System.out.println("Join room from button :" + roomName);
		SFSController.joinRoom(roomName);
	}
	
	/**
	 * smartfox event dispatcher
	 */
	@Override
	public void dispatch(BaseEvent event) throws SFSException {

		switch(event.getType()) {
				
			case SFSEvent.ROOM_JOIN:
				onRoomJoin(event);
				break;
			case SFSEvent.ROOM_JOIN_ERROR:
				onRoomJoinError(event);
				break;	
			case SFSEvent.ROOM_ADD:
				onRoomAdd(event);
				break;
			case SFSEvent.ROOM_CREATION_ERROR:
				onRoomCreationError(event);
				break;
		}
	}
	


	/**
	 * evoke when player joins a room
	 * 
	 * @param event
	 */
	private void onRoomJoin(BaseEvent event) {
		SFSRoom room = (SFSRoom) event.getArguments().get("room");
		System.out.println("The current room is " + sfsClient.getLastJoinedRoom().getName());	
		System.out.println("Room joined successfully :" + room.getName());
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

	private void onRoomAdd(BaseEvent event) {
		System.out.println("A new room is added.");
		Platform.runLater(() -> updateRoomList());
//		updateRoomList();

	}
	
	private void onRoomCreationError(BaseEvent event) {
		System.out.println("Room creation error : " + event.getArguments().get("errorMessage"));
		
	}
	
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
	
	private void updateRoomList() {		
		// remove all room on the list
//		Platform.runLater(() ->
//		hBox.getChildren().forEach(node -> hBox.getChildren().remove(node))
//		)
//		;		
		
		hBox.getChildren().clear();

		
		// add updated rooms to the list
//		Platform.runLater(() ->
		sfsClient.getRoomList().forEach(room -> {
			Button btn = new Button(room.getName());
			String roomName = room.getName();
			btn.setOnAction(event -> handleBtnJoinRoom(event, roomName));
			hBox.getChildren().add(btn);
		})
//		)
		;	

	}
	
}
