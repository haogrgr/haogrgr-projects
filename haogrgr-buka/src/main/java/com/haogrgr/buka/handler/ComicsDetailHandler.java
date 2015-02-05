package com.haogrgr.buka.handler;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haogrgr.buka.manager.DetailCapturerManager;
import com.haogrgr.buka.model.ComicsDetail;
import com.haogrgr.buka.task.model.DetailCapturerTask;
import com.haogrgr.buka.util.DBToolKit;

public class ComicsDetailHandler extends Thread {
	
	private static final Logger logger = LoggerFactory.getLogger(ComicsDetailHandler.class);
	
	private DetailCapturerManager manager;

	public ComicsDetailHandler(DetailCapturerManager manager) {
		super("comics-detail-handler");
		this.manager = manager;
	}
	
	@Override
	public void run() {
		while(true){
			DetailCapturerTask task = manager.processComplete();
			try{
				process(task);
			}catch(Exception e){
				logger.error(task != null ? task.toString() : "任务为空", e);
			}
		}
	}
	
	public void process(DetailCapturerTask task) throws Exception {
		if (task == null || !task.hasResult()) {
			return ;
		}
		ComicsDetail result = task.getResult(ComicsDetail.class);
		insert(result);
	}

	public void insert(ComicsDetail bean) throws Exception {
		Connection conn = null;
		PreparedStatement stmt = null;
		String sql = "insert into comics_detail(mid, name, author, intro, rate, lastuptimeex, popular, favor, finish, discount, modify_time, create_time) " 
				   + "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			conn = DBToolKit.getConnection();
			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, bean.getMid());
			stmt.setString(2, bean.getName());
			stmt.setString(3, bean.getAuthor());
			stmt.setString(4, bean.getIntro());
			stmt.setInt(5, bean.getRate());
			stmt.setString(6, bean.getLastuptimeex());
			stmt.setInt(7, bean.getPopular());
			stmt.setInt(8, bean.getFavor());
			stmt.setString(9, bean.getFinish());
			stmt.setInt(10, bean.getDiscount());

			stmt.execute();
		} finally {
			DBToolKit.free(conn, stmt, null);
		}
	}

}
