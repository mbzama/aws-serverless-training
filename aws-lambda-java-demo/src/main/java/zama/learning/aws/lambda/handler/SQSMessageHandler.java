package zama.learning.aws.lambda.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;

import zama.learning.aws.lambda.util.Util;


public class SQSMessageHandler implements RequestHandler<SQSEvent, Void>{

	@Override
	public Void handleRequest(SQSEvent event, Context context){
		LambdaLogger logger = context.getLogger();

		for(SQSMessage msg : event.getRecords()){
			logger.log("Message Received from SQS: "+msg.getBody());

			String apiUrl = System.getenv("apiUrl");
			logger.log("bonitaAPIUrl: "+apiUrl);

			if(apiUrl != null) {
				logger.log("Calling API");
				String response = Util.callAPIWithJavaLib(apiUrl);
				logger.log("Bonita API response: "+response);
			}
		}
		return null;
	}
}