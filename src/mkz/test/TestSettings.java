package mkz.test;

import java.io.File;
import java.io.IOException;

import mkz.kvnote.settings.Settings;

public class TestSettings
{
	public static void main(String[] args)
	{
		Settings s = new Settings();
		s.file="C:\\test.db";
		s.table="T_VALUES_X";
		s.app.auto_clipboard=true;
		s.window.always_on_top=true;
		Settings.safeToFile(s, "settings_test.xml");
		
		Settings s2 = Settings.getFromFile("settings_test.xml");
		System.out.println(s2.file);
		System.out.println(s2.table);
		System.out.println(s2.app.auto_clipboard);
		
		try
		{
			System.out.println("Current path: "+(new File(".").getCanonicalPath()));
			System.out.println("Current path2: "+System.getProperty("user.dir"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
//	Settings.safeToFile(s,"settings.xml");
}
