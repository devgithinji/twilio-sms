package com.densoft.springtwiliosms.resource;

import com.densoft.springtwiliosms.dto.PasswordResetRequestDto;
import com.densoft.springtwiliosms.service.TwilioOTPService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TwilioOtpHandler {
    private final TwilioOTPService service;

    public Mono<ServerResponse> sendOtp(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(PasswordResetRequestDto.class)
                .flatMap(passwordResetRequestDto -> service.sendOTPForPasswordReset(passwordResetRequestDto))
                .flatMap(passwordResetResponseDto -> ServerResponse.status(HttpStatus.OK)
                        .body(BodyInserters.fromValue(passwordResetResponseDto)));

    }

    public Mono<ServerResponse> validateOTP(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(PasswordResetRequestDto.class)
                .flatMap(passwordResetRequestDto -> service.validateOtp(passwordResetRequestDto.getOtp(), passwordResetRequestDto.getUserName()))
                .flatMap(dto -> ServerResponse.status(HttpStatus.OK)
                        .bodyValue(dto));
    }
}
