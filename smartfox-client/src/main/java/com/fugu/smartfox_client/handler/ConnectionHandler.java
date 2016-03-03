package com.fugu.smartfox_client.handler;

import com.fugu.smartfox_client.Client;
import com.fugu.smartfox_client.model.User;
import com.smartfoxserver.v2.exceptions.SFSException;

import javafx.application.Platform;
import sfs2x.client.SmartFox;
import sfs2x.client.core.BaseEvent;
import sfs2x.client.core.IEventListener;
import sfs2x.client.core.SFSEvent;
import sfs2x.client.requests.LoginRequest;

public class ConnectionHandler implements IEventListener {
	
	private SmartFox sfs = Client.getSmartFox();
	private User user;
	
	public ConnectionHandler(User user) {
		this.user = user;
	}
	
	@Override
	public void dispatch(BaseEvent event) throws SFSException {
		
		switch(event.getType()) {
		
			case SFSEvent.CONNECTION:
				
				if (event.getArguments().get("success").equals(true)) {				
					System.out.println("The connection mode " + sfs.getConnectionMode());
					handleConnection(event);
				} else {
					System.out.println("Connection error");
				}
				break;
				
			case SFSEvent.CONNECTION_LOST:
				System.out.println("Connection lost");
				handleConnectionLost(event);
				break;
	
			case SFSEvent.CONNECTION_RETRY:
				System.out.println("Connection retry");
				break;
				
			case SFSEvent.CONNECTION_RESUME:
				System.out.println("Connection resume");
				break;
				
			case SFSEvent.HANDSHAKE:
				System.out.println("Socket handshake");
				break;
				
			case SFSEvent.SOCKET_ERROR:
				System.out.println("Socket error");
				break;
		}
		
	}
	
	/**
	 * Handle connection success actions 
	 */
	private void handleConnection(BaseEvent event) {
		System.out.println("username input: " + user.getUsername() + " password input: " + user.getPassword());
		sfs.send(new LoginRequest(user.getUsername(), user.getPassword(), "MyExtension")); 
	}
	
	/**
	 * Handle connection lost actions 
	 * 
	 * @param event
	 */
	private void handleConnectionLost(BaseEvent event) {
		Platform.runLater(() -> Client.setScene("login"));
	}
}
