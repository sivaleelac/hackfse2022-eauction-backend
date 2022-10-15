package com.cognizant.hackfse.buyer.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.cognizant.hackfse.buyer.model.Person;

import lombok.Data;

@Document(collection = "bidder")
@Data
public class BuyerBidderEntity {
	@Id
	private String id;
	private Person person;
	private ProductEntity product;
	private String bidAmount;
	
	@Data
	public static class ProductEntity {
		private String id;
		private String productName;
		private String shortDescription;
		private String detailedDescription;
		private String category;
		private String startingPrice;
		private String bidEndDate;
	}
}
