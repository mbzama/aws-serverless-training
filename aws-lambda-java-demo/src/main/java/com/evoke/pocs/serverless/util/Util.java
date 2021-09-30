package com.evoke.pocs.serverless.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;

public class Util {
	private static final Logger logger = LogManager.getLogger(Util.class.getName());

	public static AWSCredentials getAWSCredentails() {
		return new BasicAWSCredentials(
				"AKIA4VIQTM34LQ5R65R3",
				"Mm/yO9JJgegPl/Mks16zcPxZGa9fZTFk0UYPrhFo"
				);
	}

	public static AmazonSQS getSQSClient() {
		return AmazonSQSClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(getAWSCredentails()))
				.withRegion(Regions.US_EAST_1)
				.build();
	}

	public static JSONObject getAPIGatewayResponse(String responseText) {
		JSONObject responseJson = new JSONObject();
		JSONObject responseBody = new JSONObject();
		responseBody.put("message", responseText);

		JSONObject headerJson = new JSONObject();
		headerJson.put("x-custom-header", "my custom header value");

		responseJson.put("statusCode", 200);
		responseJson.put("headers", headerJson);
		responseJson.put("body", responseBody.toString());
		return responseJson;
	}

	public static String callBonitaURLWithJavaLib(String endpointUrl) {
		System.setProperty("http.agent", "Chrome");
		StringBuilder content = new StringBuilder();
		try {
			URL url = new URL(endpointUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setAllowUserInteraction(false);

			int status = con.getResponseCode();

			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
			String inputLine;
			
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			logger.info("API - Status: {}, Response: {}", status, content.toString());
			in.close();
			con.disconnect();
		}catch (Exception e) {
			logger.error("Error while calling Bonita API: {}", e);
		}
		return content.toString();
	}
}
