package com.smsNotification.models;

import lombok.Data;

@Data
public class SmsSendRequest {

    private String destinationSmsNumber;
    private String smsMessage;

}
