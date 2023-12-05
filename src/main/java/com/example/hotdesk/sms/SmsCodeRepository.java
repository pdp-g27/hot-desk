package com.example.hotdesk.sms;

import com.example.hotdesk.sms.entity.SmsCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsCodeRepository extends CrudRepository<SmsCode, String>
{
}
