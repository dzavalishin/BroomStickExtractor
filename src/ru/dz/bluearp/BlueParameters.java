package ru.dz.bluearp;

import ru.dz.bluearp.c.ParamInfo_t;

public class BlueParameters 
{

	// ***********************************************
	// MAIN PARAMETER ARRAY (MIN, MAX, Default, Grey, ProgPar -PBGC 01234, Name, ListIdx, Polyphonic
	// ProgPar 0-hidden/unused, 1-Prog, 2-Bank, 3-Project (HW only), 4-Chain, 5-Bank/no label, 6-Global/INI
	// When ProgPar=5, it's bank-related, low value, the next value is high
	// ***********************************************
	// unused params should have Max = 0

	static final int cInt8 = -1; // TODO find val
	static final int cBit8 = -1; // TODO find val
	
	static final int patVer = -1; // TODO find val
	
	static final int maxPrg = -1; // TODO find val
	static final int maxBar = -1; // TODO find val
	static final int numChn = -1; // TODO find val
	static final int maxChn = -1; // TODO find val
	
	static final int maxPag = -1; // TODO find val

	
	static final ParamInfo_t fixpDescriptors[] = 
		{
			// FIXED PARAMS (PATCH INDEPENDENT) ------------------------------------------------ fixp
			// 					Min,   Max, Step,    Dflt, Grey, PrgP,    "Name",                ListIdx,  cInt8,  Suff
			  new ParamInfo_t(  0,     127,     1, patVer,    0,    0,    "PatchVer",                  0,  cInt8,  "" ),   // 0: cPatchVer
			  new ParamInfo_t( -1,      15,     1,     -1,   -1,    2,    "MIDI In Channel",           1,  cInt8,  "" ),   // 1: cMIDIIn_Ch
			  new ParamInfo_t(  0,      15,     1,      0,    0,    2,    "MIDI Out Channel",         16,  cInt8,  "" ),   // 2: cMIDIOut_Ch
			  new ParamInfo_t(  0,       5,     1,      0,    0,    2,    "MIDI In Port",             32,  cInt8,  "" ),   // 3: cMIDIIn_Port  (patchVer16: cArpOn)
			  new ParamInfo_t(  0,      13,     1,      0,    0,    2,    "MIDI Out Port",            33,  cInt8,  "" ),   // 4: cMIDIOut_Port (patchVer16: cMidiThruOn)
			  new ParamInfo_t(  0,       6,     1,      1,    0,    2,    "Arp Mode",                 28,  cInt8,  "" ),   // 5: cArpMode
			  new ParamInfo_t(  0,       0,     1,      0,    0,    2,    "",                          0,  cInt8,  "" ),   // 6: - unused -
			  new ParamInfo_t(  0,     120,     1,    120,    0,    2,    "NumPrograms",              -3,  cInt8,  "" ),   // 7: cNumPrograms (ListIdx=-3 for special logic)
			  new ParamInfo_t(  0,  maxPrg,     1,      0,   -1,    2,    "CurProgram",                0,  cInt8,  "" ),   // 8: cCurProgram
			  new ParamInfo_t(  0,  maxBar,     1,      3,    0,    2,    "SelectedBar",              39,  cInt8,  "" ),   // 9: cSelectedBar
			// chain-related fixed params ------------------------------------------------------
			  new ParamInfo_t(  1,  numChn,     1,      8,    8,    2,    "Num. chains",               0,  cInt8,  "" ),   // 10: cNumChains
			  new ParamInfo_t( -1,  maxChn,     1,     -1,   -1,    2,    "Cur_Chain",                21,  cInt8,  "" ),   // 11: cCurChain (-1: no chain or "---")
			  new ParamInfo_t(  0,       8,     1,      5,    5,    2,    "Chain quantize",            4,  cInt8,  "" ),   // 12: cChainQuantize
			// GUI Options ---------------------------------------------------------------------
			  new ParamInfo_t(  0,       1,     1,      0,    0,    2,    "Fine velocity adj.",        5,  cInt8,  "" ),   // 13: cFineVelocity
			  new ParamInfo_t(  0,       3,     1,      0,    0,    2,    "Semitone range",            6,  cInt8,  "" ),   // 14: cScaleStepRange
			  new ParamInfo_t(  0,       2,     1,      0,    0,    2,    "Prog.chng msg",             7,  cInt8,  "" ),   // 15: cFltProgCC
			  new ParamInfo_t(  0,       1,     1,      0,    0,    2,    "Input range mode",         22,  cInt8,  "" ),   // 16: cInRangeTruncMode
			  new ParamInfo_t(  0,       2,     1,      0,    0,    2,    "Output velo mode",         23,  cInt8,  "" ),   // 17: cOutVeloMode
			  new ParamInfo_t(  0,       2,     1,      0,    0,    2,    "Send bank msg",            24,  cInt8,  "" ),   // 18: cSendBankMsg
			  new ParamInfo_t(  0,       1,     1,      0,    0,    5,    "Chain restart on sw.",      2,  cInt8,  "" ),   // 19: cChainRestart
		};

	
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
			  new ParamInfo_t(  0,       1,     1,      0,    0,    2,    "Page scroll",               2,  cInt8,  "" ),   // 54: cAutoScroll
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
			  new ParamInfo_t(  0,       1,     1,      0,    0,    1,    "Polyphonic key sel.",       2,  cInt8,  "" ),   // 66: cPolyKeySel
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
	
	
	
	
	static final ParamInfo_t chnpDescriptors[] = 
		{
	// Chain-specific ------------------------------------------------------------------ chnp
	// 					Min,   Max, Step,    Dflt, Grey, PrgP,    "Name",                ListIdx,  cInt8,  Suff
	  new ParamInfo_t(  0,       1,     1,      1,    0,    0,    "ChainInitFlag",             0,  cInt8,  "" ),   // 20: cChainInitFlag
	  new ParamInfo_t( -1,     127,     1,     -1,   -1,    4,    "SendPatchNum",              3,  cInt8,  "" ),   // 21: cSendPatchNum
	  new ParamInfo_t( -1,     127,     1,     -1,   -1,    4,    "SendBankNum",               3,  cInt8,  "" ),   // 22: cSendBankNum
	  new ParamInfo_t( -1,     127,     1,     -1,   -1,    4,    "SendVolume",                3,  cInt8,  "" ),   // 23: cSendVolume
	  new ParamInfo_t( -1,      18,     1,     -1,   -1,    4,    "Next_Chain",               31,  cInt8,  "" ),   // 24: cNextChain (-1: "---")
	  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 25: - unused -
	  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 26: - unused -
	  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 27: - unused -
	  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 28: - unused -
	  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 29: - unused -
	  new ParamInfo_t(  0,       0,     1,      0,    0,    0,    "",                          0,  cInt8,  "" ),   // 30: - unused -
	  new ParamInfo_t( -1,  maxPrg,     1,     -1,   -1,    4,    "ChainProg_01",             17,  cInt8,  "" ),   // 31: cChainSeq[0]
	  new ParamInfo_t( -1,  maxPrg,     1,     -1,   -1,    4,    "ChainProg_02",             17,  cInt8,  "" ),   // 32: cChainSeq[1]
	  new ParamInfo_t( -1,  maxPrg,     1,     -1,   -1,    4,    "ChainProg_03",             17,  cInt8,  "" ),   // 33: cChainSeq[2]
	  new ParamInfo_t( -1,  maxPrg,     1,     -1,   -1,    4,    "ChainProg_04",             17,  cInt8,  "" ),   // 34: cChainSeq[3]
	  new ParamInfo_t( -1,  maxPrg,     1,     -1,   -1,    4,    "ChainProg_05",             17,  cInt8,  "" ),   // 35: cChainSeq[4]
	  new ParamInfo_t( -1,  maxPrg,     1,     -1,   -1,    4,    "ChainProg_06",             17,  cInt8,  "" ),   // 36: cChainSeq[5]
	  new ParamInfo_t( -1,  maxPrg,     1,     -1,   -1,    4,    "ChainProg_07",             17,  cInt8,  "" ),   // 37: cChainSeq[6]
	  new ParamInfo_t( -1,  maxPrg,     1,     -1,   -1,    4,    "ChainProg_08",             17,  cInt8,  "" ),   // 38: cChainSeq[7]
	  
	};

	/*
	//static final ParamInfo_t CParam2[gNumValueBars] = {
	static final ParamInfo_t CParam2[] = {
	// STEP-RELATED PARAMS -------------------------------------------------------------
	  new ParamInfo_t(-12,      12,     1,      0,    0,    1,    "ScaleStep_",                0,  cInt8,  "" ),   // 128: cScaleStep
	  new ParamInfo_t( -3,       3,     1,      0,    0,    1,    "Octave_",                   0,  cBit8,  "" ),   // 192: cOctave    / contains mono value!
	  new ParamInfo_t(  0,       5,     1,      1,    0,    1,    "StepType_",                18,  cInt8,  "" ),   // 256: cStepType
	  new ParamInfo_t( -1,       5,     1,      1,    1,    1,    "KeySelect_",               19,  cBit8,  "" ),   // 320: cKeySelect / contains mono value!
	  new ParamInfo_t(  0,     127,     1,     96,   96,    1,    "Velocity_",                 0,  cInt8,  "" ),   // 384: cVelocity, Fine
	  new ParamInfo_t( -3,       5,     1,      0,    0,    1,    "GateStep_",                20,  cInt8,  "" ),   // 448: cGateStep, default mode
	}; */

	
	public static void dumpWithDescriptor(String msg, byte[] data, ParamInfo_t[] desc) {
		int displ = 0;
		int nParam = 0;
		
		System.out.println(msg);
		
		while(displ < data.length)
		{
			desc[nParam].dump(data[displ]);
			
			displ++;
			nParam++;
		}
		
	}
	
}
