package com.smsNotification.controllers;

import com.smsNotification.models.SmsSendRequest;
import com.smsNotification.service.SmsMongoService;
import com.smsNotification.service.SmsServiceProvider;
import com.smsNotification.service.factory.SmsServiceProviderFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class AppController {

    @Autowired
    private SmsMongoService smsMongoService;

    @PostMapping("/sms")
    public SmsSendRequest processSMS(@RequestBody SmsSendRequest request) {
        SmsServiceProvider smsServiceProvider = SmsServiceProviderFactory.createSmsService(request.getServiceProviderName());
        System.out.println(smsServiceProvider);
        return smsServiceProvider.sendSMS(request, "");
    }

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
