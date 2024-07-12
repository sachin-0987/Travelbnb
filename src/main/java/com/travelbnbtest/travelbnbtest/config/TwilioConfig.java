package com.travelbnbtest.travelbnbtest.config;

import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfig {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    @Bean
    public void twilioInitializer(){
        Twilio.init(accountSid,authToken);
    }
    public String getTwilioPhoneNumber(){
        return twilioPhoneNumber;
    }
}
