package ru.dz.bintools;

import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Vst3Chunk {

	private String id;
	private long offset;
	private long size;
	private byte[] data;

	public Vst3Chunk(String id, long offset, long size) {
		this.id = id;
		this.offset = offset;
		this.size = size;
	}

	public void loadFrom(RandomAccessFile dis) throws IOException {
		if(size == 0)
			return;
					
		System.out.printf("Loading VST3 list element '%s' offset %d size %d\n", id, offset, size );
		
		dis.seek(offset);

		if( size > Integer.MAX_VALUE )
			throw new IOException("Loading VST3 list element "+id+" size too big");
			
		data = BinFileIO.readBytes(dis, (int)size);

		//Files.write(Paths.get("bluearp/vst3."+id+".data"), data, StandardOpenOption.CREATE);
	}

	public Object getId() {
		return id;
	}

	public DataInput getStream() {
		ByteArrayInputStream s = new ByteArrayInputStream(data);
		DataInputStream dis = new DataInputStream(s);
		return dis;
	}

}
