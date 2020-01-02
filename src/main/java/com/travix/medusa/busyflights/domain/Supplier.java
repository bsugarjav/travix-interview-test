package com.travix.medusa.busyflights.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

/* this is a template class that every additional flights supplier has to conform with */
public abstract class Supplier {

    private String name;
    private String url;

    public Supplier(String name,String url){
        this.name = name;
        this.url = url;
    }

    //the supplier will specify it's specific wait to transform BusyFlightsRequest into the right request that needs to be remotely sent
    public abstract Object mapRequest(BusyFlightsRequest request);

    //the supplier will specify the way it maps the response coming from remote into multiple BusyFlightsResponse
    public abstract List<BusyFlightsResponse> mapResponse(List<Object> supplierResponse);

    //the supplier will specify the type of remote response, to be used when deserialize the response as string
    public abstract Class getResponseClass();

    //this will aggregate all the above functions and access remote
    public List<BusyFlightsResponse> callRemote(BusyFlightsRequest request,RestTemplate restTemplate) throws IOException {
        Object remoteRequest = this.mapRequest(request);
        String remoteResponseStr = restTemplate.postForObject(url,remoteRequest,String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        return mapResponse(objectMapper.readValue(remoteResponseStr, objectMapper.getTypeFactory().constructCollectionType(List.class, this.getResponseClass())));
    }

    public String getName() {
        return name;
    }
}