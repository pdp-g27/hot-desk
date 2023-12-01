package com.example.hotdesk.desk;

import com.example.hotdesk.common.repository.GenericSpecificationRepository;
import com.example.hotdesk.desk.entity.Desk;
import org.springframework.stereotype.Repository;

@Repository
public interface DeskRepository extends GenericSpecificationRepository<Desk, Integer>
{
}
