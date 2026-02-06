package br.com.alura.challenge_literalura.view;

import br.com.alura.challenge_literalura.model.Autor;
import br.com.alura.challenge_literalura.model.DadosLivro;
import br.com.alura.challenge_literalura.model.Livro;
import br.com.alura.challenge_literalura.service.PersistenciaDB;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Menu {

    private Scanner scanner = new Scanner(System.in);
    private String json;
    private final PersistenciaDB persistenciaDB;

    private String menu = """
            ************************************************
            Bem vindo ao LiterAlura!
            Escolha uma opção:
            
            1- Buscar livro pelo título;
            2- Listar livros registrados;
            3- Listar autores registrados;
            4- Listar autores vivos em um determinado ano;
            5- Listar livros em um determinado idioma.
            0- Sair.
            ************************************************
            """;

    public Menu(PersistenciaDB persistenciaDB){
        this.persistenciaDB = persistenciaDB;

    }
    private void exibeMenu(){
        System.out.println(menu);
    }

    public void run() {
        int opcao = -1;

        while (opcao != 0) {
            exibeMenu();
             opcao = Integer.parseInt(scanner.nextLine());
            switch (opcao) {
                case 1:
                    buscarLivroPeloTitulo();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLivrosDeterminadoIdioma();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção Inválida!");
                    break;
            }
        }
    }

    private void listarLivrosDeterminadoIdioma() {
        System.out.println("""
                Insira o idioma para realizar a busca:
                es- Espanhol
                en- Inglês
                fr- Fracês
                pt- Português
                """);
        String idioma = scanner.nextLine();
        var livroIdioma = persistenciaDB.encontrarLivroPorIdioma(idioma);
        if(livroIdioma.isEmpty()){
            System.out.println("Nenhum livro encontrado com esse idioma.");
        }else{
            livroIdioma.forEach(System.out::println);
        }

    }

    private void listarAutoresVivos() {
        System.out.println("Insira o ano que deseja pesquisar:");
        int ano = scanner.nextInt();
        scanner.nextLine();
        var autoresVivosNoAno = persistenciaDB.listarAutoresVivosNoAno(ano);
        if(autoresVivosNoAno.isEmpty()){
            System.out.println("Nenhum autor vivo encontrado no ano pesquisado.");
        } else{
           autoresVivosNoAno.forEach(System.out::println);
        }


    }

    private void listarAutoresRegistrados() {
        System.out.println("Autores Registrados:");
        var autores = persistenciaDB.listarAutoresCadastrados();
        if(autores.isEmpty()){
            System.out.println("Nenhum autor encontrado!");
        } else{
            autores.forEach(System.out::println);
        }

    }

    private void listarLivrosRegistrados() {
        System.out.println("Livros Registrados:");
        var livros = persistenciaDB.listarLivrosCadastrados();
        if(livros.isEmpty()){
            System.out.println("Nenhum livro encontrado!");
        } else{
            livros.forEach(System.out::println);
        }
    }

    private void buscarLivroPeloTitulo() {
        System.out.println("Faça sua busca.");
        String titulo = scanner.nextLine();
        Livro livroBuscado = persistenciaDB.buscarESalvarPorTitulo(titulo);
        if
        (livroBuscado == null) {
            System.out.println("Livro não encontrado na API.");
        } else {
            System.out.println("Livro salvo/recuperado:");
            System.out.println(livroBuscado);
        }
    }

   private DadosLivro pegueInformacoesLivro(String titulo) {
   return null;
    }

}
