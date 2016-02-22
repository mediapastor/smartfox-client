package com.fugu.smartfox_client;

import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.SFSException;

import sfs2x.client.SmartFox;
import sfs2x.client.core.BaseEvent;
import sfs2x.client.core.IEventListener;
import sfs2x.client.core.SFSEvent;
import sfs2x.client.requests.ExtensionRequest;
import sfs2x.client.requests.JoinRoomRequest;
import sfs2x.client.requests.LoginRequest;

public class Connector implements IEventListener {

	private SmartFox sfs;
	private final String hostname = "192.168.50.5";
	
	public Connector() {
		initSmartFox();	    			
	}

	private void initSmartFox() {

		this.sfs = new SmartFox(true);
		System.out.print("Connector initialized");

	}

	@Override
	public void dispatch(final BaseEvent event) throws SFSException {
		
		
		switch(event.getType()) {
			case SFSEvent.CONNECTION:
				
				if (event.getArguments().get("success").equals(true)) {				
					// Login as guest in current zone
					sfs.send(new LoginRequest("", "", "MyExtension"));
					System.out.println("The connection mode " + sfs.getConnectionMode());
				} else {
					System.out.println("Connection error");
				}
				break;
				
			case SFSEvent.CONNECTION_LOST:
				
				System.out.println("Connection lost");
				disconnect();
				break;
				
			case SFSEvent.LOGIN:
				
				System.out.println("The current zone is " + sfs.getCurrentZone());		
				sfs.send(new JoinRoomRequest("The Lobby"));
				break;
				
			case SFSEvent.ROOM_JOIN:
				
				System.out.println("The current room is " + sfs.getLastJoinedRoom().getName());
				action();
				break;
				
			case SFSEvent.ROOM_JOIN_ERROR:
				
				System.out.println("Join room error" + event.getArguments().get("errorMessage"));
				break;	
				
			case SFSEvent.EXTENSION_RESPONSE:
				
				System.out.println("Extension response pops");
				onExtensionRequest(event);
				break;		
		}
		
	}
	
	private void disconnect() {

		if (sfs.isConnected()) {
			System.out.print("Disconnect: Disconnecting client");
			sfs.disconnect();
			System.out.print("Disconnect: Disconnected ? " + !sfs.isConnected());
		}
	}
	
    public void start() {

        String version = sfs.getVersion();
        System.out.println("Client Version: " + version);
        
        sfs.connect(this.hostname, 9933);
        
		// Add event listeners
		sfs.addEventListener(SFSEvent.CONNECTION, this);
		sfs.addEventListener(SFSEvent.CONNECTION_LOST, this);
		sfs.addEventListener(SFSEvent.LOGIN, this);
		sfs.addEventListener(SFSEvent.LOGIN_ERROR, this);
		sfs.addEventListener(SFSEvent.ROOM_JOIN, this);
		sfs.addEventListener(SFSEvent.ROOM_JOIN_ERROR, this);
		sfs.addEventListener(SFSEvent.HANDSHAKE, this);
		sfs.addEventListener(SFSEvent.SOCKET_ERROR, this);
		sfs.addEventListener(SFSEvent.EXTENSION_RESPONSE, this);
		
		
    }
    
    public void action() {
    	
    	System.out.println("Taking action");
    	
    	ISFSObject objOut = new SFSObject();
    	objOut.putInt("n1", 10);
    	objOut.putInt("n2", 4);
    	
    	sfs.send(new ExtensionRequest("math", objOut));
    }
    
	private void onExtensionRequest(BaseEvent e) throws SFSException {
    	
    	String responseCmd = (String) e.getArguments().get("cmd");
    	ISFSObject responseParams = (SFSObject) e.getArguments().get("params");
		
    	System.out.println("The cmd is: " + responseCmd);
		System.out.println("The sum is: " + responseParams.getInt("sum"));
    }
	
}
