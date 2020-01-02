package com.travix.medusa.busyflights.controller;

import com.travix.medusa.busyflights.controller.validation.ApiErrorsContainer;
import com.travix.medusa.busyflights.controller.validation.InvalidApiRequestException;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.service.application.SearchService;
import com.travix.medusa.busyflights.service.infrastructure.ApiErrorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/api")
public class BusyFlightsController {

    @Autowired
    private SearchService searchService;

    @PostMapping(value = "/search",produces = "application/json")
    @ResponseBody
    public List<BusyFlightsResponse> search(@Validated @RequestBody BusyFlightsRequest searchRequest, BindingResult bindingResult){
        ApiErrorsContainer apiErrorsContainer = ApiErrorUtils.map(bindingResult);
        if(apiErrorsContainer.hasErrors()){
            throw new InvalidApiRequestException(apiErrorsContainer);
        }
        return searchService.search(searchRequest);
    }
}