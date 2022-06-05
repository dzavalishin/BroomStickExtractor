package ru.dz.broom.midi;

import javax.sound.midi.ShortMessage;

public class GenericNote 
{
	private int key;
	
	private int note;
	private int octave;
	
	private int velocity;
	private int channel;

	public GenericNote(ShortMessage sm) {
		setKey( sm.getData1() );
		setVelocity( sm.getData2() );
		setChannel(sm.getChannel());
	}


	public GenericNote(MidiNote inote) {
		setKey( inote.getKey() );
		setVelocity( inote.getVelocity() );
		setChannel( inote.getChannel());
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
	

	@Override
	public String toString() {		
		return String.format("%-2s%d  ch %2d vel %3d", getNoteName(), getOctave(), getChannel(), getVelocity() );
	}

}
