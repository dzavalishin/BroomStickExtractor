package ru.dz.broom.midi;

import javax.sound.midi.ShortMessage;

public class MidiNote 
{
	private int key;
	
	private int note;
	private int octave;
	
	private int velocity;

	private int type;


	
	public MidiNote(ShortMessage sm) 
	{
		setKey( sm.getData1() );
		velocity = sm.getData2();
		type = sm.getCommand();
		
		switch (type) {
		case MidiDefs.NOTE_ON:
			if(velocity == 0)
				type = MidiDefs.NOTE_OFF;
			break;

		case MidiDefs.NOTE_OFF:			
			break;

		default:
			System.err.println("None type is wrong");
			break;
		}
	}
	
	public String getNoteName() { return MidiDefs.NOTE_NAMES[note]; }

	
	public int getKey() {		return key;	}
	public void setKey(int key) {
		this.key = key;
		octave = (key / 12)-1;
		note = key % 12;
	}

	public int getNote() {		return note;	}
	public void setNote(int note) {
		this.note = note;
		key = (octave+1) * 12 + note ;
	}

	public int getOctave() {		return octave;	}
	public void setOctave(int octave) {
		this.octave = octave;		
		key = (octave+1) * 12 + note ;
	}

	public int getVelocity() {		return velocity;	}
	public void setVelocity(int velocity) {		this.velocity = velocity;	}
	
	
}
