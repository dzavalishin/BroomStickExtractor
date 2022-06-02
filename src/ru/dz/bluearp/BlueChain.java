package ru.dz.bluearp;

import ru.dz.bintools.BinFileIO;

/**
 * Defines chain of BlueArp programs.
 *  
 * @author dz
 *
 */

public class BlueChain 
{

	private String name;
	private byte[] chainSteps;
	private byte[] chainParams;

	public BlueChain(String name, byte[] chainSteps, byte[] chainParams) {
		this.name = name;
		this.chainSteps = chainSteps;
		this.chainParams = chainParams;

		dump();
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

		BlueParameters.dumpWithDescriptor("Chain params", chainParams, BlueParameters.chnpDescriptors);

		
	}
}
