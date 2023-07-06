
package input;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.compress.utils.FileNameUtils;

import commands.OpenDocument;
import view.MyFrame;

public class Rot13Decode extends ReaderDecorator{

	private MyFrame frame;
	private ArrayList<String> text;
	private String path;
	private String str;
	private OpenDocument open;
	private ArrayList<String> list;
	
	public Rot13Decode(MyFrame frame, String p,OpenDocument open) {
		
		this.frame = frame;
		path = p;
		text = new ArrayList<String>();
		this.open = open;
	}
	
	
	
	protected char mapping(char c) {

		
			if (c>64 && c<91)
			{
				c-=13;
				if(c<65)
				{
					c+=26;
				}
			}else if (c>96 && c<123)
			{
				c-=13;
				if(c<97)
				{
					c+=26;
				}
			}else {
				c = c;
			}
			
		
		return c;
											
	}
	
	public ArrayList<String> read(){
		
		if(FileNameUtils.getExtension(path).equals("xlsx")) {
			ExcelReader excel = new ExcelReader(open, path);
			try {
				text = excel.read();
				list = super.decode(text);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if(FileNameUtils.getExtension(path).equals("docx")) {
			WordReader word = new WordReader(path);
			text = word.read();
			list = super.decode(text);

		}else if(FileNameUtils.getExtension(path).equals("txt")){
			TxtReader txt = new TxtReader(open,path);
			text = txt.read();
			list = super.decode(text);

		}
		
		return list;
		
		
	}
	
	
}
