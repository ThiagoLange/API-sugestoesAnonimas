package br.com.reciclaville.sugestaoanonima.service;

import br.com.reciclaville.sugestaoanonima.model.Sugestao;
import br.com.reciclaville.sugestaoanonima.repository.SugestaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SugestaoService {

    @Autowired
    private SugestaoRepository sugestaoRepository;

    public List<Sugestao> listarTodas() {
        return sugestaoRepository.findAll();
    }

    public Sugestao buscarPorId(Long id) {
        return sugestaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sugestão não encontrada com o id: " + id));
    }

    public Sugestao salvar(Sugestao sugestao) {
        return sugestaoRepository.save(sugestao);
    }

    public Sugestao atualizar(Long id, Sugestao sugestaoDetalhes) {
        Sugestao sugestao = buscarPorId(id);
        sugestao.setTitulo(sugestaoDetalhes.getTitulo());
        sugestao.setDescricao(sugestaoDetalhes.getDescricao());
        return sugestaoRepository.save(sugestao);
    }

    public void deletar(Long id) {
        Sugestao sugestao = buscarPorId(id);
        sugestaoRepository.delete(sugestao);
    }
}