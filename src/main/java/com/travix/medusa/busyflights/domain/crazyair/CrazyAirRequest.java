package com.travix.medusa.busyflights.domain.crazyair;

public class CrazyAirRequest {

    private String origin;
    private String destination;
    private String departureDate;
    private String returnDate;
    private int passengerCount;

    @Override
    public boolean equals(Object otherObj){
        CrazyAirRequest other = (CrazyAirRequest)otherObj;
        return this.origin.equals(other.getOrigin()) &&
                this.destination.equals(other.getDestination()) &&
                this.departureDate.equals(other.getDepartureDate()) &&
                this.returnDate.equals(other.getReturnDate()) &&
                this.passengerCount == other.getPassengerCount();
    }

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

    public int getPassengerCount() {
        return passengerCount;
    }

    public void setPassengerCount(final int passengerCount) {
        this.passengerCount = passengerCount;
    }
}
