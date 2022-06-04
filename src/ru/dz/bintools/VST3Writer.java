package ru.dz.bintools;

import java.io.IOException;
import java.io.RandomAccessFile;

import ru.dz.bluearp.BlueBank;
import ru.dz.bluearp.BlueDefs;

public class VST3Writer implements VST3Defs 
{	
	//private final BlueBank bank;

	public VST3Writer(BlueBank bank, String fileName) throws IOException {
		//this.bank = bank;
		RandomAccessFile dos = new RandomAccessFile(fileName, "rw");
		
		// Write header
		
		BinFileIO.write4c(dos,"VST3");
		BinFileIO.writeInt(dos,1); // version
		dos.write(VST3_ID.getBytes());
		
		long chunkListOffset_Offset = dos.getFilePointer();		
		BinFileIO.writeLong(dos,-1); // chunkListOffset

		// Make & write chunks
		
		Vst3ChunkWriter bankChunk = makeBankChunk(bank);
		Vst3ChunkWriter xmlChunk = makeXmlChunk();
		Vst3ChunkWriter contChunk = Vst3ChunkWriter.makeEmpty();
		
		bankChunk.saveTo(dos);
		contChunk.saveTo(dos);
		xmlChunk.saveTo(dos);
		
		// Write chunk list

		long chunkListOffset = dos.getFilePointer();		
		
		BinFileIO.write4c(dos,"List");
		BinFileIO.writeInt(dos,3); // list size
		
		bankChunk.writeListRecord(dos,"Comp");
		contChunk.writeListRecord(dos,"Cont");
		xmlChunk.writeListRecord(dos,"Info");

		// Go back and write list pointer

		dos.seek(chunkListOffset_Offset);
		BinFileIO.writeLong(dos,chunkListOffset); // chunkListOffset

		// Done!
	}


	private Vst3ChunkWriter makeBankChunk(BlueBank bank) throws IOException 
	{
		/*
		ByteArrayOutputStream storeBos = new ByteArrayOutputStream();
		DataOutputStream bos = new DataOutputStream(storeBos);		
		
		bank.writeTo(bos);
		return new Vst3ChunkWriter(storeBos.toByteArray());
		*/
		
		return new Vst3ChunkWriter(bank.toByteArray());
	}

	private Vst3ChunkWriter makeXmlChunk() {
		byte[] xml = BlueDefs.vts3XML.getBytes();
		
		
		byte[] chunk = new byte[xml.length + 3];
		
		chunk[0] = (byte) 0xEF;
		chunk[1] = (byte) 0xBB;
		chunk[2] = (byte) 0xBF;
		
		System.arraycopy(xml, 0, chunk, 3, xml.length);
		
		return new Vst3ChunkWriter(chunk);
	}
	
	
}
