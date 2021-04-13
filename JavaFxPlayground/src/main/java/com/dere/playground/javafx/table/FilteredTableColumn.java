package com.dere.playground.javafx.table;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import javafx.scene.control.TableColumn;

/**
 * Extended TableColumn for filtering.
 * Each column hold own filter value as inner state and can obtain predicate for filtering.
 *
 * @param <S> The type of the TableView generic type (i.e. S == TableView&lt;S&gt;)
 * @param <T> The type of the content in all cells in this TableColumn.
 */
public class FilteredTableColumn<S, T extends Object> extends TableColumn<S, T>{
	
	Class<T> valueType;
	T filterValue;
	
	BiFunction<S,String, T> bifunction = null;
	private Function<S, T> function;
	
	public FilteredTableColumn(String text, BiFunction<S,String,T> bifunction) {
		this(text, text, bifunction);
	}
	
	public FilteredTableColumn(String text, Function<S,T> function) {
		this(text, text, function);
	}

	public FilteredTableColumn(String id, String text, BiFunction<S,String,T> bifunction) {
		super(text);
		setId(id);
		this.bifunction = bifunction;
	}
	
	public FilteredTableColumn(String id, String text, Function<S,T> function) {
		super(text);
		setId(id);
		this.function = function;
	}
	
	/**
	 * Basic equals predicate for value selected from current row by reflection.
	 * 
	 * @return predicate for test
	 */
	public Predicate<S> getColumnPredicate() {
		return p -> getColumnValue(p, getId()).equals(filterValue);
	}
	
	public T getColumnValue(S p, String id) {
		System.out.println("getColumnValue " + id);
		return function != null ? function.apply(p) : bifunction.apply(p,id);
	}


	public Class<T> getValueType() {
		return valueType;
	}
	
	public void setValueType(Class<T> valueType) {
		this.valueType = valueType;
	}
	
	public T getFilterValue() {
		return filterValue;
	}
	
	@SuppressWarnings("unchecked")
	public void setFilterValue(Object filterValue) {
		this.filterValue = (T) filterValue;
	}
}
