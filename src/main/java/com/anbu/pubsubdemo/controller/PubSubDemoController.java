package com.anbu.pubsubdemo.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.cloud.spring.pubsub.core.PubSubTemplate;
import com.google.cloud.spring.pubsub.integration.inbound.PubSubInboundChannelAdapter;

@RestController
public class PubSubDemoController {
	
	String message;
	@GetMapping("getMessage")
	public String getMessage() {
		return "Message from GCP "+message;
	}
	
	@Bean
	public PubSubInboundChannelAdapter messageAdapter (
			@Qualifier("inputChannel") MessageChannel inputChannel,
			PubSubTemplate template
			) {
		PubSubInboundChannelAdapter adapter = new PubSubInboundChannelAdapter(template, "punSubDemoSubscription");
		adapter.setOutputChannel(inputChannel);
		return adapter;
	}
	
	@Bean
	MessageChannel inputChannel() {
		return new DirectChannel();
	}
	
	@ServiceActivator(inputChannel = "inputChannel")
	public void receiveMessage(String paylod) {
		this.message = paylod;
	}
	
	public boolean solution(int[] A, int[] B, int P) {
		boolean result = true;
		Set<Integer> points = new HashSet<Integer>();
		List<String> uniqueValues = new ArrayList<String>();
		for(int i=0;i<A.length;i++) {
			if(uniqueValues.indexOf(A[0]+","+B[0]) == -1 && uniqueValues.indexOf(B[0]+","+A[0])==-1) {
				uniqueValues.add(A[0]+","+B[0]);
			}
		}
		for(String uniqueValue: uniqueValues) {
			String[] pointString = uniqueValue.split(",");
			points.add(Integer.parseInt(pointString[0]));
			points.add(Integer.parseInt(pointString[1]));
		}
		int checkNum=0;
		for(Integer num: points) {
			if(checkNum == 0) {
				checkNum = num;
			}
			else {
				if(checkNum == num+1) {
					//Do Nothing
				}else {
					result = false;
				}
			}
		}
		String s = "abcd";
		StringBuffer sb = new StringBuffer();
		if(s.length()>0) {
			int length = 3;
			String appendString = s.substring(0,length);
			sb.append(appendString);
			sb.append("-");
		}
		
		return result;
	}
}
