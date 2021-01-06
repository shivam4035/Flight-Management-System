package com.fms.user.api.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TravellerDTO {

    private String name;
    private String gender;
    private int age;
    private int seatNo;
}
