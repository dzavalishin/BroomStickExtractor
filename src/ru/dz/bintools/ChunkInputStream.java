package ru.dz.bintools;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;

public class ChunkInputStream 
{
	//private DataInputStream cdis;
	private String chunkName;
	private int chunkSize;
	private byte[] chunkData;
	
	public ChunkInputStream(DataInput dis) throws IOException {
		chunkName = BinFileIO.read4c(dis);
		chunkSize = BinFileIO.readInt(dis);		
		chunkData = BinFileIO.readBytes(dis, chunkSize);
	}
	
	//public DataInputStream getStream() { return cdis; }
	public byte[] getData() { return chunkData; }
	public String getName() { return chunkName; }
	public int getLength() { return chunkSize; }
	
	
	@Override
	public String toString() {
		return String.format("chunk %s size %dn", chunkName, chunkSize);
	}


}
