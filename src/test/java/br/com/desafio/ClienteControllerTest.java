package br.com.desafio;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.desafio.dto.ClienteDto;
import lombok.SneakyThrows;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClienteControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@SneakyThrows
	@Test
	void errorEmpitySexo() {
		ClienteDto cliente = ClienteDto
				.builder()
				.nome("teste teste teste")
				.cpf("10236792059")
				.dataNascimento("01/01/1988")
				.build();

		mockMvc.perform(post("/clientes")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(cliente)))
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.errors[0].defaultMessage", is("Sexo Obrigatorio!")));
	}

	@SneakyThrows
	@Test
	void errorAmountOfCharacterForNameExceeded() {
		ClienteDto cliente = ClienteDto
				.builder()
				.nome("teste teste teste teste teste teste teste teste teste teste")
				.sexo("M")
				.cpf("10236792059")
				.dataNascimento("01/01/1988")
				.build();

		mockMvc.perform(post("/clientes")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(cliente)))
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.errors[0].defaultMessage", is("Nome deve ter entre 3 e 40 caracteres.")));
	}

	@SneakyThrows
	@Test
	void errorEmpityDataNascimento() {
		ClienteDto cliente = ClienteDto
				.builder()
				.nome("teste teste teste")
				.sexo("M")
				.cpf("10236792059")
				.build();

		mockMvc.perform(post("/clientes")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(cliente)))
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.errors[0].defaultMessage", is("Data de Nascimento obrigatorio!")));
	}

	@SneakyThrows
	@Test
	void errorEmpityCPF() {
		ClienteDto cliente = ClienteDto
				.builder()
				.nome("teste teste teste")
				.sexo("M")
				.dataNascimento("01/01/1988")
				.build();

		mockMvc.perform(post("/clientes")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(cliente)))
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.errors[0].defaultMessage", is("CPF Obrigatorio!")));
	}

	

	@SneakyThrows
	@Test
	void errorSexoEntry() {
		ClienteDto cliente = ClienteDto
				.builder()
				.nome("teste teste teste")
				.sexo("masculino")
				.cpf("10236792059")
				.dataNascimento("01/01/1988")
				.build();

		mockMvc.perform(post("/clientes")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(cliente)))
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.errors[0].defaultMessage", is("M: Masculino, F: Feminino.")));
	}
	
	@SneakyThrows
	@Test
	void errorCreateWithEmpityName() {
		ClienteDto cliente = ClienteDto
				.builder()
				.sexo("M")
				.cpf("10236792059")
				.dataNascimento("01/01/1988")
				.build();

		mockMvc.perform(post("/clientes")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(cliente)))
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.errors[0].defaultMessage", is("Nome Obrigatorio!")));
	}

	@SneakyThrows
	@Test
	void errorInvalidCPF() {
		ClienteDto cliente = ClienteDto
				.builder()
				.nome("teste teste teste")
				.cpf("1111111111")
				.sexo("M")
				.dataNascimento("01/01/1988")
				.build();

		mockMvc.perform(post("/clientes")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(cliente)))
				.andExpect(status().isConflict())
				.andExpect(jsonPath("$.errors[0].defaultMessage", is("CPF invalido!")));
	}

}
