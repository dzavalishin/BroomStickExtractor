package ru.dz.bluearp;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.dz.bintools.BinFileIO;

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
	private List<BlueProg> programs = new ArrayList<BlueProg>();
	private List<BlueChain> chains = new ArrayList<BlueChain>();

	// Temps used to construct chain
	private byte[] chainSteps;
	private byte[] chainParams;

	public void load(String fileName) throws IOException 
	{
		BufferedInputStream bis = new BufferedInputStream( new FileInputStream(fileName) );
		DataInputStream dis = new DataInputStream(bis);

		if(false)
		{
			String allFileChunkName = BinFileIO.read4c(dis);
			int allFileChunkSize = BinFileIO.readIntLE(dis);		

			System.out.printf("all file chunk %s size %d\n", allFileChunkName, allFileChunkSize);
		}
		else
		{
			dis.skip(0xA0); // fixed headers?
		}
		readTopChunk(dis);

		dis.close();
		
		System.out.printf("Loaded %d chains, %d programs", chains.size(), programs.size() );
	}

	private void readTopChunk(DataInputStream dis) throws IOException {
		String chunkName = BinFileIO.read4c(dis);
		int chunkSize = BinFileIO.readInt(dis);		

		System.out.printf("top chunk %s size %d\n", chunkName, chunkSize);

		byte[] chunkData = BinFileIO.readBytes(dis, chunkSize);

		if( !chunkName.equals("bbnk") )
		{
			System.err.printf("Unknown top chunk %s size %d\n", chunkName, chunkSize);
			return;
		}

		int displ = 0;
		while(displ < chunkData.length )
		{
			String subName = BinFileIO.read4c(chunkData,0+displ);
			int subSize = BinFileIO.readInt(chunkData,4+displ);

			byte[] subData = new byte[subSize];			
			System.arraycopy(chunkData, 8+displ, subData, 0, subSize);

			//System.out.printf("%s size %d\n", subName, subSize);

			decodeChunk(subName,subData);

			displ += subSize+8;

			//if(displ > 100) break;
		}
		/*
		dis.skip(0xA8); // fixed headers?
		int num = 100;

		while(num-- > 0)
		{
			loadChunk(dis);
		}*/
	}

	/*private void loadChunk(DataInputStream dis) throws IOException {
		String name = BinFileIO.read4c(dis);
		int size = BinFileIO.readInt(dis);

		System.out.printf("%s size %d\n", name, size);

		byte[] data = BinFileIO.readBytes(dis, size);

		decodeChunk(name,data);
	}*/

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
		/*
		byte b = data[0];
		int d = BinFileIO.readInt(data, 1);	
		System.out.printf("chn p %d %d\n", b, d);
		 */
		// TODO 
		//BlueParameters.dumpWithDescriptor("Chnp ", data,BlueParameters.chnpDescriptors);
		chainParams = data;
	}

	private void decodeChns(byte[] data) 
	{		
		//BinFileIO.dump("chn s ", data);
		chainSteps = data;
	}


	private void decodeChnn(byte[] data) {
		// Name
		String name = new String(data);
		//System.out.printf("Chain '%s'\n", name);

		// Chain def goes as chnp, chns, chnn, so if we are here, lets define a new chain
		BlueChain chain = new BlueChain(name, chainSteps, chainParams);
		chains.add(chain);
	}

	private void decodeFixp(byte[] data) {
		// BinFileIO.dump("Fixp ", data);	

		// TODO 
		//BlueParameters.dumpWithDescriptor("Fixp ", data,BlueParameters.fixpDescriptors);

	}


	private void decodeFpgp(byte[] data) {
		// BinFileIO.dump("Fpgp ", data);	
		// TODO 
		//BlueParameters.dumpWithDescriptor("Fpgp ", data,BlueParameters.fpgpDescriptors);
	}

	private void decodeProg(byte[] data) {
		// TODO Auto-generated method stub
		//dump("prog ", data);	

		BlueProg prog = new BlueProg();
		prog.decode(data);
		programs.add(prog);
	}




}
