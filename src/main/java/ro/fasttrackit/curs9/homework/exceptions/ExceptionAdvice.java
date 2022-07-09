package ro.fasttrackit.curs9.homework.exceptions;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    ApiError handleResourceNotFound(EntityNotFoundException ex) {
        return new ApiError("ERROR", ex.getMessage());
    }

    @ExceptionHandler(InvalidCleanUpModelException.class)
    @ResponseStatus(BAD_REQUEST)
    ApiError handleResourceNotFound(InvalidCleanUpModelException ex) {
        return new ApiError("ERROR", ex.getMessage());
    }

    @ExceptionHandler(InvalidReviewException.class)
    @ResponseStatus(BAD_REQUEST)
    ApiError handleResourceNotFound(InvalidReviewException ex) {
        return new ApiError("ERROR", ex.getMessage());
    }

}

record ApiError(String code, String message) {
}