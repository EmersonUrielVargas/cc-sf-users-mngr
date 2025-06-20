const { DynamoDBClient } = require("@aws-sdk/client-dynamodb");
const { PutCommand, DynamoDBDocumentClient } = require("@aws-sdk/lib-dynamodb");

const client = new DynamoDBClient({});
const docClient = DynamoDBDocumentClient.from(client);


const create = async (user) => {
    const command = new PutCommand({
        TableName: process.env.USERS_TABLE_NAME,
        Item: user
    });
    const response = await docClient.send(command);
    console.log(response);
    return response;
}

module.exports = { create };