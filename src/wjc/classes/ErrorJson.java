package wjc.classes;

import java.util.Date;

public class ErrorJson {
	public String errCode;
	public String errMsg;
	public String errTime = Long.toString(new Date().getTime() / 1000);

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getErrTime() {
		return errTime;
	}

	public void setErrTime(String errTime) {
		this.errTime = errTime;
	}
}
