package ru.dz.bluearp.c;

public class ParamInfo_t {

	private int min;
	private int max;
	private int step;
	private int deflt;
	private int grey;
	private int prg_p;
	private String name;
	private int listIdx;
	private int cInt8;
	private String suff;

	public ParamInfo_t(int min, int max, int step, int deflt, int grey, int prg_p, String name, int listIdx, int cInt8, String suff) {
		this.min = min;
		this.max = max;
		this.step = step;
		this.deflt = deflt;
		this.grey = grey;
		this.prg_p = prg_p;
		this.name = name;
		this.listIdx = listIdx;
		this.cInt8 = cInt8;
		this.suff = suff;
	}

	public void loadFromFxp()
	{
		
	}

	public void dump(int param) {
		System.out.printf("\t%s = %d%s\n", name, param, suff);
		
	}
	
}
