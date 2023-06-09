package com.smsNotification.service.providers;

import com.smsNotification.models.SmsSendRequest;
import com.smsNotification.repo.SmsRepo;
import com.smsNotification.service.SmsServiceProvider;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class SmsServiceProviderTwilio implements SmsServiceProvider {
    private final SmsRepo smsRepo;

    public SmsServiceProviderTwilio(SmsRepo smsRepo) {
        this.smsRepo = smsRepo;
    }

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
        return Message.creator(
                new PhoneNumber(toNumber),
                new PhoneNumber(fromNumber),
                messageBody
        );
    }

    /**
     * @param request -> model used for storing SMS data in mongoDB
     * @param fromNumber -> source number
     * @return -> model of SMS which is stored in DB with id
     */
    @Override
    public SmsSendRequest sendSMS(SmsSendRequest request, String fromNumber) {
        try {
            if(fromNumber.length() > 0) {
                OUTGOING_SMS_NUMBER = fromNumber;
            }

            MessageCreator message = messageCreator(OUTGOING_SMS_NUMBER, request.getDestinationSmsNumber(),request.getSmsMessage());
            String response = message.create().getStatus().toString();

            if(response.equals("queued")) {
                request.setId(UUID.randomUUID().toString().split("-")[0]);
                return smsRepo.save(request);
            }
            return null;
        } catch (Exception e) {
            System.out.println("Inside catch block: "+e.getMessage());
            return null;
        }
    }


}
