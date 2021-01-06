package com.fms.user.adapter.mapper;

import com.fms.api.dto.FlightCompositeKeyDTO;
import com.fms.api.dto.FlightDTO;
import com.fms.user.api.dto.*;
import com.fms.user.core.domain.*;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserAndBookingMapper {

    public Bookings convertToBookings(BookingsDTO bookingsDTO) {

        List<TravellerDTO> travellerDTOList = bookingsDTO.getListOfTravellers();
        List<Traveller> travellerList = this.convertToTravellerList(travellerDTOList);
        Bookings bookings = new Bookings();
        bookings.setMobile(bookingsDTO.getMobile());
        bookings.setPnr(bookingsDTO.getPnr());
        bookings.setNumberOfSeats(bookingsDTO.getNumberOfSeats());
        bookings.setListOfTravellers(travellerList);
        bookings.setSeatType(bookingsDTO.getSeatType());
        bookings.setBookedDate(bookingsDTO.getBookedDate());
        return bookings;
    }

    public List<Traveller> convertToTravellerList(List<TravellerDTO> travellerDTOList) {
        List<Traveller> travellerList = new ArrayList<>();
        for (int i = 0; i < travellerDTOList.size(); i++) {
            travellerList.add(this.convertToTraveller(travellerDTOList.get(i)));
        }
        return travellerList;
    }

    public Traveller convertToTraveller(TravellerDTO travellerDTO) {
        Traveller traveller = new Traveller(travellerDTO.getName(), travellerDTO.getGender(),
                travellerDTO.getAge(), travellerDTO.getSeatNo());
        return traveller;
    }

    public BookingsDTO convertToBookingsDTO(Bookings bookings) {

        List<Traveller> travellerList = bookings.getListOfTravellers();
        List<TravellerDTO> travellerDTOList = this.convertToTravellerDTOList(travellerList);

        FlightCompositeKeyDTO flightCompositeKeyDTO =
                this.convertToFlightCompositeKeyDTO(bookings.getFlightDetails());

        return BookingsDTO.builder().mobile(bookings.getMobile())
                .flightDetails(flightCompositeKeyDTO).pnr(bookings.getPnr())
                .numberOfSeats(bookings.getNumberOfSeats()).seatType(bookings.getSeatType())
                .listOfTravellers(travellerDTOList).bookedDate(bookings.getBookedDate()).build();

    }

    public TravellerDTO convertToTravellerDTO(Traveller traveller) {

        return TravellerDTO.builder().name(traveller.getName()).gender(traveller.getGender()).
                age(traveller.getAge()).seatNo(traveller.getSeatNo()).build();
    }


    public List<TravellerDTO> convertToTravellerDTOList(List<Traveller> travellerList) {
        List<TravellerDTO> travellerDTOList = new ArrayList<>();
        for (int i = 0; i < travellerList.size(); i++) {
            travellerDTOList.add(this.convertToTravellerDTO(travellerList.get(i)));
        }
        return travellerDTOList;
    }


    public List<BookingsDTO> convertToBookingsDTOList(List<Bookings> bookingsList) {

        List<BookingsDTO> bookingsDTOList = new ArrayList<>();
        for (int i = 0; i < bookingsList.size(); i++) {
            bookingsDTOList.add(this.convertToBookingsDTO(bookingsList.get(i)));
        }
        return bookingsDTOList;
    }

    public Flight convertToFlight(FlightDTO flightDTO) {


        FlightCompositeKeyDTO flightCompositeKeyDTO = flightDTO.getFlightId();
        FlightCompositeKey flightCompositeKey = this.convertToFlightCompositeKey(flightCompositeKeyDTO);
        Flight flight = new Flight();
        flight.setFlightId(flightCompositeKey);
        BeanUtils.copyProperties(flightDTO, flight, "FlightCompositeKey");

        return flight;
    }

    public FlightCompositeKey convertToFlightCompositeKey(FlightCompositeKeyDTO flightCompositeKeyDTO) {
        FlightCompositeKey flightCompositeKey = new FlightCompositeKey();
        BeanUtils.copyProperties(flightCompositeKeyDTO, flightCompositeKey);
        return flightCompositeKey;
    }


    public FlightDTO convertToFlightDTO(Flight flight) {
        FlightCompositeKey flightCompositeKey = flight.getFlightId();
        FlightCompositeKeyDTO flightCompositeKeyDTO = this.convertToFlightCompositeKeyDTO(flightCompositeKey);

        return FlightDTO.builder().id(flight.getId()).flightId(flightCompositeKeyDTO)
                .arrDate(flight.getArrDate()).arrTime(flight.getArrTime())
                .totalBusiness(flight.getTotalBusiness()).availableBusiness(flight.getAvailableBusiness())
                .totalEconomy(flight.getTotalEconomy()).availableEconomy(flight.getAvailableEconomy()).build();
    }

    public FlightCompositeKeyDTO convertToFlightCompositeKeyDTO(FlightCompositeKey flightCompositeKey) {

        return FlightCompositeKeyDTO.builder().vendor(flightCompositeKey.getVendor()).source(
                flightCompositeKey.getSource()).destination(flightCompositeKey.getDestination())
                .depDate(flightCompositeKey.getDepDate()).depTime(flightCompositeKey.getDepTime())
                .build();

    }
}
