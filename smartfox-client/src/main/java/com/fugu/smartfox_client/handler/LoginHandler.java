package com.fugu.smartfox_client.handler;

import com.fugu.smartfox_client.Client;
import com.fugu.smartfox_client.model.User;
import com.smartfoxserver.v2.exceptions.SFSException;

import sfs2x.client.SmartFox;
import sfs2x.client.core.BaseEvent;
import sfs2x.client.core.IEventListener;
import sfs2x.client.core.SFSEvent;
import sfs2x.client.requests.JoinRoomRequest;

public class LoginHandler implements IEventListener {

	private SmartFox sfs = Client.getSmartFox();
	private User user;
	
	public LoginHandler(User user) {
		this.user = user;
	}
	
	@Override
	public void dispatch(BaseEvent event) throws SFSException {
		
		switch(event.getType()) {
		
			case SFSEvent.LOGIN:
				System.out.println(user.getUsername() + " Login success the current zone is " + sfs.getCurrentZone());
				handleLogin();
				break;
				
			case SFSEvent.LOGIN_ERROR:
				System.out.println("Login error");
				break;

		}		
	}
	
	private void handleLogin() {
		sfs.send(new JoinRoomRequest("The Lobby"));
	}
	
}
