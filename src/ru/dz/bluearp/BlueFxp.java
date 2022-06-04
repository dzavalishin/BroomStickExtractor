package ru.dz.bluearp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.dz.bintools.BinFileIO;
import ru.dz.bintools.ChunkInputStream;
import ru.dz.bintools.ChunkOutputStream;

/**
 * 
 * BlueArp fxp file import/export.
 * 
 * It seems that fxp contains:
 * 
 * <li> 1 fixp
 * <li> 1 fpgp
 * <li> 16 groups of chnp, chns, chnn
 * <li> 128 prog
 * 
 * @author dz
 *
 */


public class BlueFxp 
{
	private static final int N_CHAINS_IN_FXP = 16;
	private static final int N_PROGS_IN_FXP = 128;
	
	private List<BlueProg> programs = new ArrayList<BlueProg>();
	private List<BlueChain> chains = new ArrayList<BlueChain>();

	// Temps used to construct chain
	private byte[] chainSteps;
	private byte[] chainParams;
	private byte[] fixpData;
	private byte[] fpgpData;

	
	/*
	 * Contains file size:
	 * 		dis.skip(0x9c); int data = BinFileIO.readIntLE(dis);		
	 *
	 */
	
	public void load(String fileName) throws IOException 
	{
		testParseTop(fileName);
		//if(true) return;
		
		BufferedInputStream bis = new BufferedInputStream( new FileInputStream(fileName) );
		DataInputStream dis = new DataInputStream(bis);

		dis.skip(0xA0); // fixed headers?
		readTopChunk(dis);

		dis.close();
		
		System.out.printf("Loaded %d chains, %d programs\n", chains.size(), programs.size() );
	}

	
	private void testParseTop(String fileName) throws IOException 
	{
		BufferedInputStream bis = new BufferedInputStream( new FileInputStream(fileName) );
		DataInputStream dis = new DataInputStream(bis);

		/*
		String allFileChunkName = BinFileIO.read4c(dis);
		int allFileChunkSize = BinFileIO.readIntLE(dis);		

		File f = new File(fileName);
		
		long diff = allFileChunkSize - f.length() - 8;
		
		System.out.printf("all file chunk %s size %d diff = %d\n", allFileChunkName, allFileChunkSize, diff );
		*/
		dis.skip(0x14);		
		int data = BinFileIO.readIntLE(dis);		
		System.out.printf("data %d\n", data );
		
		System.exit(33);
	}

	

	
	
	private void readTopChunk(DataInputStream dis) throws IOException 
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
	}


	private void decodeChunk(String name, byte[] data) 
	{
		System.out.printf("decode %s size %d\n", name, data.length);

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

	private void decodeFixp(byte[] data) {
		// BinFileIO.dump("Fixp ", data);	
		fixpData = data;		
		// TODO 
		//BlueParameters.dumpWithDescriptor("Fixp ", data,BlueParameters.fixpDescriptors);
	}


	private void decodeFpgp(byte[] data) {
		// BinFileIO.dump("Fpgp ", data);	
		// TODO 
		//BlueParameters.dumpWithDescriptor("Fpgp ", data,BlueParameters.fpgpDescriptors);
		fpgpData = data;
	}

	private void decodeProg(byte[] data) 
	{
		//dump("prog ", data);	
		BlueProg prog = new BlueProg();
		prog.decode(data);
		programs.add(prog);
	}



	
	

	public void save(String fileName) throws IOException 
	{
		BufferedOutputStream bos = new BufferedOutputStream( new FileOutputStream(fileName) );
		DataOutputStream dos = new DataOutputStream(bos);

		//dos.skip(0xA0); // TODO fixed headers?
		{
			byte[] empty = new byte[0xA0];
			dos.write(empty);
		}
		
		ChunkOutputStream bank = new ChunkOutputStream();
		writeBank(bank.getStream());
		bank.writeChunk(dos, "bbnk");
		
		dos.close();
		System.out.printf("Saved %d chains, %d programs\n", chains.size(), programs.size() );
	}


	private void writeBank(DataOutputStream dos) throws IOException {
		/*
		 * <li> 1 fixp
		 * <li> 1 fpgp
		 * <li> 16 groups of chnp, chns, chnn
		 * <li> 128 prog
		 */
		
		BinFileIO.writeChunk( dos, "fixp", fixpData, BlueParameters.fixpDescriptors.length );
		BinFileIO.writeChunk( dos, "fpgp", fpgpData, BlueParameters.fpgpDescriptors.length );
		
		
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
	}	
	

}
