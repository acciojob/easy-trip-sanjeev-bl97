package com.driver.controllers;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AirportRepository {

    private HashMap<String, Airport> airportMap = new HashMap<>();
    private HashMap<Integer, Flight> flightMap = new HashMap<>();
    private HashMap<Integer, Passenger> passengerMap = new HashMap<>();
    private HashMap<Integer, List<Integer>> flightPassengerMap = new HashMap<>();
    private HashMap<Integer,Integer> farePassenger =  new HashMap<>();
    private HashMap<Integer,Integer> revenueFlight =  new HashMap<>();



    public void addAirport(Airport airport) {

        airportMap.put(airport.getAirportName(),airport);
    }

    public void addPassenger(Passenger passenger) {

        passengerMap.put(passenger.getPassengerId(),passenger);
    }

    public void addFlight(Flight flight) {

        flightMap.put(flight.getFlightId(),flight);

    }


    public List<Airport> getAllAirports(){

        return new ArrayList<>(airportMap.values());
    }

    public List<Flight> getAllFlights(){

        return new ArrayList<>(flightMap.values());
    }

    public int getNumberOfPeopleOn(Date date, String airportName) {
        City city = airportMap.get(airportName).getCity();
        int count = 0;

        for(int flightId : flightMap.keySet()){
            Flight flight = flightMap.get(flightId);

            if( (flight.getFromCity().equals(city) || flight.getToCity().equals(city) ) && flight.getFlightDate().equals(date)){
                if(flightPassengerMap.get(flightId) != null)
                count += flightPassengerMap.get(flightId).size();
            }
        }

        return count;
    }

    public int calculateFlightFare(Integer flightId) {
        int size = 0;
        if(flightPassengerMap.containsKey(flightId))
            size = flightPassengerMap.get(flightId).size();

        return 3000 + (size * 50);
    }

    public String bookATicket(Integer flightId, Integer passengerId) {
        int maxCap = flightMap.get(flightId).getMaxCapacity();
        int curCap = flightPassengerMap.containsKey(flightId) ? flightPassengerMap.get(flightId).size() : 0;

        if(curCap == maxCap) return "FAILURE";

        if(flightPassengerMap.containsKey(flightId) && flightPassengerMap.get(flightId).contains(passengerId))
            return "FAILURE";

        List<Integer> al = flightPassengerMap.get(flightId);
        int fare = calculateFlightFare(flightId);
        farePassenger.put(passengerId,fare);
        revenueFlight.put(flightId,revenueFlight.getOrDefault(flightId,0) + fare);

        if(al == null)
            al = new ArrayList<>();

        al.add(passengerId);



        flightPassengerMap.put(flightId,al);

        return "SUCCESS";

    }

    public String cancelATicket(Integer flightId, Integer passengerId) {
        if(!flightPassengerMap.containsKey(flightId) || (flightPassengerMap.containsKey(flightId) && !flightPassengerMap.get(flightId).contains(passengerId) ) )
            return "FAILURE";

        List<Integer> al = flightPassengerMap.get(flightId);
        al.remove(passengerId);

        int fare = farePassenger.get(passengerId);
        farePassenger.remove(passengerId);
        revenueFlight.put(flightId,revenueFlight.getOrDefault(flightId,0) - fare);
        return "SUCCESS";

    }

    public int countOfBookingsDoneByPassengerAllCombined(Integer passengerId) {
        int count = 0;

        for(Integer flight : flightPassengerMap.keySet()){
            List<Integer> al = flightPassengerMap.get(flight);
            if(al.contains(passengerId))
                count++;
        }

        return count;
    }

    public String getAirportNameFromFlightId(Integer flightId) {
        if(!flightMap.containsKey(flightId)) return null;

        City city =  flightMap.get(flightId).getFromCity();

        for(String airport : airportMap.keySet()){
            if(airportMap.get(airport).getCity().equals(city))
                return airport;
        }

        return null;
    }

    public int calculateRevenueOfAFlight(Integer flightId) {

        return revenueFlight.get(flightId);
    }

}
