package com.cognizant.hackfse.seller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.hackfse.seller.model.ProductBidder;
import com.cognizant.hackfse.seller.service.SellerProductService;

@RestController
@CrossOrigin("http://localhost:3000")
public class SellerBidController {

	@Autowired
	private SellerProductService sellerProductService;
	
	@GetMapping("/api/v1/seller/show-bids/{productId}")
	public ResponseEntity<ProductBidder> getProduct(@PathVariable("productId") String productId) {
		ProductBidder productBidder = sellerProductService.getProductWithBidDetails(productId);
		return new ResponseEntity<>(productBidder, HttpStatus.OK);
	}
}
