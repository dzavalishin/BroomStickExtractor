package ru.dz.broom.midi;

import java.util.Set;
import java.util.TreeSet;

/**
 * 
 * 
 * Stores note spans at tick/key pair position
 * 
 * @author dz
 *
 */

public class SpanSet {
	
	private Set<TickAndKey> spans = new TreeSet<>();

	public void add(MidiNoteSpan s)
	{
		
	}
	
	
	
}



class TickAndKey implements Comparable<TickAndKey>
{
	private int key;
	private long tick;

	public TickAndKey(GenericNote note, long tick) {
		key = note.getKey();
		this.tick = tick;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TickAndKey) {
			TickAndKey him = (TickAndKey) obj;

			return (him.key == key) && (him.tick == tick); 
		}
		return super.equals(obj);
	}

	@Override
	public int compareTo(TickAndKey o) {
		
		if(o.tick != tick)
		{
			if( o.tick > tick ) return 1;
			return -1;
		}
		
		return o.key - key;
	}

	
	
}