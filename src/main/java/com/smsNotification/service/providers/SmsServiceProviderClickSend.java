package com.smsNotification.service.providers;

import com.smsNotification.models.SmsSendRequest;
import com.smsNotification.repo.SmsRepo;
import com.smsNotification.service.SmsMongoService;
import com.smsNotification.service.SmsServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import java.util.UUID;


// dummy sms service provider for learning factory design pattern
@Service

public class SmsServiceProviderClickSend implements SmsServiceProvider {
    private final SmsRepo smsRepo;
    @Autowired
    public SmsServiceProviderClickSend(SmsRepo smsRepo) {
        this.smsRepo = smsRepo;

    }


    @Override
    public SmsSendRequest sendSMS(SmsSendRequest request, String fromNumber) {
        try {
            request.setId(UUID.randomUUID().toString().split("-")[0]);
            SmsSendRequest a=smsRepo.save(request);
            System.out.println(a.getId());
            return a;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
