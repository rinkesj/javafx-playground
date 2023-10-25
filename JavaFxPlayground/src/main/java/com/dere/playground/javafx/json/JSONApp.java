package com.dere.playground.javafx.json;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * JavaFX App
 */
public class JSONApp extends Application {

	private static File inputFile;

	private List<TableColumn<String, String>> tablecolumns;

	@Override
	public void start(Stage stage) throws IOException {

		tablecolumns = createColumns();
//		Button button = new Button("Filter");
//		button.setOnAction(e -> {
//			showFilterModal(tablecolumns);
//		});

		ObservableList<String> createRows = createRows();
		TableView<String> tableView = new TableView<String>(createRows);


		tableView.getColumns().addAll(tablecolumns);
		tableView.setMinWidth(900);
		StackPane stackPane = new StackPane(tableView);

		VBox vbox = new VBox();
		FlowPane root = new FlowPane();
//		root.getChildren().add(button);
		root.getChildren().add(tableView);
		vbox.getChildren().add(root);
		vbox.setVgrow(root, Priority.ALWAYS);

		var scene = new Scene(vbox, 1000, 480);
		stage.setScene(scene);
		stage.show();
	}

	private List<TableColumn<String, String>> createColumns() {
		List<TableColumn<String, String>> viewColumns = new ArrayList<TableColumn<String,String>>();
			
		TableColumn<String, String> tableColumn = new TableColumn<String, String>("content");
			tableColumn.setMinWidth(550);
			tableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String,String>, ObservableValue<String>>() {
				
				@Override
				public ObservableValue<String> call(CellDataFeatures<String, String> param) {
					return new ReadOnlyStringWrapper(param.getValue());
				}
			});
			
			viewColumns.add(tableColumn);

//			Button button = new Button("F");
//			button.setFocusTraversable(false);
//			button.setOnAction(e -> {
//				showFilterModal(Arrays.asList(tableColumn));
//
//			});
//			tableColumn.setGraphic(button);
			
			TableColumn<String, String> tableColumn2 = new TableColumn<String, String>("content");
			tableColumn2.setMinWidth(550);
			tableColumn2.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String,String>, ObservableValue<String>>() {
				
				@Override
				public ObservableValue<String> call(CellDataFeatures<String, String> param) {
					
					
					
					return new ReadOnlyStringWrapper(param.getValue());
				}
			});
			
			viewColumns.add(tableColumn2);


		return viewColumns;
	}

	private ObservableList<String> createRows() {
		ObservableList<String> list = FXCollections.observableArrayList();
		
		List<String> readAllLines;
		try {
			readAllLines = Files.readAllLines(inputFile.toPath());
			list.addAll(readAllLines);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(list.size());
		
		return list;
	}

	public static void main(String[] args) {

		for (String inputFilePath : args) {
			inputFile = new File(inputFilePath);
		}

		launch();
	}

}