package ru.dz.broom.midi;
import java.util.Map;
import java.util.TreeMap;

import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class MidiTrackParser 
{
	private static final boolean debugPrint = false;
	private static final boolean printDetails = true;
	private static final boolean printOff = false;
	
	private int trackNumber;
	private MidiParser midiParser;
	private String name = null;
	

	public MidiTrackParser(int trackNumber, MidiParser midiParser) {
		this.trackNumber = trackNumber;
		this.midiParser = midiParser;
	}


	public void parse(Track track) {
		if(debugPrint) System.out.println("Track " + trackNumber + ": size = " + track.size());
		if(debugPrint) System.out.println();
		for (int i=0; i < track.size(); i++) 
		{ 
			MidiEvent event = track.get(i);
			parseMidiEvent(event);
		}

		if(debugPrint) System.out.println();
	}

	
	private void parseMidiEvent(MidiEvent event) 
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


	private void parseShortMessage(MidiEvent event, ShortMessage sm) {
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


	private void parseMidiMeta(MetaMessage mm) 
	{
		final byte[] b = mm.getData();
		
		if(debugPrint) System.out.print("Meta message type: " + mm.getType()+" ");
		switch(mm.getType())
		{
		case 47:	
			if(debugPrint) System.out.print("(stop)"); 
			break;
		case 3:		
		{
			String mmName = MidiTools.getString(mm);
			if( trackNumber == 0 )
			{
				midiParser.setPresetName(mmName);
				if(debugPrint) System.out.print("(preset name '"+mmName+"')");
			}
			else
			{
				this.name = mmName;
				if(debugPrint) System.out.print("(track name '"+name+"')");
			}
		}
			break;
		
		case 0x51:	
			long bpm = 60 * 1000 * 1000 / MidiTools.getInt3(mm);
			System.out.print("(tempo "+bpm+" BPM)"); 
			break;
		
		case 0x58:	
			MidiSignature ms = new MidiSignature(mm);			
			System.out.print(ms.toString()); 
			midiParser.setSignature(ms);
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


	private SpanSet ss = new SpanSet();
	private Map<Integer,MidiNoteSpan> playingNotes = new TreeMap<>();
	
	private void parseMidiNote(ShortMessage sm, MidiEvent event) 
	{
		MidiNote note = new MidiNote(sm);
		long tick = event.getTick();

		MidiBarBeat mbb = new MidiBarBeat(tick, 32, midiParser.getSignature());
		String ttm = mbb.toString();
		
		if (note.isOn())
		{
			MidiNoteSpan span = new MidiNoteSpan(note, tick);
			
			//checkNotePlays(note);
			
			playingNotes.put(note.getKey(), span);
		}
		else
		{
			MidiNoteSpan span = playingNotes.get(note.getKey());
			
			if(span.getChannel() != note.getChannel())
				System.err.printf("off for diff channel: %s @%s\n", note.toString(), ttm );
			
			if(span != null)
			{
				span.setEndTick(tick);
				playingNotes.remove(note.getKey());
				ss.add(span);
			}
			else
				System.err.printf("off for no on: %s @%s\n", note.toString(), ttm );
		}
		
		if(!debugPrint) return;
		
		if( !printOff ) 
			if (note.isOff())	
				return;		
		
		
		if(printDetails)
			System.out.printf("%10s %s\n", ttm, note.toString() );
		else
			System.out.print(note.toShortString() + " ");
	}


	public void dump() 
	{
		if(null == name) return; // track 0 is for set up only, no events 
		
		if("Metronome".equals(name)) return;
		if("M/W".equals(name)) return; // mod wheel
		
		System.out.println("Track "+name+", "+ss.size()+" events");
		ss.dump(midiParser.getSignature());

		
	}

	
}
