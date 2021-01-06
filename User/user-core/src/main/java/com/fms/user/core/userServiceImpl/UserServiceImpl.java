package com.fms.user.core.userServiceImpl;

import com.fms.user.core.domain.Bookings;
import com.fms.user.core.domain.Flight;
import com.fms.user.core.domain.FlightCompositeKey;
import com.fms.user.core.domain.Traveller;
import com.fms.user.core.port.in.UserService;
import com.fms.user.core.port.out.BookingClientPort;
import com.fms.user.core.port.out.BookingRepositoryport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private BookingRepositoryport bookingRepositoryport;

    @Autowired
    private BookingClientPort bookingClientPort;


    @Override
    public Bookings addBooking(Bookings bookings, String flightId) {

        Flight flight = bookingClientPort.getFlightById(flightId);
        FlightCompositeKey flightDetails = flight.getFlightId();
        String seatType = bookings.getSeatType();
        int numberOfSeats = bookings.getNumberOfSeats();
        String mobile = bookings.getMobile();

        if (seatType.equalsIgnoreCase("business")) {
            if (flight.getAvailableBusiness() < numberOfSeats) {
                System.out.println("not added, required seats not available");
                return null;
            }
        } else {
            if (flight.getAvailableEconomy() < numberOfSeats) {
                System.out.println("not added, required seats not available");
                return null;
            }
        }

        bookings.setFlightDetails(flightDetails);
        if (seatType.equalsIgnoreCase("business")) {
            for (int i = 0; i < bookings.getListOfTravellers().size(); i++) {
                int seatNumber = flight.getTotalBusiness() - flight.getAvailableBusiness() + i;
                bookings.getListOfTravellers().get(i).setSeatNo(seatNumber);
            }
            int seatsAvailableBusiness = flight.getAvailableBusiness() - bookings.getNumberOfSeats();
            flight.setAvailableBusiness(seatsAvailableBusiness);
        } else {
            for (int i = 0; i < bookings.getListOfTravellers().size(); i++) {
                int seatNumber = flight.getTotalEconomy() - flight.getAvailableEconomy() + i;
                bookings.getListOfTravellers().get(i).setSeatNo(seatNumber);
            }
            int seatsAvailableEconomy = flight.getAvailableEconomy() - bookings.getNumberOfSeats();
            flight.setAvailableEconomy(seatsAvailableEconomy);
        }
        boolean status = bookingRepositoryport.addBooking(bookings, flightDetails);

        if (status == false) {
            return null;
        }
        List<Bookings> bookingsList = bookingRepositoryport.getBookingsById(mobile);
        int totalBookings = bookingsList.size();


        boolean updateStatus = bookingClientPort.updateFlight(flight, flightId);
        System.out.println(updateStatus);
        return (bookingsList.get(totalBookings - 1));
    }

    @Override
    public List<Bookings> getBookingsById(String mobile) {
        return bookingRepositoryport.getBookingsById(mobile);
    }

}
