package guru.springframework.msscbeerservice.web.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class MvcExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<MethodError> validationExceptionHandler(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> errors = result.getFieldErrors();

        return errors.stream()
                .map(fieldError -> new MethodError(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
    }

    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> bindExceptionHandler(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @Data
    @AllArgsConstructor
    private static class MethodError {
        private String field;
        private String error;
    }
}
