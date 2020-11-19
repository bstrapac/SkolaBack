package com.skole.project.exception;

import java.time.LocalDate;

public class Message extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String message;
	private LocalDate timestamp; 
	
	
	
	public Message(String message, LocalDate timestamp) {
		super();
		this.message = message;
		this.timestamp = timestamp;
	}

	public void setMessageEx(String message) {
		this.message = message;
	}

	public String getMessageEx() {
		return message;
	}

	public LocalDate getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDate timestamp) {
		this.timestamp = timestamp;
	}
}
