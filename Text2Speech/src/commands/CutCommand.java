package commands;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;

import view.MyFrame;

public class CutCommand implements ActionListener{
	
	private JTextArea textArea;
	private MyFrame frame;
	private StringSelection data;
	private Clipboard clpbrd;

	
	public CutCommand(JTextArea textArea, MyFrame frame) {
		this.textArea = textArea;
		this.frame = frame;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		frame.getInvoker().addCommand("cut", this, textArea);
		frame.getInvoker().issueCommand("cut");	
		
	}


	public void execute(JTextArea textArea) {
		cut(textArea);
	}


	private void cut(JTextArea textArea) {
		if(textArea.getSelectedText() != null) {
			data = new StringSelection(textArea.getSelectedText().toString());
			clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
			clpbrd.setContents(data, data);
			textArea.replaceRange("", textArea.getSelectionStart(), textArea.getSelectionEnd());
		}else {
			System.out.println("Cut not executed! You should highlight a portion of the text area before pressing 'Cut'");
		}

	}
	
	
}
