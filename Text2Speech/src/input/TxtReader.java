package input;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JTextArea;

import commands.OpenDocument;
import model.Document;

public class TxtReader implements DocumentReader{

	private String fileName;
	private OpenDocument open;
	private JTextArea textArea;
	private ArrayList<String> contents;
	private File myFile;
	private Document doc;
	private Scanner myScanner;
	
	public TxtReader(OpenDocument open,String fileName) {
		this.fileName = fileName;
		this.open = open;
		textArea = new JTextArea();
		contents = new ArrayList<String>();
	}
	
	
	public ArrayList<String> read() {
		int i=0;
		myFile = new File(fileName);
		doc = new Document(fileName);
		try {
			myScanner = new Scanner(myFile);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		while(myScanner.hasNextLine()) {
			String nxtLine = myScanner.nextLine();
			System.out.println(nxtLine);
			doc.addContents(nxtLine);
			contents.add(nxtLine);
			textArea.append(doc.getContentsLine(i));
			i++;
		}
		
		System.out.println(i);
		open.getFrame().setDoc(doc);
		return contents;

	}
	
	public JTextArea getTextArea() {
		return textArea;
	}
}
