package io.github.vitoordev.clienteapirest.rest;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.vitoordev.clienteapirest.model.Cliente;
import io.github.vitoordev.clienteapirest.repository.ClienteRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/clientes")
@Api(value="API RESTFULL Cliente")
public class ClienteController {

    private final ClienteRepository repository;

    @Autowired
    public ClienteController(ClienteRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @ApiOperation(value="Retorna todos clientes")
    public List<Cliente> obterTodos(){
        return repository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value="Salva cliente")
    public Cliente salvar( @RequestBody @Valid Cliente cliente ){
        return repository.save(cliente);
    }

    @GetMapping("{id}")
    @ApiOperation(value="Busca um cliente por ID")
    public Cliente acharPorId( @PathVariable Integer id ){
        return repository
                .findById(id)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado") );
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value="Deleta um cliente")
    public void deletar( @PathVariable Integer id ){
        repository
            .findById(id)
            .map( cliente -> {
                repository.delete(cliente);
                return Void.TYPE;
            })
            .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado") );
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value="Atualiza um cliente")
    public void atualizar( @PathVariable Integer id,
                           @RequestBody @Valid Cliente clienteAtualizado ) {
        repository
                .findById(id)
                .map( cliente -> {
                    cliente.setNome(clienteAtualizado.getNome());
                    cliente.setCpf(clienteAtualizado.getCpf());
                    return repository.save(cliente);
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado") );
    }
}
