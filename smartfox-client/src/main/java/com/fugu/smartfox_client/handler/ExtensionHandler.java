package com.fugu.smartfox_client.handler;

import com.fugu.smartfox_client.Client;
import com.fugu.smartfox_client.model.Game;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.exceptions.SFSException;

import sfs2x.client.SmartFox;
import sfs2x.client.core.BaseEvent;
import sfs2x.client.core.IEventListener;
import sfs2x.client.core.SFSEvent;

public class ExtensionHandler implements IEventListener {
	
	private SmartFox sfs = Client.getSmartFox();

	@Override
	public void dispatch(BaseEvent event) throws SFSException {
		
		switch(event.getType()) {
		
		case SFSEvent.EXTENSION_RESPONSE:
			System.out.println("Extension response : " + event.getArguments().get("cmd").toString());
			handleGameGuess(event);
			break;
		}
		
	}
	
	private void handleGameGuess(BaseEvent event) {
		
		String cmd = event.getArguments().get("cmd").toString();
		System.out.println("Extension response: " + cmd);
		
		ISFSObject response = (ISFSObject) event.getArguments().get("params");
		System.out.println("Extension response object: " + response.toString());
		
		// Game game = Game.fromSFSObject(response);
		
	}
}
