package input;

import java.util.ArrayList;

public abstract class ReaderDecorator{
		
	public ArrayList<String> decode(ArrayList<String> a) {
		
		ArrayList<String> list = new ArrayList<String>();
		for(String i: a) {
			String str = "";

			char[] charArray = i.toCharArray();
			for(int j=0; j<i.length(); j++) {
				if(charArray[j] > 64 && charArray[j] < 91) {
						charArray[j] = mapping(charArray[j]);
				}
				else if (charArray[j] > 96 && charArray[j] < 123 ) {
					charArray[j] = mapping(charArray[j]);
				}else {
					charArray[j] = mapping(charArray[j]);
				}
			}
			
			for(int k = 0; k < charArray.length; k++) {
				str = str + charArray[k];
			}
			list.add(str);
			list.add("\n");
		}
		
		
		
		
		return list;
				
	}
	
	protected abstract char mapping(char c);
	
}
