package com.smsNotification.service;

import com.smsNotification.models.SmsSendRequest;

import java.util.List;

public interface SmsMongoService {

    public List<SmsSendRequest> getAllSMS();
    public SmsSendRequest getSMSById(String id);
    public List<SmsSendRequest> getSMSByProviderName(String providerName);
    public SmsSendRequest save(SmsSendRequest request);
}
