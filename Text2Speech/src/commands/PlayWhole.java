package commands;

import java.awt.event.ActionListener;

import javax.swing.JTextArea;

import java.awt.event.ActionEvent;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import model.Document;
import view.MyFrame;

public class PlayWhole implements ActionListener{
	
	private MyFrame frame;
	private Voice voice;
	private VoiceManager vm;
	private JTextArea textArea;
	
	public PlayWhole(MyFrame frame, JTextArea txt) {
		this.frame = frame;
		textArea = txt;
		setVoice();
	}
	
	public void setVoice() {
		System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory" );
		vm = VoiceManager.getInstance();
		voice = vm.getVoice("kevin16");
		voice.allocate();		
	}

	public void play(String s) {
		voice.speak(s);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
			String s = textArea.getText();
			play(s);
	}

}
