package br.com.reciclaville.sugestaoanonima.repository;

import br.com.reciclaville.sugestaoanonima.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findBySugestaoId(Long sugestaoId);
}