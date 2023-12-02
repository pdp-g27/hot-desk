package com.example.hotdesk.user;

import com.example.hotdesk.common.repository.GenericSpecificationRepository;
import com.example.hotdesk.user.entity.User;
import org.apache.commons.lang3.text.translate.NumericEntityUnescaper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends GenericSpecificationRepository<User,Integer> {
    Boolean existsByEmail(String email);
    Boolean existsByPhoneNumber(String phone);

    Optional<User> findUserByPhoneNumber(String phone);
}
