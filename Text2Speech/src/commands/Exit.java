package commands;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Exit implements ActionListener {
	
	public Exit() {
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Goodbye, see you next time!");
		System.exit(-1);
	}

}
