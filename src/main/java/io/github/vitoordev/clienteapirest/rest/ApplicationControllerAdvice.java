package io.github.vitoordev.clienteapirest.rest;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import io.github.vitoordev.clienteapirest.exception.ApiErrors;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestControllerAdvice
@Api(value="API RESTFULL Erros")
public class ApplicationControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiOperation(value="Retorna erros")
    public ApiErrors handleValidationErros(MethodArgumentNotValidException ex ){
        BindingResult bindingResult = ex.getBindingResult();
        List<String> messages = bindingResult.getAllErrors()
                .stream()
                .map(objectError -> objectError.getDefaultMessage())
                .collect(Collectors.toList());
        return new ApiErrors(messages);
    }

    @ExceptionHandler(ResponseStatusException.class)
    @ApiOperation(value="Retorna erros")
    public ResponseEntity handleResponseStatusException(ResponseStatusException ex){
        String mensagemErro = ex.getReason();
        HttpStatus codigoStatus = ex.getStatus();
        ApiErrors apiErrors = new ApiErrors(mensagemErro);
        return new ResponseEntity(apiErrors, codigoStatus);
    }
}
