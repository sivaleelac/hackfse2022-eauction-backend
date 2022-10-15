package com.cognizant.hackfse.seller.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.hackfse.seller.client.PersonClient;
import com.cognizant.hackfse.seller.entity.BuyerProductBidEntity;
import com.cognizant.hackfse.seller.entity.ProductEntity;
import com.cognizant.hackfse.seller.exception.InternalServerException;
import com.cognizant.hackfse.seller.exception.NotFoundException;
import com.cognizant.hackfse.seller.model.Person;
import com.cognizant.hackfse.seller.model.ProductBidder;
import com.cognizant.hackfse.seller.model.Product;
import com.cognizant.hackfse.seller.repository.BuyerProductBidRepository;
import com.cognizant.hackfse.seller.repository.SellerProductRepository;

@Service
public class SellerProductService {

	@Autowired
	private SellerProductRepository sellerRepository;
	@Autowired
	private BuyerProductBidRepository buyerProductBidRepository;

	@Autowired
	private PersonClient personClient;

	public Product addProduct(Product sellerProduct, String emailAddress) {
		Person person = validateAndGetPersonByEmailAddress(emailAddress);
		try {
			ModelMapper mapper = new ModelMapper();
			ProductEntity sellerEntityResponse = sellerRepository
					.save(buildSellerProductEntityRequest(sellerProduct, person, mapper));
			return mapper.map(sellerEntityResponse, Product.class);
		} catch (Exception exp) {
			throw new InternalServerException("Internal server error");
		}
	}

	public List<Product> getAllProductDetails(String emailAddress, String classification) {
		try {
			ModelMapper mapper = new ModelMapper();
			List<ProductEntity> productEntityList = new ArrayList<>();
			if("buyer".equalsIgnoreCase(classification)) {
				productEntityList = sellerRepository.findAll();	
			}else {
				productEntityList = sellerRepository.findAllByPersonEmailAddress(emailAddress);	
			}
			if (productEntityList != null) {
				Type listType = new TypeToken<List<Product>>() {}.getType();
				return mapper.map(productEntityList, listType);
			}
		} catch (Exception exp) {
			throw new InternalServerException("Something went wrong, please try again");
		}
		return Collections.emptyList();
	}

	public Product findProductById(String productId) {
		Product product = null;
		try {
			ModelMapper mapper = new ModelMapper();
			Optional<ProductEntity> productEntityOptional = sellerRepository.findById(productId);
			if (productEntityOptional.isPresent()) {
				return mapper.map(productEntityOptional.get(), Product.class);
			}
		} catch (Exception exp) {
			throw new InternalServerException("Something went wrong, please try again");
		}
		return product;
	}

	public void deleteProduct(String productId) {
		try {
			sellerRepository.deleteById(productId);
		} catch (Exception exp) {
			throw new InternalServerException("Something went wrong, please try again");
		}
	}

	public ProductBidder getProductWithBidDetails(String productId) {
		try {
			ModelMapper mapper = new ModelMapper();
			Optional<BuyerProductBidEntity> buyerProductBidEntityOptional = buyerProductBidRepository
					.findByProductId(productId);
			if (buyerProductBidEntityOptional.isPresent()) {
				return mapper.map(buyerProductBidEntityOptional.get(), ProductBidder.class);
			} else {
				throw new NotFoundException("Product not found");
			}
		} catch (Exception exp) {
			throw new InternalServerException("Unable to fetch product details for product -> "+productId+ "exp - " +exp);
		}
	}

	private ProductEntity buildSellerProductEntityRequest(Product sellerProduct, Person person,
			ModelMapper mapper) {
		ProductEntity sellerProductEntity = mapper.map(sellerProduct, ProductEntity.class);
		sellerProductEntity.setPerson(person);
		return sellerProductEntity;
	}

	private Person validateAndGetPersonByEmailAddress(String emailAddress) {
		try {
			return personClient.getPersonByEmailAddress(emailAddress);
		} catch (Exception exp) {
			throw new InternalServerException("Person not found");
		}
	}
}
