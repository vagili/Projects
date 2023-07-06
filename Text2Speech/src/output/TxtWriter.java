package output;

import java.io.FileWriter;
import java.io.IOException;

import view.MyFrame;

public class TxtWriter implements DocumentWriter{
	
	MyFrame frame;
	String fileName;
	
	public TxtWriter(MyFrame frame, String fileName) {
		this.frame = frame;
		this.fileName = fileName;
	}
	
	
	public void save(String s) {
		
		
		
		try {
			
			  //TextArea textArea = new TextArea(300, 300);
		      FileWriter out=new FileWriter(fileName);
		      out.write(s);
		      out.close();
		  	}
		    catch (IOException x) {
		    	System.out.println("Error saving file");
		    }
		
		
		
	}
	
	public void save() {
		
		System.out.println("Mphka kai stin save");
		
		try {
			
			  //TextArea textArea = new TextArea(300, 300);
		      FileWriter out=new FileWriter(fileName);
		      out.write(frame.getTextArea().getText());
		      out.close();
		  	}
		    catch (IOException x) {
		    	System.out.println("Error saving file");
		    }
		
		
		
	}
	
}

