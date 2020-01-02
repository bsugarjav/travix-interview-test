package com.travix.medusa.busyflights.domain.crazyair;

import com.travix.medusa.busyflights.domain.Supplier;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class CrazyAirSupplier extends Supplier {

    public CrazyAirSupplier(String name,String url){
        super(name,url);
    }

    @Override
    public CrazyAirRequest mapRequest(BusyFlightsRequest request) {
        CrazyAirRequest remoteRequest = new CrazyAirRequest();
        remoteRequest.setOrigin(request.getOrigin());
        remoteRequest.setDestination(request.getDestination());
        remoteRequest.setDepartureDate(request.getDepartureDate());
        remoteRequest.setReturnDate(request.getReturnDate());
        remoteRequest.setPassengerCount(request.getNumberOfPassengers());
        return remoteRequest;
    }

    @Override
    public List<BusyFlightsResponse> mapResponse(List<Object> supplierResponse) {
        return supplierResponse.stream().map(sp -> {
            CrazyAirResponse crazyAirResponse = (CrazyAirResponse)sp;
            BusyFlightsResponse remoteResponse = new BusyFlightsResponse();
            remoteResponse.setAirline(crazyAirResponse.getAirline());
            remoteResponse.setSupplier(this.getName());
            remoteResponse.setFare(new BigDecimal(crazyAirResponse.getPrice()));
            remoteResponse.setFare(remoteResponse.getFare().setScale(2,BigDecimal.ROUND_DOWN));
            remoteResponse.setDepartureAirportCode(crazyAirResponse.getDepartureAirportCode());
            remoteResponse.setDestinationAirportCode(crazyAirResponse.getDestinationAirportCode());
            remoteResponse.setDepartureDate(crazyAirResponse.getDepartureDate());
            remoteResponse.setArrivalDate(crazyAirResponse.getArrivalDate());
            return remoteResponse;
        }).collect(Collectors.toList());
    }

    @Override
    public Class getResponseClass() {
        return CrazyAirResponse.class;
    }
}