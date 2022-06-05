package ru.dz.broom.midi;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
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
	
	private Map<TickAndKey,MidiNoteSpan> spans = new TreeMap<>();

	public void add(MidiNoteSpan s)
	{
		TickAndKey tk = new TickAndKey(s, s.getStartTick());
		spans.put(tk,s);
	}

	public int size() {
		return spans.size();
	}

	public void dump(MidiSignature sig) {
		spans.forEach( (tk,s) -> {
			MidiBarBeat mbb = new MidiBarBeat(tk.getTick(),32,sig); 
			//System.out.println("Span "+s+" @"+mbb);
			System.out.println(""+s+" @"+mbb);

		});
		
	}

	int maxBar(MidiSignature sig)
	{
		int [] maxBar = {-1};
		spans.forEach( (tk,s) -> {
			MidiBarBeat mbb = new MidiBarBeat(tk.getTick(),32,sig); 
			//System.out.println("Span "+s+" @"+mbb);
			//System.out.println(""+s+" @"+mbb);
			
			maxBar[0] = Math.max(maxBar[0], mbb.getBar());

		});
		
		return maxBar[0];
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

	public long getTick() {		return tick;	}

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
		
		if(tick != o.tick)
		{
			if( tick > o.tick ) return 1;
			return -1;
		}
		
		return key - o.key;
	}

	
	
}