package commands;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;

import view.MyFrame;

public class CopyCommand implements ActionListener {

	private StringSelection data;
	private JTextArea textArea;
	private MyFrame frame;
	private Clipboard clpbrd;
	
	public CopyCommand(JTextArea textArea , MyFrame frame) {
		this.textArea = textArea;
		this.frame = frame;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		frame.getInvoker().addCommand("copy", this, textArea);
		frame.getInvoker().issueCommand("copy");
	}

	public void execute(JTextArea text) {
		copy(text);
	}
	
	public void copy(JTextArea t) {
		if(t.getSelectedText() != null) {
			data = new StringSelection(t.getSelectedText().toString());
			clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
			clpbrd.setContents(data, data);
		}else {
			System.out.println("Copy not executed! You should highlight a portion of the text area before pressing 'Copy'");
		}

	}
	
	public Clipboard getClipboard() {
		return clpbrd;
	}
}
