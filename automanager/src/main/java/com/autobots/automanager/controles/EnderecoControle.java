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
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelo.EnderecoAtualizador;
import com.autobots.automanager.modelo.Selecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.EnderecoRepositorio;

@RestController
@RequestMapping("/endereco")
public class EnderecoControle {
	@Autowired
	private EnderecoRepositorio repositorio;
	
	@Autowired
	private ClienteRepositorio ClienteRepositorio;


	@GetMapping("/endereco/{id}")
	public Endereco obterEndereco(@PathVariable long id) {
		List<Endereco> enderecos = repositorio.findAll();
		return Selecionador.enderecoSelecionador(enderecos, id);
	}

	@GetMapping("/enderecos")
	public List<Endereco> obterEndereco() {
		List<Endereco> enderecos = repositorio.findAll();
		return enderecos;
	}

	@PostMapping("/cadastro/{id}")
	public void cadastrarEndereco(@RequestBody Endereco endereco, @PathVariable long id) {
		Cliente cliente = ClienteRepositorio.getById(id);
		List<Endereco> enderecos = cliente.getEndereco();
		enderecos.add(endereco);
		cliente.setEndereco(enderecos);
		ClienteRepositorio.save(cliente);
	}

	@PutMapping("/atualizar")
	public void atualizarEndereco(@RequestBody Endereco atualizacao) {
		Endereco endereco = repositorio.getById(atualizacao.getId());
		EnderecoAtualizador atualizador = new EnderecoAtualizador();
		atualizador.atualizar(endereco, atualizacao);
		repositorio.save(endereco);
	}

	@DeleteMapping("/excluir/{id}")
	public void excluirEndereco(@RequestBody Endereco exclusao, @PathVariable long id) {
		Cliente cliente = ClienteRepositorio.getById(id);
		List<Endereco> enderecos = cliente.getEndereco();
		for (int i=0; i<enderecos.size(); i++) {
			if (enderecos.get(i).getId() == exclusao.getId()) {
				enderecos.remove(i);
				break;
			}
		}
		cliente.setEndereco(enderecos);
		ClienteRepositorio.save(cliente);
	}
}
