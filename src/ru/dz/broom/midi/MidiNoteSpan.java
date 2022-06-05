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
	
	// TODO we lose off velocity
	public void setEndTick(long tick) {
		endTick = tick;		
	}
	
	public boolean isComplete() { return (startTick != -1) && (endTick != -1); }
	
	private void checkComplete() { if(!isComplete()) throw new RuntimeException("incomplete note span"); }
	
	public long getTicks() { checkComplete(); return endTick - startTick; }

	public long getStartTick() { return startTick; }


}
