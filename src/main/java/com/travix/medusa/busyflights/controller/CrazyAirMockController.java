package com.travix.medusa.busyflights.controller;

import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = "/crazyair/api")
public class CrazyAirMockController {

    @PostMapping(value = "/search",produces = "application/json")
    @ResponseBody
    public List<CrazyAirResponse> search(@RequestBody CrazyAirRequest searchRequest){
        CrazyAirResponse response = new CrazyAirResponse();
        response.setAirline("AirlineFromCrazyAir");
        response.setPrice(100.125d);
        response.setCabinclass("E");
        response.setDepartureAirportCode(searchRequest.getOrigin());
        response.setDestinationAirportCode(searchRequest.getDestination());
        response.setDepartureDate(searchRequest.getDepartureDate());
        response.setArrivalDate(searchRequest.getReturnDate());
        return Arrays.asList(response);
    }
}
