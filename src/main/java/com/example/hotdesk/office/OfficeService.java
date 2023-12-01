package com.example.hotdesk.office;

import com.example.hotdesk.common.service.GenericCrudService;
import com.example.hotdesk.office.dto.OfficeCreateDTo;
import com.example.hotdesk.office.dto.OfficeResponseDto;
import com.example.hotdesk.office.dto.OfficeUpdateDto;
import com.example.hotdesk.office.entity.Office;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@RequiredArgsConstructor
public class OfficeService extends GenericCrudService<Office, Integer, OfficeCreateDTo, OfficeUpdateDto, OfficeUpdateDto, OfficeResponseDto>
{
    private final OfficeDtoMapper mapper;
    private final OfficeRepository repository;
    private final Class<Office> entityClass = Office.class;

    @Override
    protected Office save( OfficeCreateDTo officeCreateDTo )
    {
        Office entity = mapper.toEntity( officeCreateDTo );
        return repository.save( entity );
    }

    @Override
    protected Office updateEntity( OfficeUpdateDto officeUpdateDto, Office office )
    {
        mapper.update( officeUpdateDto, office );
        return repository.save( office );
    }
}
