package commands;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.MyFrame;
import javax.swing.JTextArea;

public class DeleteCommand implements ActionListener{
	
	private MyFrame frame;
	private JTextArea textArea;
	
	public DeleteCommand(JTextArea textArea, MyFrame frame) {
		this.frame = frame;
		this.textArea = textArea;
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		frame.getInvoker().addCommand("delete",this, textArea);
		frame.getInvoker().issueCommand("delete");
	}
	
	public void execute(JTextArea textArea) {
		delete(textArea);
	}


	private void delete(JTextArea textArea) {
		int pos = textArea.getCaretPosition();
		String s;
		try {
			textArea.replaceRange("", textArea.getSelectionStart(), textArea.getSelectionEnd());
		}catch (Exception ex){
			s = ex.toString();
		}
		frame.setTextArea(textArea);
				
	}	
	
}
