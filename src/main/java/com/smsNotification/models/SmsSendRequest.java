package com.smsNotification.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "messages")
@AllArgsConstructor
@NoArgsConstructor
public class SmsSendRequest {

    @Id
    private String id;
    private String destinationSmsNumber;
    private String smsMessage;
    private String serviceProviderName;

}
