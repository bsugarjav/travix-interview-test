package com.travix.medusa.busyflights.domain.crazyair;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class CrazyAirSupplierTest {

    private CrazyAirSupplier supplier = new CrazyAirSupplier("name","url");;
    private BusyFlightsRequest request = new BusyFlightsRequest();
    private CrazyAirResponse r1 = new CrazyAirResponse();

    {
        request.setOrigin("origin");
        request.setDestination("destination");
        request.setDepartureDate("2017-01-10T14:55+01:00");
        request.setReturnDate("2017-01-10T15:55+01:00");
        request.setNumberOfPassengers(3);

        r1.setAirline("airline1");
        r1.setPrice(123.123d);
        r1.setCabinclass("cabinclass1");
        r1.setDepartureAirportCode("LHR1");
        r1.setDestinationAirportCode("AMS1");
        r1.setDepartureDate("2017-01-10T14:55+01:00");
        r1.setArrivalDate("2017-01-10T15:55+01:00");
    }

    @Test
    public void call_remote_should_bring_crazy_response() throws IOException {
        System.out.println(1d/20);
        RestTemplate mockRestTemplate = mock(RestTemplate.class);
        CrazyAirRequest crazyAirRequest = supplier.mapRequest(request);
        when(mockRestTemplate.postForObject(eq("url"),eq(crazyAirRequest),eq(String.class))).thenReturn(new ObjectMapper().writeValueAsString(Arrays.asList(r1)));

        List<BusyFlightsResponse> responseList = supplier.callRemote(request,mockRestTemplate);

        assertNotNull(responseList);
        assertEquals(responseList.size(),1);
        verify(mockRestTemplate,times(1)).postForObject(eq("url"),eq(crazyAirRequest),eq(String.class));
    }

    @Test
    public void busy_request_should_be_mapped_to_crazy_request(){
        CrazyAirRequest crazyAirRequest = supplier.mapRequest(request);

        assertNotNull(crazyAirRequest);
        assertEquals(crazyAirRequest.getOrigin(),"origin");
        assertEquals(crazyAirRequest.getDestination(),"destination");
        assertEquals(crazyAirRequest.getDepartureDate(),"2017-01-10T14:55+01:00");
        assertEquals(crazyAirRequest.getReturnDate(),"2017-01-10T15:55+01:00");
        assertEquals(crazyAirRequest.getPassengerCount(),3);
    }

    @Test
    public void crazy_response_should_be_mapped_to_busy_response(){


        CrazyAirResponse r2 = new CrazyAirResponse();
        r2.setAirline("airline2");
        r2.setPrice(223.123d);
        r2.setCabinclass("cabinclass2");
        r2.setDepartureAirportCode("LHR2");
        r2.setDestinationAirportCode("AMS2");
        r2.setDepartureDate("2017-01-10T16:55+01:00");
        r2.setArrivalDate("2017-01-10T17:55+01:00");

        List<BusyFlightsResponse> responseList = supplier.mapResponse(Arrays.asList(r1,r2));

        assertNotNull(responseList);
        assertEquals(responseList.size(),2);

        BusyFlightsResponse response1 = responseList.get(0);
        assertNotNull(response1);
        assertEquals(response1.getAirline(),"airline1");
        assertEquals(response1.getSupplier(),"name");
        assertEquals(response1.getFare().toString(),"123.12");
        assertEquals(response1.getDepartureAirportCode(),"LHR1");
        assertEquals(response1.getDestinationAirportCode(),"AMS1");
        assertEquals(response1.getDepartureDate(),"2017-01-10T14:55+01:00");
        assertEquals(response1.getArrivalDate(),"2017-01-10T15:55+01:00");

        BusyFlightsResponse response2 = responseList.get(1);
        assertNotNull(response2);
        assertEquals(response2.getAirline(),"airline2");
        assertEquals(response2.getSupplier(),"name");
        assertEquals(response2.getFare().toString(),"223.12");
        assertEquals(response2.getDepartureAirportCode(),"LHR2");
        assertEquals(response2.getDestinationAirportCode(),"AMS2");
        assertEquals(response2.getDepartureDate(),"2017-01-10T16:55+01:00");
        assertEquals(response2.getArrivalDate(),"2017-01-10T17:55+01:00");
    }

    @Test
    public void response_class_should_be_crazy(){
        assertEquals(supplier.getResponseClass(),CrazyAirResponse.class);
    }
}
