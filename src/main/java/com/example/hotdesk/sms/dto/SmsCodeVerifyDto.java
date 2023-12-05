package com.example.hotdesk.sms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsCodeVerifyDto
{
    private String phoneNumber;
    private String smsCode;
}
