package ru.dz.bluearp;

import java.io.IOException;

import ru.dz.bluearp.c.ParamInfo_t;

public class FixedParametersTable extends BlueParametersTable implements BlueDefs {

	public FixedParametersTable() {
		super("fixp", fixpDescriptors);
		setDefaultContents();
	}
	
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

	public void checkSanity() throws IOException {
		if( contents[0] != fixpDescriptors[0].getDefault() )
			throw new IOException("fixp format version incorrect: " + contents[0] );
		
	}
	
}
