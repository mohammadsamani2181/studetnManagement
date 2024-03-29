package com.studentManagement.model.DTO.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ApiErrorDTO {
    private Date timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
