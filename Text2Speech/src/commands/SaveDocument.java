package commands;

import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JTextArea;

import org.apache.commons.compress.utils.FileNameUtils;

import model.Document;
import output.Context;
import output.ExcelWriter;
import output.TxtWriter;
import output.WordWriter;
import view.MyFrame;
import output.AtBashEncoding;
import output.Rot13Encoding;

public class SaveDocument implements ActionListener{
	
	private JTextArea textArea;
	private Document doc;
	private JFileChooser fileChooser;
	private MyFrame frame;
	private String commandName;
	private OpenDocument opd;
	private AtBashEncoding atbEn;
	private Rot13Encoding rot13En;
	
	public SaveDocument(JTextArea textArea, MyFrame frame) {
		this.frame = frame;
		this.textArea = frame.getTextArea();
	}
	
	public SaveDocument(Document doc, MyFrame frame, JTextArea textArea) {
		this.doc = doc;
		this.frame = frame;
		this.textArea = textArea;
		commandName = "saveDocument";
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println("Pathses save manmou");
		frame.getInvoker().addCommand("saveDocument", this, doc, textArea);
		frame.getInvoker().issueCommand("saveDocument");
		//saveFile(doc.getFileName());		        
		
	}
	
	public void execute(String p) {
		/*
		Context c = new Context();
		c.configureContext(new TxtWriter(frame,p));
		c.doWork();
		c.configureContext(new ExcelWriter(frame,p));
		c.doWork();
		c.configureContext(new WordWriter(frame,p));
		c.doWork();
		*/
		if(frame.getEnc().equals("No Encoding")) {
			if(FileNameUtils.getExtension(p).equals("xlsx")){
				ExcelWriter exclWriter = new ExcelWriter(frame,p);
				exclWriter.save();
			}else if(FileNameUtils.getExtension(p).equals("docx")) {
				WordWriter wordWriter = new WordWriter(frame,p);
				wordWriter.save();
			}else {
				TxtWriter txtWriter = new TxtWriter(frame,p);
				txtWriter.save();
			}
		}else if(frame.getEnc().equals("AtBash Encoding")){
			atbEn = new AtBashEncoding(frame,p);
		}else {
			rot13En = new Rot13Encoding(frame,p);
		}




	}


}
