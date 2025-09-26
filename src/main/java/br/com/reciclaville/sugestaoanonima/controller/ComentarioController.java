package br.com.reciclaville.sugestaoanonima.controller;

import br.com.reciclaville.sugestaoanonima.model.Comentario;
import br.com.reciclaville.sugestaoanonima.service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sugestoes/{sugestaoId}/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @GetMapping
    public ResponseEntity<List<Comentario>> listarComentarios(@PathVariable Long sugestaoId) {
        List<Comentario> comentarios = comentarioService.listarComentariosPorSugestao(sugestaoId);
        return ResponseEntity.ok(comentarios);
    }

    /**
     * Endpoint para criar um novo comentário para uma sugestão.
     * - Mapeado para POST /api/sugestoes/{sugestaoId}/comentarios
     * - Recebe um JSON com o campo "texto" no corpo da requisição.
     * - Retorna HTTP Status 201 Created com o corpo do comentário criado.
     *
     * @param sugestaoId O ID da sugestão pai, vindo da URL.
     * @param comentario O objeto Comentario deserializado do corpo JSON da requisição.
     * @return ResponseEntity contendo o comentário criado.
     */
    @PostMapping
    public ResponseEntity<Comentario> adicionarComentario(
            @PathVariable Long sugestaoId,
            @RequestBody Comentario comentario) { // Para um DTO simples como este, receber a entidade é aceitável.

        Comentario novoComentario = comentarioService.adicionarComentario(sugestaoId, comentario);
        return new ResponseEntity<>(novoComentario, HttpStatus.CREATED);
    }
}