package com.cheatsheet.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ErrorResponse {
    private int status;
    private String message;
    private long timeStamp;

    public ErrorResponse() {
    }
}
