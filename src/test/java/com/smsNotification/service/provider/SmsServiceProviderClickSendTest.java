package com.smsNotification.service.provider;

import com.smsNotification.models.SmsSendRequest;
import com.smsNotification.repo.SmsRepo;
import com.smsNotification.service.providers.SmsServiceProviderClickSend;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SmsServiceProviderClickSendTest {

    @Mock
    private SmsRepo smsRepo;

    @Autowired
    private SmsServiceProviderClickSend serviceProviderClickSend;

    @BeforeEach
    public void setUp() {
        serviceProviderClickSend = new SmsServiceProviderClickSend(smsRepo);
    }

    @Test
    public void sendSMS() {
        // Arrange
        SmsSendRequest request = new SmsSendRequest();
        request.setDestinationSmsNumber("+917808477162");
        request.setSmsMessage("Hello, world!");
        request.setServiceProviderName("Clicksend");
        request.setId(UUID.randomUUID().toString().split("-")[0]);

        // Mock the behavior of smsRepo.save() method
        when(smsRepo.save(request)).thenReturn(request);

        // Act
        SmsSendRequest result = serviceProviderClickSend.sendSMS(request, null);

        // Assert
        assertEquals(request, result);
        verify(smsRepo, times(1)).save(request);
    }

    @Test
    public void sendSMS_Exception() {
        // Arrange
        SmsSendRequest request = new SmsSendRequest();
        request.setDestinationSmsNumber("+917808477162");
        request.setSmsMessage("Hello, world!");
        request.setServiceProviderName("Clicksend");
        request.setId(UUID.randomUUID().toString().split("-")[0]);

        when(smsRepo.save(request)).thenThrow(new RuntimeException("Database error"));

        // Act
        SmsSendRequest result = serviceProviderClickSend.sendSMS(request, null);

        // Assert
        assertEquals(null, result);
        verify(smsRepo, times(1)).save(request);
    }
}
