package br.com.alura.challenge_literalura;

import br.com.alura.challenge_literalura.view.Menu;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengeLiteraluraApplication implements CommandLineRunner {
    private final Menu menu;

    public ChallengeLiteraluraApplication(Menu menu) {
        this.menu = menu;
    }
	public static void main(String[] args) {
		SpringApplication.run(ChallengeLiteraluraApplication.class, args);
	}


    @Override
    public void run(String... args) {
        menu.run();
    }
}
