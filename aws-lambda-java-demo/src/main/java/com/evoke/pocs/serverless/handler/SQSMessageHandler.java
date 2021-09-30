package com.evoke.pocs.serverless.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent.SQSMessage;
import com.evoke.pocs.serverless.util.Util;


public class SQSMessageHandler implements RequestHandler<SQSEvent, Void>{
	
    @Override
    public Void handleRequest(SQSEvent event, Context context){
    	LambdaLogger logger = context.getLogger();
    	
        for(SQSMessage msg : event.getRecords()){
        	logger.log("Message Received from SQS: "+msg.getBody());
        	
        	
        	String bonitaAPIUrl = System.getenv("bonitaAPIUrl");
        	logger.log("bonitaAPIUrl: "+bonitaAPIUrl);
        	
        	//Call Bonita API
        	if(bonitaAPIUrl != null) {
        		logger.log("Calling Bonita API");
        		String response = Util.callBonitaURLWithJavaLib(bonitaAPIUrl);
        		logger.log("Bonita API response: "+response);
        	}
        }
        return null;
    }
}