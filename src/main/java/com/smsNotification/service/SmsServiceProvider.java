package com.smsNotification.service;

import com.smsNotification.models.SmsSendRequest;
import org.springframework.stereotype.Service;
@Service
public interface SmsServiceProvider {

    public SmsSendRequest sendSMS(SmsSendRequest request, String fromNumber);
}
