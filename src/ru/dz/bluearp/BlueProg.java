package ru.dz.bluearp;

import ru.dz.bintools.BinFileIO;

public class BlueProg 
{
	private String name = "(unnamed)";
	private ArpStep steps[] = new ArpStep[64]; // TODO const 
	

	public void decode(byte[] data) 
	{
		int displ = 0;

		while(displ < data.length)
		{
			String name = BinFileIO.read4c(data,displ);
			int size = BinFileIO.readInt(data,displ+4);

			//System.out.printf("\tprog chunk %s size %d\n", name, size);

			byte[] buf = new byte[size];
			
			System.arraycopy(data, displ+8, buf, 0, buf.length);


			decodeChunk(name,buf);
			
			displ += 8 + size;
		}
		
		//dump();
	}

	private void dump() {
		System.out.printf("Program '%s'\n", name);
		for( ArpStep as : steps )
			as.dump();
		
	}

	private void decodeChunk(String name, byte[] data) {
		if( "pgnm".equals(name) )		{ decodePgnm(data); return; }
		if( "prgp".equals(name) )		{ decodePrgp(data); return; }
		
		if( "stp0".equals(name) )		{ decodeStep(data, 0); return; }
		if( "stp1".equals(name) )		{ decodeStep(data, 1); return; }
		if( "stp2".equals(name) )		{ decodeStep(data, 2); return; }
		if( "stp3".equals(name) )		{ decodeStep(data, 3); return; }
		if( "stp4".equals(name) )		{ decodeStep(data, 4); return; }
		if( "stp5".equals(name) )		{ decodeStep(data, 5); return; }
		
		System.out.printf("Unknown chunk %s size %d\n", name, data.length);
		BinFileIO.dump(name+" ", data);		
	}

	private void decodePrgp(byte[] data) {
		
		BlueParameters.dumpWithDescriptor("Prgp ", data, BlueParameters.fpgpDescriptors);

	}

	private void decodeStep(byte[] data, int stepType) {
		for( int i = 0; i < data.length; i++)
		{
			if(steps[i] == null) steps[i] = new ArpStep();
			
			steps[i].loadBinary(stepType, data[i]);
		}
	}

	private void decodePgnm(byte[] data) {
		name  = BinFileIO.readZeroString(data);
		//System.out.printf("pgnm '%s'\n", name);
	}

}
