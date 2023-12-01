package com.example.hotdesk.room;

import com.example.hotdesk.common.repository.GenericSpecificationRepository;
import com.example.hotdesk.room.entity.Room;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends GenericSpecificationRepository<Room, Integer>
{
}
