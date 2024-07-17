package com.travelbnbtest.travelbnbtest.service;

import com.travelbnbtest.travelbnbtest.config.TwilioConfig;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {

    private final TwilioConfig twilioConfig;

    public TwilioService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }


    public String sendSms(String to, String message) {
        try {
            Message sms = Message.creator(
                    new PhoneNumber(to),
                    new PhoneNumber(twilioConfig.getSmsPhoneNumber()),
                    message).create();
            return sms.getSid();
        } catch (Exception e) {
            // Handle exception appropriately (log it, throw custom exception, etc.)
            e.printStackTrace();
            return null; // or throw new RuntimeException("Failed to send SMS");
        }
    }
    public String sendWhatsAppMessage(String to, String messageBody) {
       try {
           PhoneNumber toPhoneNumber = new PhoneNumber("whatsapp:" + to);
           PhoneNumber fromPhoneNumber = new PhoneNumber("whatsapp:" + twilioConfig.getWhatsappPhoneNumber());
           Message message = Message.creator(toPhoneNumber, fromPhoneNumber, messageBody).create();
           return message.getSid();
       }catch (Exception e){
           e.printStackTrace();
           return null;
       }
    }
}
