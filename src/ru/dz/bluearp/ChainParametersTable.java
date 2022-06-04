package ru.dz.bluearp;

import ru.dz.bluearp.c.ParamInfo_t;

public class ChainParametersTable extends BlueParametersTable implements BlueDefs {

	public ChainParametersTable() {
		super("chnp", chnpDescriptors);
		setDefaultContents();
	}

	
	
	
	
	
	private static final ParamInfo_t chnpDescriptors[] = 
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
	
	
}
