package com.cognizant.hackfse.seller.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cognizant.hackfse.seller.model.Bidder;
import com.cognizant.hackfse.seller.service.BuyerBidderService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class QueueConsumer {
	private static final Logger log = LoggerFactory.getLogger(QueueConsumer.class);

	@Autowired
	private BuyerBidderService buyerBidderService;
	
	public void receiveMessage(String message) {
		log.info("Received {} ", message);
		processMessage(message);
	}

	public void receiveMessage(byte[] message) {
		String strMessage = new String(message);
		log.info("Received1 {} ", strMessage);
		processMessage(strMessage);
	}

	private void processMessage(String message) {
		try {
			log.info("processMessage {} ", message);
			Bidder bidder = new ObjectMapper().readValue(message, Bidder.class);
			buyerBidderService.createOrUpdateBidderAndProductDetails(bidder);
		} catch (JsonParseException e) {
			log.warn("Bad JSON in message: " + message);
		} catch (JsonMappingException e) {
			log.warn("cannot map JSON to BidderRequest: " + message);
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}
}
