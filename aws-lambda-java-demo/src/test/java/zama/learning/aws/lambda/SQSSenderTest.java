package zama.learning.aws.lambda;


import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import zama.learning.aws.lambda.constants.Constants;
import zama.learning.aws.lambda.util.Util;

public class SQSSenderTest {
	private static final Logger logger = LogManager.getLogger(SQSSenderTest.class.getName());

	public static void main(String[] args) {
		//sendMessageToSQS();
		String bonitaAPIUrl = System.getenv("bonitaAPIUrl");
		logger.info("Calling URL: {}", bonitaAPIUrl);
		Util.callAPIWithJavaLib(bonitaAPIUrl);
	}


	private static void sendMessageToSQS() {
		AmazonSQS sqs = AmazonSQSClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(Util.getAWSCredentails()))
				.withRegion(Regions.US_EAST_1)
				.build();

		String response = "Running from local IDE";

		Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();

		SendMessageRequest sendMessageStandardQueue = new SendMessageRequest()
				.withQueueUrl(Constants.QUEUE_URL)
				.withMessageBody("Message from Testcase ----- 9:21")
				.withMessageAttributes(messageAttributes);

		sqs.sendMessage(sendMessageStandardQueue);

		logger.info("Message sent to: " + Constants.QUEUE_URL);
	}
}
