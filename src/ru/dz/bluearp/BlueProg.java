package ru.dz.bluearp;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

import ru.dz.bintools.BinFileIO;
import ru.dz.bintools.ChunkOutputStream;
import ru.dz.broom.midi.MidiSignature;
import ru.dz.broom.midi.SpanSet;

public class BlueProg 
{
	public static final int N_PROG_STEPS = 64;
	private static final int PROGRAM_NAME_LEN = 16*3;
	
	private String name = "(unnamed)";
	private ArpStep steps[] = new ArpStep[N_PROG_STEPS]; 
	private ProgramParametersTable param = new ProgramParametersTable();
	
	//private byte[] prgpData;


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

	public void dump() {
		System.out.printf("Program '%s'\n", name);
		for( ArpStep as : steps )
		{
			if(as != null )
				as.dump();
			else
				System.out.println("(null)");
		}

	}

	private void decodeChunk(String name, byte[] data) {
		if( "pgnm".equals(name) )		{ decodePgnm(data); return; }
		if( "prgp".equals(name) )		{ decodePrgp(data); return; }
		//if( "fpgp".equals(name) )		{ decodePrgp(data); return; }

		if( "stp0".equals(name) )		{ decodeStep(data, 0); return; }
		if( "stp1".equals(name) )		{ decodeStep(data, 1); return; }
		if( "stp2".equals(name) )		{ decodeStep(data, 2); return; }
		if( "stp3".equals(name) )		{ decodeStep(data, 3); return; }
		if( "stp4".equals(name) )		{ decodeStep(data, 4); return; }
		if( "stp5".equals(name) )		{ decodeStep(data, 5); return; }

		System.out.printf("Unknown chunk %s size %d\n", name, data.length);
		BinFileIO.dump(name+" ", data);		
	}

	private void decodePrgp(byte[] data) 
	{
		// TODO
		//BlueParameters.dumpWithDescriptor("Prgp ", data, BlueParameters.fpgpDescriptors);
		//prgpData = data;
		param.setContents(data);
		//param.dump("Program parameters");
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

	public void writeTo(DataOutput _dos) throws IOException 
	{
		/*
		ByteArrayOutputStream storeBos = new ByteArrayOutputStream();
		//BufferedOutputStream bos = new BufferedOutputStream( new FileOutputStream(fileName) );
		DataOutputStream store = new DataOutputStream(storeBos);
		 */
		
		ChunkOutputStream storeChunk = new ChunkOutputStream();
		DataOutputStream store = storeChunk.getStream();
		
		
		// Program contains:

		//     pgnm - program name
		//     prgp - program-related params
		//     stp0 - step-related params: SCALE STEP
		//     stp1 - step-related params: OCTAVE
		//     stp2 - step-related params: STEP TYPE
		//     stp3 - step-related params: KEY SELECT
		//     stp4 - step-related params: VELOCITY
		//     stp5 - step-related params: GATE TIME		 * 

		//BinFileIO.writeChunk( store, "prgp", prgpData, BlueParameters.fpgpDescriptors.length );
		BinFileIO.writeChunk( store, "prgp", param.getContents(), 0 );
		
		BinFileIO.writeFixedStringChunk( store, "pgnm", name, PROGRAM_NAME_LEN );		

		BinFileIO.writeChunk( store, "stp0", getStepsArray(0), N_PROG_STEPS );
		BinFileIO.writeChunk( store, "stp1", getStepsArray(1), N_PROG_STEPS );
		BinFileIO.writeChunk( store, "stp2", getStepsArray(2), N_PROG_STEPS );
		BinFileIO.writeChunk( store, "stp3", getStepsArray(3), N_PROG_STEPS );
		BinFileIO.writeChunk( store, "stp4", getStepsArray(4), N_PROG_STEPS );
		BinFileIO.writeChunk( store, "stp5", getStepsArray(5), N_PROG_STEPS );

		/*
		byte[] progChunkData = storeBos.toByteArray();
		BinFileIO.writeChunk(_dos, "prog", progChunkData, 0);
		*/
		storeChunk.writeChunk(_dos, "prog");
	}

	private byte[] getStepsArray(int stepType) {
		byte[] data = new byte[N_PROG_STEPS];

		for( int i = 0; i < data.length; i++)
		{
			if(steps[i] == null) 
				data[i] = 0;
			else 
				data[i] = (byte) steps[i].getBinary(stepType);
		}
		
		return data;
	}

	public void convertFrom(SpanSet part, MidiSignature signature) 
	{
		int pbars = 1 + part.maxBar(signature);

		int arpStepSize = BlueProg.N_PROG_STEPS / pbars; // Arp step = 1/arpStepSize

		int stepTicks = signature.getTicksPerBar() / arpStepSize;		

		int maxExtra[] = {0};
		int countExtra[] = {0};

		for(int p = 0; p < N_PROG_STEPS; p++)
			steps[p] = new ArpStep();
		
		
		part.forEach(signature, (mbb, note) -> {
			int pos = (int) (note.getStartTick() / stepTicks);
			int extraTicks = (int) (note.getStartTick() % stepTicks);
			
			if(extraTicks != 0)
			{
				maxExtra[0] = Math.max(maxExtra[0], extraTicks);
				countExtra[0]++;
			}
			
			if(pos >= N_PROG_STEPS)
			{
				System.err.println("Step "+pos+" > N_PROG_STEPS");
			}
			else
			{
				steps[pos] = new ArpStep(note);
			}
			
		} );
		
		if(countExtra[0] > 0)
		{
			System.err.println("Inexact positions for "+countExtra[0]+" steps, max="+maxExtra[0]);
		}
		
	}

	public void setName(String name) {
		this.name = name;		
	}

}
