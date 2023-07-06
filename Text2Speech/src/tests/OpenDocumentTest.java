package tests;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import commands.OpenDocument;
import model.Document;
import view.MyFrame;

public class OpenDocumentTest {

	private static OpenDocument openDocument;
	private static MyFrame frame;
	
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception{
		
		frame  = new MyFrame();
		openDocument = new OpenDocument(frame);
		openDocument.execute();
		frame.setTextArea(openDocument.getTextArea());
		
	}
	@Test
	void testExecute() {
		
		String text = "this is a test with 1 line of contents";
		
		String documentText = "";
		
		for( String i : frame.getTextArea().getText().split("\n")) {
			documentText = documentText + i;
			//System.out.println(i);
		}
		System.out.println(frame.getTextArea().getText());
		
		documentText = documentText.stripTrailing();
		assertEquals(text, documentText);
	}
	
}

