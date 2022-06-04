package ru.dz.bluearp;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import ru.dz.bintools.VST3Reader;
import ru.dz.bintools.VST3Writer;

public class BlueBroomMain 
{
	//private static final String MIDI_FILE = "G:\\Projects\\BroomStickExtractor\\midi\\Disco_Octaves.mid";
	//private static final String MIDI_FILE = "midi/Disco_Octaves.mid";
	private static final String MIDI_FILE = "midi/Classic_Disco.mid";
	private static final String FXP_FILE = "bluearp/BlueARP_FactoryBank.fxb";
	private static final String VST3_FILE = "bluearp/dzBlueArpTest.vstpreset";
	
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

	
	public static void main(String[] args)  
	{
		try {
			VST3Reader vir = new VST3Reader(VST3_FILE);
			VST3Writer viw = new VST3Writer(vir.getBank(), VST3_FILE+".new");
					
			System.exit(32);
			
			BlueFxp fxp = new BlueFxp();

			fxp.load(FXP_FILE);
			fxp.save(FXP_FILE+".new");
			
			Sequence sequence = MidiSystem.getSequence(new File(MIDI_FILE));
			//printSequence(sequence);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void printSequence(Sequence sequence) 
	{
		int trackNumber = 0;
		for (Track track :  sequence.getTracks()) 
		{
			trackNumber++;
			printMidiTrack(trackNumber, track);
		}
	}

	private static void printMidiTrack(int trackNumber, Track track) {
		System.out.println("Track " + trackNumber + ": size = " + track.size());
		System.out.println();
		for (int i=0; i < track.size(); i++) 
		{ 
			MidiEvent event = track.get(i);
			printMidiEvent(event);
		}

		System.out.println();
	}

	
	private static void printMidiEvent(MidiEvent event) 
	{
		//if(printDetails) System.out.print("@" + event.getTick() + " ");
		MidiMessage message = event.getMessage();
		
		if (message instanceof ShortMessage) 
		{
			ShortMessage sm = (ShortMessage) message;
			//if(printDetails) System.out.print("Channel: " + sm.getChannel() + " ");
			if (sm.getCommand() == MidiDefs.NOTE_ON) {
				if(printDetails) printMidiNote(sm,"on",event);
				else printMidiNoteShort(sm);
			} else if (sm.getCommand() == MidiDefs.NOTE_OFF) {
				if(printDetails && printOff) printMidiNote(sm,"off",event);
			} else {
				System.out.println("Command:" + sm.getCommand());
			}
			
		} else if (message instanceof MetaMessage) { 
			MetaMessage mm = (MetaMessage) message;
			printMidiMeta(mm);
		} 
		else {
			System.out.println("Other message: " + message.getClass()+" = '"+message.toString()+"'");
		}
	}


	private static void printMidiMeta(MetaMessage mm) 
	{
		final byte[] b = mm.getData();
		
		System.out.print("Meta message type: " + mm.getType()+" ");
		switch(mm.getType())
		{
		case 47:	System.out.print("(stop)"); break;
		case 3:		System.out.print("(track name '"+getString(mm)+"')"); break;
		
		case 0x51:	
			long bpm = 60 * 1000 * 1000 / getInt3(mm);
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

	private static int getInt3(MetaMessage mm) 
	{
		int r = 0;
		
		byte[] b = mm.getData();
		
		r |= ((int)b[2]) & 0xFF;
		r |= (((int)b[1]) & 0xFF) << 8;
		r |= (((int)b[0]) & 0xFF) << 16;
		
		return r;
	}

	private static String getString(MetaMessage mm) {		
		return new String(mm.getData());
	}

	private static void printMidiNote(ShortMessage sm, String msg, MidiEvent event) 
	{		
		int key = sm.getData1();
		int octave = (key / 12)-1;
		int note = key % 12;
		String noteName = MidiDefs.NOTE_NAMES[note];
		int velocity = sm.getData2();
		
		if( !printOff ) {
			if (velocity == 0)
				return;
		}
		
		//System.out.print("@" + event.getTick() + " ");
		//System.out.print("@" + tickToMeasure(event.getTick()) + " ");
		
		/*
		System.out.print( tickToMeasure(event.getTick()) + " ");
		System.out.print("Channel: " + sm.getChannel() + " ");
		System.out.println("Note "+msg+", " + noteName + octave + " key=" + key + " velocity: " + velocity);
		*/
		
		if( !printOff )
			System.out.printf("%10s ch %2d  %-2s%d  vel %3d\n", tickToMeasure(event.getTick()), sm.getChannel(), noteName, octave, velocity );
		else
			System.out.printf("%10s ch %2d  note %-3s %-2s%d  vel %3d\n", tickToMeasure(event.getTick()), sm.getChannel(), msg, noteName, octave, velocity );
	}

	private static void printMidiNoteShort(ShortMessage sm) 
	{
		int key = sm.getData1();
		int octave = (key / 12)-1;
		int note = key % 12;
		String noteName = MidiDefs.NOTE_NAMES[note];
	
		System.out.print(noteName + octave + " ");
	}
	
	
	private static String tickToMeasure(long tick)
	{
		long beats = tick/ticksPerBeat;
		int extraTicks = (int) (tick % ticksPerBeat);

		int betasPerMeasure = 32/n32PerBeat;
		
		int measures = (int) (beats/betasPerMeasure);
		int extraBeats= (int) (beats%betasPerMeasure);
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("" + measures + "." + extraBeats );
		if(extraTicks != 0)
			sb.append("(+" +extraTicks+")");
		
		return sb.toString();
	}
	
}	
