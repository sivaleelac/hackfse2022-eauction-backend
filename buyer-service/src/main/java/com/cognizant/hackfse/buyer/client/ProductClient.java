package com.cognizant.hackfse.buyer.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cognizant.hackfse.buyer.entity.BuyerBidderEntity.ProductEntity;

@FeignClient(name = "seller-service", fallbackFactory = ProductClientFallbackFactory.class)
public interface ProductClient {

	@GetMapping(value = "/api/v1/seller/get-product/{productId}")
	public ProductEntity getProductById(@PathVariable(value = "productId") String productId);
}
