package ru.dz.broom.midi;

import javax.sound.midi.MetaMessage;

public class MidiSignature {
	/**
	 * Numerator of song signature ( 4/? )
	 */
	private int numerator;

	/**
	 * Denominator of song signature ( ?/4 )
	 */
	private int denominator;
	
	/** 
	 * Ticks per beat.
	 * 
	 * (mult by measuresPerBeat to get ticks per measure)
	 */
	private int ticksPerBeat;
	
	/** 
	 * which part of measure beat is (usually 4th). In 32th.
	 * 
	 * Какую часть такта составляет доля (обычно 4-ю) - исчисляется в 32-х
	 */
	private int n32PerBeat;

	public MidiSignature(MetaMessage mm) {
		if(mm.getType() != 0x58)
			throw new RuntimeException("MetaMessage type is not signature");

		final byte[] b = mm.getData();

		numerator    = b[0];		// 4/
		denominator  = 1 << b[1];	// /4
		ticksPerBeat = b[2];		// NB! We rewrite it from sequence!
		n32PerBeat   = b[3];
	}

	public int getNumerator()	{	return numerator; 		}
	public int getDenominator()	{	return denominator;		}
	public int getTicksPerBeat(){	return ticksPerBeat;	}
	public int getN32PerBeat()	{	return n32PerBeat;		}

	public int getBeatsPerBar()	{	return 32/n32PerBeat;	}
	public int getTicksPerBar()	{	return ticksPerBeat * (32/n32PerBeat);		}

	/*
	public int getTicksPerDenominatorBeat(int pDenominator)	
	{	
		return getTicksPerBar() / pDenominator;		
	} */
	
	@Override
	public String toString() {
		//System.out.print("(signature "+num+"/"+den+", "); 
		//System.out.print(""+ticks+" ticks/beat, "+n32+"/32 per beat)"); 
		//return ""+numerator+"/"+denominator+", "+ticksPerBeat+" ticks/beat, "+n32PerBeat+"/32 per beat";

		//int onePerN = 32/n32PerBeat;		
		//return ""+numerator+"/"+denominator+", "+ticksPerBeat+" ticks/beat = 1/"+onePerN+", "+;

		return ""+numerator+"/"+denominator+", "+ticksPerBeat+" ticks/beat = 1/"+getBeatsPerBar()+", "+getTicksPerBar()+" ticks/bar";
	}

	
	/*
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}*/

	private MidiSignature() {
		numerator = 4;
		denominator = 4;
		ticksPerBeat = 48;
		n32PerBeat = 8; // 1/4 = 8/32
	}

	/**
	 * @return Default signature: 4/4, beat is 1/4 and 48 ticks/beat
	 */
	public static MidiSignature getDefault() {
		return new MidiSignature();
	}

	public void setTicksPerBeat(int ticksPerBeat) {
		this.ticksPerBeat = ticksPerBeat;
	}
	
}
