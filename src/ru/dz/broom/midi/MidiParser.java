package ru.dz.broom.midi;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Track;

public class MidiParser 
{
	private Sequence sequence;
	private MidiSignature signature = null;
	private String presetName = "(unnamed)";
	
	private List<MidiTrackParser> tracks = new ArrayList<>();
	private long tempo;
	
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
			
			mtp.parse(track);
			tracks.add(mtp);

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


	public void dump() 
	{
		System.out.println("Preset "+presetName+", "+tracks.size()+" tracks");
		System.out.println("Signature "+signature+" tempo "+tempo);
		for( MidiTrackParser t : tracks )
		{
			t.dump();
		}
		
	}


	public void setTempo(long tempo) {
		this.tempo = tempo;
	}


	
}
