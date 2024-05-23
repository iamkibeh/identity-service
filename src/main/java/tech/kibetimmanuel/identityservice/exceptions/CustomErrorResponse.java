package tech.kibetimmanuel.identityservice.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomErrorResponse {
    private int status;
    private String message;
    private long timestamp;

}
