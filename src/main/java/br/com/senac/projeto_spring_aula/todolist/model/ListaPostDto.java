package br.com.senac.projeto_spring_aula.todolist.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record ListaPostDto(
        @NotBlank(message = "O campo descrição é obrigatório")
        String descricao
) {
}
