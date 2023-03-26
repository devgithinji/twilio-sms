package com.densoft.springtwiliosms.service;

import com.densoft.springtwiliosms.config.TwilioConfig;
import com.densoft.springtwiliosms.dto.OtpStatus;
import com.densoft.springtwiliosms.dto.PasswordResetRequestDto;
import com.densoft.springtwiliosms.dto.PasswordResetResponseDto;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class TwilioOTPService {

    private final TwilioConfig twilioConfig;
    Map<String, String> otpMap = new HashMap<>();


    public Mono<PasswordResetResponseDto> sendOTPForPasswordReset(PasswordResetRequestDto passwordResetRequestDto) {
        PasswordResetResponseDto passwordResetResponseDto = null;
        try {
            PhoneNumber to = new PhoneNumber(passwordResetRequestDto.getPhoneNumber());
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
            String otp = generateOTP();
            String otpMessage = "Dear Customer , Your OTP is ##" + otp + "##. Use this passcode to complete your transaction. Thank you.";
            Message message = Message.creator(to, from, otpMessage).create();
            passwordResetResponseDto = new PasswordResetResponseDto(OtpStatus.DELIVERED, otpMessage);
            otpMap.put(passwordResetRequestDto.getUserName(), otp);
        } catch (Exception e) {
            passwordResetResponseDto = new PasswordResetResponseDto(OtpStatus.FAILED, e.getMessage());
        }
        return Mono.just(passwordResetResponseDto);
    }

    public Mono<String> validateOtp(String otp, String username) {
        if (otp.equals(otpMap.get(username))) {
            return Mono.just("Validate OTP please proceed with your transaction");
        } else {
            return Mono.error(new IllegalArgumentException("Invalid otp please"));
        }
    }

    // 6 digit OTP
    private String generateOTP() {
        return new DecimalFormat("000000").format(new Random().nextInt(999999));
    }
}
