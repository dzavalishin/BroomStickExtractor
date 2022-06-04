package ru.dz.broom;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

/**
 * 
 * BroomStick Bass resource file directory record.
 * @author dz
 *
 */

public class DirRecord {

	private String name;
	private int len;

	public DirRecord(String name, int len) 
	{
		this.name = name;
		this.len = len;
		System.out.println("file "+name+" len "+len);
	}

	void extract(DataInputStream dis, String targetDir) throws IOException
	{
		byte[] buf = new byte[len];
		int rd = dis.read(buf);

		if(rd != len)
			System.out.println("File "+name+" not read");

		File target = new File(targetDir,name);
		System.out.println("Store to "+target);

		//target.createNewFile();
		target.getParentFile().mkdirs();
		
		try (FileOutputStream fos = new FileOutputStream(target)) {
			fos.write(buf);
			//fos.close(); There is no more need for this line since you had created the instance of "fos" inside the try. And this will automatically close the OutputStream
		}		
	}

}
