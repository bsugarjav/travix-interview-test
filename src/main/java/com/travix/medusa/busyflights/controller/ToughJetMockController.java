package com.travix.medusa.busyflights.controller;

import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = "/toughjet/api")
public class ToughJetMockController {

    @PostMapping(value = "/search",produces = "application/json")
    @ResponseBody
    public List<ToughJetResponse> search(@RequestBody ToughJetRequest searchRequest){
        ToughJetResponse response = new ToughJetResponse();
        response.setCarrier("AirlineFromToughtJet");
        response.setBasePrice(200);
        response.setTax(10d);
        response.setDiscount(10d);
        response.setDepartureAirportName(searchRequest.getFrom());
        response.setArrivalAirportName(searchRequest.getTo());
        response.setOutboundDateTime(searchRequest.getOutboundDate());
        response.setInboundDateTime(searchRequest.getInboundDate());
        return Arrays.asList(response);
    }
}