package com.smsNotification.service.providers;

import com.smsNotification.models.SmsSendRequest;
import com.smsNotification.repo.SmsRepo;
import com.smsNotification.service.SmsServiceProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;


// dummy sms service provider for learning factory design pattern
@Service
@Slf4j
public class SmsServiceProviderClickSend implements SmsServiceProvider {
    private final SmsRepo smsRepo;
    public SmsServiceProviderClickSend(SmsRepo smsRepo) {
        this.smsRepo = smsRepo;
    }

    @Override
    public SmsSendRequest sendSMS(SmsSendRequest request, String fromNumber) {
        try {
            request.setId(UUID.randomUUID().toString().split("-")[0]);
            log.info(request.getId());
            log.info("SMS sent successfully... Data stored to DB!");
            return smsRepo.save(request);
        } catch (Exception e) {
            log.info(e.getMessage());
            return null;
        }
    }

}
