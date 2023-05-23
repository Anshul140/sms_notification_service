package com.smsNotification.controllers;

import com.smsNotification.models.SmsSendRequest;
import com.smsNotification.repo.SmsRepo;
import com.smsNotification.service.SmsMongoService;
import com.smsNotification.service.SmsServiceProvider;
import com.smsNotification.service.factory.SmsServiceProviderFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class AppController {

    @Autowired
    private SmsMongoService smsMongoService;

    @PostMapping("/sms")
    public SmsSendRequest processSMS(@RequestBody SmsSendRequest request) {
        log.info("processSMS Started.... SendRequest: "+request.toString());
        System.out.println("Method invoked...");
        SmsServiceProvider smsServiceProvider = SmsServiceProviderFactory.createSmsService(request.getServiceProviderName());
        return smsServiceProvider.sendSMS(request, "");
    }

    // list of all SMSs
    @GetMapping("/sms")
    public List<SmsSendRequest> getAllSMS() {
        return smsMongoService.getAllSMS();
    }

    @GetMapping("/sms/id/{id}")
    public SmsSendRequest getSMS(@PathVariable("id") String id) {
        return smsMongoService.getSMSById(id);
    }

    @GetMapping("/sms/{providerName}")
    public List<SmsSendRequest> getSMSByProviderName(@PathVariable("providerName") String providerName) {
        return smsMongoService.getSMSByProviderName(providerName);
    }


}
