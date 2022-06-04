package ru.dz.bluearp;

public interface BlueDefs {
	/*
	static final int cInt8 = -1; 
	static final int cBit8 = -1;

	static final int patVer = -1;

	static final int maxPrg = -1;
	static final int maxBar = -1;
	static final int numChn = -1;
	static final int maxChn = -1;

	static final int maxPag = -1;
	 */

	// TODO Check if values are correct

	static final int cInt8 = 1; // Suppose to be runtime only enum
	static final int cBit8 = 2; // 

	static final int patVer = 17; // Based on distr .fxp file contents

	static final int maxPrg = 128; // Based on '#define gMaxPrograms 128'
	static final int maxBar = -1; // TODO find val
	static final int numChn = -1; // TODO find val
	static final int maxChn = -1; // TODO find val

	static final int maxPag = -1; // TODO find val

	// Stored in 'Info' chunk of VST3 '.vstpreset' file
	static final String vts3XML = 
			"<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n" +
			"<MetaInfo>\r\n" +
			"	<Attribute id=\"MediaType\" value=\"VstPreset\" type=\"string\" flags=\"writeProtected\"/>\r\n" +
			"	<Attribute id=\"PlugInCategory\" value=\"Instrument|Synth\" type=\"string\" flags=\"writeProtected\"/>\r\n" +
			"	<Attribute id=\"PlugInName\" value=\"BlueARP\" type=\"string\" flags=\"writeProtected\"/>\r\n" +
			"	<Attribute id=\"PlugInVendor\" value=\"omg-instruments\" type=\"string\" flags=\"writeProtected\"/>\r\n" +
			"</MetaInfo>\r\n" +
			"";
}
