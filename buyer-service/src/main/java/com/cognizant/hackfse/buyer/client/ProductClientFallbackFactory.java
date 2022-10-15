package com.cognizant.hackfse.buyer.client;

import org.springframework.cloud.openfeign.FallbackFactory;

import com.cognizant.hackfse.buyer.entity.BuyerBidderEntity.ProductEntity;

public class ProductClientFallbackFactory implements FallbackFactory<ProductClient> {

	@Override
	public ProductClient create(Throwable cause) {
		return new ProductClient() {
			@Override
			public ProductEntity getProductById(String productId) {
				throw new RuntimeException("Internal server error");
			}
		};
	}

}
