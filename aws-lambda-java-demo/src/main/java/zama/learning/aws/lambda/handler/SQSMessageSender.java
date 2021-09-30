package zama.learning.aws.lambda.handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import zama.learning.aws.lambda.constants.Constants;
import zama.learning.aws.lambda.util.Util;

public class SQSMessageSender implements RequestStreamHandler, Constants{

	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
		String inputMessage = "Testing-"+new Date();

		LambdaLogger logger = context.getLogger();
		String responseText = "Message send to SQS";
		logger.log("input: " + input);

		Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();

		SendMessageRequest sendMessageStandardQueue = new SendMessageRequest()
				.withQueueUrl(QUEUE_URL)
				.withMessageBody(inputMessage)
				.withMessageAttributes(messageAttributes);

		Util.getSQSClient().sendMessage(sendMessageStandardQueue);

		logger.log("Message sent to: " + QUEUE_URL);

		OutputStreamWriter writer = new OutputStreamWriter(output, "UTF-8");
		writer.write(Util.getAPIGatewayResponse(responseText).toString());
		writer.close();
	}
}
