package output;

import java.util.ArrayList;

import org.apache.commons.compress.utils.FileNameUtils;

import view.MyFrame;

public class Rot13Encoding extends WriterDecorator {

	private MyFrame frame;
	private ArrayList<String> text;
	private String str; 
	private String path;
	
	public Rot13Encoding (MyFrame frame,String p) {
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
			return (char) (((ch - 'a' + 13)%26) + 'a');
		}
		else if(ch >= 'A' && ch <= 'Z') {
			return (char) (((ch - 'A' + 13)%26) + 'A');
		}
		return 0;
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
