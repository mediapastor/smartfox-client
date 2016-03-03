package com.fugu.smartfox_client.handler;

import com.fugu.smartfox_client.Client;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.SFSException;

import javafx.application.Platform;
import sfs2x.client.SmartFox;
import sfs2x.client.core.BaseEvent;
import sfs2x.client.core.IEventListener;
import sfs2x.client.core.SFSEvent;
import sfs2x.client.requests.ExtensionRequest;

public class RoomHandler implements IEventListener {

	private SmartFox sfs = Client.getSmartFox();
	
	@Override
	public void dispatch(BaseEvent event) throws SFSException {
		
		switch(event.getType()) {
		
		case SFSEvent.ROOM_JOIN:
			System.out.println("Room joined successfully: " + sfs.getLastJoinedRoom().getName());
			handleRoom();
			break;
			
		case SFSEvent.ROOM_JOIN_ERROR:
			System.out.println("Room join error: " + event.getArguments().get("errorMessage"));
			break;
		
		case SFSEvent.ROOM_ADD:
			System.out.println("Room add");
			break;
			
		case SFSEvent.ROOM_CREATION_ERROR:
			System.out.println("Room creation error: " + event.getArguments().get("errorMessage"));
			break;
		}
		
	}
	
	private void handleRoom() {
		// change scene to game
		Platform.runLater(() -> Client.setScene("game"));

		// initialize the game
		// ISFSObject sfso = new SFSObject();
		// Platform.runLater(() ->sfs.send(new ExtensionRequest("game.init", sfso)));
		// sfs.send(new ExtensionRequest("game", sfso));
	}
}
