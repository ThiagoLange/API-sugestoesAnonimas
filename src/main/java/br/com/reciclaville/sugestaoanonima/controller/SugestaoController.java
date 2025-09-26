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
     * Endpoint para cadastrar (criar) uma nova sugestão.
     * Segue os padrões REST e convenções Java.
     * - Mapeado para POST /api/sugestoes
     * - Recebe um SugestaoRequestDTO no corpo da requisição.
     * - Usa @Valid para ativar as validações definidas no DTO.
     * - Retorna HTTP Status 201 Created em caso de sucesso.
     * - Inclui o cabeçalho 'Location' com a URL do novo recurso.
     * - Retorna o objeto recém-criado no corpo da resposta.
     *
     * @param dto Os dados da sugestão a ser criada.
     * @return ResponseEntity contendo a resposta HTTP apropriada.
     */
    @PostMapping
    public ResponseEntity<Sugestao> criarSugestao(@Valid @RequestBody SugestaoRequestDTO dto) {
        // Delega a lógica de negócio para a camada de serviço
        Sugestao novaSugestao = sugestaoService.criar(dto);

        // Constrói a URI do novo recurso para o cabeçalho "Location"
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest() // Pega a URI base (http://.../api/sugestoes)
                .path("/{id}") // Adiciona o placeholder do ID
                .buildAndExpand(novaSugestao.getId()) // Substitui o placeholder pelo ID real
                .toUri(); // Converte para o formato URI

        // Retorna a resposta HTTP 201 Created, com a URI no header e o objeto no body
        return ResponseEntity.created(location).body(novaSugestao);
    }

    // --- Outros endpoints do controller ---

    @GetMapping
    public ResponseEntity<List<Sugestao>> listarTodasSugestoes() {
        return ResponseEntity.ok(sugestaoService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sugestao> buscarSugestaoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(sugestaoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sugestao> atualizarSugestao(@PathVariable Long id, @Valid @RequestBody SugestaoRequestDTO dto) {
        return ResponseEntity.ok(sugestaoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarSugestao(@PathVariable Long id) {
        sugestaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}