package com.cognizant.hackfse.seller.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ProductBidder {
	private String id;
	private Product product;
	private List<Bid> bids = new ArrayList<>();
	
	@Data
	public static class Bid{
		private Person person;
		private String bidAmount;
	}
}
