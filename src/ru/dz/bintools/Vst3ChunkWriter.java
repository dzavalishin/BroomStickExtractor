package ru.dz.bintools;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Vst3ChunkWriter 
{
	private final byte[] data;
	private boolean saved = false;
	private long offset;

	
	private Vst3ChunkWriter() {
		this.data = null;
	}
	
	public Vst3ChunkWriter(byte [] data) {
		this.data = data;		
	}
	
	public void saveTo(RandomAccessFile dos) throws IOException {
		offset = dos.getFilePointer();
		if(null != data) dos.write(data);
		saved = true;
	}
		
	private long getOffset() {	return offset;	}
	private long getSize() { 	return (data == null) ? 0 : data.length; }

	public void writeListRecord(RandomAccessFile dos, String id) throws IOException 
	{
		if(!saved)
			throw new IOException("Attempt to write list record for unsaved Vst3ChunkWriter");
		
		BinFileIO.write4c(dos,id);
		BinFileIO.writeLong(dos,getOffset());
		BinFileIO.writeLong(dos,getSize()); 		
	}

	public static Vst3ChunkWriter makeEmpty() {
		return new Vst3ChunkWriter();
	}	
}
