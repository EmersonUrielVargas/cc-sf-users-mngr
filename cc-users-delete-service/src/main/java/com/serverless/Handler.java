package com.serverless;

import java.util.Collections;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.serverless.config.DynamoBdService;

public class Handler implements RequestHandler<APIGatewayProxyRequestEvent, ApiGatewayResponse> {

	private static final Logger LOG = LogManager.getLogger(Handler.class);
	private final DynamoBdService dynamoBdService = new DynamoBdService();

	@Override
	public ApiGatewayResponse handleRequest(APIGatewayProxyRequestEvent input, Context context) {
		LOG.info("received: " + input);
		Map<String, String> pathParameters = input.getPathParameters();
		String userId = pathParameters.get("id");
		boolean isOk = dynamoBdService.deleteUser(userId);
		int statusCode = isOk ? 200 : 500;
		Response responseBody = new Response("User with id " + userId + " deleted successfully");
		return ApiGatewayResponse.builder()
				.setStatusCode(statusCode)
				.setObjectBody(responseBody)
				.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless"))
				.build();
	}
}
