package ru.dz.broom;

import java.io.IOException;

public class BroomMain {

	public static void main(String[] args) throws IOException 
	{
		//BroomExtractor e = new BroomExtractor("Z:/tmp/broom/GiftPack2.bin");
		//e.extract("G:/tmp/Broom");
		
		process("GiftPack1");
		process("GiftPack2");
		process("BroomstickBassLibrary");
	}

	private static void process(String name) throws IOException 
	{
		BroomExtractor e = new BroomExtractor("G:/tmp/broomin/"+name+".bin");
		e.extract("G:/tmp/broomout/"+name);
	}

	public static void fail(String msg) 
	{
		System.out.println("Failed: "+msg);
		System.exit(33);
	}

}
