package ru.dz.bintools;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class VST3InputStream 
{
	static final String VST3_ID = "F2AEE70D00DE4F4E4F4D477742417270";
	
	//private DataInputStream dis;
	
	public VST3InputStream(String fileName) throws IOException {
		BufferedInputStream bis = new BufferedInputStream( new FileInputStream(fileName) );
		DataInputStream dis = new DataInputStream(bis);

		if( !"VST3".equals( BinFileIO.read4c(dis) ) )
			throw new IOException("No VST3 4cc");
		
		int fileVersion = BinFileIO.readInt(dis);
		if(fileVersion != 1)
			throw new IOException("Unknown VST3 file version "+fileVersion);
		
		byte[] vst3id = BinFileIO.readBytes(dis, 32);
		if( 0 != Arrays.compare(vst3id, VST3_ID.getBytes()) )
			throw new IOException("Unknown VST3 file ID");

		int dataSize = BinFileIO.readInt(dis);
		/*int unused = */BinFileIO.readInt(dis);		
		System.out.printf("VST3 data size %d\n", dataSize );

		byte[] vst3Data = BinFileIO.readBytes(dis, dataSize);
		Files.write(Paths.get("bluearp/vst3.data"), vst3Data, StandardOpenOption.CREATE);
		
		int rest = dis.available();
		System.out.printf("VST3 final part size %d\n", rest ); 
		// 20 bytes: "Info" D6 16 01 00  00 00 00 00  AC 01 00 00  00 00 00 00   

		//byte[] restData = BinFileIO.readBytes(dis, dataSize);
		
		String rest4cc = BinFileIO.read4c(dis);
		int restI1 = BinFileIO.readInt(dis);
		int restI2 = BinFileIO.readInt(dis);
		int restI3 = BinFileIO.readInt(dis);
		int restI4 = BinFileIO.readInt(dis);
		
		System.out.printf("VST3 final part data %s %d %d %d %d\n", rest4cc, restI1, restI2, restI3, restI4 );
		
		dis.close();
	}
	
	
}
