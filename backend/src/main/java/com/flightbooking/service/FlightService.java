package com.flightbooking.service;

import com.flightbooking.entity.Flight;
import com.flightbooking.entity.Airport;
import com.flightbooking.repository.FlightRepository;
import com.flightbooking.repository.AirportRepository;
import com.flightbooking.dto.FlightSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 新增导入

import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private AirportRepository airportRepository;

    public List<Flight> searchFlights(FlightSearchRequest searchRequest) {
        // 如果提供了航班号，直接按航班号搜索
        if (searchRequest.getFlightNumber() != null && !searchRequest.getFlightNumber().isEmpty()) {
            Optional<Flight> flight = flightRepository.findByFlightNumber(searchRequest.getFlightNumber());
            if (flight.isPresent()) {
                return List.of(flight.get());
            }
            return List.of();
        }

        // 根据出发和到达机场搜索
        if (searchRequest.getDepartureAirportCode() != null && searchRequest.getDestinationAirportCode() != null) {
            Optional<Airport> departureAirport = airportRepository.findByCode(searchRequest.getDepartureAirportCode());
            Optional<Airport> destinationAirport = airportRepository.findByCode(searchRequest.getDestinationAirportCode());

            if (departureAirport.isPresent() && destinationAirport.isPresent()) {
                return flightRepository.findByDepartureAirportAndDestinationAirport(
                        departureAirport.get(), destinationAirport.get());
            }
        }

        // 如果只提供了出发机场
        if (searchRequest.getDepartureAirportCode() != null) {
            Optional<Airport> departureAirport = airportRepository.findByCode(searchRequest.getDepartureAirportCode());
            if (departureAirport.isPresent()) {
                return flightRepository.findByDepartureAirport(departureAirport.get());
            }
        }

        // 如果只提供了到达机场
        if (searchRequest.getDestinationAirportCode() != null) {
            Optional<Airport> destinationAirport = airportRepository.findByCode(searchRequest.getDestinationAirportCode());
            if (destinationAirport.isPresent()) {
                return flightRepository.findByDestinationAirport(destinationAirport.get());
            }
        }

        // 如果没有提供任何搜索条件，返回所有航班
        return flightRepository.findAll();
    }

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public Optional<Flight> getFlightById(Long id) {
        return flightRepository.findById(id);
    }

    @Transactional // 新增注解
    public Flight saveFlight(Flight flight) {
        // 根据传入的机场ID，从数据库中查找完整的机场实体
        Airport departureAirport = airportRepository.findById(flight.getDepartureAirport().getId())
                .orElseThrow(() -> new RuntimeException("出发机场不存在: " + flight.getDepartureAirport().getId()));
        Airport destinationAirport = airportRepository.findById(flight.getDestinationAirport().getId())
                .orElseThrow(() -> new RuntimeException("到达机场不存在: " + flight.getDestinationAirport().getId()));

        // 将从数据库中查找到的受管实体设置到航班对象中
        flight.setDepartureAirport(departureAirport);
        flight.setDestinationAirport(destinationAirport);

        return flightRepository.save(flight);
    }

    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }
}