package output;
import java.util.ArrayList;

import view.MyFrame;

public abstract class WriterDecorator implements DocumentWriter {

	//private ArrayList<String> text;
	private MyFrame frame = new MyFrame();
	
	public WriterDecorator () {
		frame = frame.getFrame();
		//text = new ArrayList<String>();
		//for(String x: frame.getTextArea().getText().split("\n")) {
		//	text.add(x);
		//}
	}
	
	public String encode(ArrayList<String> text) {
		
		String str = "";
		for(String i: text) {
			
			char[] charArray = i.toCharArray();
			for(int j=0; j<i.length(); j++) {
				if(charArray[j] >= 'a' && charArray[j] <= 'z') {
						charArray[j] = mapping(charArray[j]);
				}
				else if (charArray[j] >= 'A' && charArray[j] <= 'Z' ) {
					charArray[j] = mapping(charArray[j]);
				}
			}
			
			for(int k = 0; k < charArray.length; k++) {
				str = str + charArray[k];
			}
			
		}
		
		return str;
	}
	
	protected abstract char mapping(char c);
	
	public void save() {
		
		
		
		
	}
}
