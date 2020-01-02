package com.travix.medusa.busyflights.domain.toughjet;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ToughJetSupplierTest {

    private ToughJetSupplier supplier = new ToughJetSupplier("name","url");

    @Test
    public void busy_request_should_be_mapped_to_tough_request(){
        BusyFlightsRequest request = new BusyFlightsRequest();
        request.setOrigin("origin");
        request.setDestination("destination");
        request.setDepartureDate("2017-01-10T14:55+01:00");
        request.setReturnDate("2017-01-10T15:55+01:00");
        request.setNumberOfPassengers(3);

        ToughJetRequest toughJetRequest = supplier.mapRequest(request);

        assertNotNull(toughJetRequest);
        assertEquals(toughJetRequest.getFrom(),"origin");
        assertEquals(toughJetRequest.getTo(),"destination");
        assertEquals(toughJetRequest.getOutboundDate(),"2017-01-10T14:55+01:00");
        assertEquals(toughJetRequest.getInboundDate(),"2017-01-10T15:55+01:00");
        assertEquals(toughJetRequest.getNumberOfAdults(),3);
    }

    @Test
    public void tough_response_should_be_mapped_to_busy_response(){
        ToughJetResponse r1 = new ToughJetResponse();
        r1.setCarrier("carrier1");
        r1.setBasePrice(200d);
        r1.setTax(10d);
        r1.setDiscount(10);
        r1.setDepartureAirportName("LHR1");
        r1.setArrivalAirportName("AMS1");
        r1.setOutboundDateTime("2017-01-10T14:55+01:00");
        r1.setInboundDateTime("2017-01-10T15:55+01:00");

        ToughJetResponse r2 = new ToughJetResponse();
        r2.setCarrier("carrier2");
        r2.setBasePrice(200d);
        r2.setTax(10d);
        r2.setDiscount(10);
        r2.setDepartureAirportName("LHR2");
        r2.setArrivalAirportName("AMS2");
        r2.setOutboundDateTime("2017-01-10T16:55+01:00");
        r2.setInboundDateTime("2017-01-10T17:55+01:00");

        List<BusyFlightsResponse> responseList = supplier.mapResponse(Arrays.asList(r1,r2));

        assertNotNull(responseList);
        assertEquals(responseList.size(),2);

        BusyFlightsResponse response1 = responseList.get(0);
        assertNotNull(response1);
        assertEquals(response1.getAirline(),"carrier1");
        assertEquals(response1.getSupplier(),"name");
        assertEquals(response1.getFare().toString(),"190.00");
        assertEquals(response1.getDepartureAirportCode(),"LHR1");
        assertEquals(response1.getDestinationAirportCode(),"AMS1");
        assertEquals(response1.getDepartureDate(),"2017-01-10T14:55+01:00");
        assertEquals(response1.getArrivalDate(),"2017-01-10T15:55+01:00");

        BusyFlightsResponse response2 = responseList.get(1);
        assertNotNull(response2);
        assertEquals(response2.getAirline(),"carrier2");
        assertEquals(response2.getSupplier(),"name");
        assertEquals(response2.getFare().toString(),"190.00");
        assertEquals(response2.getDepartureAirportCode(),"LHR2");
        assertEquals(response2.getDestinationAirportCode(),"AMS2");
        assertEquals(response2.getDepartureDate(),"2017-01-10T16:55+01:00");
        assertEquals(response2.getArrivalDate(),"2017-01-10T17:55+01:00");
    }

    @Test
    public void response_class_should_be_tough(){
        assertEquals(supplier.getResponseClass(),ToughJetResponse.class);
    }
}
