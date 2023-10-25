package com.dere.playground.javafx.TextTest;

import org.junit.jupiter.api.Test;

public class AppTest {

	@Test
	public void runWithData() {

		String file1 = getClass().getClassLoader().getResource("delimiter_file1.csv").getPath();

		String[] args = new String[] { file1};
		System.setProperty("log4j.configurationFile", "log4j-test.xml");
		App.main(args);
	}

}
