package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.function.BooleanSupplier;

import javax.naming.event.EventContext;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import view.MyFrame;

public class TTSFacade implements ActionListener{
	
	private int line;
	private MyFrame frame;
	private JTextArea textArea;
	private VoiceManager vm;
	private Voice voice;
	private boolean flag = false;
	private boolean flag2= false;
	private ArrayList<Integer> volumes;
	private ArrayList<Integer> rates; 
	private ArrayList<Integer> pitches; 
	
	public TTSFacade(MyFrame frame, JTextArea textArea) {
		this.frame = frame;
		this.textArea = textArea;
		volumes = new ArrayList<Integer>();
		rates = new ArrayList<Integer>();
		pitches = new ArrayList<Integer>();
		setVoice();
	}
	
	public TTSFacade(MyFrame frame, JTextArea textArea, boolean flag) {
		this.frame = frame;
		this.textArea = textArea;
		this.flag = flag;
		volumes = new ArrayList<Integer>();
		rates = new ArrayList<Integer>();
		pitches = new ArrayList<Integer>();
		setVoice();
	}
	
	public void setVoice() {
		System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory" );
		vm = VoiceManager.getInstance();
		voice = vm.getVoice("kevin16");
		voice.allocate();
	}
	
	public void play(String s) {
		flag2 = true;
		voice.speak(s);
	}
	
	
	public void setVolume(int volume) {
		voice.setVolume( volume/(float)15.0);
		//volumes.add(volume);
	}

	
	public void setPitch(int pitch) {
		voice.setPitch(41.2f);
		//pitches.add(pitch);
	}

	
	public void setRate(int rate) {
		voice.setRate((float)rate*10);
		//rates.add(rate);
	}
	
	public float getVolume(){
		return voice.getVolume();
	}
	
	public float getRate(){
		return voice.getRate();
	}
	public float getPitch(){
		return voice.getPitch();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(flag == false) {
			String s = textArea.getText();
			play(s);
		}else {
			String answer = JOptionPane.showInputDialog("Give the number of the line you wanna play: ");
			if(answer != null) {
				line = Integer.valueOf(answer);
				String text[] = textArea.getText().split("\n");
				System.out.println("answer: " + line);
				play(text[line]);
			}
		}
	}

	public boolean getFlag2() {
		return flag2;
	}
	
	
	
}
