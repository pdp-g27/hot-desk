package com.example.hotdesk.sms.entity;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash( value = "sms_code", timeToLive = 86400 /* day */ )
public class SmsCode
{
    @Id
    private String phoneNumber;
    @Column( nullable = false )
    private String code;
    @Column( nullable = false )
    private LocalDateTime lastSentTime;
    @Column( nullable = false )
    private int sentCount;
}
