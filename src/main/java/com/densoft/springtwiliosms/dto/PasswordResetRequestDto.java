package com.densoft.springtwiliosms.dto;

import lombok.Data;

@Data
public class PasswordResetRequestDto {
    private String phoneNumber; //dest
    private String userName;
    private String otp;
}
