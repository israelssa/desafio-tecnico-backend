package br.com.desafio.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClienteDto {

    private Long id;

    @ApiModelProperty(value = "Nome")
    @NotEmpty(message = "Nome Obrigatorio!")
    @Size(min = 1, max = 40, message = "Nome deve ter entre 3 e 40 caracteres.")
    private String nome;

    @ApiModelProperty(value = "Data de Nascimento")
    @NotEmpty(message = "Data de Nascimento obrigatorio!")
    private String dataNascimento;

    @ApiModelProperty(value = "CPF")
    @CPF(message = "CPF invalido!")
    @NotEmpty(message = "CPF Obrigatorio!")
    private String cpf;

    @ApiModelProperty(value = "Sexo M/F")
    @NotEmpty(message = "Sexo Obrigatorio!")
    @Pattern(regexp = "[M,F]", message = "M: Masculino, F: Feminino.")
    private String sexo;

}
