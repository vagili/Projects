package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.SoundAdjustments;
import model.TTSFacade;
import view.MyFrame;

class SoundAdjustmentsTest {

	private static SoundAdjustments sound;
	private static MyFrame frame;
	
	
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception{
		
		frame = new MyFrame();
		sound = new SoundAdjustments(frame.getPlayWhole(),frame.getPlayLine(),frame);
		
		frame.getPlayWhole().setVolume(60);
		frame.getPlayLine().setVolume(60);
		
	//	frame.getPlayWhole().setRate(5);
	//	frame.getPlayLine().setRate(5);
		
	//	frame.getPlayWhole().setPitch(80);
	//	frame.getPlayLine().setPitch(80);
		
	}
	
	
	
	
	
	@Test
	void test() {
		
		
		assertEquals((60/15.0),frame.getPlayLine().getVolume());
		assertEquals((60/15.0),frame.getPlayWhole().getVolume());
	//	assertEquals((50*22.5),frame.getPlayLine().getRate());
	//
	//	assertEquals((50*22.5),frame.getPlayWhole().getRate());
	//	assertEquals(80.0,frame.getPlayLine().getPitch());
	//	assertEquals(80.0,frame.getPlayWhole().getPitch());
	}

}

