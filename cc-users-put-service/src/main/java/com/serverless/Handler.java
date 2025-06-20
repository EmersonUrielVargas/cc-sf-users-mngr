package com.serverless;

import java.util.Collections;
import java.util.Map;

import javax.print.DocFlavor.STRING;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.config.DynamoBdService;
import com.serverless.dto.UpdateUserDto;

import software.amazon.awssdk.services.dynamodb.model.Update;

public class Handler implements RequestHandler<APIGatewayProxyRequestEvent, ApiGatewayResponse> {

	private static final Logger LOG = LogManager.getLogger(Handler.class);
	private final DynamoBdService dynamoBdService = new DynamoBdService();

	@Override
	public ApiGatewayResponse handleRequest(APIGatewayProxyRequestEvent input, Context context) {
		LOG.info("received: " + input);
		Map<String, String> pathParameters = input.getPathParameters();
		String userId = pathParameters.get("id");
		int statusCode = 500;
		String responseMessage = "Error updating user with id " + userId;
		try {
			String requestBody = input.getBody();
			ObjectMapper objectMapper = new ObjectMapper();
			UpdateUserDto updateUserDto;
			updateUserDto = objectMapper.readValue(requestBody, UpdateUserDto.class);
			updateUserDto.setId(userId);
			boolean isOk = dynamoBdService.updateUser(updateUserDto);
			statusCode = isOk ? 200 : statusCode;
			responseMessage = "User with id " + userId + " update successfully";
		} catch (JsonMappingException e) {
			LOG.error("Error mapping JSON to UpdateUserDto: " + e.getMessage());
			statusCode = 400;
			responseMessage = "Invalid request body format";
		} catch (Exception e) {
			LOG.error("Error processing event: " + e.getMessage());
			statusCode = 500;
			responseMessage = "Error processing event";
		}
		Response responseBody = new Response(responseMessage);
		return ApiGatewayResponse.builder()
				.setStatusCode(statusCode)
				.setObjectBody(responseBody)
				.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless"))
				.build();
	}
}
