package commands;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;
import javax.swing.undo.UndoManager;

import view.MyFrame;

public class RedoCommand implements ActionListener{
	
	private MyFrame frame;
	private JTextArea textArea;
	private UndoManager um;
	
	public RedoCommand(MyFrame frame, JTextArea textArea, UndoManager um) {
		this.frame = frame;
		this.textArea = textArea;
		this.um = um;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		frame.getInvoker().addCommand("redo", this, textArea, um);
		frame.getInvoker().issueCommand("redo");
	}

	public void execute(JTextArea textArea, UndoManager um) {
		um.redo();
		frame.setTextArea(textArea);
	}
	
}
