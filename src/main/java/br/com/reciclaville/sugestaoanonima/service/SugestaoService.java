package br.com.reciclaville.sugestaoanonima.service;

import br.com.reciclaville.sugestaoanonima.dto.SugestaoRequestDTO;
import br.com.reciclaville.sugestaoanonima.model.Sugestao;
import br.com.reciclaville.sugestaoanonima.repository.SugestaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class SugestaoService {

    @Autowired
    private SugestaoRepository sugestaoRepository;

    /**
     * Cria uma nova sugestão no banco de dados a partir dos dados de um DTO.
     * @param dto O objeto de transferência de dados com o título e a descrição.
     * @return A entidade Sugestao que foi salva, agora com ID e timestamps.
     */
    @Transactional
    public Sugestao criar(SugestaoRequestDTO dto) {
        // 1. Cria uma nova instância da entidade de persistência.
        Sugestao novaSugestao = new Sugestao();

        // 2. Mapeia os dados do DTO para a entidade.
        novaSugestao.setTitulo(dto.getTitulo());
        novaSugestao.setDescricao(dto.getDescricao());

        // 3. Salva a nova entidade no banco de dados. Os timestamps serão gerados
        //    automaticamente pela anotação @PrePersist na entidade.
        return sugestaoRepository.save(novaSugestao);
    }

    // --- Outros métodos do serviço para completar a funcionalidade ---

    @Transactional(readOnly = true)
    public List<Sugestao> listarTodas() {
        return sugestaoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Sugestao buscarPorId(Long id) {
        return sugestaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sugestão não encontrada com o id: " + id));
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