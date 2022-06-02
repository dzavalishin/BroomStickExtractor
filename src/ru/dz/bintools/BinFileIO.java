package ru.dz.bintools;

import java.io.DataInputStream;
import java.io.IOException;

import ru.dz.broom.BroomMain;

public class BinFileIO {

	public static String readZeroString(DataInputStream dis) throws IOException 
	{
		StringBuilder sb = new StringBuilder();
		
		int len = 0;
		
		while(true)
		{
			byte b = dis.readByte();
			if( b == 0 )
				break;

			len++;
			
			if(len > 10240)
				BroomMain.fail("string too long");
			
			sb.append( (char)(0xFF & (int) b));
		}
		
		return sb.toString();
	}
	

	
	public static int readInt(DataInputStream dis) throws IOException 
	{
		int b0 = dis.readByte() & 0xFF;
		int b1 = dis.readByte() & 0xFF;
		int b2 = dis.readByte() & 0xFF;
		int b3 = dis.readByte() & 0xFF;
		
		return b0 | (b1 <<8) | (b2 <<16) | (b3 <<24);
	}



	public static String read4c(DataInputStream dis) throws IOException {
		byte buf[] = new byte[4];
		if( buf.length != dis.read(buf) )
			throw new IOException("can't read 4c");
		
		return new String(buf);
	}
	
}
