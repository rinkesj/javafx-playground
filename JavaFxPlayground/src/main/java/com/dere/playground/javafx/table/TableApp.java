package com.dere.playground.javafx.table;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class TableApp extends Application {

	private static File inputFile;

	List<Column> columns;

	private FilteredList<Row> filtered;

	private List<TableColumn<Row, Object>> tablecolumns;

	@Override
	public void start(Stage stage) throws IOException {

		
		ObservableList<Row> createRows = createRows();
		filtered = new FilteredList<Row>(createRows);
		SortedList<Row> sortableList = new SortedList<Row>(filtered);
		FilteredTableView<Row> tableView = new FilteredTableView<Row>(sortableList);
		sortableList.comparatorProperty().bind(tableView.comparatorProperty());
		tablecolumns = createColumns(tableView);
		
		Button button = new Button("Filter");
		button.setOnAction(e -> {
			tableView.showFilterModal(tablecolumns);
		});
		tableView.getColumns().addAll(tablecolumns);
		tableView.setMinWidth(550);
		StackPane stackPane = new StackPane(tableView);

		VBox vbox = new VBox();
		FlowPane root = new FlowPane();
		root.getChildren().add(button);
		root.getChildren().add(tableView);
		vbox.getChildren().add(root);
		vbox.setVgrow(root, Priority.ALWAYS);

		var scene = new Scene(vbox, 640, 480);
		stage.setScene(scene);
		stage.show();
	}

	private List<TableColumn<Row, Object>> createColumns(FilteredTableView<Row> table) {
		columns = Stream.of(new Column("id", Integer.class), new Column("string", String.class), new Column("date", LocalDate.class), new Column("status", Status.class))
				.collect(Collectors.toList());
		List<TableColumn<Row, Object>> viewColumns = columns.stream()
				.map(s -> new FilteredTableColumn<Row, Object>(s.name, s.name, Row::getColumnValue)).collect(Collectors.toList());

		for (TableColumn<Row, Object> tableColumn : viewColumns) {
			tableColumn.setMinWidth(100);
			tableColumn.setCellValueFactory(new PropertyValueFactory<>(tableColumn.getText()));

			Button button = new Button("F");
			button.setFocusTraversable(false);
			button.setOnAction(e -> {
				table.showFilterModal(Arrays.asList(tableColumn));

			});
			tableColumn.setGraphic(button);

		}

		return viewColumns;
	}

	private ObservableList<Row> createRows() {
		ObservableList<Row> list = FXCollections.observableArrayList();

		for (int i = 0; i < 20; i++) {
			Row e = new Row();
			e.id = i;
			e.string = "String " + i;
			e.date = LocalDate.now().plusDays(i);
			e.status = Status.values()[i % 3];
			list.add(e);
		}

		return list;
	}

	public static void main(String[] args) {

		for (String inputFilePath : args) {
			inputFile = new File(inputFilePath);
		}

		launch();
	}

	enum Status {
		TODO, INPROGRESS, DONE;
	}

	class Column {
		String name;
		Class<?> type;
		Object value;
		Predicate filter;

		public Column(String name, Class<?> type) {
			this.name = name;
			this.type = type;
		}
	}

}