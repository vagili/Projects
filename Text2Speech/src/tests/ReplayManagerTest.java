package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import view.MyFrame;
import commands.CopyCommand;
import commands.OpenDocument;
import commands.PasteCommand;
import commands.ReplayManager;
import commands.SelectAllCommand;

import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JTextArea;

class ReplayManagerTest {
	
	private static ReplayManager manager;
	private static MyFrame frame;
	private static HashMap<String,ActionListener> map ;

	
	@BeforeAll
	static void setUpBeforeClass() {
		
		
		frame = new MyFrame();
		map = new HashMap<String,ActionListener>();
		OpenDocument open = new OpenDocument(frame);
		open.execute();
		frame.setTextArea(open.getTextArea());
		SelectAllCommand selectAll =  new SelectAllCommand(frame.getTextArea(), frame);
		selectAll.execute(frame.getTextArea());
		CopyCommand copy = new CopyCommand(frame.getTextArea(),frame);
		copy.execute(frame.getTextArea());
		PasteCommand paste = new PasteCommand(frame.getTextArea(),frame);
		paste.execute(frame.getTextArea());
		
		map.put("openDocument", open);
		map.put("selectAll", selectAll);
		map.put("copy", copy);
		map.put("paste", paste);
		
		manager = new ReplayManager(frame);
		manager.setRecordings(map);
		manager.replay();
	}
	
	@Test
	void testReplay() {
		
		String x = "this is a test with 1 line of contents this is a test with 1 line of contents	";
		assertEquals(x,frame.getTextArea().getText());
			
	}

}
