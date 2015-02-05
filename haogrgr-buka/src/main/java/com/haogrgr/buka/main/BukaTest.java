package com.haogrgr.buka.main;

import com.haogrgr.buka.manager.DetailCapturerManager;
import com.haogrgr.buka.task.generater.DetailTaskGenerater;
import com.haogrgr.buka.task.generater.TaskGenerater;
import com.haogrgr.buka.task.generater.TestTaskGenerater;

@SuppressWarnings("unused")
public class BukaTest {

	public static void main(String[] args) throws Exception {
		
		//Thread.sleep(1000*60*30);
		
//		DetailTaskGenerater taskGenerater = new DetailTaskGenerater(0, 210000);
//		taskGenerater.addIgon(12388, 65555);
//		taskGenerater.addIgon(65557, 99999);
//		taskGenerater.addIgon(102124, 196610);
//		taskGenerater.addIgon(102124, 196610);
		
		DetailTaskGenerater taskGenerater = new DetailTaskGenerater(101122, 101122);
		
//		TaskGenerater taskGenerater = new TestTaskGenerater("/logs/buka.do");
		
		DetailCapturerManager manager = new DetailCapturerManager(taskGenerater);
		
		manager.start(1);
		
	}

}
