package com.huarun.gohome.response;

public class BaseResp {
	public static final String SUCCESS = "success";
	private String result = "fail";
	private String errMsg = "";
	private Object data;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
