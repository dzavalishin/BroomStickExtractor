package ru.dz.broom.midi;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class MidiParser 
{
	private Sequence sequence;

	private static final boolean printDetails = true;
	private static final boolean printOff = false;
	
	/**
	 * Numerator of song signature ( 4/? )
	 */
	private static int signatureNumerator;

	/**
	 * Denominator of song signature ( ?/4 )
	 */
	private static int signatureDenominator;
	
	/** 
	 * Ticks per beat.
	 * 
	 * (mult by measuresPerBeat to get ticks per measure)
	 */
	private static int ticksPerBeat;
	
	/** 
	 * which part of measure beat is (usually 4th). In 32th.
	 * 
	 * Какую часть такта составляет доля (обычно 4-ю) - исчисляется в 32-х
	 */
	private static int n32PerBeat;
	
	public MidiParser(String midiFile) throws InvalidMidiDataException, IOException {
		sequence = MidiSystem.getSequence(new File(midiFile));
		parseSequence(sequence);

	}
	
	
	private static void parseSequence(Sequence sequence) 
	{
		int trackNumber = 0;
		for (Track track :  sequence.getTracks()) 
		{
			trackNumber++;
			parseMidiTrack(trackNumber, track);
		}
	}

	private static void parseMidiTrack(int trackNumber, Track track) {
		System.out.println("Track " + trackNumber + ": size = " + track.size());
		System.out.println();
		for (int i=0; i < track.size(); i++) 
		{ 
			MidiEvent event = track.get(i);
			parseMidiEvent(event);
		}

		System.out.println();
	}

	
	private static void parseMidiEvent(MidiEvent event) 
	{
		MidiMessage message = event.getMessage();
		
		if (message instanceof ShortMessage) 
			parseShortMessage(event, (ShortMessage) message);						
		else if (message instanceof MetaMessage)  
			parseMidiMeta((MetaMessage) message);		 
		else {
			System.out.println("Other message: " + message.getClass()+" = '"+message.toString()+"'");
		}
	}


	private static void parseShortMessage(MidiEvent event, ShortMessage sm) {
		switch(sm.getCommand())
		{
		case MidiDefs.NOTE_ON:
		case MidiDefs.NOTE_OFF:
			parseMidiNote(sm,event);
			break;
						
		default:
			System.out.println("Command:" + sm.getCommand());
			break;
		}
	}


	private static void parseMidiMeta(MetaMessage mm) 
	{
		final byte[] b = mm.getData();
		
		System.out.print("Meta message type: " + mm.getType()+" ");
		switch(mm.getType())
		{
		case 47:	System.out.print("(stop)"); break;
		case 3:		System.out.print("(track name '"+MidiTools.getString(mm)+"')"); break;
		
		case 0x51:	
			long bpm = 60 * 1000 * 1000 / MidiTools.getInt3(mm);
			System.out.print("(tempo "+bpm+" BPM)"); 
			break;
		
		case 0x58:	
			int num = b[0];
			int den = 1 << b[1];
			int ticks = b[2];
			int n32 = b[3];
			
			signatureNumerator = num;		// 4/
			signatureDenominator = den;		// /4
			ticksPerBeat = ticks;			
			n32PerBeat = n32;			 
			
			System.out.print("(signature "+num+"/"+den+", "); 
			System.out.print(""+ticks+" ticks/beat, "+n32+"/32 per beat)"); 
			break;
		}
		
		System.out.println();
		
		/* TODO
		 * 
		Message				Meta type	Data length	Contains	Occurs at
		Sequence number		0x00	2 bytes		The number of a sequence											At delta time 0
		Text				0x01	variable	Some text															Anywhere
		Copyright notice	0x02	variable	A copyright notice													At delta time 0 in the first track
		Track name			0x03	variable	A track name														At delta time 0
		Instrument name		0x04	variable	The name of an instrument in the current track						Anywhere
		Lyrics				0x05	variable	Lyrics, usually a syllable per quarter note							Anywhere
		Marker				0x06	variable	The text of a marker												Anywhere
		Cue point			0x07	variable	The text of a cue, usually to prompt for some action from the user	Anywhere
		Channel prefix		0x20	1 byte		A channel number (following meta events will apply to this channel)	Anywhere
		End of track		0x2F	0	 																			At the end of each track
		Set tempo			0x51	3			The number of microseconds per beat									Anywhere, but usually in the first track
		SMPTE offset		0x54	5			SMPTE time to denote playback offset from the beginning				Anywhere
		Time signature		0x58	4			Time signature, metronome clicks, and size of a beat in 32nd notes	Anywhere
		Key signature		0x59	2			A key signature														Anywhere
		Sequencer specific	0x7F	variable	Something specific to the MIDI device manufacturer					Anywhere		 
		* 
		*/
	}


	private static void parseMidiNote(ShortMessage sm, MidiEvent event) 
	{
		MidiNote note = new MidiNote(sm);
		
		if( !printOff ) 
			if (note.isOff())	
				return;		
		
		//String ttm = tickToMeasure(event.getTick());
		String ttm = tickTo32nd(event.getTick());
		
		if(printDetails)
			System.out.printf("%10s %s\n", ttm, note.toString() );
		else
			System.out.print(note.toShortString() + " ");
	}

	
	
	private static String tickToMeasure(long tick)
	{
		long beats = tick/ticksPerBeat;
		int extraTicks = (int) (tick % ticksPerBeat);

		int beatsPerMeasure = 32/n32PerBeat;
		
		int measures = (int) (beats/beatsPerMeasure);
		int extraBeats= (int) (beats%beatsPerMeasure);
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("" + measures + "." + extraBeats );
		if(extraTicks != 0)
			sb.append("(+" +extraTicks+")");
		
		return sb.toString();
	}

	
	private static String tickTo32nd(long tick)
	{
		//int sliceSize = 32; // 1/32
		int sliceSize = 16; // 1/32
		int ticksPerSlice = ticksPerBeat / n32PerBeat;
		
		ticksPerSlice *= 32;
		ticksPerSlice /= sliceSize;
		
		long slices = tick/ticksPerSlice;
		int extraTicks = (int) (tick % ticksPerSlice);
		
		
		int measures = (int) (slices/sliceSize);
		int extraBeats= (int) (slices%sliceSize);
		
		/*
		StringBuilder sb = new StringBuilder();
		
		sb.append("" + measures + "." + extraBeats+" 1/"+sliceSize );
		if(extraTicks != 0)
			sb.append(" (+" +extraTicks+")");
		
		return sb.toString();
		*/

		String extra = (extraTicks != 0) ? (" (+" +extraTicks+")") : "";

		return String.format("%3d.%2d 1/%d %6s", measures, extraBeats, sliceSize, extra );
	}

	
}
