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
@ToString(exclude = "comentarios") // Evita recursão no toString
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

    @OneToMany(mappedBy = "sugestao", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
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