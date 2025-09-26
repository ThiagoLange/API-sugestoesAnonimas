package br.com.reciclaville.sugestaoanonima.repository;

import br.com.reciclaville.sugestaoanonima.model.Sugestao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SugestaoRepository extends JpaRepository<Sugestao, Long> {

    /**
     * Busca todas as sugestões e as ordena pela data de atualização em ordem decrescente.
     * O Spring Data JPA cria a query automaticamente a partir do nome do método.
     * "FindAll" -> SELECT * FROM sugestao
     * "OrderByDataAtualizacaoDesc" -> ORDER BY data_atualizacao DESC
     *
     * @return Uma lista de todas as sugestões ordenadas.
     */
    List<Sugestao> findAllByOrderByDataAtualizacaoDesc();

    /**
     * Busca sugestões cujo título contenha o texto fornecido (ignorando maiúsculas/minúsculas)
     * e ordena o resultado pela data de atualização em ordem decrescente.
     * "FindBy" -> Inicia a cláusula WHERE
     * "TituloContaining" -> WHERE lower(titulo) LIKE lower('%?%')
     * "IgnoreCase" -> Converte ambos os lados da comparação para minúsculas
     * "OrderByDataAtualizacaoDesc" -> ORDER BY data_atualizacao DESC
     *
     * @param titulo O texto a ser procurado no título da sugestão.
     * @return Uma lista de sugestões que correspondem ao filtro, ordenadas.
     */
    List<Sugestao> findByTituloContainingIgnoreCaseOrderByDataAtualizacaoDesc(String titulo);
}