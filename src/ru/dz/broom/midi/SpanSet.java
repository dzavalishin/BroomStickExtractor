package ru.dz.broom.midi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiConsumer;

import ru.dz.bluearp.ArpStep;

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
		//TickAndKey tk = new TickAndKey(s, s.getStartTick());
		TickAndKey tk = new TickAndKey(s);
		spans.put(tk,s);
	}

	public int size() {
		return spans.size();
	}

	public void dump(MidiSignature sig) {
		spans.forEach( (tk,s) -> {
			MidiBarBeat mbb = new MidiBarBeat(tk.getTick(), 64, sig); 
			//System.out.println("Span "+s+" @"+mbb);
			System.out.println(""+s+" @"+mbb);

		});

	}

	public int maxBar(MidiSignature sig)
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

	/**
	 * 
	 * @param parts split self in such a number of parts
	 * @param nPart return this part
	 * @param sig 
	 * @return Copy of part of self
	 */
	public SpanSet split(int parts, int nPart, MidiSignature sig) {
		int bars = 1 + maxBar(sig);
		int barsPerPart = bars / parts;

		int startBar = barsPerPart * nPart; // >=
		int endBar = startBar + barsPerPart;  // <

		SpanSet ret[] = { new SpanSet() };

		spans.forEach( (tk,s) -> {
			MidiBarBeat mbb = new MidiBarBeat(tk.getTick(),32,sig);
			int bar = mbb.getBar();

			MidiNoteSpan ns = new MidiNoteSpan(s);
			ns.shiftLeftBars(startBar,sig);
			TickAndKey ntk = new TickAndKey(ns);

			if( (bar >= startBar) && (bar < endBar) )
				ret[0].spans.put(ntk, ns);

			//MidiBarBeat mbb = new MidiBarBeat(tk.getTick(), 64, sig); 
			//System.out.println(""+ns+" @"+mbb);

		});


		return ret[0];
	}


	private final List<Integer> blueKeyToNote = new ArrayList<>();

	/**
	 * 
	 * Some notes overlap, so we will convert to polyphonic
	 * BlueArp pattern. Need to assign a K1...K5 number to each note
	 * and generate clue for a music composer which chords to feed in.
	 * 
	 */

	public void assignNotesToKeys() 
	{
		spans.forEach( (tk,s) -> {
			int k = s.getKey();
			if(!blueKeyToNote.contains(k))
			{
				blueKeyToNote.add(k);
			}
		});

		if(blueKeyToNote.size() > ArpStep.MAX_KEYS)
			System.err.println("Too many notes in chords: "+blueKeyToNote.size());

		spans.forEach( (tk,s) -> {
			int k = s.getKey();
			int index = blueKeyToNote.indexOf(k);
			if(index < 0)
				throw new RuntimeException("Cant find key in blueKeyToNote");
			s.setBlueKey(index);
		});
	}

	public void forEach(MidiSignature sig, BiConsumer<MidiBarBeat,MidiNoteSpan> c) {
		spans.forEach( (tk,s) -> {
			MidiBarBeat mbb = new MidiBarBeat(tk.getTick(), 64, sig); 
			c.accept(mbb,s);
		});

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

	public TickAndKey(MidiNoteSpan s) {
		key = s.getKey();
		this.tick = s.getStartTick();
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