package com.cognizant.hackfse.buyer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.cognizant.hackfse.buyer.entity.BuyerBidderEntity;

@Repository
public interface BidderRepository extends MongoRepository<BuyerBidderEntity, String>{
	
	@Query("{ 'product._id' : ObjectId(?0), 'person.emailAddress' : ?1 }")
	BuyerBidderEntity findByProductIdAndPersonEmailAddress(String productId, String emailAddress);

}
