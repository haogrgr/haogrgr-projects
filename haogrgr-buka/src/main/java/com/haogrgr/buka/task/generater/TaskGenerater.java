package com.haogrgr.buka.task.generater;

import com.haogrgr.buka.task.model.CapturerTask;

public interface TaskGenerater {
	
	public CapturerTask next();
	
	public boolean hasNext();
}
