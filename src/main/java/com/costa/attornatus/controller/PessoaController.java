package com.costa.attornatus.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.costa.attornatus.entities.Endereco;
import com.costa.attornatus.entities.Pessoa;
import com.costa.attornatus.repository.EnderecoRepository;
import com.costa.attornatus.repository.PessoaRepository;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

	@Autowired
    private PessoaRepository pessoaRepository;
 
    @Autowired
    private EnderecoRepository enderecoRepository;
 
    
    @GetMapping
    public List<Pessoa> listarPessoas() {
        return pessoaRepository.findAll();
    }
 
    
    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> consultarPessoa(@PathVariable Long id) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        return pessoa.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
 
    
    @PostMapping
    public ResponseEntity<Pessoa> criarPessoa(@RequestBody Pessoa pessoa) {
        pessoa = pessoaRepository.save(pessoa);
        return ResponseEntity.created(URI.create("/pessoas/" + pessoa.getId())).body(pessoa);
    }
 
    
    @PutMapping("/{id}")
    public ResponseEntity<Pessoa> atualizarPessoa(@PathVariable Long id, @RequestBody Pessoa pessoaAtualizada) {
    	Optional<Pessoa> pessoaExistente = pessoaRepository.findById(id);
    	if (pessoaExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        pessoaAtualizada.setId(id);
        pessoaAtualizada.setEnderecos(pessoaExistente.get().getEnderecos());
        pessoaAtualizada = pessoaRepository.save(pessoaAtualizada);

        return ResponseEntity.ok(pessoaAtualizada);
    }

    
    @PostMapping("/{id}/enderecos")
    public ResponseEntity<Endereco> criarEndereco(@PathVariable Long id, @RequestBody Endereco endereco) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);

        if (pessoa.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        endereco.setPessoa(pessoa.get());
        endereco = enderecoRepository.save(endereco);

        return ResponseEntity.created(URI.create("/pessoas/" + id + "/enderecos/" + endereco.getId())).body(endereco);
    }

    
    @GetMapping("/{id}/enderecos")
    public ResponseEntity<List<Endereco>> listarEnderecos(@PathVariable Long id) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);

        if (pessoa.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<Endereco> enderecos = pessoa.get().getEnderecos();

        return ResponseEntity.ok(enderecos);
    }

    
    @PutMapping("/{id}/endereco-principal/{enderecoId}")
    public ResponseEntity<Pessoa> definirEnderecoPrincipal(@PathVariable Long id, @PathVariable Long enderecoId) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);

        if (pessoa.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Endereco> enderecoPrincipal = pessoa.get().getEnderecos().stream()
                .filter(endereco -> endereco.getId().equals(enderecoId)).findFirst();

        if (enderecoPrincipal.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Pessoa pessoaAtualizada = pessoa.get();
        pessoaAtualizada.getEnderecos().forEach(endereco -> endereco.setPrincipal(false));
        enderecoPrincipal.get().setPrincipal(true);
        pessoaAtualizada = pessoaRepository.save(pessoaAtualizada);

        return ResponseEntity.ok(pessoaAtualizada);
    }
	
 }

