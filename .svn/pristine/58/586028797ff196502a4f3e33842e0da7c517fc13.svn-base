package mkz.kvnote;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mkz.kvnote.settings.Settings;
import mkz.kvnote.settings.VersionInfo;
import mkz.kvnote.ui.panels.GMainPanelController;
import mkz.util.file.FileHandler;
import mkz.util.io.IO;
import mkz.util.jcli.Parser;

public class KVNote extends Application
{
	private static final String DEFAULT_CFG_PATH = System.getProperty("user.dir")+"\\kvnote_settings.xml";

	public static String configPath=DEFAULT_CFG_PATH;
	
	private static String _dbPath=null;
	private static String _table=null;
	
	private static Settings settings = null;
	
	public static void main(String[] args)
	{
		CmdArgsParser lArgsParser = new CmdArgsParser();
		
		for(String iArg:args)
		{
			try
			{
				lArgsParser.exec(new String[]{iArg});
			}
			catch (Exception e)
			{
				IO.dbOutE(e);
			}
		}
		
		launch(args);
	}

	@Override
	public void start(Stage aStage) throws Exception
	{
		// Load Settings
		//
		if (FileHandler.fileExists(configPath)) settings = Settings.getFromFile(configPath);
		else settings = new Settings();
		
		// Init GUI
		//
		FXMLLoader loader = new FXMLLoader();
		BorderPane root = (BorderPane) loader.load(GMainPanelController.class.getResource("GMainPanel.fxml").openStream());
		Scene scene = new Scene(root);
		
		aStage.getIcons().add(new Image("icon.png"));
		aStage.setTitle("KVNote V"+VersionInfo.getVersion());
		aStage.setScene(scene);
		
		aStage.setX(settings.window.posx);
		aStage.setY(settings.window.posy);
		aStage.setWidth(settings.window.width);
		aStage.setHeight(settings.window.height);
		
//		aStage.sizeToScene();
		aStage.show();
		
		//
		// Update file and table if available
		//
		if(_dbPath!=null) settings.file=_dbPath;
		if(_table!=null) settings.table=_table;
		
		
		// Init Controller
		//
		GMainPanelController controller = loader.getController();
		controller.init(settings, aStage);
		
		// Save cfg file when closing
		//
		aStage.setOnCloseRequest((we)->
		{
//			System.out.println("X = "+aStage.getX());
//			System.out.println("Y = "+aStage.getY());
//			System.out.println("W = "+aStage.getWidth());
//			System.out.println("H = "+aStage.getHeight());
			settings.window.posx=aStage.getX();
			settings.window.posy=aStage.getY();
			settings.window.width=aStage.getWidth();
			settings.window.height=aStage.getHeight();
			
			Settings.safeToFile(settings, configPath);
		});
		       
	}
	
	private static class CmdArgsParser extends Parser<Boolean>
	{
		public CmdArgsParser()
		{
			super.addCommand("cf:.+", (arr)->
			{
				configPath = arr[0].substring(3);
				IO.dbOutD("Parsed Command: Set config file. Path is: "+ configPath);
				return true;
			});
			
			// Set file
			super.addCommand("sf:.+", (arr)->
			{
				_dbPath = arr[0].substring(3);
				IO.dbOutD("Parsed Command: Set DB file. Path is: "+ _dbPath);
				return true;
			});
			
			// Set table
			super.addCommand("st:.+", (arr)->
			{
				_table = arr[0].substring(3);
				IO.dbOutD("Parsed Command: Set table. New table is: "+ _table);
				return true;
			});
			
			super.addCommand(".+", (arr)->
			{
				IO.dbOutE("The command could not be parsed, command is: "+arr[0]);
				return false;
			});
		}
	}

}
