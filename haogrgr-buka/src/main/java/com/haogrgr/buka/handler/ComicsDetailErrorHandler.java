package com.haogrgr.buka.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haogrgr.buka.manager.DetailCapturerManager;
import com.haogrgr.buka.task.model.DetailCapturerTask;

public class ComicsDetailErrorHandler extends Thread {

	private static final Logger logger = LoggerFactory.getLogger(ComicsDetailErrorHandler.class);

	private DetailCapturerManager manager;

	public ComicsDetailErrorHandler(DetailCapturerManager manager) {
		super("comics-detail-error-handler");
		this.manager = manager;
	}

	@Override
	public void run() {
		while (true) {
			DetailCapturerTask task = manager.processError();
			try {
				if (task != null) {
					logger.error("任务失败:" + task.toString());
				}
			} catch (Exception e) {
				logger.error(task != null ? task.toString() : "任务为空", e);
			}
		}
	}

}
