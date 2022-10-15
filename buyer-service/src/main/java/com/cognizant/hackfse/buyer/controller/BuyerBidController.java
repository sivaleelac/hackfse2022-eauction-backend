package com.cognizant.hackfse.buyer.controller;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.hackfse.buyer.exception.AccessDeniedException;
import com.cognizant.hackfse.buyer.exception.BadRequestException;
import com.cognizant.hackfse.buyer.model.BidderResource;
import com.cognizant.hackfse.buyer.service.BidderService;
import com.cognizant.hackfse.buyer.service.JWTTokenProvider;

@RestController
@CrossOrigin("http://localhost:3000")
public class BuyerBidController {
	private static final Logger log = LoggerFactory.getLogger(BuyerBidController.class);

	@Autowired
	private BidderService bidderService;

	@Autowired
	private JWTTokenProvider tokenProvider;

	@GetMapping("/api/v1/buyer/get-bids/{productId}")
	public ResponseEntity<BidderResource> getBid(@RequestHeader(value = "Authorization") String authorization, @PathVariable(name = "productId") String productId) {
		String emailAddress = getPersonEmailAddressFromToken(authorization);
		BidderResource response = bidderService.getBid(emailAddress, productId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/api/v1/buyer/place-bid")
	public ResponseEntity<BidderResource> addProduct(@Valid @RequestBody BidderResource bidder,
			@RequestHeader(value = "Authorization") String authorization) {
		String emailAddress = getPersonEmailAddressFromToken(authorization);
		validateRequest(bidder);
		BidderResource response = bidderService.placeBid(bidder, emailAddress);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	private void validateRequest(BidderResource bidder) {
		if (bidder.getProduct() == null || StringUtils.isBlank(bidder.getProduct().getId())) {
			throw new BadRequestException("error.client.productId.blank");
		}
	}

	@PutMapping("/api/v1/buyer/update-bid/{productId}/{newBidAmount}")
	public ResponseEntity<BidderResource> updateBid(@RequestHeader(value = "Authorization") String authorization, @PathVariable("productId") String productId,
			@PathVariable("newBidAmount") String newBidAmount) {
		String emailAddress = getPersonEmailAddressFromToken(authorization);

		bidderService.updateBidAmount(productId, emailAddress, newBidAmount);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	private String getPersonEmailAddressFromToken(String bearerToken) {
		String emailAddress = null;
		if (StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith("Bearer ")) {
			String token = bearerToken.substring(7, bearerToken.length());

			if (StringUtils.isNotBlank(token) && tokenProvider.validateToken(token)) {
				log.info("Token is Valid ");
				emailAddress = tokenProvider.getUserNameFromToken(token);
				log.info("emailAddress -> {}", emailAddress);
			}
		}

		if (StringUtils.isBlank(emailAddress)) {
			throw new AccessDeniedException("Access Denied");
		}
		return emailAddress;
	}
}
