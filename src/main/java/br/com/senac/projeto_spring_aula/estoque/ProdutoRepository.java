package br.com.senac.projeto_spring_aula.estoque;

import br.com.senac.projeto_spring_aula.estoque.model.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {
}
