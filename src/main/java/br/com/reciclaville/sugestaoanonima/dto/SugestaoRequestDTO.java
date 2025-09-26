package br.com.reciclaville.sugestaoanonima.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO (Data Transfer Object) para receber os dados de criação de uma nova sugestão.
 * Ele desacopla o formato da requisição da entidade de persistência (Sugestao).
 * Inclui validações para garantir a integridade dos dados de entrada.
 */
@Getter
@Setter
public class SugestaoRequestDTO {

    @NotBlank(message = "O título não pode ser vazio ou nulo.")
    @Size(min = 5, max = 100, message = "O título deve ter entre 5 e 100 caracteres.")
    private String titulo;

    @NotBlank(message = "A descrição não pode ser vazia ou nula.")
    @Size(min = 10, max = 2000, message = "A descrição deve ter entre 10 e 2000 caracteres.")
    private String descricao;
}