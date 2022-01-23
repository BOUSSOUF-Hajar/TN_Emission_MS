package com.example.EmissionMS.Twilio;

import java.util.List;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.http.HttpStatus;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.twilio.Twilio;


import io.swagger.v3.oas.annotations.parameters.RequestBody;





@RestController
public class SMSController {

    
    @Value("${phoneNumber}")
    private String myTwilioPhoneNumber;

    @Autowired
    public SMSController(
        @Value("${twilioAccountSid}") String twilioAccountSid,
        @Value("${twilioAuthToken}") String twilioAuthToken) {
        Twilio.init(twilioAccountSid, twilioAuthToken);
    }

    @PostMapping("/send-messages")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public static void sendMessages(String num,String  msg) {
    		System.out.println("details :"+msg);       
            Message message = Message.creator(
                new PhoneNumber(num),"MG50417ddbd6e495748f28f99a24017e71",
                msg).create();
            System.out.println("Sent message w/ sid: " + message.getSid());
           
    }
}