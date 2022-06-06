package ru.dz.bluearp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import ru.dz.bintools.BinFileIO;
import ru.dz.bintools.ChunkInputStream;
import ru.dz.bintools.ChunkOutputStream;

/**
 * 
 * BlueArp fxp file import/export.
 * 
 * It seems that 'bbnk' contains:
 * 
 * <li> 1 fixp
 * <li> 1 fpgp
 * <li> 16 groups of chnp, chns, chnn
 * <li> 128 prog
 * 
 * @author dz
 *
 */


public class BlueFxp 
{
	private BlueBank bank = new BlueBank();
	
	public BlueBank getBank() {		return bank;	}
	public void setBank(BlueBank bb) {		bank = bb;	}
	
	/**
	 * 
	 * Load .fxp file for BlueArp
	 *
	 */
	
	public void load(String fileName) throws IOException 
	{		
		BufferedInputStream bis = new BufferedInputStream( new FileInputStream(fileName) );
		DataInputStream dis = new DataInputStream(bis);

		dis.skip(0xA0); // fixed headers?
		bank.readFrom(dis);

		dis.close();		
	}

	

	

	
	


	
	

	public void save(String fileName) throws IOException 
	{
		/*
		BufferedOutputStream bos = new BufferedOutputStream( new FileOutputStream(fileName) );
		DataOutputStream dos = new DataOutputStream(bos);

		//dos.skip(0xA0); // TODO fixed headers?
		{
			byte[] empty = new byte[0xA0];
			dos.write(empty);
		}
		
		bank.writeTo(dos);
		
		dos.close();
		*/
		
		byte[] bba = bank.toByteArray();
		
		RandomAccessFile dos = new RandomAccessFile(fileName, "rw");

		FxbWriteHeader(dos, bba.length );
		dos.write(bba);
		
		dos.close();
	}


	// write FXB header to chunk
	private void FxbWriteHeader(RandomAccessFile dos, int chunk_size) throws IOException
	{
		// TODO orig BlueArp FXB has 160 added to both chunk_size fields below for some reason. Though it is possible that both fields are ignored
		
		BinFileIO.write4c(dos,"CcnK");						// chunkMagic, 4 bytes
		BinFileIO.writeIntLE(dos, chunk_size + 160);       	// chunk size + header size (dummy1)

		BinFileIO.write4c(dos,"FBCh");						// fxbMagic

		BinFileIO.writeIntLE(dos, 2);                  		// fxbVersion, 4 bytes
		BinFileIO.write4c(dos,"7dWx");						// pluginID, 4 bytes - PLUG_UNIQUE_ID
		BinFileIO.writeIntLE(dos, 0x4EF0);                 	// pluginVersion, 4 bytes
		
		BinFileIO.writeIntLE(dos, 0x80);                  	// number of programs, 4 bytes - gMaxPrograms 
		BinFileIO.writeIntLE(dos, 0);                  		// current program, 4 bytes
		BinFileIO.writeFillBytes(dos, 124);              	// future dummy, 124 bytes
	 
		BinFileIO.writeIntLE(dos, chunk_size);         		// chunk size (dummy2)
	}

	
	/*
	private void FxbWriteHeader_UpdateSize(RandomAccessFile dos, int chunk_size) throws IOException
	{
		dos.seek(4); // dummy1 pos
		BinFileIO.writeInt(dos,chunk_size + 160);                  	// chunk size + header size (real inst of dummy) TODO is 160 a correct header size?
		dos.seek(156); // dummy2 pos
		BinFileIO.writeInt(dos,chunk_size);                  	// chunk size (real inst of dummy2)
	}*/
	
	/*

// update FXB header according to actual chunk size
void ARP_Bank_Chunk_FXB_Header_UpdateSize(ARP_t* pARP, BChunk* pChunk)
{
  int32_t chunk_size = pChunk->size;
  pChunk->size = 4;   // это байтовое смещение от начала chunk
  BChunk_PutInt_bswap(pChunk, chunk_size + 160);            // chunk size + header size
  pChunk->size = 156;  // это байтовое смещение от начала chunk   
  BChunk_PutInt_bswap(pChunk, chunk_size);                  // chunk size
  pChunk->size = chunk_size;
}

// write FXB header to chunk
void ARP_Bank_Chunk_FXB_Header_Write(ARP_t* pARP, BChunk* pChunk)
{
  BChunk_PutInt_bswap(pChunk, 'CcnK');                      // chunkMagic, 4 bytes
   
  BChunk_PutInt_bswap(pChunk, 0 + 160);                     // chunk size + header size (dummy)
 
  BChunk_PutInt_bswap(pChunk, 'FBCh');                      // fxbMagic
  BChunk_PutInt_bswap(pChunk, kFXBVersionNum);              // fxbVersion, 4 bytes
  BChunk_PutInt_bswap(pChunk, PLUG_UNIQUE_ID);              // pluginID, 4 bytes
  BChunk_PutInt_bswap(pChunk, GetDecimalVersion(PLUG_VER)); // pluginVersion, 4 bytes
  BChunk_PutInt_bswap(pChunk, gMaxPrograms);                // number of programs, 4 bytes
  BChunk_PutInt_bswap(pChunk, pARP->curProgram);            // current program, 4 bytes (пишите 0 по умолчанию)
  BChunk_PutConstBytes(pChunk, 0, 124);                     // future dummy, 124 bytes
 
  BChunk_PutInt_bswap(pChunk, 0);      // chunk size (dummy)
}

	 
	 */
	

}
