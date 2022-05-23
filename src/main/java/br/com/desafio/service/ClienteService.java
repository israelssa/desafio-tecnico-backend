package br.com.desafio.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import br.com.desafio.dto.ClienteDto;
import br.com.desafio.mapper.ClienteMapper;
import br.com.desafio.model.Cliente;
import br.com.desafio.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteService {

    @Autowired
	ClienteRepository repository;
    
    @Autowired
    ClienteMapper converter;

    /**
     * Criar
     * @param cliente
     */
    public ClienteDto create(ClienteDto cliente) {
        Cliente novoCliente = this.repository.save(this.converter.toModel(cliente));
        return this.converter.toDto(novoCliente);
    }

    /**
     * Atualizar
     * @param id
     * @param cliente
     */
    public ClienteDto update(ClienteDto cliente) {
        Cliente clienteAtualizado = this.repository.save(this.converter.toModel(cliente));
        return converter.toDto(clienteAtualizado);
    }

    /**
     * Remover
     * @param id
     */
    public void delete(Long id) {
        this.repository.deleteById(id);
    }
    
    /**
     * Buscar Por CPF
     * @param cpf
     */
    public Optional<ClienteDto> getByCPF(String cpf) {
        return this.repository.findByCpf(cpf).map(this.converter::toDto);
    }
    
    
    /**
     * Buscar por trecho nome, paginação.
     */
    public Page<Cliente> findByFieldAndPage(Integer pageNumber, Integer pageSize, String nome, String sortField, String sort){
		
		Pageable pageRequest;
		
		if(sort.equals("ASC")) {
			pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(sortField));
		}else {
			pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(sortField).descending());
		}
		
		return this.repository.findByNomeContaining(nome, pageRequest);
		
	}
    
    
    /**
     * Buscar por ID
     */
    public Optional<ClienteDto> getById(Long id) {
        return this.repository.findById(id).map(this.converter::toDto);
    }

}
