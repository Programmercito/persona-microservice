package com.devsu.finapp.common.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MessageError {
    private String message;
    private String code;
}
