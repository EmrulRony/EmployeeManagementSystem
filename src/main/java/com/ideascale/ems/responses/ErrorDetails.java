package com.ideascale.ems.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

// Custom error response bean
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String error;
    private String errorDetails;

    public ErrorDetails(Date timestamp,String error, String message, String errorDetails) {
        super();
        this.timestamp = timestamp;
        this.error = error;
        this.message = message;
        this.errorDetails = errorDetails;
    }

    public Date getTimestamp() {
        return timestamp;
    }
    public String getError() {
        return error;
    }
    public String getMessage() {
        return message;
    }
    public String getErrorDetails() {
        return errorDetails;
    }
}
