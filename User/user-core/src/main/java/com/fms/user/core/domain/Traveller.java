package com.fms.user.core.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Traveller {

    private String name;
    private String gender;
    private int age;
    private int seatNo;
}
