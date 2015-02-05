package com.haogrgr.buka.task.generater;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.haogrgr.buka.task.model.CapturerTask;
import com.haogrgr.buka.task.model.DetailCapturerTask;

public class DetailTaskGenerater implements TaskGenerater, Serializable {

	private static final long serialVersionUID = -2523521947113014118L;

	private static Lock lock = new ReentrantLock();

	private Integer end;
	private Long endTime;
	private Integer index = 0;
	private List<IgonDomain> igons = new ArrayList<IgonDomain>();

	public DetailTaskGenerater(Integer index, Integer end) {
		this.end = end;
		this.index = index;
	}

	public DetailTaskGenerater(Integer index, Long timeLength) {
		this.endTime = System.currentTimeMillis() + timeLength;
		this.index = index;
	}

	@Override
	public boolean hasNext() {
		return !end();
	}

	@Override
	public CapturerTask next() {
		lock.lock();
		try {
			DetailCapturerTask task = null;
			if (igon(index)) {
				index++;
				return null;
			}
			if (!end()) {
				task = new DetailCapturerTask(index++);
				task.setUuid(UUID.randomUUID().toString());
			}
			return task;
		} finally {
			lock.unlock();
		}
	}

	public boolean end() {
		Integer temp = index;
		if (end != null) {
			return temp > end;
		}
		if (endTime != null) {
			return System.currentTimeMillis() > endTime;
		}
		throw new RuntimeException("数据初始化不完全!");
	}

	public void addIgon(Integer start, Integer end) {
		igons.add(new IgonDomain(start, end));
	}

	public boolean igon(Integer id) {
		for (IgonDomain igon : igons) {
			if (igon.igon(id)) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		DetailTaskGenerater generater = new DetailTaskGenerater(1, 20);
		generater.addIgon(1, 5);

		while (generater.hasNext()) {
			System.out.println(generater.next());
		}
	}

	protected static class IgonDomain {
		Integer start;
		Integer end;

		public IgonDomain(Integer start, Integer end) {
			this.start = start;
			this.end = end;
		}

		public boolean igon(Integer id) {
			if (id >= start && id <= end) {
				return true;
			}
			return false;
		}
	}

}
