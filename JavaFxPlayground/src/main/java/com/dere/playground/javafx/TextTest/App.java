package com.dere.playground.javafx.TextTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

	private static File inputFile;

	@Override
	public void start(Stage stage) throws IOException {
		var javaVersion = SystemInfo.javaVersion();
		var javafxVersion = SystemInfo.javafxVersion();

		var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
		StackPane stackPane = new StackPane(label);
		
		ScrollPane scroll = new ScrollPane();
		FlowPane pane = new FlowPane();
		
		List<String> readAllLines = Files.readAllLines(inputFile.toPath());
		for (String string : readAllLines) {
			pane.getChildren().addAll(new Label(string + System.lineSeparator()));
		}
		scroll.setContent(pane);
		stackPane.getChildren().add(scroll);
		var scene = new Scene(stackPane, 640, 480);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {

		for (String inputFilePath : args) {
			inputFile = new File(inputFilePath);
		}

		launch();
	}

}