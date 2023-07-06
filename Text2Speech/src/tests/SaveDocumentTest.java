package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JTextArea;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import commands.SaveDocument;
import view.MyFrame;

class SaveDocumentTest {

	private static SaveDocument save;
	private static MyFrame frame;
	private static JTextArea area;
	

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		frame = new MyFrame();
		area = new JTextArea();
		String text = "this is a test with 1 line of contents";
		area.append(text);
		frame.setTextArea(area);
		
		save = new SaveDocument(area, frame);
		save.execute("saveTest.txt");
		
		
	
	}
	
	@Test
	void testExecute() throws FileNotFoundException {
		
		String fileName = "saveTest.txt";
		String fileText = "";
		
		FileInputStream inputStream = new FileInputStream(fileName);
		Scanner input = new Scanner(inputStream);
		
		while(input.hasNextLine()) {
			String inputLine = input.nextLine();
			fileText = fileText + inputLine + "\n";
		}
		input.close();
		String text = "this is a test with 1 line of contents";
		fileText = fileText.stripTrailing();
		assertEquals(text, fileText);
		
	}

}

