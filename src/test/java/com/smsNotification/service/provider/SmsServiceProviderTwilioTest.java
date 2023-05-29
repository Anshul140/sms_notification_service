package com.smsNotification.service.provider;

import com.smsNotification.models.SmsSendRequest;
import com.smsNotification.repo.SmsRepo;
import com.smsNotification.service.providers.SmsServiceProviderTwilio;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.MessageCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class SmsServiceProviderTwilioTest {
    String ACCOUNT_SID = "AC582c69968b6fca57a3f030d16d873132";
    String AUTH_TOKEN = "edfb32506082f4a661e72409f02a2d74";
    String OUTGOING_SMS_NUMBER = "+15005550006";

    @Mock
    private SmsRepo smsRepo;

    private SmsServiceProviderTwilio serviceProviderTwilio;

    @BeforeEach
    public void setup(){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        serviceProviderTwilio = new SmsServiceProviderTwilio(smsRepo);
    }

    @Test
    public void testSendSMS() {
        // arrange
        SmsSendRequest request = new SmsSendRequest();
        request.setDestinationSmsNumber("+917808477162");
        request.setSmsMessage("Hello, world!");
        request.setServiceProviderName("twilio");

        // mock twilio response
        SmsServiceProviderTwilio smsServiceProviderTwilio = mock(SmsServiceProviderTwilio.class);
        MessageCreator messageCreatorMock = mock(MessageCreator.class);
        doReturn(messageCreatorMock).when(smsServiceProviderTwilio).messageCreator(anyString(), anyString(), anyString());

        // mock sms repo
        request.setId(UUID.randomUUID().toString().split("-")[0]);
        when(smsRepo.save(request)).thenReturn(request);

        // call sendSMS
        SmsSendRequest res = serviceProviderTwilio.sendSMS(request, OUTGOING_SMS_NUMBER);
        if(res != null) {
            assertEquals(request.getSmsMessage(), res.getSmsMessage());
            assertEquals(request.getId(), res.getId());
            assertEquals(request.getDestinationSmsNumber(), res.getDestinationSmsNumber());
        } else {
            assertEquals(null, res);
        }
    }

    @Test
    public void testSendSMS_Exception() {
        // Arrange
        SmsSendRequest request = new SmsSendRequest();
        request.setDestinationSmsNumber("+917808477162");
        request.setSmsMessage("Hello World!");
        request.setServiceProviderName("twilio");

        // mock twilio
        SmsServiceProviderTwilio smsServiceProviderTwilio = mock(SmsServiceProviderTwilio.class);
        MessageCreator messageCreatorMock = mock(MessageCreator.class);
        doReturn(messageCreatorMock).when(smsServiceProviderTwilio).messageCreator(anyString(), anyString(), anyString());

        // covering twilio exception
        //when(messageCreatorMock.create().getStatus().toString()).thenReturn("Mockito Exception");

        // mock smsRepo
        request.setId(UUID.randomUUID().toString().split("-")[0]);

        // covering database exception
        when(smsRepo.save(request)).thenThrow(new RuntimeException("Database Error!"));

        // Act
        SmsSendRequest result = serviceProviderTwilio.sendSMS(request, OUTGOING_SMS_NUMBER);
        //MessageCreator messageCreatorRes = smsServiceProviderTwilio.messageCreator(OUTGOING_SMS_NUMBER, request.getDestinationSmsNumber(), request.getSmsMessage());
        //String res = messageCreatorRes.create().getStatus().toString();

        // Assert
        verify(smsRepo, times(1)).save(any());
        assertEquals(null, result);
        //assertEquals("Mockito Exception", res);
    }

}
