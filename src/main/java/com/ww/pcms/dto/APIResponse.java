package com.ww.pcms.dto;

public class APIResponse<T> {

	public APIResponse() {}
	
	public APIResponse(int recordCount, T response) {
		this.recordCount = recordCount;
		this.response = response;
	}
	
    int recordCount;
    T response;
    
	public int getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
	public T getResponse() {
		return response;
	}
	public void setResponse(T response) {
		this.response = response;
	}

}
