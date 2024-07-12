package com.travelbnbtest.travelbnbtest.payload;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorDetails {

    private String errorMessage;

    private Date date;

    private String webRequest;


    public ErrorDetails(String errorMessage,Date date,String webRequest){
        this.errorMessage=errorMessage;
        this.date=date;
        this.webRequest=webRequest;
    }
}
