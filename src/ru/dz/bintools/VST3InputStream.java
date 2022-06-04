package ru.dz.bintools;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.dz.bluearp.BlueBank;

public class VST3InputStream 
{
	static final String VST3_ID = "F2AEE70D00DE4F4E4F4D477742417270";
	private BlueBank bank; 
	
	//private DataInputStream dis;
	
	public VST3InputStream(String fileName) throws IOException {
		//BufferedInputStream bis = new BufferedInputStream( new FileInputStream(fileName) );
		//DataInputStream dis = new DataInputStream(bis);

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
		
		System.out.printf("VST3 chunkListOffset %d\n", chunkListOffset );

		//byte[] vst3Data = BinFileIO.readBytes(dis, dataSize);
		//Files.write(Paths.get("bluearp/vst3.data"), vst3Data, StandardOpenOption.CREATE);
		/*
		int rest = dis.available();
		System.out.printf("VST3 final part size %d\n", rest ); 
		// 20 bytes: "Info" D6 16 01 00  00 00 00 00  AC 01 00 00  00 00 00 00   

		//byte[] restData = BinFileIO.readBytes(dis, dataSize);
		
		String rest4cc = BinFileIO.read4c(dis);
		int restI1 = BinFileIO.readInt(dis); // XML part file offset - long value?
		int restI2 = BinFileIO.readInt(dis);
		int restI3 = BinFileIO.readInt(dis); // XML part size
		int restI4 = BinFileIO.readInt(dis);
		
		System.out.printf("VST3 final part data %s %d %d %d %d\n", rest4cc, restI1, restI2, restI3, restI4 );
		*/
		
		dis.seek(chunkListOffset);
		
		if( !"List".equals( BinFileIO.read4c(dis) ) )
			throw new IOException("No List 4cc");
		
		int listSize = BinFileIO.readInt(dis);
		System.out.printf("VST3 list size %d\n", listSize );
		
		List<Vst3Chunk> chunks = new ArrayList<Vst3Chunk>();
		
		while(listSize-- > 0)
		{
			String id = BinFileIO.read4c(dis);
			long chunkOffset = BinFileIO.readLong(dis);
			long chunkSize = BinFileIO.readLong(dis);
			
			System.out.printf("VST3 list element '%s' offset %d size %d\n", id, chunkOffset, chunkSize );
			
			Vst3Chunk v3chunk = new Vst3Chunk(id, chunkOffset, chunkSize);
			chunks.add(v3chunk);
		}
		
		for(Vst3Chunk c : chunks)
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
