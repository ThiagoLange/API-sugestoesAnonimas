package br.com.reciclaville.sugestaoanonima.repository;

import br.com.reciclaville.sugestaoanonima.model.Sugestao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SugestaoRepository extends JpaRepository<Sugestao, Long> {
}