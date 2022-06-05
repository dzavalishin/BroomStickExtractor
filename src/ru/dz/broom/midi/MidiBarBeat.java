package ru.dz.broom.midi;

public class MidiBarBeat {

	/** Bar of this event */
	private int bars;
	/** Beat of this event */
	private int beats;
	/** Extra ticks of this event */
	private int extraTicks;
	private int sliceSize;

	/**
	 * 
	 * Reprsents tick as measure (bar) number, beat
	 * (of a given size) number and extra ticks.
	 * 
	 * @param tick Midi tick (value)
	 * @param beatDenominator Size of beat (1/32, 1/16, etc)
	 * @param Global midi signature
	 */
	
	public MidiBarBeat(long tick, int beatDenominator, MidiSignature sig) {
		sliceSize = beatDenominator; 

		int ticksPerSlice = sig.getTicksPerBeat() / sig.getN32PerBeat();
		
		ticksPerSlice *= 32;
		ticksPerSlice /= sliceSize;
		
		long slices = tick/ticksPerSlice;
		extraTicks = (int) (tick % ticksPerSlice);
		
		
		bars = (int) (slices/sliceSize);
		beats= (int) (slices%sliceSize);
	}
	
	private String tickTo32nd(long tick)
	{
		MidiSignature sig = midiParser.getSignature();
		
		//int sliceSize = 32; // 1/32
		int sliceSize = 16; // 1/32
		int ticksPerSlice = sig.getTicksPerBeat() / sig.getN32PerBeat();
		
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

	}
	
	@Override
	public String toString() {
		String extra = (extraTicks != 0) ? (" (+" +extraTicks+")") : "";

		return String.format("%3d.%2d /%d %6s", bars, beats, sliceSize, extra );
	}
	
}