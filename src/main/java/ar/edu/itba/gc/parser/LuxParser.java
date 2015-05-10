package ar.edu.itba.gc.parser;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class LuxParser {

	private Path filePath;

	public LuxParser(String fileName) {
		filePath = Paths.get(fileName);
	}

	public final void processLines() throws IOException {
	    try (Scanner scanner =  new Scanner(filePath, "UTF-8")){
	      while (scanner.hasNextLine()){
	        processLine(scanner.nextLine());
	      }      
	    }
	}
	
	protected void processLine(String aLine){
		//TODO parse line
	}
	
	
}
