package tech.kibetimmanuel.identityservice.exceptions;


import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.lang.model.type.ErrorType;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(CustomException exception) {
        CustomErrorResponse customErrorResponse = new CustomErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                System.currentTimeMillis()
        );
        return ResponseEntity.badRequest().body(customErrorResponse);

    }


    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityExceptions(Exception exception) {
        ProblemDetail errorDetail = null;
        // todo: send this stack trace to an observability tool
        exception.printStackTrace();

        if (exception instanceof BadCredentialsException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
            errorDetail.setProperty("description", "The username or password is incorrect");

        }

        if (exception instanceof AccountStatusException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The account is locked");

        }

        if (exception instanceof AccessDeniedException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "You are not allowed to access this resource");

        }

        if (exception instanceof ExpiredJwtException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
            errorDetail.setProperty("description", "The JWT token has expired");

        }

        if (exception instanceof SignatureException) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
            errorDetail.setProperty("description", "The JWT signature is invalid");

        }

        if (errorDetail == null) {
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), exception.getMessage());
            errorDetail.setProperty("description", "Unknown internal server error");

        }

        return errorDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ValidationErrorResponseModel handleValidationException(MethodArgumentNotValidException exception) {
        List<ValidationErrorModel> validationErrors = processValidationErrors(exception);

        return ValidationErrorResponseModel.builder()
                .type("VALIDATION")
                .errors(validationErrors)
                .status(exception.getStatusCode().value())
                .build();
    }

    private List<ValidationErrorModel> processValidationErrors(MethodArgumentNotValidException exception) {

        List<ValidationErrorModel> validationErrorModels = new ArrayList<>();
        for(FieldError fieldError: exception.getBindingResult().getFieldErrors()) {
            validationErrorModels.add(ValidationErrorModel.builder()
                            .code(fieldError.getCode())
                            .source(fieldError.getObjectName() + "/" + fieldError.getField())
                            .detail(fieldError.getDefaultMessage())
                    .build());
        }
        return validationErrorModels;
    }
}
