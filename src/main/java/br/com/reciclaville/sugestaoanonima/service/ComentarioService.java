package br.com.reciclaville.sugestaoanonima.service;

import br.com.reciclaville.sugestaoanonima.model.Comentario;
import br.com.reciclaville.sugestaoanonima.model.Sugestao;
import br.com.reciclaville.sugestaoanonima.repository.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private SugestaoService sugestaoService;

    public List<Comentario> listarComentariosPorSugestao(Long sugestaoId) {
        sugestaoService.buscarPorId(sugestaoId);
        return comentarioRepository.findBySugestaoId(sugestaoId);
    }

    public Comentario adicionarComentario(Long sugestaoId, Comentario comentario) {
        Sugestao sugestao = sugestaoService.buscarPorId(sugestaoId);
        comentario.setSugestao(sugestao);
        return comentarioRepository.save(comentario);
    }
}