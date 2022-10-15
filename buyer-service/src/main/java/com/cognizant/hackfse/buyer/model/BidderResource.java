package com.cognizant.hackfse.buyer.model;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class BidderResource {
	private String id;
	
	private Product product;
	
	@NotBlank(message = "error.client.bidAmount.blank")
	private String bidAmount;
}
