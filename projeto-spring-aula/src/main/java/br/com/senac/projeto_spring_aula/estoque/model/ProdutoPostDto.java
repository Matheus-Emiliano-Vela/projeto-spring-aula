package br.com.senac.projeto_spring_aula.estoque.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ProdutoPostDto(
        @NotBlank(message = "O campo nome é obrigatório")
        String nome,

        @NotNull(message = "O campo preço é obrigatório")
        @Positive(message = "O preço deve ser maior que zero")
        BigDecimal preco,

        @NotNull(message = "O campo quantidade em estoque é obrigatório")
        @PositiveOrZero(message = "A quantidade em estoque não pode ser negativa")
        Integer quantidadeEstoque
) {
}
