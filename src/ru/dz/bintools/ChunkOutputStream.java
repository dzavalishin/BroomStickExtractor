package ru.dz.bintools;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ChunkOutputStream 
{
	private ByteArrayOutputStream storeBos;
	private DataOutputStream bos;
	
	public ChunkOutputStream() {
		storeBos = new ByteArrayOutputStream();
		bos = new DataOutputStream(storeBos);		
	}

	public DataOutputStream getStream() {
		return bos;
	}

	public void writeChunk(DataOutputStream dos, String chunkName) throws IOException {
		byte[] bankChunkData = storeBos.toByteArray();
		BinFileIO.writeChunk(dos, chunkName, bankChunkData, 0);		
	}
	
	
}
