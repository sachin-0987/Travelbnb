package com.travelbnbtest.travelbnbtest.service;

import com.travelbnbtest.travelbnbtest.entity.AppUser;
import com.travelbnbtest.travelbnbtest.entity.Favourite;
import com.travelbnbtest.travelbnbtest.entity.Property;
import com.travelbnbtest.travelbnbtest.exception.ResourceNotFoundException;
import com.travelbnbtest.travelbnbtest.payload.FavouriteDto;
import com.travelbnbtest.travelbnbtest.repository.FavouriteRepository;
import com.travelbnbtest.travelbnbtest.repository.PropertyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavouriteServiceImpl implements FavouriteService{

    private FavouriteRepository favouriteRepository;
    private PropertyRepository propertyRepository;

    public FavouriteServiceImpl(FavouriteRepository favouriteRepository, PropertyRepository propertyRepository) {
        this.favouriteRepository = favouriteRepository;
        this.propertyRepository = propertyRepository;
    }

    @Override
    public FavouriteDto addFavourite(AppUser user, long propertyId, FavouriteDto favouriteDto) {

        Property property = propertyRepository.findById(propertyId).orElseThrow(
                ()->new ResourceNotFoundException("Property not found with id: "+propertyId)
        );
        favouriteDto.setProperty(property);

        favouriteDto.setAppUser(user);
        Favourite favourite = dtoToEntity(favouriteDto);

        Favourite save = favouriteRepository.save(favourite);
        FavouriteDto dto = entityToDto(save);
        return dto;
    }

    @Override
    public List<FavouriteDto> getFavouriteByUser(AppUser user) {
        List<Favourite> favouriteByUser = favouriteRepository.findFavouriteByUser(user);
        List<FavouriteDto> collect = favouriteByUser.stream().map(f -> entityToDto(f)).collect(Collectors.toList());
        return collect;

    }

    @Override
    public FavouriteDto updateFavorite(AppUser user, long favId, long propertyId, FavouriteDto favouriteDto) {
       Favourite favourite=null;
         favourite = favouriteRepository.findById(favId).orElseThrow(
                 ()->new ResourceNotFoundException("Favourite not found with id:" +favId)
         );

        Property property = propertyRepository.findById(propertyId).orElseThrow(
                ()->new ResourceNotFoundException("Property not found with id: "+propertyId)
        );
        favouriteDto.setProperty(property);
        favouriteDto.setAppUser(user);
        favourite = dtoToEntity(favouriteDto);
        favourite.setId(favId);

        Favourite save = favouriteRepository.save(favourite);
        FavouriteDto dto = entityToDto(save);
        return dto;
    }

    //dto to entity

    Favourite dtoToEntity(FavouriteDto favouriteDto){
        Favourite entity=new Favourite();
        entity.setStatus(favouriteDto.getStatus());
        entity.setProperty(favouriteDto.getProperty());
        entity.setAppUser(favouriteDto.getAppUser());
        return entity;
    }
    //entity to dto
    FavouriteDto entityToDto(Favourite favourite){
        FavouriteDto dto=new FavouriteDto();
        dto.setId(favourite.getId());
        dto.setStatus(favourite.getStatus());
        dto.setProperty(favourite.getProperty());
        dto.setAppUser(favourite.getAppUser());
        return dto;
    }
}
