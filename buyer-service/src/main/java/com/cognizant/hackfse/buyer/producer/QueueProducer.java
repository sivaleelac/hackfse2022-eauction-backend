package com.cognizant.hackfse.buyer.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cognizant.hackfse.buyer.entity.BuyerBidderEntity;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class QueueProducer {
	private static final Logger log = LoggerFactory.getLogger(QueueProducer.class);

	@Value("${fanout.exchange}")
	private String fanoutExchange;

	private final RabbitTemplate rabbitTemplate;

	@Autowired
	public QueueProducer(RabbitTemplate rabbitTemplate) {
		super();
		this.rabbitTemplate = rabbitTemplate;
	}

	public void produce(BuyerBidderEntity buyerBidderEntity) throws Exception {
		log.info("Storing notification...");
		rabbitTemplate.setExchange(fanoutExchange);
		rabbitTemplate.convertAndSend(new ObjectMapper().writeValueAsString(buyerBidderEntity));
		log.info("Notification stored in queue sucessfully");
	}
}
