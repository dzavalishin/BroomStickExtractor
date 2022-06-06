package ru.dz.bluearp;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

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
	
	private static final String VST3_REAL = "C:/Users/dz/Documents/VST3 Presets/omg-instruments/BlueARP/Classic_Disco.mid.vstpreset";
	private static final String VST3_USER_DIR = "C:/Users/dz/Documents/VST3 Presets/omg-instruments/BlueARP"; 
	

	
	public static void main(String[] args)  
	{
		try {
			//testProcess();
			//processFile("midi/Disco_Octaves.mid");
			//processFile("midi/Classic_Disco.mid");
			//processFile("midi/Call_Me_Mad.mid"); 
			//processFile("midi/Disco_Sole.mid");
			//processFile("midi/Room4aKick.mid"); 
			
			//processFile("midi/70s_Dim_Bouncer.mid");
			//processFile("midi/Aggression.mid");			
			processFile("midi/In10CT.mid");
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}

	}


	private static void processFile( String midiFilePath ) throws InvalidMidiDataException, IOException {
		Path mpt = Paths.get(midiFilePath);
		
		Path mfn = mpt.getFileName();
		Path vpt = Paths.get(VST3_USER_DIR, mfn.toString() + VST3_SUFFIX );

		System.out.printf("Will convert '%s' to '%s'\n", mpt, vpt );
		
		MidiParser mp = new MidiParser(mpt.toString());
		mp.convert();		
		BlueBank bb = mp.getBlueArpBank();
		
		
		new VST3Writer(bb, vpt.toString());
	}

	private static void testProcess() throws InvalidMidiDataException, IOException {
		/*
		VST3Reader vir = new VST3Reader(VST3_FILE);
		VST3Writer viw = new VST3Writer(vir.getBank(), VST3_FILE+".new");
				
		System.exit(32);
		*/
		
		//BlueFxp fxp = new BlueFxp();

		//fxp.load(FXP_FILE);
		//fxp.save(FXP_FILE+".new");
		

		MidiParser mp = new MidiParser(MIDI_FILE);
		mp.convert();
		
		//BlueBank bb = new BlueBank();
		
		BlueBank bb = mp.getBlueArpBank();
		
		new VST3Writer(bb, VST3_REAL);
		/*VST3Writer viw = */
		//new VST3Writer(bb, MIDI_FILE+VST3_SUFFIX);
	}

	
}	
