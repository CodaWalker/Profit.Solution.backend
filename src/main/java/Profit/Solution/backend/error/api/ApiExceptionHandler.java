package Profit.Solution.backend.error.api;

import Profit.Solution.backend.error.dto.ErrorDTO;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ApiExceptionHandler {

    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ErrorDTO handleResourceNotFoundException(ConstraintViolationException e) {
        return new ErrorDTO(400, "Ошибка параметров запроса");
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {BindException.class})
    public ErrorDTO parameterBindException(BindException e) {
        return new ErrorDTO(400, "Некорректный формат одного из параметров запроса");
    }

    @ResponseBody
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorDTO processEntityNotFoundException(EntityNotFoundException exception) {
        return new ErrorDTO(1000, exception.getMessage());
    }

}
