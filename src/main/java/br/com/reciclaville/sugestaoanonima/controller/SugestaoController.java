package br.com.reciclaville.sugestaoanonima.controller;

import br.com.reciclaville.sugestaoanonima.model.Sugestao;
import br.com.reciclaville.sugestaoanonima.service.SugestaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sugestoes")
public class SugestaoController {

    @Autowired
    private SugestaoService sugestaoService;

    @GetMapping
    public List<Sugestao> listarTodasSugestoes() {
        return sugestaoService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sugestao> buscarSugestaoPorId(@PathVariable Long id) {
        Sugestao sugestao = sugestaoService.buscarPorId(id);
        return ResponseEntity.ok(sugestao);
    }

    @PostMapping
    public ResponseEntity<Sugestao> criarSugestao(@RequestBody Sugestao sugestao) {
        Sugestao novaSugestao = sugestaoService.salvar(sugestao);
        return new ResponseEntity<>(novaSugestao, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sugestao> atualizarSugestao(@PathVariable Long id, @RequestBody Sugestao sugestaoDetalhes) {
        Sugestao sugestaoAtualizada = sugestaoService.atualizar(id, sugestaoDetalhes);
        return ResponseEntity.ok(sugestaoAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarSugestao(@PathVariable Long id) {
        sugestaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}