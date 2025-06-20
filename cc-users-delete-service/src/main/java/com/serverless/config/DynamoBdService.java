package com.serverless.config;

import java.util.Map;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemResponse;


public class DynamoBdService{

    private DynamoDbClient dynamoBdClient;
    private final Region region = Region.US_EAST_1;

    public DynamoBdService() {
        initDynamoDbClient();
    }

    private void initDynamoDbClient() {
       this.dynamoBdClient = DynamoDbClient.builder()
                .region(region)
                .build();
    }

    public boolean deleteUser(String id) {
        DeleteItemRequest request = DeleteItemRequest.builder()
            .tableName(System.getenv("USERS_TABLE_NAME"))
            .key(Map.of(
                "id", AttributeValue.builder().s(id).build()
            )).build();
        DeleteItemResponse response = dynamoBdClient.deleteItem(request);
        return response.sdkHttpResponse().isSuccessful();
    }
    
}
