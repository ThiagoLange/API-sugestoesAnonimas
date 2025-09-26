package br.com.reciclaville.sugestaoanonima.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "comentarios")
public class Sugestao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(nullable = false, length = 2000)
    private String descricao;

    private LocalDateTime dataEnvio;
    private LocalDateTime dataAtualizacao;

    // A MÁGICA ACONTECE AQUI!
    /**
     * Adicionamos @OrderBy para que o JPA sempre ordene esta coleção
     * pela coluna 'dataEnvio' em ordem descendente (DESC) no nível do banco de dados.
     */
    @OneToMany(mappedBy = "sugestao", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("dataEnvio DESC") // <--- ADICIONE ESTA LINHA
    private List<Comentario> comentarios = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        dataEnvio = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }
}