package br.com.reciclaville.sugestaoanonima.service;

import br.com.reciclaville.sugestaoanonima.model.Comentario;
import br.com.reciclaville.sugestaoanonima.model.Sugestao;
import br.com.reciclaville.sugestaoanonima.repository.ComentarioRepository;
import br.com.reciclaville.sugestaoanonima.repository.SugestaoRepository; // Import necessário
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime; // Import necessário
import java.util.List;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private SugestaoService sugestaoService;

    // Injetamos o SugestaoRepository para poder salvar a sugestão atualizada.
    @Autowired
    private SugestaoRepository sugestaoRepository;

    @Transactional(readOnly = true)
    public List<Comentario> listarComentariosPorSugestao(Long sugestaoId) {
        sugestaoService.buscarPorId(sugestaoId);
        return comentarioRepository.findBySugestaoId(sugestaoId);
    }

    /**
     * Adiciona um novo comentário a uma sugestão existente e atualiza a
     * data de atualização da sugestão pai.
     * A anotação @Transactional garante que ambas as operações sejam executadas
     * em uma única transação atômica.
     *
     * @param sugestaoId O ID da sugestão que receberá o comentário.
     * @param comentarioInput O objeto Comentario contendo o texto a ser adicionado.
     * @return O Comentario que foi criado e salvo no banco.
     */
    @Transactional
    public Comentario adicionarComentario(Long sugestaoId, Comentario comentarioInput) {
        // 1. Busca a sugestão pai. Se não existir, o método buscarPorId lançará uma exceção.
        Sugestao sugestao = sugestaoService.buscarPorId(sugestaoId);

        // 2. Associa o comentário à sugestão pai.
        comentarioInput.setSugestao(sugestao);

        // 3. Salva o novo comentário. O @PrePersist cuidará da dataEnvio do comentário.
        Comentario novoComentario = comentarioRepository.save(comentarioInput);

        // 4. ATUALIZA A DATA DA SUGESTÃO PAI.
        // Isso fará com que a sugestão suba para o topo nas listagens.
        // Não é necessário chamar o @PreUpdate manualmente, o JPA detecta a alteração.
        sugestao.setDataAtualizacao(LocalDateTime.now());
        sugestaoRepository.save(sugestao); // Salva a sugestão com a data atualizada

        // 5. Retorna o comentário recém-criado.
        return novoComentario;
    }
}