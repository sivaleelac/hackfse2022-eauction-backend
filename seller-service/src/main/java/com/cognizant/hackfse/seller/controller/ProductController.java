package com.cognizant.hackfse.seller.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.hackfse.seller.exception.AccessDeniedException;
import com.cognizant.hackfse.seller.exception.BadRequestException;
import com.cognizant.hackfse.seller.exception.NotFoundException;
import com.cognizant.hackfse.seller.model.Product;
import com.cognizant.hackfse.seller.model.Product.Category;
import com.cognizant.hackfse.seller.service.JWTTokenProvider;
import com.cognizant.hackfse.seller.service.SellerProductService;

@RestController
@CrossOrigin("http://localhost:3000")
public class ProductController {
	private static final Logger log = LoggerFactory.getLogger(ProductController.class);

	private final static String DATE_FORMAT = "dd/MM/yyyy";

	@Autowired
	private SellerProductService productService;

	@Autowired
	private JWTTokenProvider tokenProvider;

	@PostMapping("/api/v1/seller/add-product")
	public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product,
			@RequestHeader(value = "Authorization") String authorization) {
		String emailAddress = getPersonEmailAddressFromToken(authorization);

		validateRequest(product);

		Product addedProduct = productService.addProduct(product, emailAddress);

		return new ResponseEntity<>(addedProduct, HttpStatus.CREATED);
	}

	@GetMapping("/api/v1/seller/get-product/all")
	public ResponseEntity<List<Product>> getAllProducts(@RequestHeader(value = "Authorization") String authorization, @RequestParam(name = "classification") String classification) {
		log.info("----------- Inside ProductController --------");
		String emailAddress = getPersonEmailAddressFromToken(authorization);
		List<Product> products = productService.getAllProductDetails(emailAddress, classification);

		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@GetMapping("/api/v1/seller/get-product/{productId}")
	public ResponseEntity<Product> getProduct(@PathVariable("productId") String productId) {

		Product product = getProductDetails(productId);
		log.info("---- inside ProductController >> getProduct - {}", product);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@DeleteMapping("/api/v1/seller/delete/{productId}")
	public ResponseEntity<Product> deleteProduct(@PathVariable("productId") String productId) {

		Product product = getProductDetails(productId);

		validateBidEnddate(product.getBidEndDate());

		productService.deleteProduct(productId);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}

	private Product getProductDetails(String productId) {
		Product product = productService.findProductById(productId);
		if (product == null) {
			throw new NotFoundException("error.client.product.notFound");
		}
		return product;
	}

	private void validateRequest(Product product) {
		validateCategory(product.getCategory());
		validateBidEnddate(product.getBidEndDate());
	}

	private void validateCategory(String category) {
		if (StringUtils.isNotBlank(category) && !Category.isValidCategoryName(category)) {
			throw new BadRequestException("error.client.category.invalid");
		}
	}

	private void validateBidEnddate(String bidEnddate) {
		if (StringUtils.isNotBlank(bidEnddate)) {
			LocalDate formattedDate = getFormattedBidDate(bidEnddate);
			if (formattedDate.isBefore(LocalDate.now())) {
				throw new BadRequestException("Bid end date is before current date");
			}
		}
	}

	private LocalDate getFormattedBidDate(String bidEnddate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
		try {
			return LocalDate.parse(bidEnddate, formatter);
		} catch (DateTimeParseException e) {
			throw new BadRequestException("error.client.bidEndDate.invalid");
		}
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
