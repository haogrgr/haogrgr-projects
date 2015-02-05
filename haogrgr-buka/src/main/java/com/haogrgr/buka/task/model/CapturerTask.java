package com.haogrgr.buka.task.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class CapturerTask implements Serializable {

	private static final long serialVersionUID = -3889426952970486994L;

	private String uuid;
	private Map<String, Object> attrs = new HashMap<String, Object>();

	public CapturerTask() {
		super();
	}

	public CapturerTask(String uuid) {
		this.uuid = uuid;
	}

	public abstract <T> T getData(Class<T> clazz);
	public abstract <T> T getResult(Class<T> clazz);

	@SuppressWarnings("unchecked")
	public <T> T getAttr(String name, Class<T> clazz) {
		T value = null;
		if (attrs.containsKey(name)) {
			value = (T) attrs.get(name);
		}
		return value;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public String toString() {
		return "BukaTask [uuid=" + uuid + ", attrs=" + attrs + "]";
	}

}
