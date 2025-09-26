package br.com.reciclaville.sugestaoanonima.service;

import br.com.reciclaville.sugestaoanonima.dto.SugestaoRequestDTO;
import br.com.reciclaville.sugestaoanonima.model.Sugestao;
import br.com.reciclaville.sugestaoanonima.repository.SugestaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class SugestaoService {

    @Autowired
    private SugestaoRepository sugestaoRepository;

    /**
     * Lista as sugestões com ordenação e filtro opcionais.
     * Se um título for fornecido, filtra por ele.
     * Caso contrário, retorna todas as sugestões.
     * A ordenação por 'dataAtualizacao DESC' é garantida em ambos os casos pelos métodos do repositório.
     *
     * @param titulo O filtro opcional para o título da sugestão. Pode ser nulo ou vazio.
     * @return A lista de sugestões filtrada e ordenada.
     */
    @Transactional(readOnly = true)
    public List<Sugestao> listarTodas(String titulo) {
        if (StringUtils.hasText(titulo)) {
            return sugestaoRepository.findByTituloContainingIgnoreCaseOrderByDataAtualizacaoDesc(titulo);
        } else {
            return sugestaoRepository.findAllByOrderByDataAtualizacaoDesc();
        }
    }

    @Transactional(readOnly = true)
    public Sugestao buscarPorId(Long id) {
        return sugestaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sugestão não encontrada com o id: " + id));
    }

    @Transactional
    public Sugestao criar(SugestaoRequestDTO dto) {
        Sugestao novaSugestao = new Sugestao();
        novaSugestao.setTitulo(dto.getTitulo());
        novaSugestao.setDescricao(dto.getDescricao());
        return sugestaoRepository.save(novaSugestao);
    }

    @Transactional
    public Sugestao atualizar(Long id, SugestaoRequestDTO sugestaoDetalhes) {
        Sugestao sugestao = buscarPorId(id);
        sugestao.setTitulo(sugestaoDetalhes.getTitulo());
        sugestao.setDescricao(sugestaoDetalhes.getDescricao());
        return sugestaoRepository.save(sugestao);
    }

    @Transactional
    public void deletar(Long id) {
        Sugestao sugestao = buscarPorId(id);
        sugestaoRepository.delete(sugestao);
    }
}