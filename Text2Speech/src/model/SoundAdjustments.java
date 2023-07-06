package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.undo.UndoManager;

import view.MyFrame;

public class SoundAdjustments implements ActionListener{
	
	private TTSFacade playWhole;
	private TTSFacade playLine;
	private MyFrame frame;
	private JTextArea textArea;
	private ActionListener event;
	
	
	public SoundAdjustments(TTSFacade playWhole,TTSFacade playLine,MyFrame frame) {
		this.playWhole = playWhole;
		this.playLine = playLine;
		this.frame = frame.getFrame();
		textArea =frame.getTextArea();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("Volume")){
			String answer = JOptionPane.showInputDialog("Type the value of the desired volume ");
			if(answer != null) {
				
				boolean flag =false;
				while(flag != true) {
					try {
						frame.getInvoker().addCommand("Volume", event, textArea);
						frame.getInvoker().issueCommand("Volume");
						int valueV = Integer.parseInt(answer);
						if(valueV>=0 && valueV<=100) {
							flag = true;
							playWhole.setVolume(valueV);
							playLine.setVolume(valueV);
						}else {
							answer = JOptionPane.showInputDialog("Wrong Input! Type a number from 0 to 100");
							if(answer == null) {
								break;
							}
						}

					}catch(NumberFormatException ex) {
						answer = JOptionPane.showInputDialog("Wrong Input! Type a number from 0 to 100");
						if(answer == null) {
							break;
						}
					}
				}
			}
		}else if(e.getActionCommand().equals("Speech Rate")) {
			String answer = JOptionPane.showInputDialog("Type the value of the desired speech rate ");
			if(answer != null) {
				
				boolean flag =false;
				while(flag != true) {
					try {
						frame.getInvoker().addCommand("Speech Rate", event, textArea);
						frame.getInvoker().issueCommand("Speech Rate");
						int valueS = Integer.parseInt(answer);
						if(valueS>0 && valueS<=20) {
							flag = true;
							playWhole.setRate(valueS);
							playLine.setRate(valueS);
						}else {
							answer = JOptionPane.showInputDialog("Wrong Input! Type a number from 0 to 20");
							if(answer == null) {
								break;
							}
						}

					}catch(NumberFormatException ex) {
						answer = JOptionPane.showInputDialog("Wrong Input! Type a number from 0 to 20");
						if(answer == null) {
							break;
						}
					}
				}
			}

		}else {
			String answer = JOptionPane.showInputDialog("Type the value of the desired speech rate ");
			if(answer != null) {
				
				boolean flag =false;
				while(flag != true) {
					try {
						frame.getInvoker().addCommand("Pitch", event,textArea);
						frame.getInvoker().issueCommand("Pitch");
						flag = true;
						int valueP = Integer.parseInt(answer);
						playLine.setPitch(valueP);
						playLine.setPitch(valueP);
						/*
						if(valueP>0 && valueP<=20) {
							flag = true;
							playWhole.setRate(valueP);
							playLine.setRate(valueP);
						}else {
							answer = JOptionPane.showInputDialog("Wrong Input! Type a number from 0 to 20");
							if(answer == null) {
								break;
							}
						}
	*/				
					}catch(NumberFormatException ex) {
						answer = JOptionPane.showInputDialog("Wrong Input! Type a number from 0 to 20");
						if(answer == null) {
							break;
						}
					}
				}
			}


		}
		
	}

	
}
