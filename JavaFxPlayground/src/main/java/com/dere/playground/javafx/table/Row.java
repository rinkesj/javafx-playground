package com.dere.playground.javafx.table;

import java.time.LocalDate;

import com.dere.playground.javafx.table.TableApp.Status;

public class Row {

	int id;
	String string;
	LocalDate date;
	Status status;
	
	public Row() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	interface Functional {
        void funct(String fieldname);
    }
	
	public Object getColumnValue(String fieldName) {
		switch (fieldName) {
			case "id":
				return id;
			case "string":
				return string;
			case "date":
				return date;
			case "status":
				return status;
			default:
				return null;
		}
	}

}
