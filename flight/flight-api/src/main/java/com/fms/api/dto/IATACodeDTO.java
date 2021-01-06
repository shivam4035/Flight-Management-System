package com.fms.api.dto;


import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class IATACodeDTO {
    private String cityCode;
    private String cityName;
}
