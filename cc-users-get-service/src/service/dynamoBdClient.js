const { DynamoDBClient } = require("@aws-sdk/client-dynamodb");
const { QueryCommand, DynamoDBDocumentClient } = require("@aws-sdk/lib-dynamodb");

const client = new DynamoDBClient({});
const docClient = DynamoDBDocumentClient.from(client);


const getUserById = async (id) => {
    const command = new QueryCommand({
        TableName: process.env.USERS_TABLE_NAME,
        KeyConditionExpression:
            "id = :id",
        ExpressionAttributeValues: {
            ":id": id,
        },
        ConsistentRead: true,
    });
    const response = await docClient.send(command);
    console.log(response);
    return response.Items[0] || null;
}

module.exports = { getUserById };