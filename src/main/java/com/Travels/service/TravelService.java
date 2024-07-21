package com.Travels.service;

import com.Travels.payload.TravelDto;

import java.util.List;

public interface TravelService {

    public TravelDto createTravel(TravelDto travelDto
    );


    void deleteTravelsById(long id);

    TravelDto updateTravels(long id, TravelDto travelDto);

    List<TravelDto> getAllTravels(int pageNo, int pageSize, String sortBy, String sortDir);
}
