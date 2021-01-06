package com.fms.core.port.out;

import com.fms.core.domain.IATACode;

import java.util.List;

public interface IATACodeRepositoryPort {
    boolean addIATACode(IATACode iataCode);

    List<IATACode> getIATACodes();
}
