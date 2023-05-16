package com.smsNotification.controllers;

import com.smsNotification.models.SmsSendRequest;
import com.smsNotification.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class AppController {

    @Autowired
    private SmsService smsService;

    @PostMapping("/sms")
    public String processSMS(@RequestBody SmsSendRequest smsSendRequest) {

        log.info("processSMS Started.... SendRequest: "+smsSendRequest.toString());
        return smsService.sendSMS(smsSendRequest, "");
    }

}
