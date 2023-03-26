package com.densoft.springtwiliosms;

import com.densoft.springtwiliosms.config.TwilioConfig;
import com.twilio.Twilio;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
@RequiredArgsConstructor
public class SpringTwilioSmsApplication {
    private final TwilioConfig twilioConfig;
    @PostConstruct
    public void initTwilio(){
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken(), twilioConfig.getTrialNumber());
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringTwilioSmsApplication.class, args);
    }

}
