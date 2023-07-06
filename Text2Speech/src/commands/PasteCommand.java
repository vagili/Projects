package commands;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;
import java.awt.Component;
import javax.swing.JTextArea;

import view.MyFrame;

public class PasteCommand implements ActionListener{

	private JTextArea textArea;
	private Transferable clipData;
	private MyFrame frame;
	private Clipboard clpbrd;
	
	public PasteCommand(JTextArea textArea,MyFrame frame) {
		this.textArea = textArea;
		this.frame = frame;
		clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		frame.getInvoker().addCommand("paste",this, textArea);
		frame.getInvoker().issueCommand("paste");
	}
	
	public void execute(JTextArea txt) {
		paste(txt);
	}
	
	public void paste(JTextArea txt) {
		clipData = clpbrd.getContents(this);
		int pos = txt.getCaretPosition();
		String s;
		try {
			s = (String) (clipData.getTransferData(DataFlavor.stringFlavor));
			txt.insert(s, pos);
			//txt.replaceRange(s, txt.getSelectionStart(), txt.getSelectionEnd());
		}catch (Exception ex) {
			s = ex.toString();
		}
		//txt.setText(s);
		frame.setTextArea(txt);
	}
	
}
