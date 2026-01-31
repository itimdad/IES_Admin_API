package com.imdad.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class ExceptionInfo {

    private String exCode;
    private String exMsg;
    private LocalDate date;

}
