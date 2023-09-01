package com.example.demo.dtoRequest;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.Date;

@AllArgsConstructor
@ToString
public class ErrorDtoR {
    public String path;
    public Integer code;
    public Date date;
    public String reason;
}
