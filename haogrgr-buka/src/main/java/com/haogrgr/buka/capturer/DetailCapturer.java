package com.haogrgr.buka.capturer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.haogrgr.buka.manager.DetailCapturerManager;
import com.haogrgr.buka.model.ComicsDetail;
import com.haogrgr.buka.task.model.DetailCapturerTask;
import com.haogrgr.buka.util.BukaUtils;
import com.haogrgr.buka.util.HttpUtils;

public class DetailCapturer extends Thread {

	private static final Logger logger = LoggerFactory.getLogger(DetailCapturer.class);
	private static final String PARAM = "i={\"f\":\"func_getdetail\",\"mid\":%s}&c=%s&z=1&p=android&v=4&cv=17170469";

	private HttpClient client;
	private volatile Boolean stop;
	private DetailCapturerManager manager;
	private Integer sleep;

	public DetailCapturer(String name, DetailCapturerManager manager, Integer sleep) {
		super(name);
		this.client = BukaUtils.client();
		this.manager = manager;
		this.stop = false;
		this.sleep = sleep;
	}

	@Override
	public void run() {
		while (!stop) {
			DetailCapturerTask task = manager.getTask();
			if (task != null) {
				Integer id = task.getData(Integer.class);
				String param = getParam(id);
				doTask(task, param);
			}
			try {
				if (!manager.hasTask()) {
					Thread.sleep(sleep + 10000);
				} else {
					Thread.sleep(sleep);
				}
			} catch (Exception e) {
				logger.warn("线程被中断");
			}
		}
	}

	public void doTask(DetailCapturerTask task, String param) {
		try {
			Integer id = task.getData(Integer.class);
			ComicsDetail detail = capture(id, param);

			reClient();

			task.setResult(detail);
			manager.complete(task);
		}catch(ConnectTimeoutException to){
			reClient();
			manager.error(task);
		}catch (Throwable t) {
			logger.error("抓取数据出错 : param=" + param, t);
			reClient();
			manager.error(task);
		}
	}

	public void reClient() {
		HttpUtils.closeClient(client);
		client = BukaUtils.client();
	}

	public ComicsDetail capture(Integer mid, String param) throws Exception {
		HttpPost post = BukaUtils.post(param);
		String exec = BukaUtils.exec(client, post);
		if (exec != null) {
			ComicsDetail parse = parse(mid, exec);
			return parse;
		}
		return null;
	}

	public ComicsDetail parse(Integer mid, String content) throws Exception {
		Integer index = content.indexOf("recomctrlparam");
		if (index < 0) {
			index = content.indexOf("recomwords");
		}
		if (index < 0) {
			index = content.indexOf("recomenter");
		}
		if (index < 0) {
			return null;
		}
		String text = content.substring(0, index);
		ComicsDetail detail = new ComicsDetail(mid);
		detail.setName(getProperty("\"name\":.*(?=,\"author\")", text));
		detail.setAuthor(getProperty("\"author\":[\\s\\S]*(?=,\"rate\")", text));
		detail.setIntro(getProperty("\"intro\":[\\s\\S]*(?=,\"lastup\")", text));
		detail.setLastuptimeex(getProperty("\"lastuptimeex\":.*(?=,\"resupno\")", text));
		detail.setFinish(getProperty("\"finish\":.*(?=,\"discount\")", text));
		
		String rate = getProperty("\"rate\":.*(?=,\"intro\")", text);
		detail.setRate(!StringUtils.isBlank(rate) ? Integer.valueOf(rate) : null);
		
		String popular = getProperty("\"popular\":.*(?=,\"populars\")", text);
		detail.setPopular(!StringUtils.isBlank(popular) ? Integer.valueOf(popular) : null);
		
		String favor = getProperty("\"favor\":.*(?=,\"finish\")", text);
		detail.setFavor(!StringUtils.isBlank(favor) ? Integer.valueOf(favor) : null);
		
		String discount = getProperty("\"discount\":.*(?=,\"recomctrltype\")", text);
		detail.setDiscount(!StringUtils.isBlank(discount) ? Integer.valueOf(discount) : null);
		
		return detail;
	}

	public String getProperty(String regex, String text) {
		Pattern p = Pattern.compile(regex);
		Matcher matcher = p.matcher(text);
		int indexOf = regex.indexOf("*");
		if (matcher.find()) {
			String group = matcher.group();
			if(group.indexOf("favor") != -1){
				indexOf = indexOf - 1;
			}
			if(group.indexOf("intro") != -1 || group.indexOf("author") != -1){
				indexOf = indexOf - 5;
			}
			String tempGroup = group.substring(indexOf);
			String result = tempGroup.replace("\"", "");
			return result.trim();
		}
		logger.warn("获取属性出错: regex : " + regex + "\n" + text);
		return null;
	}

	public void stopCapturer() {
		this.stop = true;
	}

	public static String getParam(Integer id) {
		return String.format(PARAM, id, BukaUtils.uuid());
	}
	
}
