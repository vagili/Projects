package tests;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JTextArea;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.TTSFacade;
import view.MyFrame;

class TTSFacadeTest {

	private static TTSFacade facade ;
	private static MyFrame frame;
	private static JTextArea area;
	
	@BeforeAll
	static void setUpBeforeClass() {
		
		frame = new MyFrame();
		area = new JTextArea();
		String text = "this is a test with 3 lines of contents";
		area.append(text);
		frame.setTextArea(area);
		
		facade = new TTSFacade(frame,frame.getTextArea());		
	}
	
	@Test
	void test() {
		
		String text = "this is a test with 3 lines of contents";
		facade.play(text);
		assertTrue(facade.getFlag2());
				
	}

}
