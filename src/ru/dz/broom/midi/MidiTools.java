package ru.dz.broom.midi;

import javax.sound.midi.MetaMessage;

public class MidiTools {

	public static int getInt3(MetaMessage mm) 
	{
		int r = 0;
		
		byte[] b = mm.getData();
		
		r |= ((int)b[2]) & 0xFF;
		r |= (((int)b[1]) & 0xFF) << 8;
		r |= (((int)b[0]) & 0xFF) << 16;
		
		return r;
	}

	public static String getString(MetaMessage mm) {		
		return new String(mm.getData());
	}
	
	
}
