package com.smsNotification.service.implementations;

import com.smsNotification.models.SmsSendRequest;
import com.smsNotification.repo.SmsRepo;
import com.smsNotification.service.SmsMongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SmsMongoServiceImpl implements SmsMongoService {

    @Autowired
    private SmsRepo smsRepo;
    @Override
    public List<SmsSendRequest> getAllSMS() {
        return smsRepo.findAll();
    }

    @Override
    public SmsSendRequest getSMSById(String id) {
        return smsRepo.findById(id).get();
    }

    @Override
    public List<SmsSendRequest> getSMSByProviderName(String providerName) {
        return smsRepo.findByServiceProviderName(providerName);
    }

    @Override
    public SmsSendRequest save(SmsSendRequest request) {
        request.setId(UUID.randomUUID().toString().split("-")[0]);
        return smsRepo.save(request);
    }
}
