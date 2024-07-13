package com.travelbnbtest.travelbnbtest.config;

import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfig {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.sms.phone.number}")
    private String smsPhoneNumber;

    @Value("${twilio.whatsapp.Number}")
    private String whatsappPhoneNumber;

    @PostConstruct
    public void twilioInitializer(){
        Twilio.init(accountSid,authToken);
    }
    public String getSmsPhoneNumber() {
        return smsPhoneNumber;
    }
    public String getWhatsappPhoneNumber() {
        return whatsappPhoneNumber;
    }
}
