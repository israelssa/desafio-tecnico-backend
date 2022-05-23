package br.com.desafio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import static br.com.desafio.utils.Util.stringToDate;

import java.sql.Array;

import br.com.desafio.mapper.ClienteMapper;
import br.com.desafio.model.Cliente;
import br.com.desafio.service.ClienteService;

@SpringBootApplication
public class Application implements CommandLineRunner {
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
    private ClienteMapper clienteMapper;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	/***
	 * Carga Inicial de 10 Registros.
	 */
	@Override
    public void run(String... args) throws Exception {
		
		String[] cpfs = {
		"10236792059",
		"74204627048",
		"35601732051",
		"83550028067",
		"76696663070",
		"49378321070",
		"32555071032",
		"75096606023",
		"24548811010",
		"86890327039"
		};
		
		for(int i = 0; i <= 9; i++) {
			Cliente cliente = new Cliente("Cliente Teste ".concat(String.valueOf(i)), stringToDate("01/01/2000"), cpfs[i], "M");
			clienteService.create(clienteMapper.toDto(cliente));
		}
		
    }

}
