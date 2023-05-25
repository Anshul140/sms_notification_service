package com.smsNotification.repo;

import com.smsNotification.models.SmsSendRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SmsRepo extends MongoRepository<SmsSendRequest, String> {
    List<SmsSendRequest> findByServiceProviderName(String serviceProviderName);
}
