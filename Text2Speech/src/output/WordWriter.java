package output;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import view.MyFrame;



public class WordWriter implements DocumentWriter{

	MyFrame frame;
	String fileName;
	
	public WordWriter(MyFrame frame, String fileName) {
		this.frame = frame;
		this.fileName = fileName;
	}

	public void save(String s) {

		int rows = 0;
		for(String word:s.split(" ")) {

			rows++;
		}
		String array[] = new String [rows];
		int i = 0;
		for(String word: s.split(" ")) {
			array[i] = word;
			i++;
		}
		String str = " ";
		for(String word: array) {
			str = str + "\n" + word;
		}
		try {
			XWPFDocument doc = new XWPFDocument();
			FileOutputStream out = new FileOutputStream(fileName);
			XWPFParagraph par = doc.createParagraph();
			XWPFRun run = par.createRun();
			run.setText(str);
			doc.write(out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
	
	public void save() {
		
		int rows=0;
		for(String line: frame.getTextArea().getText().split("\n")) {
			
			
			rows++;
			
		}
		
		String array[] = new String[rows];
		
		int i=0;
		for(String line: frame.getTextArea().getText().split("\n")) {
			
			
			array[i]= line;
			i++;
			
		}

		String str = " ";
		for(String line: array) {
			str = str + "\n" + line;
		}
		try {
			XWPFDocument doc = new XWPFDocument();
			FileOutputStream out = new FileOutputStream(fileName);
			XWPFParagraph par = doc.createParagraph();
			XWPFRun run = par.createRun();
			run.setText(str);
			doc.write(out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
	}
	
	
	
}
