package ru.dz.bluearp;

import ru.dz.bluearp.c.ParamInfo_t;

public class ProgramParametersTable extends BlueParametersTable implements BlueDefs {

	public ProgramParametersTable() {
		super("prgp", fpgpDescriptors);
		setDefaultContents();
	}
	
	static final ParamInfo_t fpgpDescriptors[] = 
		{
			// PATCH PARAMS -------------------------------------------------------------------- fpgp
			// 					Min,   Max, Step,    Dflt, Grey, PrgP,    "Name",                ListIdx,  cInt8,  Suff
			  new ParamInfo_t(  0,     127,     1, patVer,    0,    0,    "PatchVerProg",              0,  cInt8,  "" ),   // 39: cPatchVerProg
			  new ParamInfo_t(  0,      64,     1,     16,    0,    1,    "Steps",                     0,  cInt8,  "" ),   // 40: cNumSteps
			  new ParamInfo_t(  1,     125,     1,     90, -127,    1,    "Gate",                      0,  cInt8,  "%" ),  // 41: cGate
			  new ParamInfo_t(  0,      12,     1,      4, -127,    1,    "Sync",                      8,  cInt8,  "" ),   // 42: cSync
			  new ParamInfo_t(  0,       1,     1,      0,    0,    2,    "Latch",                     2,  cInt8,  "" ),   // 43: cLatch
			  new ParamInfo_t(  0,       4,     1,      0,    0,    1,    "Missing key subst.",        9,  cInt8,  "" ),   // 44: cMissing_key
			  new ParamInfo_t(  0,     127,     1,      0,    0,    5,    "Input range (low)",        -1,  cInt8,  "" ),   // 45: cInRange_Low
			  new ParamInfo_t(  0,     127,     1,    127,  127,    2,    "Input range (high)",       -1,  cInt8,  "" ),   // 46: cInRange_High
			  new ParamInfo_t(  0,     127,     1,      0,    0,    5,    "Output rng wrap (low)",    -1,  cInt8,  "" ),   // 47: cOutRange_Low
			  new ParamInfo_t(  0,     127,     1,    127,  127,    2,    "Output rng wrap (high)",   -1,  cInt8,  "" ),   // 48: cOutRange_High
			  new ParamInfo_t(  0,       7,     1,      0,    0,    1,    "Order algorithm",          10,  cInt8,  "" ),   // 49: cKeyOrderAlgo
			  new ParamInfo_t(  0,     127,     1,     64,   64,    1,    "Fixed key",                -1,  cInt8,  "" ),   // 50: cFixedKey
			  new ParamInfo_t( -3,       3,     1,      0,    0,    1,    "Master transpose oct",     11,  cInt8,  "" ),   // 51: cMTranspose_Oct
			  new ParamInfo_t(  0,       8,     1,      1,    1,    2,    "Input quantize",            4,  cInt8,  "" ),   // 52: cInQuantize
			  new ParamInfo_t(-12,      12,     1,      0,    0,    3,    "Master transpose semi",    12,  cInt8,  "" ),   // 53: cMTranspose_Semi
			  new ParamInfo_t(  0,       1,     1,      1,    0,    2,    "Page scroll",               2,  cInt8,  "" ),   // 54: cAutoScroll
			  new ParamInfo_t(-50,      50,     1,      0,    0,    1,    "Swing",                     0,  cInt8,  "%" ),  // 55: cSwing
			  new ParamInfo_t(  0,       3,     1,      0,    0,    2,    "Restart On",               13,  cInt8,  "" ),   // 56: cRestartOn
			  new ParamInfo_t(  0,      13,     1,      0,    0,    1,    "Force scale: key",         14,  cInt8,  "" ),   // 57: cForceScale_Key
			  new ParamInfo_t(  0,      14,     1,      0,    0,    1,    "Force scale: scale",       15,  cInt8,  "" ),   // 58: cForceScale_Scale
			  new ParamInfo_t(  0,       1,     1,      0,    0,    1,    "Force scale: mode",        25,  cInt8,  "" ),   // 59: cForceScale_Mode
			  new ParamInfo_t(  0,       1,     1,      0,    0,    0,    "Page lock",                 2,  cInt8,  "" ),   // 60: cPageLock
			  new ParamInfo_t(  0,  maxPag,     1,      0,    0,    0,    "Page selected",             0,  cInt8,  "" ),   // 61: cSelectedPage
			  new ParamInfo_t( -8,       8,     1,      0,    0,    0,    "Pattern shift",             0,  cInt8,  "" ),   // 62: cPatternShift
			  new ParamInfo_t(  0,     127,     1,      0,    0,    5,    "Input rng wrap (low)",     -1,  cInt8,  "" ),   // 63: cInRangeWrap_Low
			  new ParamInfo_t(  0,     127,     1,    127,  127,    2,    "Input rng wrap (high)",    -1,  cInt8,  "" ),   // 64: cInRangeWrap_High
			  new ParamInfo_t(  0,       1,     1,      0,    0,    1,    "Polyphonic octave",         2,  cInt8,  "" ),   // 65: cPolyOctave
			  new ParamInfo_t(  0,       1,     1, /*0*/1,    0,    1,    "Polyphonic key sel.",       2,  cInt8,  "" ),   // 66: cPolyKeySel
			  new ParamInfo_t(  0,       2,     1,      0,    0,    1,    "Missing key octave",       26,  cInt8,  "" ),   // 67: cMissing_key_oct
			  new ParamInfo_t(  0,       4,     1,      0,    0,    2,    "Selected layer",            0,  cInt8,  "" ),   // 68: cSelectedLayer
			  new ParamInfo_t(  0,       1,     1,      0,    0,    1,    "GATE lane mode",            0,  cInt8,  "" ),   // 69: cGateLaneMode
			  new ParamInfo_t(  0,     100,     1,      0,    0,    1,    "Randomize velocity",        0,  cInt8,  "%" ),  // 70: cRandVelo
			  new ParamInfo_t(  0,     100,     1,      0,    0,    1,    "Randomize gate per step",   0,  cInt8,  "%" ),  // 71: cRandGate
			  new ParamInfo_t(  0,     100,     1,      0,    0,    1,    "Randomize start time",      0,  cInt8,  "%" ),  // 72: cRandStart
			  new ParamInfo_t(  0,       1,     1,      0,    0,    2,    "Latch mode",               44,  cInt8,  "" ),   // 73: cLatchMode
			  new ParamInfo_t(  0,       1,     1,      1,    0,    2,    "Flt: PBend msg",           30,  cInt8,  "" ),   // 74: cFltPBend
			  new ParamInfo_t(  0,       1,     1,      1,    0,    2,    "Flt: MWheel msg",          30,  cInt8,  "" ),   // 75: cFltMWheel
			  new ParamInfo_t(  0,       1,     1,      1,    0,    2,    "Flt: ATouch msg",          30,  cInt8,  "" ),   // 76: cFltATouch
			  new ParamInfo_t(  0,       1,     1,      0,    0,    2,    "Flt: Other CC msg",        30,  cInt8,  "" ),   // 77: cFltOtherCC
			  new ParamInfo_t(  0,       3,     1,      3,    0,    2,    "Flt: Sustain msg",         40,  cInt8,  "" ),   // 78: cFltSustain
			  new ParamInfo_t(  0,       1,     1,      0,    0,    2,    "Sustain polarity",         43,  cInt8,  "" ),   // 79: cFltSusPol
			  new ParamInfo_t(-7,        7,     1,      0,    0,    1,    "Smart bend",                0,  cInt8,  "" ),   // 80: cSmartBend
			  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 81: - unused -
			  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 82: - unused -
			  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 83: - unused -
			  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 84: - unused -
			  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 85: - unused -
			  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 86: - unused -
			  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 87: - unused -
			  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 88: - unused -
			  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 89: - unused -
			  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 90: - unused -
			  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 91: - unused -
			  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 92: - unused -
			  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 93: - unused -
			  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 94: - unused -
			  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 95: - unused -
			  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 96: - unused -
			  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 97: - unused -
			  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 98: - unused -
			  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 99: - unused -
			  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 100: - unused -
			  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 101: - unused -
			  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 102: - unused -
			  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 103: - unused -
			  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 104: - unused -
			  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 105: - unused -
			  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 106: - unused -
			  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 107: - unused -
			  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 108: - unused -
			  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 109: - unused -
			  new ParamInfo_t( -128, maxChn,    1,     -1,   -1,    4,    "MetaChain_i1",              0,  cInt8,  "" ),   // 110: cMetaChain[0]
			  new ParamInfo_t( -128, maxChn,    1,     -1,   -1,    4,    "MetaChain_i2",              0,  cInt8,  "" ),   // 111: cMetaChain[1]
			  new ParamInfo_t( -128, maxChn,    1,     -1,   -1,    4,    "MetaChain_i3",              0,  cInt8,  "" ),   // 112: cMetaChain[2]
			  new ParamInfo_t( -128, maxChn,    1,     -1,   -1,    4,    "MetaChain_i4",              0,  cInt8,  "" ),   // 113: cMetaChain[3]
			  new ParamInfo_t( -128, maxChn,    1,     -1,   -1,    4,    "MetaChain_i5",              0,  cInt8,  "" ),   // 114: cMetaChain[4]
			  new ParamInfo_t( -128, maxChn,    1,     -1,   -1,    4,    "MetaChain_i6",              0,  cInt8,  "" ),   // 115: cMetaChain[5]
			  new ParamInfo_t( -128, maxChn,    1,     -1,   -1,    4,    "MetaChain_i7",              0,  cInt8,  "" ),   // 116: cMetaChain[6]
			  new ParamInfo_t( -128, maxChn,    1,     -1,   -1,    4,    "MetaChain_i8",              0,  cInt8,  "" ),   // 117: cMetaChain[7]
			  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 120: - unused -
			  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 121: - unused -
			  new ParamInfo_t(  1,  numChn,     1,      8,    8,    2,    "Num. meta-chains",          0,  cInt8,  "" ),   // 120: cNumMetaChains
			  new ParamInfo_t( -1,  maxChn,     1,     -1,   -1,    2,    "Cur_MetaChain",            21,  cInt8,  "" ),   // 121: cCurMetaChain (-1: no chain or "---")
			  new ParamInfo_t(  4,      64,     1,     16,   16,    2,    "RefLoop_Beats",             0,  cInt8,  "" ),   // 122: cRefLoopBeats
			  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 123: - unused -
			  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 124: - unused -
			  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 125: - unused -
			  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 126: - unused -
			  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 127: - unused -
		};

	
	// TODO offsets hardcoded?
	
	public void setNumberOfSteps(int nSteps) {
		contents[1] = (byte) nSteps;		
	}	
	
}
