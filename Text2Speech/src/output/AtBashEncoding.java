package output;

import java.util.ArrayList;

import org.apache.commons.compress.utils.FileNameUtils;

import view.MyFrame;

public class AtBashEncoding extends WriterDecorator {

	private MyFrame frame;
	private ArrayList<String> text;
	private String path;
	private String str;
	
	public AtBashEncoding(MyFrame frame,String p) {
		this.frame = frame;
		path = p;
		text = new ArrayList<String>();
		for(String x: frame.getTextArea().getText().split("\n")) {
			text.add(x);
		}
		str = super.encode(text);
		save(str);
	}
	
	protected char mapping(char ch) {
		
		if(ch >= 'a' && ch <= 'z') {
			return (char) ('z' - ch + 'a');
		}
		else if(ch >= 'A' && ch <= 'Z') {
			return (char) ('Z' - ch + 'A');
		}else {
			return ch;
		}
	}

	
	public void save(String s) {

		if(FileNameUtils.getExtension(path).equals("xlsx")){
			ExcelWriter exclWriter = new ExcelWriter(frame,path);
			exclWriter.save(this.str);
		}else if(FileNameUtils.getExtension(path).equals("docx")) {
			WordWriter wordWriter = new WordWriter(frame,path);
			wordWriter.save(this.str);
		}else {
			TxtWriter txtWriter = new TxtWriter(frame,path);
			txtWriter.save(this.str);
		}
	}
	
	
}

