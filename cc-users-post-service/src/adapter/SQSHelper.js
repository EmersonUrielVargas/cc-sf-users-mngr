const { SQSClient, SendMessageCommand } = require('@aws-sdk/client-sqs');

class SQSHelper {
    constructor() {
        this.client = new SQSClient({});
    }

    async sendMessage(message) {
        const command = new SendMessageCommand(message);
        try {
            const response = await this.client.send(command);
            console.log('Message sent successfully:', response);
            return response;
        } catch (error) {
            console.error('Error sending message:', error);
            throw error;
        }
    }
}

module.exports = SQSHelper;