package com.Travels.controller;

import com.Travels.entity.Travel;
import com.Travels.payload.TravelDto;
import com.Travels.service.TravelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v3/Travels")

public class TravelController {

    private TravelService travelService;

    public TravelController(TravelService travelService) {
        this.travelService = travelService;
    }

    // http://localhost:8091/api/v3/Travels
    @PostMapping
    public ResponseEntity<TravelDto> addTravels(@RequestBody TravelDto travelDto){
        TravelDto trsDto = travelService.createTravel(travelDto);
        return new ResponseEntity<>(trsDto, HttpStatus.CREATED);

    }
    //http://localhost:8091/api/v3/Travels?id=3
    @DeleteMapping
    public ResponseEntity<String> deleteTravelsById(@RequestParam long id){
        travelService.deleteTravelsById(id);
        return new ResponseEntity<>("Travels Deleted",HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<TravelDto> updateTravels(@RequestParam long id,
                                                   @RequestBody TravelDto travelDto){

        TravelDto dto =travelService.updateTravels(id, travelDto);
        return  new ResponseEntity<>(dto,HttpStatus.OK);

    }
    //http://localhost:8091/api/v3/Travels?pageNo=0&pageSize=3&sortBy=email
    @GetMapping
    public ResponseEntity<List<TravelDto>> getAllTravel(
            @RequestParam (name="pageNo", defaultValue = "0",required = false)int pageNo,
             @RequestParam (name="pageSize", defaultValue = "3",required = false)int pageSize,
            @RequestParam(name="sortBy", defaultValue="name", required=false )String sortBy,
             @RequestParam(name="sortDir", defaultValue="name", required=false )String sortDir

    ){
        List<TravelDto> dtos = travelService.getAllTravels(pageNo,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(dtos,HttpStatus.OK);
    }

}
