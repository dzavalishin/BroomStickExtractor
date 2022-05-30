package ru.dz.broom;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class BroomExtractor 
{
	DataInputStream dis;
	List<DirRecord> records = new ArrayList<DirRecord>();

	public BroomExtractor(String name) throws FileNotFoundException {
		BufferedInputStream bis = new BufferedInputStream( new FileInputStream(name) );
		dis = new DataInputStream(bis);
	}

	public void extract(String targetDir) throws IOException 
	{
		String signature = readZeroString();
		int max = readInt();
		
		if(!"bbassdata 1".equals(signature))
			BroomMain.fail("Wrong signature");
		
		//System.out.println("sig "+signature+" total files: "+max);
		System.out.println("Total files: "+max);
		
		int nFile = 0;
		while(nFile < max)
		{
			System.out.println("File "+nFile);
			readDirRecord();
			nFile++;
		}

		// some more strange records here
		extractParameters(targetDir);
		
		
		nFile = 0;		
		for( DirRecord r : records )
		{
			System.out.println("Loading file "+nFile);
			r.extract(dis,targetDir);
			nFile++;
		}
		
	}

	private void extractParameters(String targetDir) throws IOException, FileNotFoundException 
	{
		StringBuilder params = new StringBuilder();
		
		int extra = readInt();
		int nExtra = 0;
		while(nExtra < extra)
		{
			//System.out.println("Extra "+nExtra);

			String name = readZeroString();
			int data = readInt();

			String hex = Integer.toHexString(data);
			
			System.out.println("Extra "+nExtra+": "+name+" = "+data+" (0x"+hex+")");
			params.append(name+" = "+data+" (0x"+hex+")"+"\r\n");
			
			nExtra++;
		}
		
		File target = new File(targetDir,"_broomstick_parameters.txt");
		System.out.println("Store to "+target);

		//target.createNewFile();
		target.getParentFile().mkdirs();
		try (PrintWriter out = new PrintWriter(target)) {
		    out.println(params.toString());
		}
	}

	private void readDirRecord() throws IOException {
		String name = readZeroString();
		int pos = readInt();
		
		DirRecord r = new DirRecord(name,pos);
		records.add(r);
		
	}

	
	
	private int readInt() throws IOException {
		int b0 = dis.readByte() & 0xFF;
		int b1 = dis.readByte() & 0xFF;
		int b2 = dis.readByte() & 0xFF;
		int b3 = dis.readByte() & 0xFF;
		
		return b0 | (b1 <<8) | (b2 <<16) | (b3 <<24);
	}

	private String readZeroString() throws IOException 
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

}
