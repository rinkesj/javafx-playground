package com.dere.playground.javafx.table;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Orientation;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;

public class FilteredTableView<S> extends TableView<S> {

    private FilteredList<? extends S> filteredCollection;

    FilteredTableView(FilteredList<S> items) {
        super(items);
        filteredCollection = items;
    }

    FilteredTableView(SortedList<S> items) {
        super(items);
        
        ObservableList<? extends S> source = items.getSource();
        if(source instanceof FilteredList) {
            filteredCollection = (FilteredList<? extends S>) source;
        } else {
            throw new IllegalArgumentException("Argument Items is not {$link FilteredList} wrapped in {$link SortedList}.");
        }
    }

    public void showFilterModal(List<? extends TableColumn<S, ?>> tableColumns) {

        FilteredList<? extends S> filtered = getFilteredCollection();

        // only filtered columns will be processed for predicate
        List<FilteredTableColumn<S, ?>> filteredColumns = tableColumns.stream().filter(c -> c instanceof FilteredTableColumn).map(c -> (FilteredTableColumn<S, ?>)c).collect(Collectors.toList());

		Dialog<ButtonType> dialog = new Dialog<ButtonType>();
		Map<String, TextField> map = new HashMap<String, TextField>();
		FlowPane panel = new FlowPane();
		panel.setOrientation(Orientation.VERTICAL);
		ToggleGroup group = new ToggleGroup();
		if (tableColumns.size() > 1 || filtered.getPredicate() != null) {
			RadioButton button1 = new RadioButton("AND");
			button1.setToggleGroup(group);
			button1.setSelected(true);
			RadioButton button2 = new RadioButton("OR");
			button2.setToggleGroup(group);
			panel.getChildren().add(new Label("Select filter combination logic"));
			panel.getChildren().add(button1);
			panel.getChildren().add(button2);
		}
		for (FilteredTableColumn<S, ?> tableColumn : filteredColumns) {
			TextField textField = new TextField();
			panel.getChildren().add(new Label(tableColumn.getText()));
			panel.getChildren().add(textField);
			map.put(tableColumn.getText(), textField);
		}

		dialog.getDialogPane().setContent(panel);
		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
		dialog.getDialogPane().setPrefWidth(350);
		dialog.setResizable(true);
		dialog.setOnShowing(ed -> {
			// hack for issue : https://github.com/javafxports/openjdk-jfx/issues/222
			Platform.runLater(() -> {
				dialog.getDialogPane().getScene().getWindow().sizeToScene();
				dialog.setResizable(false);
			});
		});
		Optional<ButtonType> result = dialog.showAndWait();
		result.ifPresent(response -> {
			if (response == ButtonType.OK) {
				Predicate<S> predicate = (Predicate<S>) filtered.getPredicate();

				for (FilteredTableColumn<S, ?> tableColumn : filteredColumns) {
					String text = map.get(tableColumn.getText()).getText();
					if (text != null && !text.isEmpty() && !text.isBlank()) {
                        
                        // TODO String vs Objects like enum or Integer - this doesnt work properly
                        tableColumn.setFilterValue(text); 
                        //we need binding between modal nad filterValue with exact value conversion to T type.
						
                        Predicate<S> columnPredicate = (Predicate<S>) tableColumn.getColumnPredicate();

						if (predicate == null) {
							predicate = columnPredicate;
							System.out.println("SetPredicate");
						} else {
							if (tableColumns.size() > 1 || predicate != null) {
								RadioButton selectedToggle = (RadioButton) group.getSelectedToggle();
								System.out.println("Compine Predicate " + selectedToggle.getText());
								if (selectedToggle.getText().equalsIgnoreCase("AND")) {
									predicate = predicate.and(columnPredicate);
								} else {
									predicate = predicate.or(columnPredicate);
								}
							} else {
								predicate = predicate.and(columnPredicate);
							}
						}
					}

				}

				filtered.setPredicate(predicate);
			} else {
				filtered.setPredicate(null);
			}
		});

	}

    private FilteredList<? extends S> getFilteredCollection() {
        return filteredCollection;
    }
}
