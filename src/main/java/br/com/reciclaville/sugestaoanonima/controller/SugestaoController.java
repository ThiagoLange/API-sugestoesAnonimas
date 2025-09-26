package br.com.reciclaville.sugestaoanonima.controller;

import br.com.reciclaville.sugestaoanonima.dto.SugestaoRequestDTO;
import br.com.reciclaville.sugestaoanonima.model.Sugestao;
import br.com.reciclaville.sugestaoanonima.service.SugestaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/sugestoes")
public class SugestaoController {

    @Autowired
    private SugestaoService sugestaoService;

    /**
     * Endpoint para consultar sugestões com ordenação padrão e filtro opcional por título.
     * - Mapeado para GET /api/sugestoes
     * - A ordenação é sempre por data de atualização decrescente.
     * - Aceita um parâmetro de consulta opcional 'titulo' para filtrar os resultados.
     * Exemplo: /api/sugestoes?titulo=limpeza
     * - Retorna HTTP Status 200 OK com la lista de sugestões (que pode ser vazia).
     *
     * @param titulo Parâmetro de consulta não obrigatório para filtrar pelo título.
     * @return ResponseEntity contendo a lista de sugestões.
     */
    @GetMapping
    public ResponseEntity<List<Sugestao>> listarTodasSugestoes(
            @RequestParam(required = false) String titulo) {
        List<Sugestao> lista = sugestaoService.listarTodas(titulo);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sugestao> buscarSugestaoPorId(@PathVariable Long id) {
        Sugestao sugestao = sugestaoService.buscarPorId(id);
        return ResponseEntity.ok(sugestao);
    }

    @PostMapping
    public ResponseEntity<Sugestao> criarSugestao(@Valid @RequestBody SugestaoRequestDTO dto) {
        Sugestao novaSugestao = sugestaoService.criar(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novaSugestao.getId())
                .toUri();
        return ResponseEntity.created(location).body(novaSugestao);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sugestao> atualizarSugestao(@PathVariable Long id, @Valid @RequestBody SugestaoRequestDTO dto) {
        Sugestao sugestaoAtualizada = sugestaoService.atualizar(id, dto);
        return ResponseEntity.ok(sugestaoAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarSugestao(@PathVariable Long id) {
        sugestaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}