const { v4 } =  require('uuid');
const { create } = require('./dynamoBdClient');
const SQSHelper = require('../adapter/SQSHelper');


async function createUser(userData) {
    const userId = v4();
    const user = {
        id: userId,
        ...userData
    };
    const rs = await create(user);
    await notifyCreate(user);
    return {
        statusCode: rs.$metadata.httpStatusCode,
        data: user
    };
}

async function notifyCreate(userData) {
    const sqs = new SQSHelper();
    const message = {
        QueueUrl: process.env.SQS_QUEUE_URL,
        MaxNumberOfMessages: 1,
        MessageBody: JSON.stringify(userData)
    };
    const responsePublish = await sqs.sendMessage(message);
    console.log("Message sent to SQS:", responsePublish);
}

module.exports = { createUser };