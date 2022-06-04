package ru.dz.bluearp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
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
 * It seems that 'bbnk' contains:
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
	private BlueBank bank = new BlueBank();
	
	/**
	 * 
	 * Load .fxp file for BlueArp
	 *
	 */
	
	public void load(String fileName) throws IOException 
	{
		testParseTop(fileName);
		//if(true) return;
		
		BufferedInputStream bis = new BufferedInputStream( new FileInputStream(fileName) );
		DataInputStream dis = new DataInputStream(bis);

		dis.skip(0xA0); // fixed headers?
		bank.readFrom(dis);

		dis.close();
		
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
		
		//System.exit(33);
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
		
		bank.writeTo(dos);
		
		dos.close();
	}


	

}
