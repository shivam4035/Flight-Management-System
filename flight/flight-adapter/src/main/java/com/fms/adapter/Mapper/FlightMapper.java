package com.fms.adapter.Mapper;

import com.fms.api.dto.IATACodeDTO;
import com.fms.api.dto.FlightCompositeKeyDTO;
import com.fms.api.dto.FlightDTO;
import com.fms.api.dto.FlightFareDTO;
import com.fms.core.domain.IATACode;
import com.fms.core.domain.Flight;
import com.fms.core.domain.FlightCompositeKey;
import com.fms.core.domain.FlightFare;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FlightMapper {

    public Flight convertToFlight(FlightDTO flightDTO) {


        FlightCompositeKeyDTO flightCompositeKeyDTO = flightDTO.getFlightId();
        FlightCompositeKey flightCompositeKey = this.convertToFlightCompositeKey(flightCompositeKeyDTO);
        Flight flight = new Flight();
        flight.setFlightId(flightCompositeKey);
        BeanUtils.copyProperties(flightDTO, flight, "flightId");

        return flight;
    }

    public FlightCompositeKey convertToFlightCompositeKey(FlightCompositeKeyDTO flightCompositeKeyDTO) {
        FlightCompositeKey flightCompositeKey = new FlightCompositeKey();
        BeanUtils.copyProperties(flightCompositeKeyDTO, flightCompositeKey);
        return flightCompositeKey;
    }

    public FlightFare convertToFlightFare(FlightFareDTO flightFareDTO, String flightId) {
        FlightFare flightFare = new FlightFare();
        BeanUtils.copyProperties(flightFareDTO, flightFare, "flightId");
        flightFare.setFlightId(flightId);
        return flightFare;
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

    public FlightFareDTO convertToFlightFareDTO(FlightFare flightFare) {
        return FlightFareDTO.builder().flightId(flightFare.getFlightId()).
                businessFare(flightFare.getBusinessFare()).economyFare(flightFare.getEconomyFare())
                .date(flightFare.getDate()).build();
    }

    public ArrayList<FlightDTO> convertToFlightDTOList(List<Flight> availableFlights) {

        List<FlightDTO> availableFlightsDTO = new ArrayList<>();
        for (int i = 0; i < availableFlights.size(); i++) {
            availableFlightsDTO.add(this.convertToFlightDTO(availableFlights.get(i)));
        }
        return (ArrayList<FlightDTO>) availableFlightsDTO;
    }

    public IATACode convertToIATACode(IATACodeDTO IATACodeDTO) {
        return new IATACode(IATACodeDTO.getCityCode(), IATACodeDTO.getCityName());
    }

    public IATACodeDTO convertToIATACodeDTO(IATACode IATACode) {
        return IATACodeDTO.builder().cityCode(IATACode.getCityCode()).cityName(IATACode.getCityName()).build();
    }

    public List<IATACodeDTO> convertToIATACodeDTOList(List<IATACode> iataCodeList) {

        List<IATACodeDTO> iataCodeDTOList = new ArrayList<>();
        for (int i = 0; i < iataCodeList.size(); i++) {
            iataCodeDTOList.add(convertToIATACodeDTO(iataCodeList.get(i)));
        }
        return iataCodeDTOList;
    }
}
