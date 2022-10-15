package com.cognizant.hackfse.buyer.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cognizant.hackfse.buyer.exception.BadRequestException;
import com.cognizant.hackfse.buyer.model.BidderResource;
import com.cognizant.hackfse.buyer.model.Product;
import com.cognizant.hackfse.buyer.service.BidderService;

@SpringBootTest
public class BuyerBidControllerTest {

	/*@InjectMocks
	private BuyerBidController controller;

	@Mock
	private BidderService bidderService;

	@Test
	public void testToAddAProduct() {
		BidderResource request = buildBidderResource(null);
		when(bidderService.placeBid(request, "")).thenReturn(buildBidderResource("abcd"));
		ResponseEntity<BidderResource> response = controller.addProduct(request, "");
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("abcd", response.getBody().getId());
	}

	@Test
	public void testToThrowBadRequestWhenPersonIdIsBlankWhileAddingAProduct() {
		try {
			controller.addProduct(new BidderResource(), "");
		} catch (BadRequestException exp) {
			assertEquals("error.client.personId.blank", exp.getMessage());
		}
	}

	@Test
	public void testToThrowBadRequestWhenProductIdIsBlankWhileAddingAProduct() {
		try {
			BidderResource resource = new BidderResource();
			resource.setProduct(null);
			controller.addProduct(resource, "");
		} catch (BadRequestException exp) {
			assertEquals("error.client.productId.blank", exp.getMessage());
		}
	}
	
	@Test
	public void testToUpdateBid() {
		Mockito.doNothing().when(bidderService).updateBidAmount(anyString(), anyString(), anyString());
		ResponseEntity<BidderResource> response = controller.updateBid("productId", "emailAddress", "amount");
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	private BidderResource buildBidderResource(String id) {
		BidderResource resource = new BidderResource();
		resource.setId(id);
		Product product = new Product();
		product.setId("productId");
		resource.setProduct(product);
		return resource;
	}*/
}
