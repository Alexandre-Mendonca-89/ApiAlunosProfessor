package aluno.apiAlunos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = { "/principal", "/" })
public class PrincipalController {

	@GetMapping
	public String principal() {
		return "Bem Vindo!!";
	}
}
