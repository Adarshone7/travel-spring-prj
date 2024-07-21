package com.Travels.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.Travels.entity.Travel;
import com.Travels.payload.TravelDto;
import com.Travels.repository.TravelRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TravelServiceImpl implements TravelService {
    private TravelRepository travelRepository;

    public TravelServiceImpl(TravelRepository travelRepository) {
        this.travelRepository = travelRepository;
    }

    @Override
    public TravelDto createTravel(TravelDto travelDto) {
       Travel travel = mapToEntity(travelDto);
        Travel savedEntity = travelRepository.save(travel);
        TravelDto dto = mapToDto(savedEntity);
        return dto;
    }

    @Override
    public void deleteTravelsById(long id) {

        travelRepository.deleteById(id);
    }

    @Override
    public TravelDto updateTravels(long id, TravelDto travelDto) {
        Optional<Travel> opTrs = travelRepository.findById(id);
        Travel travel = opTrs.get();

        travel.setName(travelDto.getName());
        travel.setEmail(travelDto.getEmail());
        travel.setNumber(travelDto.getNumber());
        Travel savedEntity = travelRepository.save(travel);
       TravelDto dto = mapToDto(travel);

        return dto;
    }

    @Override
    public List<TravelDto> getAllTravels(int pageNo, int pageSize, String sortBy, String sortDir) {
//        List<Travel> travelss = travelRepository.findAll();
        //Ternary Operator

       Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(Sort.Direction.ASC, sortBy): Sort.by(Sort.Direction.DESC,sortBy);

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Travel> all = travelRepository.findAll(pageable);
        List<Travel> travelss = all.getContent();
        List<TravelDto> travelDtos = travelss.stream().map(r -> mapToDto(r)).collect(Collectors.toList());


        System.out.println(all.getTotalPages());
        System.out.println(all.isLast());
        System.out.println(all.isFirst());
        System.out.println(pageable.getPageSize());
        System.out.println(pageable.getPageNumber());


        return travelDtos;
    }


    Travel mapToEntity(TravelDto dto){
        Travel entity = new Travel();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setNumber(dto.getNumber());
        return entity;

    }

    TravelDto mapToDto(Travel travel){
        TravelDto dto = new TravelDto();
        dto.setId(travel.getId());
        dto.setName(travel.getName());
        dto.setEmail(travel.getEmail());
        dto.setNumber(travel.getNumber());
        return dto;

    }
}
