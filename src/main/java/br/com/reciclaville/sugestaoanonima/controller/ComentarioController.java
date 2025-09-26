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
    public List<Comentario> listarComentarios(@PathVariable Long sugestaoId) {
        return comentarioService.listarComentariosPorSugestao(sugestaoId);
    }

    @PostMapping
    public ResponseEntity<Comentario> adicionarComentario(
            @PathVariable Long sugestaoId,
            @RequestBody Comentario comentario) {
        Comentario novoComentario = comentarioService.adicionarComentario(sugestaoId, comentario);
        return new ResponseEntity<>(novoComentario, HttpStatus.CREATED);
    }
}