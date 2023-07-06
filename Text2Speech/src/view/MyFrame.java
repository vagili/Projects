package view;

import model.Document;
import model.SoundAdjustments;
import model.TTSFacade;
import commands.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.datatransfer.Clipboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;
import javax.swing.JOptionPane;

public class MyFrame extends JFrame implements ActionListener{
	

	private static final long serialVersionUID = 1L;
	
	private JFrame frame;

	private JMenuBar menuBar;
	
	private JMenu fileMenu;
	private JMenu editMenu;
	private JMenu recordMenu;
	private JMenu speechMenu;
	private JMenu helpMenu;
	private JMenu encoding;
	private JMenu decoding;
	private JMenu sound;
	
	private JTextArea textArea;
	
	private JMenuItem openItem;
	private JMenuItem saveItem;
	private JMenuItem saveAsItem;
	private JMenuItem exitItem;
	private JMenuItem startRecording;
	private JMenuItem endRecording;
	private JMenuItem replay;
	private JMenuItem atBashEncoding;
	private JMenuItem rot13Encoding;
	private JMenuItem noEncoding;
	private JMenuItem atBashEncoded;
	private JMenuItem rot13Encoded;
	private JMenuItem nonEncoded;
	
	private JMenuItem copyItem;
	private JMenuItem pasteItem;
	private JMenuItem deleteItem;
	private JMenuItem cutItem;
	private JMenuItem selectAllItem;
	private JMenuItem undoItem;
	private JMenuItem redoItem;
	//private JMenuItem findItem;
	
	private JMenuItem playWholeItem;
	private JMenuItem playLineItem;
	
	private JMenuItem volume;
	private JMenuItem speechRate;
	private JMenuItem pitch;
	
	private ImageIcon openFileIcon;
	private ImageIcon saveFileIcon;
	private ImageIcon saveAsFileIcon;
	private ImageIcon playWholeIcon;
	private ImageIcon playLineIcon;
	private ImageIcon exitFileIcon;
	private ImageIcon copyIcon;
	private ImageIcon pasteIcon;
	private ImageIcon deleteIcon;
	private ImageIcon cutIcon;
	private ImageIcon undoIcon;
	private ImageIcon redoIcon;
	private ImageIcon selectAllIcon;
	private ImageIcon findIcon;
	
	private OpenDocument openDocumentCommand;
	private SaveDocument saveDocumentCommand;
	
	private CopyCommand copyCommand;
	private PasteCommand pasteCommand;
	private DeleteCommand deleteCommand;
	private CutCommand cutCommand;
	private SelectAllCommand selectAllCommand;
	private UndoCommand undoCommand;
	private RedoCommand redoCommand;
	
	

	private TTSFacade playWhole;
	private TTSFacade playLine;
	
	private SoundAdjustments adj;
	
	private Clipboard clpbrd;
	
	private Document doc;
	
	private Invoker myInvoker; 
	private TTSFacade myFacade;
	
	private JPanel textAreaPanel;
	
	private UndoManager um;
	private String enc;
	private String dec;
	
	private Scanner myScan;
	
	private ReplayManager repMan;
	
	
	public MyFrame(){
		
		
		
		enc = "No Encoding";
		dec = "Non-Encoded";
		
		//Undo Manager
		um = new UndoManager();
		
		//Menu Bar
		menuBar = new JMenuBar();
		
			
		//Menu Tabs
		fileMenu = new JMenu("File"); 
		editMenu = new JMenu("Edit");
		recordMenu = new JMenu("Record");
		speechMenu = new JMenu("Speech");
		helpMenu = new JMenu("Help");
		encoding = new JMenu("Encoding");
		decoding = new JMenu("Decoding");
		sound = new JMenu("Sound");
		
		//File Tab Items
		openItem = new JMenuItem("Open File...");
		saveItem = new JMenuItem("Save");
		saveAsItem = new JMenuItem("Save As...");
		exitItem = new JMenuItem("Exit");
		
		//Edit Tab Items
		copyItem = new JMenuItem("Copy");
		pasteItem = new JMenuItem("Paste");
		deleteItem = new JMenuItem("Delete");
		cutItem = new JMenuItem("Cut");
		selectAllItem = new JMenuItem("Select All");
		undoItem = new JMenuItem("Undo");
		redoItem = new JMenuItem("Redo");
		//findItem = new JMenuItem("Find");
		
		//Speech Tab Items
		playWholeItem = new JMenuItem("Play Whole");
		playLineItem = new JMenuItem("Play Line");
		
		//Encoding Tab Items
		atBashEncoding = new JMenuItem("AtBash Encoding");
		rot13Encoding = new JMenuItem("Rot13 Encoding");
		noEncoding = new JMenuItem("No Encoding");
		
		//Decoding Tab Items
		atBashEncoded = new JMenuItem("AtBash Encoded");
		rot13Encoded = new JMenuItem("Rot13 Encoded");
		nonEncoded = new JMenuItem("Non-Encoded");
		
		//Record Tab Items
		startRecording = new JMenuItem("Start Recording");
		endRecording = new JMenuItem("End Recording");
		replay = new JMenuItem("Replay");
		
		//Sound tab Items
		volume = new JMenuItem("Volume");
		speechRate = new JMenuItem("Speech Rate");
		pitch = new JMenuItem("Pitch");
		
		//Menu Bar add items
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(recordMenu);
		menuBar.add(speechMenu);
		menuBar.add(encoding);
		menuBar.add(decoding);
		menuBar.add(sound);
		menuBar.add(helpMenu);
		
		
		//Icon creation
		openFileIcon = new ImageIcon("load.png");
		saveFileIcon = new ImageIcon("save.png");
		saveAsFileIcon = new ImageIcon("saveAS.png");
		playWholeIcon = new ImageIcon("playWhole.png");
		playLineIcon = new ImageIcon("playLine.png");
		exitFileIcon = new ImageIcon("exit.png");
		copyIcon = new ImageIcon("copy.png");
		pasteIcon = new ImageIcon("paste.png");
		cutIcon = new ImageIcon("cut.png");
		deleteIcon = new ImageIcon("delete.png");
		undoIcon = new ImageIcon("undo.png");
		redoIcon = new ImageIcon("redo.png");
		selectAllIcon = new ImageIcon("selectall.png");
		findIcon = new ImageIcon("find.png");
		
		//FileMenu add items
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(saveAsItem);
		fileMenu.add(exitItem);
		
		//EditMenu 
		editMenu.add(copyItem);
		editMenu.add(pasteItem);
		editMenu.add(deleteItem);
		editMenu.add(cutItem);
		editMenu.add(undoItem);
		editMenu.add(redoItem);
		editMenu.add(selectAllItem);
		//editMenu.add(findItem);
		
		//RecordMenu
		recordMenu.add(startRecording);
		recordMenu.add(endRecording);
		recordMenu.add(replay);
		
		//SpeechMenu add items
		speechMenu.add(playWholeItem);
		speechMenu.add(playLineItem);
		
		//Encoding add items
		encoding.add(atBashEncoding);
		encoding.add(rot13Encoding);
		encoding.add(noEncoding);
		
		//Decoding add items
		decoding.add(atBashEncoded);
		decoding.add(rot13Encoded);
		decoding.add(nonEncoded);
		
		//Sound add items
		sound.add(volume);
		sound.add(speechRate);
		sound.add(pitch);
		
		//Insert Icon
		openItem.setIcon(openFileIcon);
		saveItem.setIcon(saveFileIcon);
		saveAsItem.setIcon(saveAsFileIcon);
		playWholeItem.setIcon(playWholeIcon);
		playLineItem.setIcon(playLineIcon);
		exitItem.setIcon(exitFileIcon);
		copyItem.setIcon(copyIcon);
		pasteItem.setIcon(pasteIcon);
		cutItem.setIcon(cutIcon);
		deleteItem.setIcon(deleteIcon);
		undoItem.setIcon(undoIcon);
		redoItem.setIcon(redoIcon);
		selectAllItem.setIcon(selectAllIcon);
		//findItem.setIcon(findIcon);
		
		//Frame parameters
		frame = new JFrame();
		frame.setLayout(null);
		frame.setSize(1000,800); // Frame Dimensions
        //frame.setLayout(new FlowLayout()); //Icon scale adjustability.
		frame.setVisible(true); //visibility
		frame.setTitle("Text2Speech"); //App Title
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setJMenuBar(menuBar); //"Load" Menu bar on the App GUI

		
		myInvoker = new Invoker(this);
		
		openDocumentCommand = new OpenDocument(this);
		openItem.addActionListener(openDocumentCommand);
		doc = openDocumentCommand.getDoc();


		textArea = new JTextArea(39, 110);
		textArea = openDocumentCommand.getTextArea();
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		textArea.setVisible(true);
		textAreaPanel = new JPanel();
		textAreaPanel.setBounds(-500, 0 ,2000 , 700);
        JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		textAreaPanel.add(scrollPane);
		frame.add(textAreaPanel);

		saveDocumentCommand = new SaveDocument(doc ,this, textArea );
		saveItem.addActionListener(saveDocumentCommand);
		
		copyCommand = new CopyCommand(textArea, this);
		copyItem.addActionListener(copyCommand);
		
		pasteCommand = new PasteCommand(textArea, this);
		pasteItem.addActionListener(pasteCommand);

		deleteCommand = new DeleteCommand(textArea, this);
		deleteItem.addActionListener(deleteCommand);
		
		cutCommand = new CutCommand(textArea, this);
		cutItem.addActionListener(cutCommand);
				
		selectAllCommand = new SelectAllCommand(textArea, this);
		selectAllItem.addActionListener(selectAllCommand);
				
		textArea.getDocument().addUndoableEditListener(um);
		undoCommand = new UndoCommand(this, textArea, um);
		undoItem.addActionListener(undoCommand);
		
		redoCommand = new RedoCommand(this, textArea, um);
		redoItem.addActionListener(redoCommand);
		
		
		playWhole = new TTSFacade(this, textArea);
		playWholeItem.addActionListener(playWhole);
		
		playLine = new TTSFacade(this, textArea, true);
		playLineItem.addActionListener(playLine);
		
		//Encoding
		atBashEncoding.addActionListener(this);
		rot13Encoding.addActionListener(this);
		noEncoding.addActionListener(this);
		
		//Decoding
		atBashEncoded.addActionListener(this);
		rot13Encoded.addActionListener(this);
		nonEncoded.addActionListener(this);
		
		//Recording
		repMan = new ReplayManager(this);
		startRecording.addActionListener(repMan);
		endRecording.addActionListener(repMan);
		replay.addActionListener(repMan);
		
		//Sound Adjustment
		adj = new SoundAdjustments(playWhole,playLine,this);
		volume.addActionListener(adj);
		speechRate.addActionListener(adj);
		pitch.addActionListener(adj);
		
		Exit exitProg = new Exit();
		exitItem.addActionListener(exitProg);
		
	}
	
	public ReplayManager getRepMan() {
		return repMan;
	}
	
	public Invoker getInvoker() {
		return myInvoker;
	}
	
	public Document getDoc() {
		return doc;
	}
	
	public void setTextArea(JTextArea txt) {
		this.textArea = txt;
	}
	
	public JTextArea getTextArea() {
		return textArea;
	}
	
	public MyFrame getFrame() {
		return this;
	}

	public OpenDocument getOpenItem() {
		return openDocumentCommand;
	}
	
	public void setDoc(Document doc) {
		this.doc = doc;
	}

	
	public JMenuItem getSaveItem() {
		return saveItem;
	}
	
	public UndoManager getUM() {
		return um;
	}

	public String getEnc() {
		return enc;
	}
	
	public String getDec() {
		return dec;
	}
	
	public TTSFacade getPlayWhole() {
		return playWhole;
		
	}
	
	public TTSFacade getPlayLine() {
		return playLine;
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("AtBash Encoding")) {
			
			enc = "AtBash Encoding";
			JOptionPane.showMessageDialog(null, "Save file type set to 'AtBash' " , "Warning!", JOptionPane.INFORMATION_MESSAGE);
			
		}else if(e.getActionCommand().equals("Rot13 Encoding")) {
			enc = "Rot13 Encoding";
			JOptionPane.showMessageDialog(null, "Save file type set to 'Rot13' " , "Warning!", JOptionPane.INFORMATION_MESSAGE);

		}else if(e.getActionCommand().equals("No Encoding")){
			enc = "No Encoding";
			JOptionPane.showMessageDialog(null, " Default save type " , "Warning!", JOptionPane.INFORMATION_MESSAGE);

		}else if(e.getActionCommand().equals("AtBash Encoded")) {
			dec = "AtBash Encoded";
			JOptionPane.showMessageDialog(null, " You are about to open a 'AtBash' encoded file " , "Warning!", JOptionPane.INFORMATION_MESSAGE);

		}else if(e.getActionCommand().equals("Rot13 Encoded")) {
			dec = "Rot13 Encoded";
			JOptionPane.showMessageDialog(null, " You are about to open a 'Rot13' encoded file " , "Warning!", JOptionPane.INFORMATION_MESSAGE);

		}else {
			dec = "Non-Encoded";
			JOptionPane.showMessageDialog(null, " You are about to open a 'Non-Encoded' file " , "Warning!", JOptionPane.INFORMATION_MESSAGE);

		}
		
	}
	
}


