package com.travix.medusa.busyflights.domain.toughjet;

import com.travix.medusa.busyflights.domain.Supplier;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ToughJetSupplier extends Supplier {

    public ToughJetSupplier(String name,String url){
        super(name,url);
    }

    @Override
    public ToughJetRequest mapRequest(BusyFlightsRequest request) {
        ToughJetRequest remoteRequest = new ToughJetRequest();
        remoteRequest.setFrom(request.getOrigin());
        remoteRequest.setTo(request.getDestination());
        remoteRequest.setOutboundDate(request.getDepartureDate());
        remoteRequest.setInboundDate(request.getReturnDate());
        remoteRequest.setNumberOfAdults(request.getNumberOfPassengers());
        return remoteRequest;
    }

    @Override
    public List<BusyFlightsResponse> mapResponse(List<Object> supplierResponse) {
        return supplierResponse.stream().map(sp -> {
            ToughJetResponse toughJetResponse = (ToughJetResponse)sp;
            BusyFlightsResponse remoteResponse = new BusyFlightsResponse();
            remoteResponse.setAirline(toughJetResponse.getCarrier());
            remoteResponse.setSupplier(this.getName());
            remoteResponse.setFare(new BigDecimal(toughJetResponse.getBasePrice() + toughJetResponse.getTax() - toughJetResponse.getBasePrice() * (toughJetResponse.getDiscount()/100)));
            remoteResponse.setFare(remoteResponse.getFare().setScale(2,BigDecimal.ROUND_DOWN));
            remoteResponse.setDepartureAirportCode(toughJetResponse.getDepartureAirportName());
            remoteResponse.setDestinationAirportCode(toughJetResponse.getArrivalAirportName());
            remoteResponse.setDepartureDate(toughJetResponse.getOutboundDateTime());
            remoteResponse.setArrivalDate(toughJetResponse.getInboundDateTime());
            return remoteResponse;
        }).collect(Collectors.toList());
    }

    @Override
    public Class getResponseClass() {
        return ToughJetResponse.class;
    }
}