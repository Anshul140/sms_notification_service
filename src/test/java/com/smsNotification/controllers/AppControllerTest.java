package com.smsNotification.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smsNotification.models.SmsSendRequest;
import com.smsNotification.service.SmsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AppControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SmsService smsService;

    @InjectMocks
    private AppController appController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(appController).build();
    }


    @Test
    void processSMS() throws Exception{

        SmsSendRequest smsSendRequest = new SmsSendRequest();
        smsSendRequest.setDestinationSmsNumber("+917808477162");
        smsSendRequest.setSmsMessage("This is a test message.");

        // Mock the smsService.sendSMS method
        when(smsService.sendSMS(any(SmsSendRequest.class), any(String.class))).thenReturn("queued");

        // Perform the POST request
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/sms")
                        .content(asJsonString(smsSendRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();

                // Verify the response
                assertEquals("queued", result.getResponse().getContentAsString());
    }

    // Helper method to convert object to JSON string
    private static String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
