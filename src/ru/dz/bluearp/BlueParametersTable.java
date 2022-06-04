package ru.dz.bluearp;

import ru.dz.bluearp.c.ParamInfo_t;

/**
 * 
 * Represents generic BlueArp parameters set.
 * 
 * @author dz
 *
 */

public class BlueParametersTable 
{
	private String id4cc;
	private ParamInfo_t[] def;
	protected byte[] contents;


	public BlueParametersTable(String id4cc, ParamInfo_t[] def ) {
		this.id4cc = id4cc;
		this.def = def;
	}
	
	protected int getBinarySize()
	{
		// TODO supposed that all fields are byte size!
		return def.length;
	}
	
	protected byte[] newArray()
	{
		return new byte[getBinarySize()];
	}
	
	protected byte[] generateDefault()
	{
		byte[] data = newArray();
		int pos = 0;
		for(ParamInfo_t p : def)
		{
			data[pos++] = (byte)p.getDefault();
		}
		return data;
	}
	
	
	public byte[] getContents() {
		return contents;
	}

	public void setContents(byte[] contents) {
		this.contents = contents;
	}
	
	public void setDefaultContents() {
		this.contents = generateDefault();
	}
	
	
	
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
	
	public void dump(String msg) {
		dumpWithDescriptor( msg, contents, def);
	}
	
}
