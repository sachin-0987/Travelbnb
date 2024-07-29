package com.travelbnbtest.travelbnbtest.service;

import com.travelbnbtest.travelbnbtest.entity.Property;
import com.travelbnbtest.travelbnbtest.entity.Room;
import com.travelbnbtest.travelbnbtest.exception.ResourceNotFoundException;
import com.travelbnbtest.travelbnbtest.payload.RoomDto;
import com.travelbnbtest.travelbnbtest.repository.PropertyRepository;
import com.travelbnbtest.travelbnbtest.repository.RoomRepository;
import jakarta.persistence.OptimisticLockException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService{

    private RoomRepository roomRepository;
    private PropertyRepository propertyRepository;

    public RoomServiceImpl(RoomRepository roomRepository, PropertyRepository propertyRepository) {
        this.roomRepository = roomRepository;
        this.propertyRepository = propertyRepository;
    }

    @Override
    public RoomDto addRooms(long propertyId, RoomDto roomDto) {
        //get the property details
        Property property = propertyRepository.findById(propertyId).orElseThrow(
                ()->new ResourceNotFoundException("property not found with id: "+propertyId)
        );
        roomDto.setProperty(property);
        Room room = dtoToEntity(roomDto);

        Optional<Room> existingRoom  = roomRepository.findByPropertyAndRoomNumber(property, room.getRoomNumber());
        if (existingRoom.isPresent()){
            throw new ResourceNotFoundException("Room already exists with the same properties.");
        }
        Room save = roomRepository.save(room);
        RoomDto dto2 = entityToDto(save);
        return dto2;

    }

    @Transactional
    @Override
    public RoomDto updateRooms(long roomId, long propertyId, RoomDto dto) {

      try {
          Room existingRoom = roomRepository.findById(roomId).orElseThrow(
                  ()->new ResourceNotFoundException("Room not found with id: "+roomId)
          );

          Property property = propertyRepository.findById(propertyId).orElseThrow(
                  ()->new ResourceNotFoundException("Property not found with id: "+propertyId)
          );

          // Update the existing room entity with new values from the DTO
          existingRoom.setRoomNumber(dto.getRoomNumber());
          existingRoom.setStatus(dto.isStatus());
          existingRoom.setProperty(property);

          Room save = roomRepository.save(existingRoom);
          RoomDto dto1 = entityToDto(save);
          return dto1;
      } catch (OptimisticLockException e) {
          e.printStackTrace();
          throw new ResourceNotFoundException("The room was updated by another transaction. Please try again.");
      }


    }


    //dto to entity
    Room dtoToEntity(RoomDto roomDto){
        Room room=new Room();
        room.setRoomNumber(roomDto.getRoomNumber());
        room.setStatus(roomDto.isStatus());
        room.setProperty(roomDto.getProperty());
        return room;

    }
    //entity to dto
    RoomDto entityToDto(Room room){
        RoomDto dto=new RoomDto();
        dto.setId(room.getId());
        dto.setRoomNumber(room.getRoomNumber());
        dto.setStatus(room.isStatus());
        dto.setProperty(room.getProperty());
        return dto;
    }
}
