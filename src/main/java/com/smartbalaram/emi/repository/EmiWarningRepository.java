package com.smartbalaram.emi.repository;

import com.smartbalaram.emi.model.EmiRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmiWarningRepository extends MongoRepository<EmiRequest, String> {
	void deleteByUserId(String userId);

}
