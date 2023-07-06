package commands;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

import view.MyFrame;

public class UndoCommand implements ActionListener{

	private MyFrame frame;
	private JTextArea textArea;
	private UndoManager um;
	
	public UndoCommand(MyFrame frame, JTextArea textArea, UndoManager um) {
		this.frame = frame;
		this.textArea = textArea;
		this.um = um;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		frame.getInvoker().addCommand("undo", this, textArea, um);
		frame.getInvoker().issueCommand("undo");
	}


	public void execute(JTextArea textArea, UndoManager um) {
		um.undo();
		frame.setTextArea(textArea);
	}
	
	
}
