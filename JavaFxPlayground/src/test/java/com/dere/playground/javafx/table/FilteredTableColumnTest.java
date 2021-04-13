package com.dere.playground.javafx.table;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.BiFunction;

import org.junit.jupiter.api.Test;

import com.dere.playground.javafx.table.TableApp.Status;

public class FilteredTableColumnTest {

	@Test
	public void testPredicate() throws NoSuchMethodException, SecurityException {

		Row row = new Row();
		row.status = Status.DONE;
		
		BiFunction<Row,String,Status> ref = Row::getColumnValue;
		
		FilteredTableColumn<Row, Status> column = new FilteredTableColumn<Row, Status>("status",Row::getColumnValue);
		FilteredTableColumn<Row, Status> column2 = new FilteredTableColumn<Row, Status>("status",Row::getStatus);
		column.filterValue = Status.DONE;
		System.out.println(ref.apply(row, "status"));
		assertTrue(column.getColumnPredicate().test(row));
	}

}
