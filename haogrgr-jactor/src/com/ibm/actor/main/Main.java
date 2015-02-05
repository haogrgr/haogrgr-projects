package com.ibm.actor.main;

import com.ibm.actor.Actor;
import com.ibm.actor.DefaultActorManager;
import com.ibm.actor.DefaultMessage;

public class Main {

	public static void main(String[] args) throws Exception {
		DefaultActorManager manager = DefaultActorManager.getDefaultInstance();
		manager.setRecordSentMessages(false);
		
		
		Actor from = manager.createAndStartActor(DefaultActor.class, "actor-1");
		Actor to = manager.createAndStartActor(DefaultActor.class, "actor-2");
		
		for (int i = 0; i < 20; i++) {
			DefaultMessage msg = new DefaultMessage("test", "xxxx" + i);
			if(i % 2 == 0){
				manager.send(msg, null, to);
			}else{
				manager.send(msg, null, from);
			}
		}
		
		Thread.sleep(10000);
		manager.terminateAndWait();
	}

}

