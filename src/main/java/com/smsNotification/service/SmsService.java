package com.smsNotification.service;

import com.smsNotification.models.SmsSendRequest;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    @Value("${TWILIO_ACCOUNT_SID}")
    String ACCOUNT_SID;

    @Value("${TWILIO_AUTH_TOKEN}")
    String AUTH_TOKEN;

    @Value("${TWILIO_OUTGOING_SMS_NUMBER}")
    String OUTGOING_SMS_NUMBER;

    @PostConstruct
    private void setUp() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public MessageCreator messageCreator(String fromNumber, String toNumber, String messageBody) {
        MessageCreator message = Message.creator(
                new PhoneNumber(toNumber),
                new PhoneNumber(fromNumber),
                messageBody
        );
        return message;
    }

    public String sendSMS(SmsSendRequest request, String fromNumber) {
        try {
            if(fromNumber != "") {
                OUTGOING_SMS_NUMBER = fromNumber;
            }
            MessageCreator message = messageCreator(OUTGOING_SMS_NUMBER, request.getDestinationSmsNumber(),request.getSmsMessage());
            return message.create().getStatus().toString();
        } catch (Exception e) {
            System.out.println(e);
            return e.getMessage();
        }
    }
}
