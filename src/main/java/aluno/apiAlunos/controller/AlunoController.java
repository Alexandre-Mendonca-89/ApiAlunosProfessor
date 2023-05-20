package aluno.apiAlunos.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import aluno.apiAlunos.model.AlunoModel;
import aluno.apiAlunos.service.AlunoService;
import jakarta.validation.Valid;

@RestController

@RequestMapping("/aluno")
public class AlunoController {
	@Autowired
	AlunoService alunoService = new AlunoService();

@GetMapping("/todos")
	public ResponseEntity<List<AlunoModel>> retornaAlunos1() {
		List listaDeAlunos = new ArrayList();
		try {
			listaDeAlunos = alunoService.buscarTodos();
			return new ResponseEntity<List<AlunoModel>>(listaDeAlunos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<AlunoModel>>(listaDeAlunos, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/unico")
	public ResponseEntity<List<AlunoModel>> retornaAlunos() {
		List listaDeAlunos = new ArrayList();
		try {
			listaDeAlunos = alunoService.buscarTodos();
			return new ResponseEntity<List<AlunoModel>>(listaDeAlunos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<AlunoModel>>(listaDeAlunos, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping(value = "nome/{nome}")
	public ResponseEntity<Optional<AlunoModel>> buscarPorNome(@PathVariable(value = "nome") String nome) {
		Optional<AlunoModel> listaDeAlunos = alunoService.buscaPorNome(nome);
		if (listaDeAlunos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Optional<AlunoModel>>(listaDeAlunos, HttpStatus.OK);
		}

	}

	@PostMapping
	public ResponseEntity salvar(@RequestBody AlunoModel aluno) {
		try {
			alunoService.salvar(aluno);
			return new ResponseEntity(aluno, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(aluno, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/remover/{id}", method = RequestMethod.DELETE)
	public ResponseEntity remover(@PathVariable(value = "id") long id) {
		try {
			Optional aluno = alunoService.buscarPorId(id); // procurar por o que é Optional e fazer o tratamento caso a
															// pessoa tenha digitado um id inexistente.
			alunoService.removerPorId(id);
			return new ResponseEntity("Aluno " + ((AlunoModel) aluno.get()).getNome() + " removido com sucesso", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity("Id não encontrada", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/alterar/{id}")
	public ResponseEntity alterar(@PathVariable(value = "id") long id, @Valid @RequestBody AlunoModel aluno) {
		System.out.println("aqui");
		Optional AlunoOptional = alunoService.buscarPorId(id);
		if (AlunoOptional.isPresent()) {
			AlunoModel alunoQueSeraAlterado = (AlunoModel) AlunoOptional.get();
			alunoQueSeraAlterado.setNome(aluno.getNome());
			alunoService.salvar(alunoQueSeraAlterado);
			return new ResponseEntity(alunoQueSeraAlterado, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}