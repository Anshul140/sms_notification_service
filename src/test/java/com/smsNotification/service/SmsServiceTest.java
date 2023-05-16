package com.smsNotification.service;

import com.smsNotification.models.SmsSendRequest;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.MessageCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class SmsServiceTest {
    String ACCOUNT_SID = "AC582c69968b6fca57a3f030d16d873132";
    String AUTH_TOKEN = "edfb32506082f4a661e72409f02a2d74";

    private SmsService smsService;

    @BeforeEach
    public void setUp() {
        smsService = new SmsService();
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    @Test
    public void sendSMSTest() {
        String fromNumber = "+15005550006";
        SmsSendRequest request = new SmsSendRequest();
        request.setDestinationSmsNumber("+917808477162");
        request.setSmsMessage("This is a test message. Kindly Ignore. ~Anshul");

        String res = smsService.sendSMS(request, fromNumber);
        assertEquals("queued", res);
    }

    @Test
    public void sendSMS_ExceptionTest() {
        String fromNumber = "+15005550006";
        SmsSendRequest request = new SmsSendRequest();
        request.setDestinationSmsNumber("+917808477162");
        request.setSmsMessage("This is a test message. Kindly Ignore. ~Anshul");

        // Set up a mock that throws an exception
        MessageCreator mockMessageCreator = mock(MessageCreator.class);
        when(mockMessageCreator.create()).thenThrow(new RuntimeException("Mock Exception"));
        SmsService smsService = spy(new SmsService());
        doReturn(mockMessageCreator).when(smsService).messageCreator(anyString(), anyString(), anyString());

        String res = smsService.sendSMS(request, fromNumber);
        assertEquals("Mock Exception", res);
    }
}