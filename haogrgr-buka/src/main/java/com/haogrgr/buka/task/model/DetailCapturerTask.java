package com.haogrgr.buka.task.model;

import com.haogrgr.buka.model.ComicsDetail;

public class DetailCapturerTask extends CapturerTask {

	private static final long serialVersionUID = 976314830842198753L;

	private Integer detailId;
	private ComicsDetail result;

	public DetailCapturerTask() {
		super();
	}

	public DetailCapturerTask(Integer detailId) {
		this.detailId = detailId;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getData(Class<T> clazz) {
		return (T) detailId;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getResult(Class<T> clazz) {
		return (T) result;
	}

	public void setResult(ComicsDetail result) {
		this.result = result;
	}
	
	public boolean hasResult(){
		return result != null;
	}
	
	@Override
	public String toString() {
		return "DetailTask [detailId=" + detailId + ", result=" + result + ", getUuid()=" + getUuid() + "]";
	}

}
