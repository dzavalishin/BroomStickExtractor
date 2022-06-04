package ru.dz.bintools;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

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
	

	
	public static String readZeroString(byte [] data) 
	{
		StringBuilder sb = new StringBuilder();
		
		int len = 0;
		
		while(len < data.length)
		{
			byte b = data[len];
			if( b == 0 )
				break;

			len++;
			
			if(len > 10240)
				BroomMain.fail("string too long");
			
			sb.append( (char)(0xFF & (int) b));
		}
		
		return sb.toString();
	}
	
	
	public static int readInt(DataInput dis) throws IOException 
	{
		int b0 = dis.readByte() & 0xFF;
		int b1 = dis.readByte() & 0xFF;
		int b2 = dis.readByte() & 0xFF;
		int b3 = dis.readByte() & 0xFF;
		
		return b0 | (b1 <<8) | (b2 <<16) | (b3 <<24);
	}

	public static long readLong(DataInput dis) throws IOException 
	{
		//return dis.readLong();
		/*int b1 = dis.readByte() & 0xFF;
		int b2 = dis.readByte() & 0xFF;
		int b3 = dis.readByte() & 0xFF;
		
		return b0 | (b1 <<8) | (b2 <<16) | (b3 <<24); */
		
		int lo = readInt(dis);
		int hi = readInt(dis);
		
		return lo | (hi << 32);
	}
	
	
	public static int readIntLE(DataInput dis) throws IOException 
	{
		int b3 = dis.readByte() & 0xFF;
		int b2 = dis.readByte() & 0xFF;
		int b1 = dis.readByte() & 0xFF;
		int b0 = dis.readByte() & 0xFF;
		
		return b0 | (b1 <<8) | (b2 <<16) | (b3 <<24);
	}



	//public static String read4c(DataInputStream dis) throws IOException {
	public static String read4c(DataInput dis) throws IOException {
		byte buf[] = new byte[4];
		//if( buf.length != dis.read(buf) )			throw new IOException("can't read 4c");
		
		dis.readFully(buf);
		
		return new String(buf);
	}

	public static String read4c(byte[] data, int displ) {
		byte buf[] = new byte[4];
		
		System.arraycopy(data, displ, buf, 0, buf.length);
		
		return new String(buf);
	}
	
	
	public static byte[] readBytes(DataInput dis, int len) throws IOException {
		byte buf[] = new byte[len];
		//if( buf.length != dis.read(buf) )			throw new IOException("can't read "+len+" bytes");
		
		dis.readFully(buf);
		
		return buf;
	}

	
	public static int readInt(byte [] data, int displ) 
	{
		int b0 = data[displ+0] & 0xFF;
		int b1 = data[displ+1] & 0xFF;
		int b2 = data[displ+2] & 0xFF;
		int b3 = data[displ+3] & 0xFF;
		
		return b0 | (b1 <<8) | (b2 <<16) | (b3 <<24);
	}




	
	public static void dump(String name, byte[] data) 
	{
		System.out.printf("%s", name);

		for(int i = 0; i < data.length; i++)
			System.out.printf("%d ", data[i]);

		System.out.println();
	}


	/**
	 * 
	 * @param dos File to write to.
	 * @param chunkName Name (4cc) of chunk.
	 * @param chunkData Binary data to write.
	 * @param checkLength Max possible length of data or 0 to skip check.
	 * @return Length of chunk written.
	 * @throws IOException
	 */

	public static int writeChunk(DataOutput dos, String chunkName, byte[] chunkData, int checkLength) throws IOException 
	{
		if( (checkLength != 0) && (chunkData.length > checkLength) )
		{
			String err = String.format("Writing %s: len %d > %d", chunkName, chunkData.length, checkLength );
			System.err.println(err);
			throw new IOException(err);
		}

		//if( chunkName.length() != 4 )			System.err.printf("Writing %s: name len %d != 4", chunkName, chunkName.length() );
		
		//write4c(dos, chunkName);
		//writeInt(dos, chunkData.length);
		
		writeChunkHeader(dos, chunkName, chunkData.length);		
		dos.write(chunkData);
		
		return chunkData.length + 8;
	}


	public static void writeChunkHeader(DataOutput dos, String chunkName, int length) throws IOException 
	{
		if( chunkName.length() != 4 )
		{
			String err = String.format("Writing %s: name len %d != 4", chunkName, chunkName.length() );
			System.err.println(err);
			throw new IOException(err);
		}

		write4c(dos, chunkName);
		writeInt(dos, length);
	}
	
	

	private static void write4c(DataOutput dos, String chunkName) throws IOException {
		byte[] b = chunkName.getBytes();
		
		dos.writeByte(b[0]);
		dos.writeByte(b[1]);
		dos.writeByte(b[2]);
		dos.writeByte(b[3]);		
	}



	public static void writeInt(DataOutput dos, int val) throws IOException 
	{
		byte b0 = (byte) (val & 0xFF);
		byte b1 = (byte) ((val >>  8) & 0xFF);
		byte b2 = (byte) ((val >> 16) & 0xFF);
		byte b3 = (byte) ((val >> 24) & 0xFF);
		
		dos.writeByte(b0);
		dos.writeByte(b1);
		dos.writeByte(b2);
		dos.writeByte(b3);		
	}


	/**
	 * Write chunk that contains a string of a fixed size.
	 * 
	 * @param dos File to write to.
	 * @param name Chunk name (4cc).
	 * @param data Chunk contents.
	 * @param dataLen Fixed size.
	 * @return Length of a chunk written.
	 * @throws IOException
	 */

	public static int writeFixedStringChunk(DataOutput dos, String name, String data, int dataLen) throws IOException {
		byte[] datab = data.getBytes();
		
		byte[] dataf = new byte[dataLen];
		Arrays.fill(dataf, (byte)0);
		
		int copySize = Math.min(dataf.length-1, datab.length); // -1 left zero at end?
		System.arraycopy(datab, 0, dataf, 0, copySize);
		
		return writeChunk(dos, name, dataf, dataLen);
	}
	
}
