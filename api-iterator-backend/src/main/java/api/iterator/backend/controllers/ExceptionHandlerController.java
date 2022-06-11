package api.iterator.backend.controllers;

import api.iterator.backend.exceptions.SystemError;
import api.iterator.backend.exceptions.SystemException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * Controller Exception Handler
 */
@ControllerAdvice
class ExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    final ResponseEntity<SystemError> exception(Exception ex, ServletWebRequest request) {
        ex.printStackTrace();
        return new ResponseEntity<>(new SystemError.Builder(999)
                .details(ex.getMessage())
                .fromPath(request.getRequest().getRequestURI())
                .withMessage("Unknown exception")
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SystemException.class)
    final ResponseEntity<SystemError> handleBusinessException(SystemException ex, ServletWebRequest request) {
        ex.printStackTrace();
        return new ResponseEntity<>(new SystemError.Builder(ex.getCode())
                .details(ex.getDetailMessage())
                .fromPath(request.getRequest().getRequestURI())
                .withMessage(ex.getUserMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<SystemError> handleSQLException(DataIntegrityViolationException ex, ServletWebRequest request) {
        ex.printStackTrace();
        return new ResponseEntity<>(new SystemError.Builder(101)
                .details("Query data conflicted, please check logs.")
                .fromPath(request.getRequest().getRequestURI())
                .withMessage("SQL exception")
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<SystemError> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, ServletWebRequest request) {
        ex.printStackTrace();
        return new ResponseEntity<>(new SystemError.Builder(400)
                .details("Missing fields in request body")
                .fromPath(request.getRequest().getRequestURI())
                .withMessage("Invalid request body exception")
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<SystemError> handleIllegalArgumentException(IllegalArgumentException ex, ServletWebRequest request) {
        return new ResponseEntity<>(new SystemError.Builder(400)
                .details(ex.getMessage())
                .fromPath(request.getRequest().getRequestURI())
                .withMessage("Invalid field")
                .build(), HttpStatus.BAD_REQUEST);
    }
}
