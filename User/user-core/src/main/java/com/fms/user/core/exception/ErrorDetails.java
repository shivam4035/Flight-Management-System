package com.fms.user.core.exception;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;

}
