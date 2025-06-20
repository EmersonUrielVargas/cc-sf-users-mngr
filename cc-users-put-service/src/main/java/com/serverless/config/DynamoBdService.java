package com.serverless.config;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.serverless.dto.UpdateUserDto;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeAction;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.AttributeValueUpdate;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemRequest;
import software.amazon.awssdk.services.dynamodb.model.UpdateItemResponse;


public class DynamoBdService{

    private DynamoDbClient dynamoBdClient;
    private final Region region = Region.US_EAST_1;
    private static final Logger LOG = LogManager.getLogger(DynamoBdService.class);


    public DynamoBdService() {
        initDynamoDbClient();
    }

    private void initDynamoDbClient() {
       this.dynamoBdClient = DynamoDbClient.builder()
                .region(region)
                .build();
    }

    public boolean updateUser(UpdateUserDto updateUserDto) {
        UpdateItemRequest request = UpdateItemRequest.builder()
            .tableName(System.getenv("USERS_TABLE_NAME"))
            .key(Map.of(
                "id", AttributeValue.builder().s(updateUserDto.getId()).build()
            ))
            .attributeUpdates(Map.of(
                "name", AttributeValueUpdate.builder()
                        .value(AttributeValue.builder().s(updateUserDto.getName()).build())
                        .action(AttributeAction.PUT)
                        .build(),
                "email", AttributeValueUpdate.builder()
                        .value(AttributeValue.builder().s(updateUserDto.getEmail()).build())
                        .action(AttributeAction.PUT)
                        .build(),
                "lastname", AttributeValueUpdate.builder()
                        .value(AttributeValue.builder().s(updateUserDto.getLastname()).build())
                        .action(AttributeAction.PUT)
                        .build()
            ))
            .build();
        
        UpdateItemResponse response = dynamoBdClient.updateItem(request);
        LOG.info("response dynamoBd : " + response);
        return response.sdkHttpResponse().isSuccessful();
    }
    
}
