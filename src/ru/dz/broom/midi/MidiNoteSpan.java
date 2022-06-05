package ru.dz.broom.midi;


/**
 * 
 * Defines note, start position and length
 * 
 * @author dz
 *
 */

public class MidiNoteSpan extends GenericNote 
{
	private long startTick = -1;
	private long endTick = -1;
	
	public MidiNoteSpan(MidiNote note, long startTick) {
		super(note);
		
		this.startTick  = startTick;
		
		if(!note.isOn())
			throw new RuntimeException("note span must start at note on");
	}
	
	public MidiNoteSpan(MidiNoteSpan s) {
		super(new GenericNote(s));
		startTick = s.startTick;
		endTick = s.endTick;
	}

	// TODO we lose not off velocity
	public void setEndTick(long tick) {
		endTick = tick;		
	}
	
	public boolean isComplete() { return (startTick != -1) && (endTick != -1); }
	
	private void checkComplete() { if(!isComplete()) throw new RuntimeException("incomplete note span"); }
	
	public long getTicks() { checkComplete(); return endTick - startTick; }

	public long getStartTick() { return startTick; }

	public void shiftLeftBars(int bars, MidiSignature sig) 
	{
		checkComplete();
		
		int tickPerBar = sig.getTicksPerBar();
		
		startTick -= (bars * tickPerBar);
		endTick -= (bars * tickPerBar);
	}

	
	/**
	 * Get semitone difference of this note to C2 (note C of 2nd octave)
	 * @return
	 */
	public int getNoteDiffToC() {
		// note value itself is difference to C, so we need just add an octave
		// our octave value of '3' equals to Cubase's 2nd octave for some reason
		
		int oct = getOctave() - 3;
		
		return getNote() + oct * 12;
	}


}
