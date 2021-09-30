package zama.learning.aws.lambda.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WelcomeHandler implements RequestHandler<String, String>{
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	@Override
	public String handleRequest(String event, Context context){
		LambdaLogger logger = context.getLogger();
		logger.log("EVENT: " + gson.toJson(event));
		logger.log("EVENT TYPE: " + event.getClass().toString());
		
		return "Welcome to AWS Lambda Training: Mr/Ms. "+ event ;
	}
}