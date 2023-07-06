package commands;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;

import view.MyFrame;

public class SelectAllCommand implements ActionListener{
	
	private JTextArea textArea;
	private MyFrame frame;
	
	public SelectAllCommand(JTextArea textArea, MyFrame frame) {
		this.textArea = textArea;
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		frame.getInvoker().addCommand("selectAll",this, textArea);
		frame.getInvoker().issueCommand("selectAll");
	}
	
	public void execute(JTextArea textArea) {
		slctAll(textArea);
	}
	
	public void slctAll(JTextArea textArea) {
		textArea.selectAll();
		frame.setTextArea(textArea);
	}
}
