package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelo.Selecionador;
import com.autobots.automanager.modelo.TelefoneAtualizador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.TelefoneRepositorio;

@RestController
@RequestMapping("/telefone")
public class TelefoneControle {
	@Autowired
	private TelefoneRepositorio repositorio;
	
	@Autowired
	private ClienteRepositorio ClienteRepositorio;

	@GetMapping("/telefone/{id}")
	public Telefone obterTelefone(@PathVariable long id) {
		List<Telefone> telefones = repositorio.findAll();
		return Selecionador.telefoneSelecionador(telefones, id);
	}

	@GetMapping("/telefones")
	public List<Telefone> obterTelefone() {
		List<Telefone> telefones = repositorio.findAll();
		return telefones;
	}

	@PostMapping("/cadastro/{id}")
	public void cadastrarTelefone(@RequestBody Telefone telefone, @PathVariable long id) {
		Cliente cliente = ClienteRepositorio.getById(id);
		List<Telefone> telefones = cliente.getTelefones();
		telefones.add(telefone);
		cliente.setTelefones(telefones);
		ClienteRepositorio.save(cliente);
	}

	@PutMapping("/atualizar")
	public void atualizarTelefone(@RequestBody Telefone atualizacao) {
		Telefone telefone = repositorio.getById(atualizacao.getId());
		TelefoneAtualizador atualizador = new TelefoneAtualizador();
		atualizador.atualizar(telefone, atualizacao);
		repositorio.save(telefone);
	}

	@DeleteMapping("/excluir/{id}")
	public void excluirTelefone(@RequestBody Telefone exclusao, @PathVariable long id) {
		Cliente cliente = ClienteRepositorio.getById(id);
		List<Telefone> telefones = cliente.getTelefones();
		for (int i=0; i<telefones.size(); i++) {
			if (telefones.get(i).getId() == exclusao.getId()) {
				telefones.remove(i);
				break;
			}
		}
		cliente.setTelefones(telefones);
		ClienteRepositorio.save(cliente);
	}
}
