package com.cheatsheet.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class ResponseDTO {
    private String message;
    private String status;
    private Integer id;
//    private User user;
    public ResponseDTO(String message) {
        this.message = message;
    }

    public ResponseDTO(String message, String status, Integer id) {
        this.message = message;
        this.status = status;
        this.id = id;
    }
}
