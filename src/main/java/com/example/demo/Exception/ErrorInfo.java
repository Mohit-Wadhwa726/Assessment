package com.example.demo.Exception;

import java.util.Date;

public class ErrorInfo {

	private String message;
	private String information;
	private Date timeStamp;

	public ErrorInfo(String message, String information, Date timeStamp) {
		super();
		this.message = message;
		this.information = information;
		this.timeStamp = timeStamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

}