package ru.dz.bluearp;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

import ru.dz.bintools.BinFileIO;

/**
 * Defines chain of BlueArp programs.
 *  
 * @author dz
 *
 */

public class BlueChain 
{
	private final static int CHAIN_NAME_LEN = 48;

	private String name;
	private byte[] chainSteps;
	//private byte[] chainParams;
	ChainParametersTable params = new ChainParametersTable();

	public BlueChain(String name, byte[] chainSteps) { // , byte[] chainParams) {
		this.name = name;
		this.chainSteps = chainSteps;
		//this.chainParams = chainParams;

		dump();
	}

	public BlueChain(String name, byte[] chainSteps, byte[] chainParams) {
		this.name = name;
		this.chainSteps = chainSteps;
		params.setContents(chainParams);

		dump();
	}
	
	static private byte[] emptySteps = new byte[8];
	//static private byte[] defaultChainParams = new byte[5];
	static {
		Arrays.fill(emptySteps, (byte)-1);
		
		//Arrays.fill(defaultChainParams, (byte)-1);
		// TODO table defauls this to 1
		//defaultChainParams[0] = 0;
	}

	/**
	 * Create empty chain
	 */
	public BlueChain() {
		//this("(empty)", emptySteps, defaultChainParams );
		this("(empty)", emptySteps );
	}


	public void dump()
	{
		System.out.printf("Chain '%s' progs: ", name);
		for(int i = 0; i < chainSteps.length; i++)
		{
			if(chainSteps[i] < 0)
				break;
			if(i != 0) System.out.print(", ");
			System.out.printf("%d", chainSteps[i]);
		}
		System.out.println();

		//BlueParameters.dumpWithDescriptor("Chain params", chainParams, BlueParameters.chnpDescriptors);
		params.dump("Chain params");

		
	}


	/**
	 * 
	 * Write chain definition to fxp file.
	 * 
	 * Writes group of chnp, chns, chnn chunks
	 * @return 
	 * @throws IOException 
	 */
	public int writeTo(DataOutput dos) throws IOException 
	{
		if(chainSteps.length != 8)
			throw new IOException("Chain size != 8");
		
		int len = 0;
		
		//len += BinFileIO.writeChunk( dos, "chnp", chainParams, BlueParameters.chnpDescriptors.length );
		len += BinFileIO.writeChunk( dos, "chnp", params.getContents(), 0 );
		len += BinFileIO.writeChunk( dos, "chns", chainSteps, 8 );
		len += BinFileIO.writeFixedStringChunk( dos, "chnn", name, CHAIN_NAME_LEN ); 
		
		return len;		
	}
}
