package ru.dz.bluearp;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import ru.dz.bintools.BinFileIO;

public class BlueFxp 
{
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
		
		System.out.printf("%s size %d\n", name, size);
		
		dis.skip(size);
	}

}
