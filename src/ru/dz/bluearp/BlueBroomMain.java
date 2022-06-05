package ru.dz.bluearp;

import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;

import ru.dz.bintools.VST3Reader;
import ru.dz.bintools.VST3Writer;
import ru.dz.broom.midi.MidiParser;

public class BlueBroomMain 
{
	//private static final String MIDI_FILE = "G:\\Projects\\BroomStickExtractor\\midi\\Disco_Octaves.mid";
	//private static final String MIDI_FILE = "midi/Disco_Octaves.mid";
	private static final String MIDI_FILE = "midi/Classic_Disco.mid";
	private static final String FXP_FILE = "bluearp/BlueARP_FactoryBank.fxb";
	private static final String VST3_FILE = "bluearp/dzBlueArpTest.vstpreset";
	private static final String VST3_SUFFIX = ".vstpreset";
	

	
	public static void main(String[] args)  
	{
		try {
			/*
			VST3Reader vir = new VST3Reader(VST3_FILE);
			VST3Writer viw = new VST3Writer(vir.getBank(), VST3_FILE+".new");
					
			System.exit(32);
			*/
			
			//BlueFxp fxp = new BlueFxp();

			//fxp.load(FXP_FILE);
			//fxp.save(FXP_FILE+".new");
			

			MidiParser mp = new MidiParser(MIDI_FILE);
			mp.dump();
			
			BlueBank bb = new BlueBank();
			
			/*VST3Writer viw = */new VST3Writer(bb, MIDI_FILE+VST3_SUFFIX);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
}	
