package br.com.reciclaville.sugestaoanonima.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sugestao_id", nullable = false)
    @JsonIgnore
    private Sugestao sugestao;

    @Column(nullable = false, length = 1000)
    private String texto;

    private LocalDateTime dataEnvio;

    @PrePersist
    protected void onCreate() {
        dataEnvio = LocalDateTime.now();
    }
}