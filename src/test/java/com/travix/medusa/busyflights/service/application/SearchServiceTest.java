package com.travix.medusa.busyflights.service.application;

import com.travix.medusa.busyflights.domain.Supplier;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirSupplier;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SearchServiceTest {

    @Test
    public void search_should_call_suppliers_and_return_indirect_results() throws IOException {
        CrazyAirSupplier supplier = mock(CrazyAirSupplier.class);
        BusyFlightsRequest request = new BusyFlightsRequest();
        BusyFlightsResponse response = new BusyFlightsResponse();
        List<BusyFlightsResponse> expectedResponseList = Arrays.asList(response);
        when(supplier.callRemote(request,null)).thenReturn(expectedResponseList);
        SearchService searchService = new SearchService(new Supplier[]{supplier});

        List<BusyFlightsResponse> responseList = searchService.search(request);

        assertNotNull(responseList);
        assertEquals(responseList.size(),1);
        assertTrue(responseList.contains(response));
        verify(supplier,times(1)).callRemote(request,null);
    }
}
