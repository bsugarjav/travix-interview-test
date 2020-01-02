package com.travix.medusa.busyflights;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travix.medusa.busyflights.controller.validation.ApiErrorsContainer;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(properties = "server.port=8080")//please don't change this
public class BusyFlightsApplicationTests {

	@Autowired
	private RestTemplate restTemplate;

	@LocalServerPort
	int serverPort;

	@Test
	public void busy_request_should_reach_suppliers_and_return_response(){
		BusyFlightsRequest request = new BusyFlightsRequest();
		request.setNumberOfPassengers(4);
		request.setOrigin("LHR");
		request.setDestination("AMS");
		request.setDepartureDate("2017-01-10T14:55+01:00");
		request.setReturnDate("2017-01-10T14:55+01:00");

		HttpEntity httpEntity = new HttpEntity(request);
		ResponseEntity<List<BusyFlightsResponse>> responseEntity = restTemplate.exchange("http://localhost:" + serverPort + "/api/search",
				HttpMethod.POST,
				httpEntity,
				new ParameterizedTypeReference<List<BusyFlightsResponse>>(){});

		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertNotNull(responseEntity.getBody());
		assertEquals(responseEntity.getBody().size(),2);

		List<BusyFlightsResponse> body = responseEntity.getBody();
		BusyFlightsResponse r1 = body.get(0);
		BusyFlightsResponse r2 = body.get(1);

		assertEquals(r1.getAirline(),"AirlineFromCrazyAir");
		assertEquals(r1.getSupplier(),"CrazyAir");
		assertEquals(r1.getFare().toString(),"100.12");
		assertEquals(r1.getDepartureAirportCode(),"LHR");
		assertEquals(r1.getDestinationAirportCode(),"AMS");
		assertEquals(r1.getDepartureDate(),"2017-01-10T14:55+01:00");
		assertEquals(r1.getArrivalDate(),"2017-01-10T14:55+01:00");

		assertEquals(r2.getAirline(),"AirlineFromToughtJet");
		assertEquals(r2.getSupplier(),"ToughJet");
		assertEquals(r2.getFare().toString(),"190.00");
		assertEquals(r2.getDepartureAirportCode(),"LHR");
		assertEquals(r2.getDestinationAirportCode(),"AMS");
		assertEquals(r2.getDepartureDate(),"2017-01-10T14:55+01:00");
		assertEquals(r2.getArrivalDate(),"2017-01-10T14:55+01:00");
	}

	@Test
	public void invalid_busy_request_should_reach_api_and_fail_with_400() throws IOException {
		//add test with failing payload request and assert on ApiErrorsContainer
		BusyFlightsRequest request = new BusyFlightsRequest();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			restTemplate.postForObject("http://localhost:" + serverPort + "/api/search", request, ApiErrorsContainer.class);
		}
		catch (HttpClientErrorException e){
			assertEquals(e.getStatusCode(),HttpStatus.BAD_REQUEST);
			ApiErrorsContainer apiErrorsContainer = objectMapper.readValue(e.getResponseBodyAsString(),ApiErrorsContainer.class);
			assertTrue(apiErrorsContainer.hasErrors());
			assertEquals(apiErrorsContainer.getErrors().size(),5);
			assertTrue(apiErrorsContainer.getErrors().stream().anyMatch(f -> f.getField().equals("origin")));
			assertTrue(apiErrorsContainer.getErrors().stream().anyMatch(f -> f.getField().equals("destination")));
			assertTrue(apiErrorsContainer.getErrors().stream().anyMatch(f -> f.getField().equals("departureDate")));
			assertTrue(apiErrorsContainer.getErrors().stream().anyMatch(f -> f.getField().equals("returnDate")));
			assertTrue(apiErrorsContainer.getErrors().stream().anyMatch(f -> f.getField().equals("numberOfPassengers")));
			//etc..add more concrete assertions
		}
	}

	@Test
	public void contextLoads() {
	}

}
