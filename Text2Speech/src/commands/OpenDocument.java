package commands;

import model.Document;
import view.MyFrame;

import java.util.*;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

import org.apache.commons.compress.utils.FileNameUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import input.AtBashDecode;
import input.ExcelReader;
import input.Rot13Decode;
import input.TxtReader;
import input.WordReader;

public class OpenDocument implements ActionListener{
	
	private String commandName;
	private String fileName;
	private File myFile;
	private Scanner myScanner;
	private Document doc;
	private ReplayManager manager;
	private MyFrame frame;
	private JFileChooser fileChooser;
	private JTextArea textArea;
	
	
	public OpenDocument() {
		
	}
	
	public OpenDocument(MyFrame frame) {
		textArea = new JTextArea(39, 110);
		this.frame = frame;
		commandName = "openDocument";
	}
	
	public OpenDocument(String commandName) {
		this.commandName = commandName;
		//frame  = new MyFrame();
	}
	

	public Document getDocument() {
		return doc;
	}
	
	public void setReplayManager(ReplayManager manager) {
		this.manager = manager;
						
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		frame.getInvoker().addCommand("openDocument", this, doc, textArea);
		frame.getInvoker().issueCommand("openDocument");
	}


	public JTextArea getTextArea() {
		return textArea;
	}
	
	public Document getDoc() {
		return doc;
	}
	
	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public MyFrame getFrame() {
		return frame;
	}
	
	public void execute(){
		
		fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(".")); //Set default file chooser directory.
		int response = fileChooser.showOpenDialog(null);//Store return value.0 if successful. 1 if x/cancel.
		if(frame.getDec().equals("Non-Encoded")) {
			if(response == JFileChooser.APPROVE_OPTION) {
					
					if((FileNameUtils.getExtension(fileChooser.getSelectedFile().getAbsolutePath())).equals("xlsx")) {
						ExcelReader excel = new ExcelReader(this, fileChooser.getSelectedFile().getAbsolutePath());
						try {
							fileName = fileChooser.getSelectedFile().getAbsolutePath();
							frame.getTextArea().setText("");
							ArrayList<String> sth = excel.read();
							for(String x: sth) {
								textArea.append(x);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}else if(FileNameUtils.getExtension(fileChooser.getSelectedFile().getAbsolutePath()).equals("docx")){
						fileName = fileChooser.getSelectedFile().getAbsolutePath();
						WordReader word = new WordReader(fileChooser.getSelectedFile().getAbsolutePath());
						frame.getTextArea().setText("");
						ArrayList<String> sth = word.read();
						for(String x: sth) {
							textArea.append(x);
						}
						
					}else if(FileNameUtils.getExtension(fileChooser.getSelectedFile().getAbsolutePath()).equals("txt")) {
						fileName = fileChooser.getSelectedFile().getAbsolutePath();
						TxtReader txt = new TxtReader(this,fileName);
						frame.getTextArea().setText("");
						ArrayList<String> contents = txt.read();
						for(String x: contents) {
							textArea.append(x + "\n");
						}
						
					}
					
	
			}
		
		}else if(frame.getDec().equals("AtBash Encoded")) {
			fileName = fileChooser.getSelectedFile().getAbsolutePath();
			AtBashDecode atbDec = new AtBashDecode(frame,fileName,this);
			frame.getTextArea().setText("");
			ArrayList<String> contents = atbDec.read();
			for(String x: contents) {
				textArea.append(x);
			}
			
			
		}else if(frame.getDec().equals("Rot13 Encoded")) {
			fileName = fileChooser.getSelectedFile().getAbsolutePath();
			Rot13Decode rot13Dec = new Rot13Decode(frame,fileName,this);
			frame.getTextArea().setText("");
			ArrayList<String> contents = rot13Dec.read();
			for(String x: contents) {
				textArea.append(x);
			}
			
		}
			
	}

	
}	
	
	
