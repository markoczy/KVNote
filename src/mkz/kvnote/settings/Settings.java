/**
 * File: KVNote::Settings.java
 *
 * @author Aleistar Mark�czy
 * 
 */
package mkz.kvnote.settings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import mkz.util.io.IO;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Settings", namespace = "http://www.mkz.kvnote.app/settings")
public class Settings
{
	//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//
	//
	// Default Values
	//
	//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//
	
	public static final String DEFAULT_DB_PATH = "vals.db";
	public static final String DEFAULT_VALTABLE_NAME = "T_VALUES";
	
	//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//
	//
	// All Settings
	//
	//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//
	
	public String file = DEFAULT_DB_PATH;
	public String table = DEFAULT_VALTABLE_NAME;
	public App app = new App();
	public Window window = new Window();
	
	//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//
	//
	// Definition Classes
	//
	//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//
	
	//
	// + Settings
	// |
	// +-+ App
	// |
	// +-+ Window
	//
	
	/**
	 * Application specific settings.
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class App
	{
		public int log_level=4;
		public boolean auto_clipboard = false;
		public boolean auto_overwrite = false;
		public boolean wrap_text = true;
		public int max_undo_steps = 20;
		public String notepad_path = "notepad";
		
		public Font font = new Font();
		
		@XmlAccessorType(XmlAccessType.FIELD)
		public static class Font
		{
			public String font="Consolas";
			public boolean monospace = true;
			public int default_size=12;
			public String font_color="#000000";
			public String bg_color="#ffffff";
		}
	}
	
	/**
	 * Window specific settings.
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class Window
	{
		public boolean always_on_top = false;
		public boolean cmd_expanded = true;
		public boolean status_expanded = true;
		
		public double posx=200;
		public double posy=200;
		public double width=600;
		public double height=600;
	}
	
	//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//
	//
	// Safe / Load Mechanism
	//
	//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//-//
	
	/**
	 * Gets the Settings from file.
	 *
	 * @param aPath the reference path
	 * @return the from file
	 */
	public static Settings getFromFile(String aPath)
	{
		Object object = null;
		try
		{
			JAXBContext context = JAXBContext.newInstance(Settings.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			object = Settings.class.cast(unmarshaller.unmarshal(new BufferedReader(new InputStreamReader(new FileInputStream(aPath), "UTF-8"))));
		}
		catch (Exception e)
		{
			IO.dbOutE(e);
		}
		return (Settings) object;
	}

	/**
	 * Safe settings to file.
	 *
	 * @param aSettings the reference settings
	 * @param aPath the reference path
	 * @return true, if successful
	 */
	public static boolean safeToFile(Settings aSettings, String aPath)
	{
		try
		{
			JAXBContext context = JAXBContext.newInstance(Settings.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(aSettings, new File(aPath));
			return true;
		}
		catch (Exception e)
		{
			IO.dbOutE(e);
			return false;
		}
	}

}