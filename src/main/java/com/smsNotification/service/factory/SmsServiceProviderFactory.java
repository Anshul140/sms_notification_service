package com.smsNotification.service.factory;

import com.smsNotification.service.SmsServiceProvider;
import com.smsNotification.service.providers.SmsServiceProviderClickSend;
import com.smsNotification.service.providers.SmsServiceProviderTwilio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SmsServiceProviderFactory {

    private static SmsServiceProviderClickSend smsServiceProviderClickSend = null;
    private static SmsServiceProviderTwilio smsServiceProviderTwilio = null;

    @Autowired
    public SmsServiceProviderFactory(SmsServiceProviderClickSend smsServiceProviderClickSend,
                                     SmsServiceProviderTwilio smsServiceProviderTwilio) {
        this.smsServiceProviderClickSend = smsServiceProviderClickSend;
        this.smsServiceProviderTwilio = smsServiceProviderTwilio;
    }

    public static SmsServiceProvider createSmsService(String providerName) {
        if ("twilio".equalsIgnoreCase(providerName)) {
            return smsServiceProviderTwilio;
        } else if ("ClickSend".equalsIgnoreCase(providerName)) {
            return smsServiceProviderClickSend;
        } else {
            throw new IllegalArgumentException("Invalid SMS provider: " + providerName);
        }
    }
}
