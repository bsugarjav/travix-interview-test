package com.travix.medusa.busyflights.service.application;

import com.travix.medusa.busyflights.domain.Supplier;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirSupplier;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetSupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    @Autowired
    private RestTemplate restTemplate;

    //here I'm aware that it's not the best place to put the suppliers. some ideas would be: getting the details from database if the number is quite big or just from the prop file.
    private List<Supplier> suppliers = Arrays.asList(new CrazyAirSupplier("CrazyAir","http://localhost:8080/crazyair/api/search"),
            new ToughJetSupplier("ToughJet","http://localhost:8080/toughjet/api/search"));

    public SearchService(){

    }

    public SearchService(Supplier[] suppliers){
        this.suppliers = Arrays.asList(suppliers);
    }

    public List<BusyFlightsResponse> search(BusyFlightsRequest request) {
        return suppliers.stream()
                .map(supplier -> {
                    try{
                        return supplier.callRemote(request,restTemplate);
                    }
                    catch (IOException remoteException){
                        return (List<BusyFlightsResponse>)Collections.EMPTY_LIST;
                    }
                })
                .flatMap(List::stream)
                .sorted(Comparator.comparing(BusyFlightsResponse::getFare))
                .collect(Collectors.toList());
    }
}