package com.serverless;

public class Response {

	private final String message;
	private String code = "0-000";

	public Response(String message, String code) {
		this.message = message;
		this.code = code;
	}
	public Response(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

	public String getInput() {
		return this.code;
	}
}
