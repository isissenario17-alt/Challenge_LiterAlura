package br.com.alura.challenge_literalura.view;

import br.com.alura.challenge_literalura.model.DadosLivro;
import br.com.alura.challenge_literalura.model.Livro;
import br.com.alura.challenge_literalura.model.Results;
import br.com.alura.challenge_literalura.service.ConsumoAPI;
import br.com.alura.challenge_literalura.service.ConverteDados;
import br.com.alura.challenge_literalura.service.PersistenciaDB;
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

    }

    private void listarAutoresVivos() {

    }

    private void listarAutoresRegistrados() {

    }

    private void listarLivrosRegistrados() {
        System.out.println("Livros Registrados:");



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
//        Results dados =  conversor.obterDados(json, Results.class);
//         return  dados.listaDadosLivro().stream()
//                .filter(livro -> livro.titulo().toUpperCase().contains(titulo.toUpperCase()))
//                .findFirst().orElse(null);
//
return null;
    }

}
