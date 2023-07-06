package input;

import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import model.Document;

public class WordReader implements DocumentReader{

	//private Document doc;
	private String filename;
	private ArrayList<String> contents;
	private Scanner scan = null;

	
	public WordReader(String filaname) {
		contents = new ArrayList <String>();
		this.filename = filaname;
	}
	
	@Override
	public ArrayList<String> read() {
		try {
			File file = new File(filename);
			FileInputStream fis = new FileInputStream(file.getAbsolutePath());

			XWPFDocument docx = new XWPFDocument(OPCPackage.open(fis));
			XWPFWordExtractor ext = new XWPFWordExtractor(docx);
			scan = new Scanner(ext.getText());
			int i=0;
			while(scan.hasNextLine()) {
				String line = scan.nextLine();
				contents.add(line);
				contents.add("\n");
			}

			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contents;
	}

}