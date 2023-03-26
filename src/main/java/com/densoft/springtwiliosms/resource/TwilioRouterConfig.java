package com.densoft.springtwiliosms.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class TwilioRouterConfig {
    private final TwilioOtpHandler twilioOtpHandler;

    @Bean
    public RouterFunction<ServerResponse> handleSMS(){
        return RouterFunctions.route()
                .POST("/router/sendOTP", twilioOtpHandler::sendOtp)
                .POST("/router/validateOTP", twilioOtpHandler::validateOTP)
                .build();
    }
}
