package model;
import java.util.*;
import java.io.*;

public class Document {

	private ArrayList <String> contents;
	private File file;
	private String fileName;
	
	
	public Document() {
	
	}
	
	public Document (String path) {
		
		contents = new ArrayList<String>();
		file = new File(path);
		this.fileName = path;
		
		if(this.file.exists() == false) {
			System.out.println("This file not existed");
		}
		
		Scanner inputf = null;
		
		try {
			inputf = new Scanner(this.file);
		}
		catch (FileNotFoundException e){
			e.printStackTrace();
		}
		while(inputf.hasNextLine()) {
			String line = inputf.nextLine();
			contents.add(line);
		}
		
	}
	
	public void playLine(int x) { 

		if (contents.get(x).equals(null) ) {
			System.out.println("This line does not exist.");
		}else {
			String line = contents.get(x);
		}
		
	}

	public void playContents() {
		
		for(int i=0; i<contents.size(); i++) {
			String line = contents.get(i);
		}
		
	}
	
	public String getContentsLine(int i) {
	
		String line = contents.get(i);
		return line + "\n";
		
	}
	public ArrayList<String> getContents(){
		return contents;
	}
	

	public void addContents(String nextLine) {
		contents.add(nextLine);
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
