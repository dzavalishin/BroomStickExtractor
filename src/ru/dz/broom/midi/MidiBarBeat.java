package ru.dz.broom.midi;

public class MidiBarBeat {

	/** Bar of this event */
	private int bar;
	/** Beat of this event */
	private int beat;
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

		/*
		int ticksPerSlice = sig.getTicksPerBeat() / sig.getN32PerBeat();
		
		ticksPerSlice *= 32;
		ticksPerSlice /= sliceSize;
		
		long slices = tick/ticksPerSlice;
		extraTicks = (int) (tick % ticksPerSlice);
		
		
		bar  = (int) (slices/sliceSize);
		beat = (int) (slices%sliceSize);
		*/
		
		bar    = (int) (tick / sig.getTicksPerBar());
		int be = (int) (tick % sig.getTicksPerBar());
		
		/*
		int tpb = sig.getTicksPerBeat();
		
		tpb *= sig.getBeatsPerBar();
		tpb /= sliceSize;
		*/
		
		int tpb = sig.getTicksPerBar() / sliceSize;
		
		beat       = (int) (be / tpb);
		extraTicks = (int) (be % tpb);
	}
	
	
	@Override
	public String toString() {
		String extra = (extraTicks != 0) ? (" (+" +extraTicks+")") : "";

		return String.format("%3d.%2d /%d %6s", bar, beat, sliceSize, extra );
	}


	public int getBar() {
		return bar;
	}
	
}
