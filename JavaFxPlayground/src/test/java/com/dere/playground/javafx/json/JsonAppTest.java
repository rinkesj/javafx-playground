package com.dere.playground.javafx.json;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;

public class JsonAppTest {
	
//	@Test
	public void runWithData() {

		String file1 = getClass().getClassLoader().getResource("test.json").getPath();

		String[] args = new String[] { file1 };
		JSONApp.main(args);
	}
	
	@Test
	public void testJson() throws IOException {
		
		String file1 = getClass().getClassLoader().getResource("test.json").getPath();
		List<String> readAllLines = Files.readAllLines(new File(file1).toPath());
		
		for (String string : readAllLines) {
			Configuration conf = Configuration.builder()
					   .options(Option.AS_PATH_LIST).build();
			
			DocumentContext documentContext = JsonPath.using(conf).parse(string);
			
			Object path = documentContext.read("@..associatedDrug..strength");
			System.out.println("path : " + path.toString());
		}
	}
}
