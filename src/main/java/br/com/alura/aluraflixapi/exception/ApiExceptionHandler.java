package br.com.alura.aluraflixapi.exception;

import br.com.alura.aluraflixapi.dto.ExceptionDto;
import org.postgresql.util.PSQLException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
public class ApiExceptionHandler {
    private final MessageSource messageSource;

    public ApiExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ExceptionDto> handle(MethodArgumentNotValidException exception) {
        List<ExceptionDto> exceptionDtoList = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e -> {
            String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ExceptionDto error = new ExceptionDto(HttpStatus.BAD_REQUEST,message);
            exceptionDtoList.add(error);
        });

        return exceptionDtoList;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public List<ExceptionDto> handle(ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        List<ExceptionDto> exceptionDtoList = new ArrayList<>();

        constraintViolations.forEach(e -> {
            ExceptionDto error = new ExceptionDto(HttpStatus.BAD_REQUEST,e.getMessage());
            exceptionDtoList.add(error);
        });

        return exceptionDtoList;
    }

    @ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ExceptionDto handle(HttpRequestMethodNotSupportedException exception) {
        return new ExceptionDto(HttpStatus.METHOD_NOT_ALLOWED, exception.getMessage());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ExceptionDto handle(MethodArgumentTypeMismatchException exception) {
        return new ExceptionDto(HttpStatus.BAD_REQUEST,exception.getMessage());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PSQLException.class)
    public ExceptionDto handle(PSQLException exception) {
        return new ExceptionDto(HttpStatus.BAD_REQUEST,exception.getMessage());
    }
}
