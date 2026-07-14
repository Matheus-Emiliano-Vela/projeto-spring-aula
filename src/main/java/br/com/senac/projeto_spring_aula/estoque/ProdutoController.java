package br.com.senac.projeto_spring_aula.estoque;

import br.com.senac.projeto_spring_aula.estoque.model.ProdutoEntity;
import br.com.senac.projeto_spring_aula.estoque.model.ProdutoPostDto;
import br.com.senac.projeto_spring_aula.estoque.model.ProdutoStatus;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoRepository produtoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<ProdutoEntity> createProduto(@Valid @RequestBody ProdutoPostDto dto) {
        ProdutoEntity produtoEntity = new ProdutoEntity();

        produtoEntity.setNome(dto.nome());
        produtoEntity.setPreco(dto.preco());
        produtoEntity.setQuantidadeEstoque(dto.quantidadeEstoque());
        produtoEntity.setStatus(ProdutoStatus.DISPONIVEL);

        ProdutoEntity salvo = produtoRepository.save(produtoEntity);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(salvo);
    }

    @GetMapping
    @Transactional
    public ResponseEntity<List<ProdutoEntity>> getAll() {
        List<ProdutoEntity> produtos = produtoRepository.findAll();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(produtos);
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<ProdutoEntity> getById(@PathVariable Long id) {
        Optional<ProdutoEntity> optionalProduto = produtoRepository.findById(id);

        if (optionalProduto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(optionalProduto.get());
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        produtoRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}/reabastecer")
    @Transactional
    public ResponseEntity<ProdutoEntity> reabastecer(@PathVariable Long id, @RequestParam Integer quantidade) {
        Optional<ProdutoEntity> optionalProduto = produtoRepository.findById(id);

        if (optionalProduto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        ProdutoEntity produtoEntity = optionalProduto.get();

        int novaQuantidade = produtoEntity.getQuantidadeEstoque() + quantidade;
        produtoEntity.setQuantidadeEstoque(novaQuantidade);

        if (produtoEntity.getStatus().equals(ProdutoStatus.ESGOTADO) && novaQuantidade > 0) {
            produtoEntity.setStatus(ProdutoStatus.DISPONIVEL);
        }

        ProdutoEntity produtoAlterado = produtoRepository.save(produtoEntity);

        return ResponseEntity.status(HttpStatus.OK).body(produtoAlterado);
    }
}
