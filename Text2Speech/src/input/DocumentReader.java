package input;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTextArea;

public interface DocumentReader {
	
	public static final JTextArea textArea = new JTextArea();
	
	public ArrayList<String> read() throws FileNotFoundException, IOException;
}