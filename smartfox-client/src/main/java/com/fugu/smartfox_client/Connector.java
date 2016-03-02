package com.fugu.smartfox_client;

import com.fugu.smartfox_client.handler.ConnectionHandler;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.SFSException;

import sfs2x.client.SmartFox;
import sfs2x.client.core.BaseEvent;
import sfs2x.client.core.IEventListener;
import sfs2x.client.core.SFSEvent;
import sfs2x.client.requests.CreateRoomRequest;
import sfs2x.client.requests.ExtensionRequest;
import sfs2x.client.requests.JoinRoomRequest;
import sfs2x.client.requests.LoginRequest;
import sfs2x.client.requests.RoomSettings;

public class Connector {

	private static SmartFox sfs;
//	private static final String hostname = "192.168.1.153";
	
	public Connector(SmartFox sfs) {
		
		this.sfs = sfs;
		
//		init();
		
//		sfs.addEventListener(SFSEvent.CONNECTION, this);
//		sfs.addEventListener(SFSEvent.CONNECTION_LOST, this);
//		sfs.addEventListener(SFSEvent.CONNECTION_RETRY, this);
//		sfs.addEventListener(SFSEvent.CONNECTION_RESUME, this);
//		sfs.addEventListener(SFSEvent.HANDSHAKE, this);
//		sfs.addEventListener(SFSEvent.SOCKET_ERROR, this);
//		sfs.addEventListener(SFSEvent.LOGIN, this);
//		sfs.addEventListener(SFSEvent.LOGIN_ERROR, this);
//		sfs.addEventListener(SFSEvent.ROOM_JOIN, this);
//		sfs.addEventListener(SFSEvent.ROOM_JOIN_ERROR, this);
//		sfs.addEventListener(SFSEvent.ROOM_ADD, this);
//		sfs.addEventListener(SFSEvent.ROOM_CREATION_ERROR, this);
//		sfs.addEventListener(SFSEvent.EXTENSION_RESPONSE, this);
		
		
		// add smartfox event listener
		ConnectionHandler connectionHandler = new ConnectionHandler(Client.getUser());
//		LoginHandler loginHandler = new LoginHandler(user);
		System.out.println("bind user to loginHandler");
//		RoomHandler roomHandler = new RoomHandler();
//		ExtensionHandler extensionHandler = new ExtensionHandler();
		
		sfs.addEventListener(SFSEvent.CONNECTION, connectionHandler);
		sfs.addEventListener(SFSEvent.CONNECTION_LOST, connectionHandler);
		sfs.addEventListener(SFSEvent.CONNECTION_RETRY, connectionHandler);
		sfs.addEventListener(SFSEvent.CONNECTION_RESUME, connectionHandler);
		sfs.addEventListener(SFSEvent.HANDSHAKE, connectionHandler);
		sfs.addEventListener(SFSEvent.SOCKET_ERROR, connectionHandler);
//		sfs.addEventListener(SFSEvent.LOGIN, loginHandler);
//		sfs.addEventListener(SFSEvent.LOGIN_ERROR, loginHandler);
//		sfs.addEventListener(SFSEvent.ROOM_JOIN, roomHandler);
//		sfs.addEventListener(SFSEvent.ROOM_JOIN_ERROR, roomHandler);
//		sfs.addEventListener(SFSEvent.ROOM_ADD, roomHandler);
//		sfs.addEventListener(SFSEvent.ROOM_CREATION_ERROR, roomHandler);
//		sfs.addEventListener(SFSEvent.EXTENSION_RESPONSE, extensionHandler);

	}

//	private static void init() {
//		
//		// Create an instance of the SmartFox class
//		Connector.sfs = new SmartFox(true);
//		
//		// Turn on the debug feature
//		sfs.setDebug(true);
//		
//		System.out.print("Create an instance of the SmartFox class");
//	}

//	@Override
//	public void dispatch(final BaseEvent event) throws SFSException {		
//		
//		switch(event.getType()) {
//			case SFSEvent.CONNECTION:
//				
//				if (event.getArguments().get("success").equals(true)) {				
//					System.out.println("The connection mode " + sfs.getConnectionMode());
//				} else {
//					System.out.println("Connection error");
//				}
//				break;
//				
//			case SFSEvent.CONNECTION_LOST:
//				
//				System.out.println("Connection lost");
//				break;
//				
//			case SFSEvent.LOGIN:
//				
//				System.out.println("The current zone is " + sfs.getCurrentZone());		
//				sfs.send(new JoinRoomRequest("The Lobby"));
//				break;
//				
//			case SFSEvent.ROOM_JOIN:
//				
//				System.out.println("The current room is " + sfs.getLastJoinedRoom().getName());
//				break;
//				
//			case SFSEvent.ROOM_JOIN_ERROR:
//				
//				System.out.println("Join room error" + event.getArguments().get("errorMessage"));
//				break;	
//				
//			case SFSEvent.EXTENSION_RESPONSE:
//				
//				System.out.println("Extension response pops");
//				onExtensionRequest(event);
//				break;	
//				
//			case SFSEvent.ROOM_ADD:
//				
//				this.onRoomAdded(event);
//				break;
//				
//			case SFSEvent.ROOM_CREATION_ERROR:
//			
//				this.onRoomCreationError(event);
//				break;
//		}
//		
//	}
//	
//    public void connect() {
//
//        String version = sfs.getVersion();
//        System.out.println("Client Version: " + version);
//        
//        sfs.connect(this.hostname, 9933);
//        
//		// Add event listeners
//		sfs.addEventListener(SFSEvent.CONNECTION, this);
//		sfs.addEventListener(SFSEvent.CONNECTION_LOST, this);
//		sfs.addEventListener(SFSEvent.LOGIN, this);
//		sfs.addEventListener(SFSEvent.LOGIN_ERROR, this);
//		sfs.addEventListener(SFSEvent.ROOM_JOIN, this);
//		sfs.addEventListener(SFSEvent.ROOM_JOIN_ERROR, this);
//		sfs.addEventListener(SFSEvent.HANDSHAKE, this);
//		sfs.addEventListener(SFSEvent.SOCKET_ERROR, this);
//		sfs.addEventListener(SFSEvent.EXTENSION_RESPONSE, this);
//		
//    }
//	
//	public void disconnect() {
//
//		if (sfs.isConnected()) {
//			System.out.println("Disconnect: Disconnecting client");
//			sfs.disconnect();
//			System.out.println("Disconnect: Disconnected ? " + !sfs.isConnected());
//		}
//	}
//	
//    
//    public void action() {
//    	
//    	System.out.println("Taking action");
//    	
//    	ISFSObject objOut = new SFSObject();
//    	objOut.putInt("n1", 10);
//    	objOut.putInt("n2", 4);
//    	
//    	
//    	// 測試 server 端 MathMultiHandler
//    	sfs.send(new ExtensionRequest("math.add", objOut));
//    	
//    	// 測試 The other room
////    	this.createNewRoom();
//    }
//    
//	private void onExtensionRequest(BaseEvent e) throws SFSException {
//    	
//    	String responseCmd = (String) e.getArguments().get("cmd");
//    	ISFSObject responseParams = (SFSObject) e.getArguments().get("params");
//		
//    	System.out.println("The cmd is: " + responseCmd);
//		System.out.println("The sum is: " + responseParams.getInt("sum"));
//    }
//	
//	
//	private void createNewRoom() {
//		
//		System.out.println("Creating new room!");
//		
//		RoomSettings settings = new RoomSettings("The Room");
//		settings.setMaxUsers(40);
////		settings.setGroupId("ChatGroup");
//		
//		sfs.send(new CreateRoomRequest(settings));
//		
//	}
//	
//	private void onRoomAdded(BaseEvent e) {
//		
//		System.out.println("Room created");
//		
////		Arrays.toString(e.getArguments().entrySet().toArray());
//		String responseCmd = e.getArguments().get("room").toString();
////		ISFSObject response = (SFSObject) e.getArguments().get("params");
//		System.out.println("The room is: " + responseCmd);
//	}
	
//	private void onRoomCreationError(BaseEvent e)
//	{
//		String responseError = (String) e.getArguments().get("errorMessage");
//		System.out.println("An error occurred while attempting to create the Room: " + responseError);
//	}
//	
//	public static SmartFox getSmartFox() {
//		return Connector.sfs;
//	}
	
}
