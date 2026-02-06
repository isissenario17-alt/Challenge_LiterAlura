package br.com.alura.challenge_literalura.service;

import br.com.alura.challenge_literalura.model.Autor;
import br.com.alura.challenge_literalura.model.DadosLivro;
import br.com.alura.challenge_literalura.model.Livro;
import br.com.alura.challenge_literalura.model.Results;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.Parameter;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersistenciaDB {

    private final ConsumoAPI consumoAPI;
    private final ConverteDados converteDados;
    @PersistenceContext
    private EntityManager em;
    private static final String URL_BASE = "https://gutendex.com/books/";

    public PersistenciaDB(ConsumoAPI consumoAPI, ConverteDados converteDados) {
        this.consumoAPI = consumoAPI;
        this.converteDados = converteDados;

    }

    @Transactional
    public Livro buscarESalvarPorTitulo(String tituloBuscado) {

        String json = consumoAPI.obterDados(
                URL_BASE + "?search=" + tituloBuscado.replace(" ", "+"),
                DadosLivro.class
        );

        Results results = converteDados.obterDados(json, Results.class);

        Optional<DadosLivro> dadosOptional =
                results.listaDadosLivro().stream().findFirst();

        if (dadosOptional.isEmpty()) {
            return null;
        }

        DadosLivro dados = dadosOptional.get();

        Optional<Livro> existente = em.createQuery(
                        "SELECT l FROM Livro l WHERE LOWER(l.titulo) = LOWER(:titulo)",
                        Livro.class
                ).setParameter("titulo", dados.titulo())
                .getResultList()
                .stream()
                .findFirst();

        if (existente.isPresent()) {
            return existente.get();
        }

        Autor autor = em.createQuery(
                        "SELECT a FROM Autor a WHERE LOWER(a.nome) = LOWER(:nome)",
                        Autor.class
                ).setParameter("nome", dados.autor().get(0).nome())
                .getResultList()
                .stream()
                .findFirst()
                .orElseGet(() -> {
                    Autor novo = new Autor();
                    novo.setNome(dados.autor().get(0).nome());
                    novo.setAnoNascimento(dados.autor().get(0).anoNascimento());
                    novo.setAnoFalecimento(dados.autor().get(0).anoFalecimento());
                    em.persist(novo);
                    return novo;
                });

        Livro livro = new Livro();
        livro.setTitulo(dados.titulo());
        livro.setIdioma(dados.idioma().get(0));
        livro.setNumeroDownload(dados.numeroDownload());
        livro.setAutor(autor);

        em.persist(livro);

        return livro;
    }

    public List<Livro> listarLivrosCadastrados(){
        return em.createQuery("SELECT l FROM Livro l ", Livro.class).getResultList();
    }

    public List<Autor> listarAutoresCadastrados(){
        return em.createQuery("SELECT a FROM Autor a", Autor.class).getResultList();
    }

    public List<Autor> listarAutoresVivosNoAno(int ano){
        return em.createQuery("""
                SELECT a FROM Autor a WHERE a.anoNascimento <= :ano 
                and (a.anoFalecimento is null or a.anoFalecimento >= :ano)
                """, Autor.class).getResultList();
    }

    public List<Livro> encontrarLivroPorIdioma(String idioma){
        return em.createQuery("SELECT l FROM Livro JOIN l.idiomas i WHERE LOWER(i) = LOWER(idioma)", Livro.class).getResultList();
    }


}
