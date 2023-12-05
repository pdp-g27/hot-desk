package com.example.hotdesk.sms;

import com.example.hotdesk.common.exception.SmsVerificationException;
import com.example.hotdesk.sms.dto.SmsCodeSendDto;
import com.example.hotdesk.sms.dto.SmsCodeVerifyDto;
import com.example.hotdesk.sms.entity.SmsCode;
import com.example.hotdesk.user.UserRepository;
import com.example.hotdesk.user.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SmsCodeService
{
    private final SmsCodeRepository repository;
    private final Random random = new Random();
    private final UserRepository userRepository;

    @Transactional
    public void sendSms( SmsCodeSendDto smsCodeSendDto )
    {
        String phoneNumber = smsCodeSendDto.getPhoneNumber();
        Optional<SmsCode> optionalSmsCode = repository.findById( phoneNumber );

        if( optionalSmsCode.isEmpty() )
        {
            int code = random.nextInt( 100000, 999999 );

            SmsCode smsCode = new SmsCode( phoneNumber, String.valueOf( code ), LocalDateTime.now(), 1 );
            repository.save( smsCode );

            System.out.println( code );
        }
        else
        {
            SmsCode smsCode = optionalSmsCode.get();

            if( smsCode.getSentCount() >= 3 )
            {
                throw new SmsVerificationException( "You already tried 3 times. Please try after 24 hour" );
            }

            if( !smsCode.getLastSentTime().plusMinutes( 1 ).isBefore( LocalDateTime.now() ) )
            {
                Duration between = Duration.between( smsCode.getLastSentTime(), LocalDateTime.now() );
                long diff = 60 - between.getSeconds();
                throw new SmsVerificationException( "Please try after %d seconds".formatted( diff ) );
            }

            int code = random.nextInt( 100000, 999999 );

            smsCode.setCode( String.valueOf( code ) );
            smsCode.setSentCount( smsCode.getSentCount() + 1 );
            smsCode.setLastSentTime( LocalDateTime.now() );

            repository.save( smsCode );

            System.out.println( code );
        }
    }

    public void verifyCode( SmsCodeVerifyDto verifyDto )
    {
        String phoneNumber = verifyDto.getPhoneNumber();
        String code = verifyDto.getSmsCode();

        SmsCode smsCode = repository.findById( phoneNumber )
                                    .orElseThrow( () -> new SmsVerificationException( "the sms code already expired" ) );

        if( smsCode.getLastSentTime().plusMinutes( 3 ).isBefore( LocalDateTime.now() ) )
        {
            throw new SmsVerificationException( "the sms code already expired" );
        }

        if( !smsCode.getCode().equals( code ) )
        {
            throw new SmsVerificationException( "Sms code doesn't match" );
        }

        User user = userRepository.findUserByPhoneNumber( phoneNumber )
                                  .orElseThrow(
                                      () -> new EntityNotFoundException(
                                          String.format( "User with phone number = %s not found", phoneNumber
                                          )
                                      ) );

        user.setPhoneNumberVerified( true );
        userRepository.save( user );
    }
}
