package com.example.hotdesk.office;

import com.example.hotdesk.common.repository.GenericSpecificationRepository;
import com.example.hotdesk.office.entity.Office;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeRepository extends GenericSpecificationRepository<Office, Integer>
{
}
