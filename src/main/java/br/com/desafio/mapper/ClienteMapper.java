package br.com.desafio.mapper;

import static br.com.desafio.utils.Util.dateToString;
import static br.com.desafio.utils.Util.stringToDate;
import static br.com.desafio.utils.Util.removeMascaraCpf;

import org.springframework.stereotype.Component;

import br.com.desafio.dto.ClienteDto;
import br.com.desafio.model.Cliente;

@Component
public class ClienteMapper implements Converter<ClienteDto, Cliente> {

    @Override
    public ClienteDto toDto(Cliente model) {
        return ClienteDto
                .builder()
                .id(model.getId())
                .nome(model.getNome())
                .dataNascimento(dateToString(model.getDataNascimento()))
                .cpf(model.getCpf())
                .sexo(model.getSexo())
                .build();
    }

    @Override
    public Cliente toModel(ClienteDto dto) {
        Cliente cliente = new Cliente();
        cliente.setId(dto.getId());
        cliente.setNome(dto.getNome());
        cliente.setDataNascimento(stringToDate(dto.getDataNascimento()));
        cliente.setCpf(removeMascaraCpf(dto.getCpf()));
        cliente.setSexo(dto.getSexo());
        return cliente;
    }

}
