package com.driver.controllers;
import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class AirportService {

    AirportRepository airportRepository = new AirportRepository();

    public void addAirport(Airport airport) {
        airportRepository.addAirport(airport);
    }

    public void addPassenger(Passenger passenger) {
        airportRepository.addPassenger(passenger);
    }

    public void addFlight(Flight flight) {
        airportRepository.addFlight(flight);
    }

    public String getLargestAirportName() {
        List<Airport> names = airportRepository.getAllAirports();
        List<String> ans = new ArrayList<>();
        int max = Integer.MIN_VALUE;

        for(Airport name : names) {
            if (max <= name.getNoOfTerminals()) {
                max = name.getNoOfTerminals();
                ans.add(name.getAirportName());
            }
        }
        Collections.sort(ans);

        return ans.get(0);

    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity) {
        List<Flight> names = airportRepository.getAllFlights();
        double min = Integer.MAX_VALUE;

        for(Flight name : names){
            if(name.getFromCity().equals(fromCity) && name.getToCity().equals(toCity) && min > name.getDuration())
                min = name.getDuration();
        }

        if(min == Integer.MAX_VALUE) return -1;
        return min;


    }

    public int getNumberOfPeopleOn(Date date, String airportName) {
        return airportRepository.getNumberOfPeopleOn(date,airportName);
    }

    public int calculateFlightFare(Integer flightId) {
        return airportRepository.calculateFlightFare(flightId);
    }

    public String bookATicket(Integer flightId, Integer passengerId) {
        return airportRepository.bookATicket(flightId,passengerId);

    }

    public String cancelATicket(Integer flightId, Integer passengerId) {
        return airportRepository.cancelATicket(flightId,passengerId);
    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) {
        return airportRepository.countOfBookingsDoneByPassengerAllCombined(passengerId);
    }

    public String getAirportNameFromFlightId(Integer flightId) {
        return airportRepository.getAirportNameFromFlightId(flightId);
    }

    public int calculateRevenueOfAFlight(Integer flightId) {
        return airportRepository.calculateRevenueOfAFlight(flightId);
    }

}
