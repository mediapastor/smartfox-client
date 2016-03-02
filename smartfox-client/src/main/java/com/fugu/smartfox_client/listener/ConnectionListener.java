package com.fugu.smartfox_client.listener;

import com.smartfoxserver.v2.exceptions.SFSException;

import sfs2x.client.core.BaseEvent;
import sfs2x.client.core.IEventListener;
import sfs2x.client.core.SFSEvent;

public class ConnectionListener implements IEventListener {

	@Override
	public void dispatch(BaseEvent event) throws SFSException {
		switch(event.getType()) {		
		case SFSEvent.CONNECTION:
			onConnection(event);
			break;
		case SFSEvent.CONNECTION_LOST:
			onConnectionLost(event);
			break;
		case SFSEvent.CONNECTION_RETRY:
			System.out.println("Connection retry");
			break;
		case SFSEvent.CONNECTION_RESUME:
			System.out.println("Connection resume");
			break;
	}
		
	}

	private void onConnectionLost(BaseEvent event) {
		// TODO Auto-generated method stub
		
	}

	private void onConnection(BaseEvent event) {
		// TODO Auto-generated method stub
		
	}

	
}
