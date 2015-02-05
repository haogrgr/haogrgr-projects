package com.ibm.actor.main;

import com.ibm.actor.AbstractActor;
import com.ibm.actor.Message;

public class DefaultActor extends AbstractActor {

	@Override
	protected void loopBody(Message m) {
		System.out.println(m);
	}

}
