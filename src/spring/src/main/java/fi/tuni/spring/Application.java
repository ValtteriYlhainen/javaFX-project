package fi.tuni.spring;

import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The Application program implements an application that
 * opens a JavaFX window, which can be used to generate simple html-files.
 *
 * @author Valtteri Peltonen
 * @version 1.0
 */

@SpringBootApplication
public class Application extends javafx.application.Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		System.out.println("Author: Valtteri Peltonen");
		launch(args);
	}

	@Override
	public void start(Stage window) {
		Ui windowUi = new Ui();
		windowUi.generateUi(window);
	}
}