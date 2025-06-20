package com.serverless;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;

public class Response {

	private final String message;
	private final APIGatewayProxyRequestEvent input;

	public Response(String message, APIGatewayProxyRequestEvent input) {
		this.message = message;
		this.input = input;
	}

	public Response(String message) {
		this.message = message;
		this.input = null;
	}

	public String getMessage() {
		return this.message;
	}

	public APIGatewayProxyRequestEvent getInput() {
		return this.input;
	}
}
