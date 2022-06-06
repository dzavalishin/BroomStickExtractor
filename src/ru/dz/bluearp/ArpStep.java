package ru.dz.bluearp;

import ru.dz.broom.midi.MidiNoteSpan;

/** 
 * 
 * BlueArp sequence step definition.
 * 
 * @author dz
 *
 */
public class ArpStep 
{
	private int scaleStep;
	private int octave;
	private int key;
	private int velocity;
	private int gateTime;
	private int type;

	// 0 = off, 1 = norm, ? rest, tie, chord, random
	public static final int TYPE_OFF = 0;
	public static final int TYPE_NORM = 1;
	public static final int TYPE_REST = 2;
	public static final int TYPE_TIE = 3;
	public static final int TYPE_CHORD = 4;
	public static final int TYPE_RANDOM = 5;
	
	// bits, 1 = fixed, 2 = root, 4 = k1, 8 = k2 ...
	public static final int KEY_FIXED = 1 << 0;
	public static final int KEY_ROOT  = 1 << 1;
	public static final int KEY_K1    = 1 << 2;
	public static final int KEY_K2    = 1 << 3;
	public static final int KEY_K3    = 1 << 4;
	public static final int KEY_K4    = 1 << 5;
	public static final int KEY_K5    = 1 << 6;
	
	public static final int MAX_KEYS = 5;
	
	public ArpStep() {
		// TODO Auto-generated constructor stub
		octave = 8;
		type = TYPE_OFF; // default entry is off
		key = KEY_K1;
	}
	
	public ArpStep(MidiNoteSpan note) {
		type = TYPE_NORM;
		key = KEY_K1;
		velocity = note.getVelocity();
		scaleStep = note.getNoteDiffToC();
		// TODO
		//System.out.printf("Note %s step %d\n", note, scaleStep);
	}

	/**
	 * 
	 * Used when we convert polyphonic MIDI part to BlueArp program.
	 * 
	 * @param note next note to put in.
	 */
	
	public void mergeIn(MidiNoteSpan note) 
	{
		// First note?		
		if(type == TYPE_OFF)
		{
			type = TYPE_NORM;
			//key = KEY_K1;
			velocity = note.getVelocity();
			//scaleStep = note.getNoteDiffToC();
		}
		
		// Use assigned number
		key = KEY_K1 << note.getBlueKey();

		// TODO
		System.out.printf("Note poly %d: %s step %d\n", note.getBlueKey(), note, scaleStep);
	}
	
	
	public void loadBinary(int stepType, byte val) 
	{
		int v = val & 0xFF;
		switch(stepType)
		{
		case 0:
			scaleStep = v;
			break;
		case 1:
			octave = v; // bits? 4 = -1, 8 = 0, 16 = +1
			break;
		case 2:
			type = v; // 0 = off, 1 = norm, ? rest, tie, chord, random
			break;
		case 3:
			key = v; // bits, 1 = fixed, 2 = root, 4 = k1, 8 = k2 ...
			break;
		case 4:
			velocity = v;
			break;
		case 5:
			gateTime = v;
			break;

		default:
			System.err.printf("unknown step type %d", stepType);
			break;
		}

	}

	public int getBinary(int stepType) {
		switch(stepType)
		{
		case 0:			return scaleStep;
		case 1:			return octave;		// bits? 4 = -1, 8 = 0, 16 = +1
		case 2:			return type;		// 0 = off, 1 = norm, ? rest, tie, chord, random
		case 3:			return key; 		// bits, 1 = fixed, 2 = root, 4 = k1, 8 = k2 ...
		case 4:			return velocity;
		case 5:			return gateTime;

		default:
			System.err.printf("unknown step type %d", stepType);
			return 0;
		}
	}
	
	
	public void dump() {
		System.out.printf("\t step %3d %3d %3d %3d %3d %3d\n", 
				scaleStep, octave, type, key, velocity, gateTime
				);
		
	}



}
