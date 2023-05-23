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

    @Autowired
    private SmsRepo smsRepo;

    @Autowired
    private SmsMongoService smsMongoService;

    @Override
    public SmsSendRequest sendSMS(SmsSendRequest request, String fromNumber) {
        //request.setId(UUID.randomUUID().toString().split("-")[0]);
        //ystem.out.println("new generated id: "+request.getId());
        return smsMongoService.save(request);
//        return "success";
    }

}
