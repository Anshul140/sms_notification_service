package com.smsNotification.service.factory;

import com.smsNotification.repo.SmsRepo;
import com.smsNotification.service.SmsServiceProvider;
import com.smsNotification.service.providers.SmsServiceProviderClickSend;
import com.smsNotification.service.providers.SmsServiceProviderTwilio;

public class SmsServiceProviderFactory {
    public static SmsServiceProvider createSmsService(String providerName) {
        if ("twilio".equalsIgnoreCase(providerName)) {
            return new SmsServiceProviderTwilio();
        } else if ("ClickSend".equalsIgnoreCase(providerName)) {
            return new SmsServiceProviderClickSend();
        } else {
            throw new IllegalArgumentException("Invalid SMS provider: " + providerName);
        }
    }
}
