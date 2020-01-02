package com.travix.medusa.busyflights.domain.busyflights;

import com.travix.medusa.busyflights.controller.validation.ValidationMessages;
import com.travix.medusa.busyflights.controller.validation.rule.IsValidDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/* here I assumed returnDate must be specified, although if null it can be interpreted as one-way.
also I could have added a (departureDate < returnDate) validation check */
public class BusyFlightsRequest {
    @NotNull
    @Pattern(regexp = "[a-zA-Z]{3}",message = ValidationMessages.INVALID_IATA_CODE)
    private String origin;
    @NotNull
    @Pattern(regexp = "[a-zA-Z]{3}",message = ValidationMessages.INVALID_IATA_CODE)
    private String destination;
    @NotNull
    @IsValidDate(message = ValidationMessages.INVALID_ISO_DATE_FORMAT)
    private String departureDate;
    @NotNull
    @IsValidDate(message = ValidationMessages.INVALID_ISO_DATE_FORMAT)
    private String returnDate;
    @Max(value = 4)
    @Min(value = 1)
    private int numberOfPassengers;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(final String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(final String destination) {
        this.destination = destination;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(final String departureDate) {
        this.departureDate = departureDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(final String returnDate) {
        this.returnDate = returnDate;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(final int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }
}