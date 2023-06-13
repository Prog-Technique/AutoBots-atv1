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
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelo.DocumentoAtualizador;
import com.autobots.automanager.modelo.Selecionador;
import com.autobots.automanager.repositorios.DocumentoRepositorio;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@RestController
@RequestMapping("/documento")
public class DocumentoControle {
	@Autowired
	private DocumentoRepositorio repositorio;
	
	@Autowired
	private ClienteRepositorio ClienteRepositorio;

	@GetMapping("/documento/{id}")
	public Documento obterDocumento(@PathVariable long id) {
		List<Documento> documentos = repositorio.findAll();
		return Selecionador.documentoSelecionador(documentos, id);
	}

	@GetMapping("/documentos")
	public List<Documento> obterDocumentos() {
		List<Documento> documentos = repositorio.findAll();
		return documentos;
	}

	@PostMapping("/cadastro/{id}")
	public void cadastrarDocumento(@RequestBody Documento documento, @PathVariable long id) {
		Cliente cliente = ClienteRepositorio.getById(id);
		List<Documento> documentos = cliente.getDocumentos();
		documentos.add(documento);
		cliente.setDocumentos(documentos);
		ClienteRepositorio.save(cliente);
	}

	@PutMapping("/atualizar")
	public void atualizarDocumento(@RequestBody Documento atualizacao) {
		Documento documento = repositorio.getById(atualizacao.getId());
		DocumentoAtualizador atualizador = new DocumentoAtualizador();
		atualizador.atualizar(documento, atualizacao);
		repositorio.save(documento);
	}

	@DeleteMapping("/excluir/{id}")
	public void excluirDocumento(@RequestBody Documento exclusao, @PathVariable long id) {
		Cliente cliente = ClienteRepositorio.getById(id);
		List<Documento> documentos = cliente.getDocumentos();
		for (int i=0; i<documentos.size(); i++) {
			if (documentos.get(i).getId() == exclusao.getId()) {
				documentos.remove(i);
				break;
			}
		}
		cliente.setDocumentos(documentos);
		ClienteRepositorio.save(cliente);
	}
}
