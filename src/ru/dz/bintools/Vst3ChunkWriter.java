package ru.dz.bintools;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Vst3ChunkWriter 
{
	private final byte[] data;
	//private boolean saved = false;
	private long offset;

	public Vst3ChunkWriter(byte [] data) {
		this.data = data;		
	}
	
	public void saveTo(RandomAccessFile dos) throws IOException {
		offset = dos.getFilePointer();
		dos.write(data);
		//saved = true;
	}
		
	public long getOffset() {	return offset;	}
	public long getSize() { 	return data.length; }	
}
