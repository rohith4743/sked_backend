package com.rohithkankipati.projects.Sked.exception;

import org.springframework.http.HttpStatus;

public class SkedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final String errorCode;
	private final HttpStatus status;
	
	public SkedException(String errorCode, HttpStatus status) {
		super();
		this.errorCode = errorCode;
		this.status = status;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public HttpStatus getStatus() {
		return status;
	}
	
}
