package br.com.desafio.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.desafio.dto.ClienteDto;
import br.com.desafio.error.ResourceNoContentException;
import br.com.desafio.model.Cliente;
import br.com.desafio.service.ClienteService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("clientes")
public class ClienteController {

    @Autowired
	ClienteService clienteService;
    
    @ApiOperation(value = "Get Clients By Filter | Paginator", notes = "Returns a List of Clients")
    @ApiResponses(value = {
    		  @ApiResponse(code = 200, message = "Successfully retrieved"),
    		  @ApiResponse(code = 404, message = "Not found - The client was not found")
    		})
    @GetMapping("/{nome}/{pageNumber}/{pageSize}/{fieldSort}/{sort}")
    public Page<Cliente> getAllbyFilterPage(@PathVariable(name = "nome", required = false) @ApiParam(name = "nome", value = "Nome do Cliente") String nome, 
    		@PathVariable("pageNumber") @ApiParam(name = "pageNumber", value = "Número da página") Integer pageNumber, 
    		@PathVariable("pageSize") @ApiParam(name = "pageSize", value = "Tamanho da Página") Integer pageSize, 
    		@PathVariable("fieldSort") @ApiParam(name = "fieldSort", value = "Campo Para Ordenação") String fieldSort, 
    		@PathVariable("sort") @ApiParam(name = "sort", value = "Direção Ordenação DESC/ASC") String sort) {
        return this.clienteService.findByFieldAndPage(pageNumber, pageSize, nome, fieldSort, sort);
    }

    @ApiOperation(value = "Get Client By CPF", notes = "Returns a Client")
    @ApiResponses(value = {
    		  @ApiResponse(code = 200, message = "Successfully retrieved"),
    		  @ApiResponse(code = 404, message = "Not found - The client was not found")
    		})
    @GetMapping("/{cpf}")
    public ResponseEntity<?> getByCPF(@PathVariable("cpf") @ApiParam(name = "CPF", value = "CPF") String cpf) {
        final Optional<ClienteDto> clienteDTO = this.clienteService.getByCPF(cpf);
        if (clienteDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clienteDTO.get(), HttpStatus.OK);
    }

    @ApiOperation(value = "Create Client", notes = "Return a new Client")
    @ApiResponses(value = {
    		  @ApiResponse(code = 200, message = "Successfully retrieved"),
    		  @ApiResponse(code = 404, message = "Not found - The client was not found")
    		})
    @PostMapping
    public ClienteDto create(@Valid @RequestBody ClienteDto cliente) {
    	verifyIfClientExists(cliente.getCpf());
        return this.clienteService.create(cliente);
    }

    @ApiOperation(value = "Update Client", notes = "Returns a updated Client")
    @ApiResponses(value = {
    		  @ApiResponse(code = 200, message = "Successfully retrieved"),
    		  @ApiResponse(code = 404, message = "Not found - The client was not found")
    		})
    @PutMapping("/{id}")
    public ClienteDto update(@Valid @RequestBody ClienteDto cliente, @PathVariable("id") @ApiParam(name = "id", value = "ID") Long id) {
        verifyIfClientExists(id);
    	return this.clienteService.update(cliente);
    }

    @ApiOperation(value = "Delete Client", notes = "No Returns")
    @ApiResponses(value = {
    		  @ApiResponse(code = 404, message = "Not found - The client was not found")
    		})
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") @ApiParam(name = "id", value = "ID") Long id) {
        this.clienteService.delete(id);
    }
    
    private void verifyIfClientExists(String cpf) {
    	if(this.clienteService.getByCPF(cpf).isEmpty()){
    		throw new ResourceNoContentException("Cliente com CPF: " + cpf + " não encontrado.");
    	}
    }
    
    private void verifyIfClientExists(Long id) {
    	if(this.clienteService.getById(id).isEmpty()){
    		throw new ResourceNoContentException("Cliente com ID: " + id + " não encontrado.");
    	}
    }

}
