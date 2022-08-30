package ro.fasttrackit.curs9.homework.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ExceptionAdvice {

    private static final String ERROR_CODE = "ERROR";

    private record ApiError(String code, String message) {
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    ApiError handleResourceNotFound(EntityNotFoundException ex) {
        return new ApiError(ERROR_CODE, ex.getMessage());
    }

    @ExceptionHandler(InvalidCleanUpModelException.class)
    @ResponseStatus(BAD_REQUEST)
    ApiError handleResourceNotFound(InvalidCleanUpModelException ex) {
        return new ApiError(ERROR_CODE, ex.getMessage());
    }

    @ExceptionHandler(InvalidReviewException.class)
    @ResponseStatus(BAD_REQUEST)
    ApiError handleResourceNotFound(InvalidReviewException ex) {
        return new ApiError(ERROR_CODE, ex.getMessage());
    }

    @ExceptionHandler(JsonPatchCannotBeAppliedException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    ApiError handleResourceNotFound(JsonPatchCannotBeAppliedException ex) {
        return new ApiError(ERROR_CODE, ex.getMessage());
    }
}

