package com.smsNotification.service;

import com.smsNotification.models.SmsSendRequest;

public interface SmsServiceProvider {

    public SmsSendRequest sendSMS(SmsSendRequest request, String fromNumber);
}
