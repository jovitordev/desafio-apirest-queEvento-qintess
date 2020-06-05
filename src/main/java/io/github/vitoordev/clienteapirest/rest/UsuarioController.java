package io.github.vitoordev.clienteapirest.rest;


import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.vitoordev.clienteapirest.exception.UsuarioCadastradoException;
import io.github.vitoordev.clienteapirest.model.Usuario;
import io.github.vitoordev.clienteapirest.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Api(value="API RESTFULL Usuario")
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value="salvar um usuario")
    public void salvar(@RequestBody @Valid Usuario usuario){
        try{
            service.salvar(usuario);
        }catch (UsuarioCadastradoException e){
            throw new ResponseStatusException( HttpStatus.BAD_REQUEST, e.getMessage() );
        }
    }
}
