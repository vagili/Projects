package commands;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.undo.UndoManager;

import model.Document;
import model.SoundAdjustments;
import model.TTSFacade;
import view.MyFrame;

public class Invoker{
	
	private Document doc;
	private UndoManager um;
	private MyFrame frame;
	private ActionEvent e;
	private JTextArea textArea;
	private String commandName;
	private JMenuItem openItem;
	private ActionListener command;
	private HashMap<String, ActionListener> commands;
	//private TTSFacade playWhole;
	//private TTSFacade playLine;
	
	
	public Invoker(MyFrame frame) {
		commands = new HashMap<String, ActionListener>();
		this.frame = frame;
	}
	
	public Invoker(String commandName) {
		
		commands = new HashMap<String, ActionListener>();
		//e = new ActionEvent();
		
		if(commandName == "openDocument") {
			this.commandName = commandName;
			frame = new MyFrame();
			commands.put(commandName, command);
			//issueCommand(commandName);
		}
		
	}

	public String getCommandName() {
		return commandName;
	}
	public Invoker getInvoker() {
		return this;
	}
	
	public void addCommand(String commandName, ActionListener command) {
		commands.put(commandName, command);
		if(frame.getRepMan().getRecStatus()) {
			frame.getRepMan().addToArray(commandName,command);
		}
	}
	
	public void addCommand(String commandName, ActionListener command, JTextArea textArea, UndoManager um) {
		commands.put(commandName,command);
		this.textArea = textArea;
		this.um = um;
		if(frame.getRepMan().getRecStatus()) {
			frame.getRepMan().addToArray(commandName,command);
		}
	}
	
	public void addCommand(String commandName, ActionListener command, JTextArea textArea) {
		commands.put(commandName,command);
		this.textArea = textArea;
		if(frame.getRepMan().getRecStatus()) {
			frame.getRepMan().addToArray(commandName,command);
		}
	}
	
	public void addCommand(String commandName, ActionListener command, Document doc, JTextArea textArea){
		commands.put(commandName, command);
		if( commandName.equals("saveDocument")) {
			this.doc = doc;
			this.textArea = textArea;
		}
		if(frame.getRepMan().getRecStatus()) {
			frame.getRepMan().addToArray(commandName,command);
		}
	}
		

	public void issueCommand(String commandKey) {
		OpenDocument op = frame.getOpenItem();
		//OpenDocument op = new OpenDocument(frame);
		
		if(commandKey.equals("openDocument")) {
			op = (OpenDocument) commands.get(commandKey);
			op.execute();
		}else if(commandKey.equals("saveDocument")){
			op.setTextArea(frame.getTextArea());
			SaveDocument sav = new SaveDocument(textArea, frame);
			sav = (SaveDocument) commands.get(commandKey);

			sav.execute(op.getFileName());
		}else if(commandKey.equals("copy")){
			CopyCommand cp = new CopyCommand(textArea, frame);
			cp = (CopyCommand) commands.get(commandKey);
			cp.execute(textArea);
		}else if(commandKey.equals("paste")){
			PasteCommand pst = new PasteCommand(textArea,frame);
			pst = (PasteCommand) commands.get(commandKey);
			pst.execute(textArea);
		}else if(commandKey.equals("delete")){
			DeleteCommand dlt = new DeleteCommand(textArea,frame);
			dlt.execute(textArea);
		}else if(commandKey.equals("selectAll")){
			SelectAllCommand slct = new SelectAllCommand(textArea, frame);
			slct.execute(textArea);
		}else if(commandKey.equals("cut")) {
			CutCommand cut = new CutCommand(textArea, frame);
			cut.execute(textArea);
		}else if(commandKey.equals("undo")) {
			UndoCommand und = new UndoCommand(frame, textArea, um);
			und.execute(textArea, um);
		}else if(commandKey.equals("redo")) {
			RedoCommand rd = new RedoCommand(frame,textArea, um);
			rd.execute(textArea, um);
		
		/*}else if(commandKey.equals("Volume")) {
			TTSFacade soundAdj1 = new TTSFacade(frame,frame.getTextArea());
			//soundAdj1 = (TTSFacade) commands.get(commandKey);
			soundAdj1.setVolume(frame.getPlayWhole().getVolume());		
		}else if(commandKey.equals(e.getActionCommand().equals("Speech Rate"))) {
			TTSFacade soundAdj2 = new TTSFacade(frame,frame.getTextArea());
			//soundAdj2 = (SoundAdjustments) commands.get(commandKey);
			soundAdj2.setRate();
		}else if(commandKey.equals(e.getActionCommand().equals("Pitch"))) {
			TTSFacade soundAdj3 = new TTSFacade(frame,frame.getTextArea());
			//soundAdj3 = (SoundAdjustments) commands.get(commandKey);
			soundAdj3.setPitch();*/
		}
		//System.out.println(commandKey);
	}
	
	
	
}
