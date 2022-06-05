package ru.dz.broom.midi;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

public class MidiParser 
{
	private Sequence sequence;
	private MidiSignature signature = null;
	private String presetName = "(unnamed)";
	
	
	public MidiParser(String midiFile) throws InvalidMidiDataException, IOException {
		sequence = MidiSystem.getSequence(new File(midiFile));
		parseSequence(sequence);

	}
	
	
	private void parseSequence(Sequence sequence) 
	{
		int trackNumber = 0;
		for (Track track :  sequence.getTracks()) 
		{
			
			MidiTrackParser mtp = new MidiTrackParser(trackNumber, this);
			
			mtp.parseMidiTrack(track);

			trackNumber++;
		}
	}


	public MidiSignature getSignature() {
		if(signature == null)
			throw new RuntimeException("No signature");
		
		return signature;
	}


	public void setSignature(MidiSignature signature) {
		if(signature != null)
		{
			System.err.println("Double signature: "+signature.toString());
			//throw new RuntimeException("Double signature");
		}
		this.signature = signature;
	}


	public String getPresetName() {		return presetName;	}
	public void setPresetName(String presetName) {		this.presetName = presetName;	}


	
}
