package io.github.vitoordev.clienteapirest.rest;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.vitoordev.clienteapirest.dto.ServicoPrestadoDTO;
import io.github.vitoordev.clienteapirest.model.Cliente;
import io.github.vitoordev.clienteapirest.model.ServicoPrestado;
import io.github.vitoordev.clienteapirest.repository.ClienteRepository;
import io.github.vitoordev.clienteapirest.repository.ServicoPrestadoRepository;
import io.github.vitoordev.clienteapirest.util.BigDecimalConverter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/servicos-prestados")
@RequiredArgsConstructor
@Api(value="API RESTFULL Evento")
public class ServicoPrestadoController  {

    private final ClienteRepository clienteRepository;
    private final ServicoPrestadoRepository repository;
    private final BigDecimalConverter bigDecimalConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value="Salvar um evento")
    public ServicoPrestado salvar( @RequestBody @Valid ServicoPrestadoDTO dto ){
        LocalDate data = LocalDate.parse(dto.getData(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Integer idCliente = dto.getIdCliente();

        Cliente cliente =
                clienteRepository
                        .findById(idCliente)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.BAD_REQUEST, "Cliente inexistente."));


        ServicoPrestado servicoPrestado = new ServicoPrestado();
        servicoPrestado.setDescricao(dto.getDescricao());
        servicoPrestado.setData( data );
        servicoPrestado.setCliente(cliente);
        servicoPrestado.setValor( bigDecimalConverter.converter(dto.getPreco())  );

        return repository.save(servicoPrestado);
    }

    @GetMapping
    @ApiOperation(value="Pesquisa evento")
    public List<ServicoPrestado> pesquisar(
            @RequestParam(value = "nome", required = false, defaultValue = "") String nome,
            @RequestParam(value = "mes", required = false) Integer mes
    ) {
        return repository.findByNomeClienteAndMes("%" + nome + "%", mes);
    }
}
