package com.fms.core.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Document(collection = "CityCode")
public class IATACode {

    @Id
    private String cityCode;

    private String cityName;

}
