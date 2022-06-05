package ru.dz.bluearp;

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.dz.bintools.BinFileIO;
import ru.dz.bintools.ChunkInputStream;
import ru.dz.bintools.ChunkOutputStream;

public class BlueBank 
{

	private static final int N_CHAINS_IN_FXP = 16;
	private static final int N_PROGS_IN_FXP = 128;
	
	private List<BlueProg> programs = new ArrayList<BlueProg>();
	private List<BlueChain> chains = new ArrayList<BlueChain>();
	
	FixedParametersTable fixedParameters = new FixedParametersTable();
	ProgramParametersTable programParameters = new ProgramParametersTable();

	// Temps used to construct chain
	private byte[] chainSteps;
	private byte[] chainParams;

	
	
	public void readFrom(DataInput dis) throws IOException 
	{
		ChunkInputStream cis = new ChunkInputStream(dis); 
		System.out.printf("top %s\n", cis);

		if( !cis.getName().equals("bbnk") )
		{
			System.err.printf("Unknown top %s\n", cis );
			return;
		}

		int displ = 0;
		while(displ < cis.getLength() )
		{
			byte[] chunkData = cis.getData();
			String subName = BinFileIO.read4c(chunkData,0+displ);
			int subSize = BinFileIO.readInt(chunkData,4+displ);

			byte[] subData = new byte[subSize];			
			System.arraycopy(chunkData, 8+displ, subData, 0, subSize);

			//System.out.printf("%s size %d\n", subName, subSize);

			decodeChunk(subName,subData);

			displ += subSize+8;
		}
		
		System.out.printf("Loaded %d chains, %d programs\n", chains.size(), programs.size() );
	}

	


	private void decodeChunk(String name, byte[] data) throws IOException 
	{
		//System.out.printf("decode %s size %d\n", name, data.length);

		if( "prog".equals(name) )		{ decodeProg(data); return; }
		if( "chns".equals(name) )		{ decodeChns(data); return; }
		if( "chnn".equals(name) )		{ decodeChnn(data); return; }
		if( "chnp".equals(name) )		{ decodeChnp(data); return; }
		if( "fixp".equals(name) )		{ decodeFixp(data); return; }
		if( "fpgp".equals(name) )		{ decodeFpgp(data); return; }

		System.out.printf("Unknown chunk %s size %d\n", name, data.length);
	}


	private void decodeChnp(byte[] data) 
	{
		// TODO 
		//BlueParameters.dumpWithDescriptor("Chnp ", data,BlueParameters.chnpDescriptors);
		chainParams = data;
	}

	private void decodeChns(byte[] data) 
	{		
		//BinFileIO.dump("chn s ", data);
		chainSteps = data;
	}


	/**
	 * Chain name
	 * @param data Chunk data to decode
	 */
	private void decodeChnn(byte[] data) {
		String name = new String(data);
		//System.out.printf("Chain '%s'\n", name);

		// TODO if order will change we will do strange things. Provide for that?
		// Chain def goes as chnp, chns, chnn, so if we are here, lets define a new chain
		BlueChain chain = new BlueChain(name, chainSteps, chainParams);
		chains.add(chain);
	}

	private void decodeFixp(byte[] data) throws IOException {
		// BinFileIO.dump("Fixp ", data);	
		//fixpData = data;		
		// TODO 
		//BlueParameters.dumpWithDescriptor("Fixp ", data,BlueParameters.fixpDescriptors);
		fixedParameters.setContents(data);
		//fixedParameters.dump("Fixp ");
		
		fixedParameters.checkSanity();
	}


	private void decodeFpgp(byte[] data) {
		// BinFileIO.dump("Fpgp ", data);	
		// TODO 
		//BlueParameters.dumpWithDescriptor("Fpgp ", data,BlueParameters.fpgpDescriptors);
		//fpgpData = data;
		programParameters.setContents(data);
	}

	private void decodeProg(byte[] data) 
	{
		//dump("prog ", data);	
		BlueProg prog = new BlueProg();
		prog.decode(data);
		programs.add(prog);
	}


	
	
	
	
	
	
	private void writeBank(DataOutput dos) throws IOException {
		/*
		 * <li> 1 fixp
		 * <li> 1 fpgp
		 * <li> 16 groups of chnp, chns, chnn
		 * <li> 128 prog
		 */
		
		//BinFileIO.writeChunk( dos, "fixp", fixpData, BlueParameters.fixpDescriptors.length );
		BinFileIO.writeChunk( dos, "fixp", fixedParameters.getContents(), 0 );
		
		//BinFileIO.writeChunk( dos, "fpgp", fpgpData, BlueParameters.fpgpDescriptors.length );
		BinFileIO.writeChunk( dos, "fpgp", programParameters.getContents(), 0 );
		
		
		
		int nChains = 0;
		for( BlueChain c : chains )
		{
			c.writeTo( dos );
			if( ++nChains >= N_CHAINS_IN_FXP )
				break;
		}
		
		while(nChains < N_CHAINS_IN_FXP )
		{
			BlueChain empty = new BlueChain();
			empty.writeTo( dos );
			nChains++;
		}


		int nProgs = 0;
		for( BlueProg p : programs )
		{
			p.writeTo( dos );
			if( ++nProgs >= N_PROGS_IN_FXP)
				break;
		}
		
		while(nProgs < N_PROGS_IN_FXP )
		{
			BlueProg empty = new BlueProg();
			empty.writeTo( dos );
			nProgs++;
		}
		
		System.out.printf("Saved %d chains, %d programs\n", chains.size(), programs.size() );		
	}




	public void writeTo(DataOutputStream dos) throws IOException {
		ChunkOutputStream banks = new ChunkOutputStream();
		writeBank(banks.getStream());
		banks.writeChunk(dos, "bbnk");
	}




	public byte[] toByteArray() throws IOException {
		ByteArrayOutputStream storeBos = new ByteArrayOutputStream();
		DataOutputStream bos = new DataOutputStream(storeBos);		
		
		writeTo(bos);
		
		return storeBos.toByteArray();
	}




	public void setProgram(int programNumber, BlueProg program) {
		if(programs.size() < programNumber+1)
			programs.add(program);
		else
			programs.set(programNumber, program);		
	}	
	
}
