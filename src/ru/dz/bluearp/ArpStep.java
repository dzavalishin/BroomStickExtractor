package ru.dz.bluearp;

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
	private int parm2;
	private int key;
	private int velocity;
	private int gateTime;

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
			stepType = v; // 0 = off, 1 = norm, ? rest, tie, chord, random
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

	public void dump() {
		System.out.printf("\t step %3d %3d %3d %3d %3d %3d\n", 
				scaleStep, octave, parm2, key, velocity, gateTime
				);
		
	}

}
