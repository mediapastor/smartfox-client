package com.fugu.smartfox_client;

import sfs2x.client.SmartFox;
import sfs2x.client.requests.JoinRoomRequest;

public class SFSController {
	
	private final static boolean DEBUG_SFS = true;

	private static SmartFox sfsClientInstance;

	public static synchronized SmartFox getSFSClient() {
		if (sfsClientInstance == null) {
			sfsClientInstance = new SmartFox(DEBUG_SFS);
		}
		return sfsClientInstance;
	}
	
	/**
	 * connect to smartfox server
	 * 
	 * @param serverIP
	 * @param serverPortValue
	 */
	public static void connect(String serverIP,int serverPortValue) {
		sfsClientInstance.connect(serverIP, serverPortValue);
	}
	
	/**
	 * join smartfox server room
	 * 
	 * @param roomName
	 */
	public static void joinRoom(String roomName) {
		System.out.println("login success!");
//		System.out.println(event.getArguments().toString());
		sfsClientInstance.send( new JoinRoomRequest(roomName) );
		System.out.println("joining room...... : " + roomName);
	}
	
}
