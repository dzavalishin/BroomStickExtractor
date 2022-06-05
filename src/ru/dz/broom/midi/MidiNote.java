package ru.dz.broom.midi;

import javax.sound.midi.ShortMessage;

public class MidiNote 
{
	private int key;
	
	private int note;
	private int octave;
	
	private int velocity;
	private int type;
	private int channel;


	
	public MidiNote(ShortMessage sm) 
	{
		setKey( sm.getData1() );
		velocity = sm.getData2();
		type = sm.getCommand();
		setChannel(sm.getChannel());
		
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
	
	public int getChannel() {		return channel;	}
	public void setChannel(int channel) {		this.channel = channel;	}

	public boolean isOn() { return type == MidiDefs.NOTE_ON; }
	public boolean isOff() { return type == MidiDefs.NOTE_OFF; }

	@Override
	public String toString() {		
		String off = isOff() ? " off" : "";
		return String.format("%-2s%d  ch %2d vel %3d%s", getNoteName(), octave, getChannel(), velocity, off );
	}

	public String toShortString() {		
		String off = isOff() ? " off" : "";
		return String.format("%-2s%d%s", getNoteName(), octave, off );
	}
}
