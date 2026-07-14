package br.com.senac.projeto_spring_aula.livraria;

import br.com.senac.projeto_spring_aula.livraria.model.LivroEntity;
import br.com.senac.projeto_spring_aula.livraria.model.LivroPostDto;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController {

    private final LivroRepository livroRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<LivroEntity> createLivro(@Valid @RequestBody LivroPostDto dto) {
        LivroEntity livroEntity = new LivroEntity();

        livroEntity.setTitulo(dto.titulo());
        livroEntity.setAutor(dto.autor());
        livroEntity.setAnoPublicacao(dto.anoPublicacao());

        LivroEntity salvo = livroRepository.save(livroEntity);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(salvo);
    }

    @GetMapping
    @Transactional
    public ResponseEntity<List<LivroEntity>> getAll() {
        List<LivroEntity> livros = livroRepository.findAll();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(livros);
    }
}
