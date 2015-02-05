package com.haogrgr.buka.manager;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haogrgr.buka.capturer.DetailCapturer;
import com.haogrgr.buka.handler.ComicsDetailErrorHandler;
import com.haogrgr.buka.handler.ComicsDetailHandler;
import com.haogrgr.buka.task.generater.TaskGenerater;
import com.haogrgr.buka.task.model.DetailCapturerTask;

public class DetailCapturerManager {
	
	private static final Logger logger = LoggerFactory.getLogger(DetailCapturerManager.class);
	
	private Map<String, DetailCapturer> capturers = new ConcurrentHashMap<String, DetailCapturer>();
	private BlockingQueue<DetailCapturerTask> completes = new LinkedBlockingQueue<>(1000);
	private BlockingQueue<DetailCapturerTask> errors = new LinkedBlockingQueue<>(1000);
	private TaskGenerater taskGenerater = null;
	private ComicsDetailHandler resultHandler = null;
	private ComicsDetailErrorHandler errorTaskHandler = null;

	public DetailCapturerManager(TaskGenerater taskGenerater) {
		this.taskGenerater = taskGenerater;
	}

	public void start(int size) {
		for (int i = 0; i < size; i++) {
			String name = "detail-capturer-" + i;
			DetailCapturer capturer = new DetailCapturer(name, this, 100);
			capturers.put(name, capturer);
			capturer.start();
		}
		
		resultHandler = new ComicsDetailHandler(this);
		resultHandler.start();
		
		errorTaskHandler = new ComicsDetailErrorHandler(this);
		errorTaskHandler.start();
	}
	
	public void stop(){
		for (Entry<String, DetailCapturer> entry : capturers.entrySet()) {
			entry.getValue().stopCapturer();
		}
	}

	public boolean hasTask() {
		return taskGenerater.hasNext();
	}

	public DetailCapturerTask getTask() {
		return (DetailCapturerTask) taskGenerater.next();
	}
	
	public void complete(DetailCapturerTask task){
		if(task == null){
			return ;
		}
		try {
			completes.put(task);
		} catch (InterruptedException e) {
			logger.warn("线程被中断");
		}
	}
	
	public void error(DetailCapturerTask task){
		if(task == null){
			return ;
		}
		try {
			errors.put(task);
		} catch (InterruptedException e) {
			logger.warn("线程被中断");
		}
	}
	
	public DetailCapturerTask processComplete(){
		try {
			return completes.take();
		} catch (InterruptedException e) {
			logger.warn("线程被中断");
		}
		return null;
	}
	
	public DetailCapturerTask processError(){
		try {
			return errors.take();
		} catch (InterruptedException e) {
			logger.warn("线程被中断");
		}
		return null;
	}
}
