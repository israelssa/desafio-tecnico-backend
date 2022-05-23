package br.com.desafio.error;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import javax.validation.UnexpectedTypeException;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import br.com.desafio.dto.ErrorDto;


@ControllerAdvice
public class Handler {

    @ResponseStatus(CONFLICT)
    @ResponseBody
    @ExceptionHandler(ResourceConflictException.class)
    public ErrorDto registroJaExisteHandlerException(ResourceConflictException ex) {
        return ErrorDto.of(CONFLICT.value(),ex.getMessage());
    }

    @ResponseStatus(CONFLICT)
    @ResponseBody
    @ExceptionHandler(UnexpectedTypeException.class)
    public ErrorDto unexpectedTypeException(UnexpectedTypeException ex) {
        return ErrorDto.of(BAD_REQUEST.value(), ex.getMessage());
    }

    @ResponseStatus(CONFLICT)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDto methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return new ErrorDto(BAD_REQUEST.value(), "Campos invalidos!", ex.getBindingResult().getAllErrors());
    }

    @ResponseStatus(NO_CONTENT)
    @ResponseBody
    @ExceptionHandler(ResourceNoContentException.class)
    public ErrorDto registroNaoEncontradoHandlerException(ResourceNoContentException ex) {
        return ErrorDto.of(NO_CONTENT.value(), ex.getMessage());
    }

}