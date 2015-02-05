package com.haogrgr.buka.task.generater;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.haogrgr.buka.task.model.CapturerTask;
import com.haogrgr.buka.task.model.DetailCapturerTask;

public class TestTaskGenerater implements TaskGenerater {
	
	private static Lock lock = new ReentrantLock();
	
	private Queue<Integer> queue;
	
	public static void main(String[] args) throws Exception {
		TestTaskGenerater g = new TestTaskGenerater("/logs/buka.do");
		for (int i = 0; i < 100; i++) {
			System.out.println(g.next());
		}	
	}
	
	public TestTaskGenerater(String path){
		this.queue = new LinkedList<Integer>();
		try{
			File file = new File(TestTaskGenerater.class.getResource(path).toURI());
	        List<String> lines = FileUtils.readLines(file, "UTF-8");
	        Pattern pattern = Pattern.compile("detailId=.*(?=, result)");
	        
	        for (String line : lines) {
	        	if (line == null || StringUtils.isBlank(line)) {
	                continue;
	            }
	            Matcher matcher = pattern.matcher(line);
	            if (matcher.find()) {
	                String group = matcher.group().substring(9);
	                String result = group;
	                queue.offer(Integer.valueOf(result));
	            }
			}
	        System.out.println(queue);
		}catch(Exception e){
			throw new RuntimeException("初始化失败", e);
		}
		
	}
	
	@Override
	public CapturerTask next() {
		lock.lock();
		try{
			if(!hasNext()){
				return null;
			}
			DetailCapturerTask task = new DetailCapturerTask(queue.poll());
			task.setUuid(UUID.randomUUID().toString());
			return task;
		}finally{
			lock.unlock();
		}
	}

	@Override
	public boolean hasNext() {
		return queue.size() > 0;
	}

}
