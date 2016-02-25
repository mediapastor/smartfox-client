package com.fugu.smartfox_client.controller;

import com.fugu.smartfox_client.Connector;
import com.smartfoxserver.v2.exceptions.SFSException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sfs2x.client.SmartFox;
import sfs2x.client.core.BaseEvent;
import sfs2x.client.core.IEventListener;
import sfs2x.client.core.SFSEvent;
import sfs2x.client.requests.CreateRoomRequest;
import sfs2x.client.requests.JoinRoomRequest;
import sfs2x.client.requests.RoomSettings;

public class LobbyController implements IEventListener {
	
	private Connector conn;
	private SmartFox sfs;
	
	@FXML private TextField firstName;	
	
	public void initData(Connector conn) {
		this.conn = conn;
		this.sfs = conn.getSmartFox();
		
		// 新增 room 事件
		sfs.addEventListener(SFSEvent.ROOM_ADD, this);
		sfs.addEventListener(SFSEvent.ROOM_CREATION_ERROR, this);
	}

	/**
	 * connect to smartfox
	 * 
	 * @param actionEvent
	 */
	public void handleConnect(ActionEvent actionEvent) {
		
		conn.connect();
		System.out.println("JavaFX handleConnect done");
	}
	
	/**
	 * disconnect from smartfox
	 * 
	 * @param actionEvent
	 */
	public void handleDisconnect(ActionEvent actionEvent) {
		
		conn.disconnect();
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
		
		sfs.send(new CreateRoomRequest(settings, true, sfs.getLastJoinedRoom()));
	}
	
	public void handleJoinRoom(ActionEvent actionEvent) {
		sfs.send( new JoinRoomRequest("hangman") );
	}

	/**
	 * smartfox event dispatcher
	 */
	@Override
	public void dispatch(BaseEvent event) throws SFSException {

		switch(event.getType()) {
		
			case SFSEvent.CONNECTION:
				
				System.out.println("Room created");
				
//				Arrays.toString(e.getArguments().entrySet().toArray());
				String responseCmd = event.getArguments().get("room").toString();
//				ISFSObject response = (SFSObject) e.getArguments().get("params");
				System.out.println("The room is: " + responseCmd);
				break;
				
			case SFSEvent.CONNECTION_LOST:
				
				String responseError = (String) event.getArguments().get("errorMessage");
				System.out.println("An error occurred while attempting to create the Room: " + responseError);
				break;
		}
	}
	
}
