package ru.dz.bluearp;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.dz.bintools.BinFileIO;

public class BlueFxp 
{
	List<BlueProg> programs = new ArrayList<BlueProg>();
	
	public void load(String name) throws IOException {
		BufferedInputStream bis = new BufferedInputStream( new FileInputStream(name) );
		DataInputStream dis = new DataInputStream(bis);
		
		dis.skip(0xA8); // fixed headers?
		
		int num = 100;
		
		while(num-- > 0)
		{
			loadChunk(dis);
		}
		
		dis.close();
	}

	private void loadChunk(DataInputStream dis) throws IOException {
		String name = BinFileIO.read4c(dis);
		int size = BinFileIO.readInt(dis);
		
		//System.out.printf("%s size %d\n", name, size);
		
		byte[] data = BinFileIO.readBytes(dis, size);
		
		decodeChunk(name,data);
	}

	private void decodeChunk(String name, byte[] data) 
	{
		if( "prog".equals(name) )		{ decodeProg(data); return; }
		if( "chns".equals(name) )		{ decodeChns(data); return; }
		if( "chnn".equals(name) )		{ decodeChnn(data); return; }
		if( "chnp".equals(name) )		{ decodeChnp(data); return; }
		if( "fixp".equals(name) )		decodeFixp(data);
		if( "fpgp".equals(name) )		decodeFpgp(data);
		 
		System.out.printf("Unknown chunk %s size %d\n", name, data.length);
	}


	private void decodeChnp(byte[] data) 
	{
		/*
		byte b = data[0];
		int d = BinFileIO.readInt(data, 1);	
		System.out.printf("chn p %d %d\n", b, d);
		*/
		BlueParameters.dumpWithDescriptor("Chnp ", data,BlueParameters.chnpDescriptors);

	}

	private void decodeChns(byte[] data) 
	{		
		BinFileIO.dump("chn s ", data);	
	}


	private void decodeChnn(byte[] data) {
		// Name
		String name = new String(data);
		System.out.printf("chn n '%s'\n", name);
	}

	private void decodeFixp(byte[] data) {
		// BinFileIO.dump("Fixp ", data);	
		
		BlueParameters.dumpWithDescriptor("Fixp ", data,BlueParameters.fixpDescriptors);
		
	}
	

	private void decodeFpgp(byte[] data) {
		// BinFileIO.dump("Fpgp ", data);	
		
		BlueParameters.dumpWithDescriptor("Fpgp ", data,BlueParameters.fpgpDescriptors);
	}

	private void decodeProg(byte[] data) {
		// TODO Auto-generated method stub
		//dump("prog ", data);	
		
		BlueProg prog = new BlueProg();
		prog.decode(data);
		programs.add(prog);
	}


	
	
}
