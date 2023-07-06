package commands;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JMenuItem;
import javax.swing.undo.UndoManager;

import model.SoundAdjustments;
import model.TTSFacade;
import view.MyFrame;

public class ReplayManager implements ActionListener{
	
	private boolean recStatus;
	private HashMap<String ,ActionListener> recordings;
	private MyFrame frame;
	private UndoManager um;
	private TTSFacade playWhole;
	private TTSFacade playLine;
	
	
	public ReplayManager(MyFrame frame) {
		recStatus = false;
		recordings = new HashMap<String, ActionListener>();
		this.frame = frame.getFrame();
		//um = frame.getUM();
	}
	
	public void startRecording() {
		recordings = new HashMap<String, ActionListener>();
		recStatus = true;
	}
	
	public void endRecording() {
		recStatus = false;
	}
	
	
	
	
	public void replay() {
		
		int i=0;
		for(Map.Entry<String, ActionListener> entry:recordings.entrySet()) {
			String name = entry.getKey();
			
			switch(name) {
				case "openDocument":
					OpenDocument op = new OpenDocument(frame);
					op.execute();
				case "saveDocument":					
					System.out.println("Mphka sto save tou replay");
					SaveDocument save = new SaveDocument(frame.getTextArea(),frame);
					save.execute(frame.getOpenItem().getFileName());				
				case "copy":
					CopyCommand copy = new CopyCommand(frame.getTextArea(),frame);
					copy.execute(frame.getTextArea());
				case "paste":
					PasteCommand paste = new PasteCommand(frame.getTextArea(),frame);
					paste.execute(frame.getTextArea());
				case "delete":
					DeleteCommand dlt = new DeleteCommand(frame.getTextArea(),frame);
					dlt.execute(frame.getTextArea());
				case "selectAll":
					SelectAllCommand slct = new SelectAllCommand(frame.getTextArea(),frame);
					slct.execute(frame.getTextArea());					
				case "cut":
					CutCommand cut = new CutCommand(frame.getTextArea(),frame);
					cut.execute(frame.getTextArea());				
				case "undo":
					UndoCommand undo = new UndoCommand(frame, frame.getTextArea(), frame.getUM());
					undo.execute(frame.getTextArea(), frame.getUM());
				/*case "redo":
					RedoCommand redo = new RedoCommand(frame, frame.getTextArea(), frame.getUM());
					redo.execute(frame.getTextArea(), frame.getUM());
				
				case "Volume":
					TTSFacade soundAdj1 = new TTSFacade(frame,frame.getTextArea());
					soundAdj1.setVolume(frame.getPlayWhole().getVolume(i));
					System.out.println(frame.getPlayWhole().getVolume(i));
					i++;
				case "Speech Rate":
					TTSFacade soundAdj2 = new TTSFacade(frame,frame.getTextArea());
					soundAdj2.setRate(frame.getPlayWhole().getRate(i));
				case "Pitch":
					TTSFacade soundAdj3 = new TTSFacade(frame,frame.getTextArea());
					soundAdj3.setPitch(frame.getPlayWhole().getPitch(i));
					
				System.out.println(name);
				*/
			}
			
		}
	}
	/*public void replay(ActionEvent e) {
		
		for(Map.Entry<String, ActionListener> entry:recordings.entrySet()) {
			String name = entry.getKey();
			
			switch(name) {
			case "Volume":
				SoundAdjustments soundAdj1 = new SoundAdjustments(frame.getPlayWhole(),frame.getPlayLine(),frame);
				soundAdj1.actionPerformed(e);
			case "Speech Rate":
				SoundAdjustments soundAdj2 = new SoundAdjustments(frame.getPlayWhole(),frame.getPlayLine(),frame);
				soundAdj2.actionPerformed(e);
			case "Pitch":
				SoundAdjustments soundAdj3 = new SoundAdjustments(frame.getPlayWhole(),frame.getPlayLine(),frame);
				soundAdj3.actionPerformed(e);
				
			System.out.println(name);
				
			}
			
		}	
	}*/
	
	
	
	public void addToArray(String name ,ActionListener command){
	
		recordings.put(name, command);
		/*for(int i=0; i<recordings.size(); i++) {
			System.out.println(i);
		}*/
	}
	
	public boolean getRecStatus() {
		return recStatus;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("Start Recording")) {
			startRecording();
		}else if(e.getActionCommand().equals("End Recording")) {
			endRecording();
		}else {

				replay();
			
		}
		
	}

	public void setRecordings(HashMap<String, ActionListener> map) {
		recordings = map;
		
	}
	
	
	
}
