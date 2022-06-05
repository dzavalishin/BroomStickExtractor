package ru.dz.broom.midi;

import javax.sound.midi.ShortMessage;

public class MidiNote extends GenericNote
{
	private int type;

	
	public MidiNote(ShortMessage sm) 
	{
		super(sm);

		type = sm.getCommand();
		
		switch (type) {
		case MidiDefs.NOTE_ON:
			if(getVelocity() == 0)
				type = MidiDefs.NOTE_OFF;
			break;

		case MidiDefs.NOTE_OFF:			
			break;

		default:
			System.err.println("None type is wrong");
			break;
		}
	}
	

	public boolean isOn() { return type == MidiDefs.NOTE_ON; }
	public boolean isOff() { return type == MidiDefs.NOTE_OFF; }

	@Override
	public String toString() {		
		String off = isOff() ? " off" : "";
		return String.format("%-2s%d  ch %2d vel %3d%s", getNoteName(), getOctave(), getChannel(), getVelocity(), off );
	}

	public String toShortString() {		
		String off = isOff() ? " off" : "";
		return String.format("%-2s%d%s", getNoteName(), getOctave(), off );
	}
}
