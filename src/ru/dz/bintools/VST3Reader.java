package ru.dz.bintools;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.dz.bluearp.BlueBank;

public class VST3Reader 
{
	static final String VST3_ID = "F2AEE70D00DE4F4E4F4D477742417270";
	private BlueBank bank; 
	
	public VST3Reader(String fileName) throws IOException 
	{
		RandomAccessFile dis = new RandomAccessFile(fileName, "r");
		
		if( !"VST3".equals( BinFileIO.read4c(dis) ) )
			throw new IOException("No VST3 4cc");
		
		int fileVersion = BinFileIO.readInt(dis);
		if(fileVersion != 1)
			throw new IOException("Unknown VST3 file version "+fileVersion);
		
		byte[] vst3id = BinFileIO.readBytes(dis, 32);
		if( 0 != Arrays.compare(vst3id, VST3_ID.getBytes()) )
			throw new IOException("Unknown VST3 file ID");

		long chunkListOffset = BinFileIO.readLong(dis);		
		//System.out.printf("VST3 chunkListOffset %d\n", chunkListOffset );
		
		dis.seek(chunkListOffset);
		
		if( !"List".equals( BinFileIO.read4c(dis) ) )
			throw new IOException("No List 4cc");
		
		int listSize = BinFileIO.readInt(dis);
		System.out.printf("VST3 list size %d\n", listSize );
		
		List<Vst3ChunkReader> chunks = new ArrayList<Vst3ChunkReader>();
		
		while(listSize-- > 0)
		{
			String id = BinFileIO.read4c(dis);
			long chunkOffset = BinFileIO.readLong(dis);
			long chunkSize = BinFileIO.readLong(dis);
			
			//System.out.printf("VST3 list element '%s' offset %d size %d\n", id, chunkOffset, chunkSize );
			
			Vst3ChunkReader v3chunk = new Vst3ChunkReader(id, chunkOffset, chunkSize);
			chunks.add(v3chunk);
		}
		
		for(Vst3ChunkReader c : chunks)
		{
			c.loadFrom(dis);
			if("Comp".equals(c.getId()))
			{
				bank = new BlueBank();
				bank.readTopChunk(c.getStream());
			}
		}
		
		dis.close();
	}

	public BlueBank getBank() { return bank; }
	
}
