package mkz.kvnote.ui.panels;

import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mkz.kvnote.db.DbHandler;
import mkz.kvnote.parser.KVParser;
import mkz.kvnote.settings.Settings;
import mkz.util.io.IO;

public class GMainPanelController
{
	@FXML
	private Label lblFileName;

	@FXML
	private Label lblTableName;

	@FXML
	private TitledPane tpCommand;

	@FXML
	private Label lblTableNameStr;

	@FXML
	private ListView<String> lvwSearchResult;

	@FXML
	private CheckMenuItem cmiOutputAOT;
	
	@FXML
	private CheckMenuItem cmiOutputWrapText;
	
	@FXML
	private TextField tfdSearchInput;

	@FXML
	private TextArea taaStatusConsole;

	@FXML
	private Label lblCmdTxt;

	@FXML
	private BorderPane bpRoot;

	@FXML
	private CheckMenuItem cmiOutputACB;

	@FXML
	private Label lblKeyStr;

	@FXML
	private Button btnToDb;

	@FXML
	private Button btnResetSearch;
	
	@FXML
	private MenuItem miOutputToClipboard;

	@FXML
	private TitledPane tpOutput;

	@FXML
	private TextField tfdCommand;

	@FXML
	private Button btnSearchInput;

	@FXML
	private MenuItem miVarsToClipboard;

	@FXML
	private TextField mitfdVarsSetTable;

	@FXML
	private MenuItem miOutputFromClipboard;

	@FXML
	private TextArea taaOutput;

	@FXML
	private TextField tfdKey;

	@FXML
	private Button btnDeleteDb;

	@FXML
	private CheckMenuItem cmiVarsACB;

	@FXML
	private TitledPane tpStatus;

	@FXML
	private MenuItem miVarsFromClipboard;

	@FXML
	private Button btnToOutput;

	@FXML
	private CheckMenuItem cmiVarsAOT;

	@FXML
	void bpRoot_KeyPress(KeyEvent event)
	{
		if(event.isControlDown())
		{
			if(event.getCode()==KeyCode.S)
			{
				IO.dbOutD("Ctrl+S pressed.");
				_updateDbContent();
			}
			else if(event.getCode()==KeyCode.Q)
			{
				IO.dbOutD("Ctrl+Q pressed.");
				_resetContent();
				taaOutput.requestFocus();
			}
			else if(event.getCode()==KeyCode.R)
			{
				IO.dbOutD("Ctrl+R pressed.");
				_updateKeyContent();
				taaOutput.requestFocus();
			}
			else if(event.getCode()==KeyCode.F)
			{
				IO.dbOutD("Ctrl+F pressed.");
//				_resetSearch();
				tfdSearchInput.selectAll();
				tfdSearchInput.requestFocus();
			}
			
			else if(event.getCode()==KeyCode.ENTER)
			{
				IO.dbOutD("Ctrl+Enter pressed.");
				_updateKeyContent();
			}
			else if(event.getCode()==KeyCode.T)
			{
				IO.dbOutD("Ctrl+T pressed.");
				_openNewTable();
			}
			if(event.getCode()==KeyCode.K)
			{
				IO.dbOutW("TODO: Open key");
			}
		}
		else if(event.isAltDown())
		{
			if(event.getCode()==KeyCode.K)
			{
				tfdKey.selectAll();
				tfdKey.requestFocus();
			}
			else if(event.getCode()==KeyCode.S)
			{
				tfdSearchInput.selectAll();
				tfdSearchInput.requestFocus();
			}
			else if(event.getCode()==KeyCode.T)
			{
				taaOutput.selectAll();
				taaOutput.requestFocus();
			}
		}
	}

	@FXML
	void tfdKey_KeyPress(KeyEvent event)
	{
		if(event.getCode()==KeyCode.ENTER)
		{
			IO.dbOutD("Enter pressed.");
			_updateDbContent();
		}
	}
	

	@FXML
	void tfdCommand_KeyPress(KeyEvent event)
	{
		if(event.getCode()==KeyCode.ENTER)
		{
			IO.dbOutD("Enter pressed.");
			_execCommand();
		}
	}

	@FXML
	void taaOutput_KeyPressed(KeyEvent event)
	{
//		if(event.isControlDown())
//		{
//			if(event.getCode()==KeyCode.S)
//			{
//				IO.dbOutD("Ctrl+S pressed.");
//				_updateDbContent();
//			}
//			else if(event.getCode()==KeyCode.X)
//			{
//				IO.dbOutD("Ctrl+X pressed.");
//				_resetContent();
//			}
//			else if(event.getCode()==KeyCode.R)
//			{
//				IO.dbOutD("Ctrl+R pressed.");
//				_updateKeyContent();
//			}
//		}
	}
	
	@FXML
	void taaOutput_OnScroll(ScrollEvent event)
	{
//		IO.dbOutD("Action trigerred.");
		if(event.isControlDown())
		{
			double lDelta = event.getDeltaY();
			IO.dbOutV("Delta = "+lDelta);
			if(lDelta>0)
			{
				_fontSizeUp();
			}
			else if(lDelta<0)
			{
				_fontSizeDown();
			}
			
//			IO.dbOutD("Mpl is: "+event.getMultiplierY());
//			IO.dbOutD("Delta is: "+event.getDeltaY());
		}
	}
	

	@FXML
	void miOutputToClipboard_Action(ActionEvent event)
	{
		IO.dbOutD("Action started.");
		_contentToClipboard(true);
	}

	@FXML
	void miOutputFromClipboard_Action(ActionEvent event)
	{
		IO.dbOutD("Action started.");
		_contentFromClipboard();
	}

	@FXML
	void cmiOutputAOT_Action(ActionEvent event)
	{
		IO.dbOutD("Action started.");
		mSettings.window.always_on_top=cmiOutputAOT.selectedProperty().get();
		_updateAlwaysOnTop();
	}

	@FXML
	void cmiOutputACB_Action(ActionEvent event)
	{
		IO.dbOutD("Action started.");
		mSettings.app.auto_clipboard=cmiOutputACB.selectedProperty().get();
		_updateAutoClipboard();
	}

	@FXML
	void tfdSearchInput_KeyPress(KeyEvent event)
	{
//		IO.dbOutD("Code is: "+event.getCode());
		
		if(event.getCode()==KeyCode.ENTER)
		{
			IO.dbOutD("Enter pressed.");
			_updateKeysTable();
		}
		else if(event.getCode()==KeyCode.UP) _searchResultUp();
		else if(event.getCode()==KeyCode.DOWN) _searchResultDown();
	}

	
	@FXML
    void lvwSearchResult_Clicked(MouseEvent event) 
	{
//		IO.dbOutD("Action started.");
		if (event.getClickCount() == 2) 
		{
//			mCurrentKey = lvwSearchResult.getSelectionModel().getSelectedItem();
			IO.dbOutD("Double clicked, key is: "+lvwSearchResult.getSelectionModel().getSelectedItem());
			_updateKeyContent();
	    }
    }
	

	@FXML
	void btnSearchInput_Action(ActionEvent event)
	{
		IO.dbOutD("Action started.");
		_updateKeysTable();
	}


	@FXML
	void btnResetSearch_Action(ActionEvent event)
	{
		IO.dbOutD("Action started.");
		_resetSearch();
		
	}
	
	@FXML
	void btnToOutput_Action(ActionEvent event)
	{
		IO.dbOutD("Action started.");
		_updateKeyContent();
	}

	@FXML
	void btnToDb_Action(ActionEvent event)
	{
		IO.dbOutD("Action started.");
		_updateDbContent();
	}

	@FXML
	void btnDeleteDb_Action(ActionEvent event)
	{
		IO.dbOutD("Action started.");
		_deleteFromDb();
	}

	@FXML
	void miVarsToClipboard_Action(ActionEvent event)
	{
		IO.dbOutD("Action started.");

	}

	@FXML
	void miVarsFromClipboard_Action(ActionEvent event)
	{
		IO.dbOutD("Action started.");

	}

	@FXML
	void cmiVarsAOT_Action(ActionEvent event)
	{
		IO.dbOutD("Action started.");
		mSettings.window.always_on_top=cmiVarsAOT.selectedProperty().get();
		_updateAlwaysOnTop();
	}

	@FXML
	void cmiVarsACB_Action(ActionEvent event)
	{
		IO.dbOutD("Action started.");
		mSettings.app.auto_clipboard=cmiVarsACB.selectedProperty().get();
		_updateAutoClipboard();
		
	}
	
	@FXML
	void cmiOutputWrapText_Action(ActionEvent event)
	{
		IO.dbOutD("Action started.");
		mSettings.app.wrap_text=cmiOutputWrapText.selectedProperty().get();
		_updateWrapText();
	}


	@FXML
	void mitfdVarsSetTable_Action(ActionEvent event)
	{
		IO.dbOutD("Action started.");

	}

	private Settings mSettings=null;
	private Stage mProgramStage = null;
	private String mCurrentKey = null;
	
	private int mCurrentFontSize = 12;
	
	private KVParser mCommandParser = null;
	
	private static final DbHandler KV = DbHandler.getInstance();
	private static final Clipboard CLIPBOARD = Clipboard.getSystemClipboard();
	
	public void init(Settings aSettings, Stage aProgramStage)
	{
		//
		// Copy references
		//
		
		mSettings=aSettings;
		mProgramStage=aProgramStage;
		
		//
		// Init Engine
		//
		
		KV.open(mSettings.file, mSettings.table);
		mCommandParser = new KVParser();
		
		//
		// Add listeners
		//
		
		tpCommand.expandedProperty().addListener((obsv,ov,nv)->
		{
			if(nv!=null)
			{
				mSettings.window.cmd_expanded=nv;
			}
		});
		
		tpStatus.expandedProperty().addListener((obsv,ov,nv)->
		{
			 if(nv!=null)
			 {
				 mSettings.window.status_expanded=nv;
				 IO.dbOutD("New value: "+mSettings.window.status_expanded);
			 }
		});
		
		//
		// Redirect logs to console
		//
		IO.Options.debug_print_override = (msg) -> { Platform.runLater(() -> {taaStatusConsole.appendText(msg+System.lineSeparator());});}; // lambda spam..
		IO.Options.normal_print_override = (msg) -> { Platform.runLater(() -> {taaStatusConsole.appendText(msg+System.lineSeparator());});};
		IO.Options.log_level = mSettings.app.log_level;
		
		_updateSettings();
		
	}
	
	private void _updateSettings()
	{
		_updateAutoClipboard();
		_updateAlwaysOnTop();
		_updateKeysTable();
		_updateFileName();
		_updateTableName();
		_updateCommandExpanded();
		_updateStatusExpanded();
		_updateFont();
		_updateWrapText();
//		_safeSettings();
	}
	
	private void _contentToClipboard(boolean considerSelection)
	{
		String lToPut = null;
		if(considerSelection)
		{
			String lSelection = taaOutput.getSelectedText();
			if(lSelection==null || lSelection.equals(""))
			{
				IO.dbOutV("Nothing selected, assigning all to clipboard.");
				lToPut=taaOutput.getText();
			}
			else
			{
				IO.dbOutV("Assigning selection to clipboard.");
				lToPut=lSelection;
			}
		}
		else lToPut = taaOutput.getText();
		
		final ClipboardContent content = new ClipboardContent();
		content.putString(lToPut);
		CLIPBOARD.setContent(content);
		IO.dbOutD("Content assigned to clipboard.");
	}
	
	private void _contentFromClipboard()
	{
		final String lContent = (String) CLIPBOARD.getContent(DataFormat.PLAIN_TEXT);
		taaOutput.setText(lContent);
	}
	
	private void _updateCommandExpanded()
	{
		tpCommand.setExpanded(false);
//		tpCommand.setExpanded(mSettings.window.cmd_expanded);
	}
	
	private void _updateStatusExpanded()
	{
		tpStatus.setExpanded(false);
//		tpStatus.setExpanded(mSettings.window.status_expanded);
	}
	
	private void _updateFileName()
	{
		lblFileName.setText(mSettings.file);
	}
	
	private void _updateTableName()
	{
		lblTableName.setText(mSettings.table);
	}
	
	private void _updateAutoClipboard()
	{
		cmiOutputACB.selectedProperty().set(mSettings.app.auto_clipboard);
		cmiVarsACB.selectedProperty().set(mSettings.app.auto_clipboard);
	}
	
	private void _updateAlwaysOnTop()
	{
		cmiOutputAOT.selectedProperty().set(mSettings.window.always_on_top);
		cmiVarsAOT.selectedProperty().set(mSettings.window.always_on_top);
		mProgramStage.setAlwaysOnTop(mSettings.window.always_on_top);
	}
	

	private void _updateWrapText()
	{
		taaOutput.setWrapText(mSettings.app.wrap_text);
		cmiOutputWrapText.selectedProperty().set(mSettings.app.wrap_text);
	}
	
	// avoids reflection overkill
	private Region mContentRegion=null;
	private Region _getContentRegion()
	{
		return mContentRegion==null ? (Region) taaOutput.lookup( ".content" ) : mContentRegion;
	}
	
	private void _updateFont() // from settings
	{
		IO.dbOutV("Setting font: "+mSettings.app.font.font);
		IO.dbOutV("Setting font size: "+mSettings.app.font.default_size);
		
		taaOutput.setStyle("-fx-font-family: '"+mSettings.app.font.font+"'"+ //
				(mSettings.app.font.monospace ? " , monospace;" : ";")+ //
				("-fx-text-fill: "+mSettings.app.font.font_color+"; ")+ //
				("-fx-font-size: "+mSettings.app.font.default_size+"pt")); //

		_getContentRegion().setStyle( "-fx-background-color: " + mSettings.app.font.bg_color);
		
		mCurrentFontSize=mSettings.app.font.default_size;
	}
	
	// temp update: not saveable
	private void _updateFontSize(int aFontSize) // from current value
	{
		taaOutput.setStyle("-fx-font-family: '"+mSettings.app.font.font+"'"+ //
				(mSettings.app.font.monospace ? " , monospace;" : ";")+ //
				("-fx-text-fill: "+mSettings.app.font.font_color+"; ")+ //
				("-fx-font-size: "+aFontSize+"pt")); //
		
		_getContentRegion().setStyle( "-fx-background-color: " + mSettings.app.font.bg_color);
		
	}
	
	private void _fontSizeUp()
	{
		mCurrentFontSize++;
		IO.dbOutV("Updating font size, current: "+mCurrentFontSize);
		_updateFontSize(mCurrentFontSize);
	}
	
	private void _fontSizeDown()
	{
		if(mCurrentFontSize>8)
		{
			mCurrentFontSize--;
			IO.dbOutV("Updating font size, current: "+mCurrentFontSize);
			_updateFontSize(mCurrentFontSize);
		}
	}
	
	private void _updateKeysTable()
	{
		String lValue=tfdSearchInput.getText();
//		IO.dbOutD("lValue = "+lValue);
		ObservableList<String> lKeys = FXCollections.observableArrayList(KV.getKeysList("%"+lValue+"%"));
		lvwSearchResult.setItems(lKeys);
	}
	
	private void _updateKeyContent()
	{
		mCurrentKey=lvwSearchResult.getSelectionModel().getSelectedItem();
		if(mCurrentKey!=null && !mCurrentKey.equals(""))
		{
			String lContent = KV.retreiveValue(mCurrentKey);
			tfdKey.setText(mCurrentKey);
			taaOutput.setText(lContent);
			if(mSettings.app.auto_clipboard) _contentToClipboard(false); // don't consider selection (if any)
		}
		else IO.dbOutD("Not loading: No key selected.");
	}
	
	private void _updateDbContent()
	{
		mCurrentKey=tfdKey.getText();
		if(mCurrentKey==null || mCurrentKey.equals("")) mCurrentKey = _requestKey("No key specified","Please enter a key name:");
		if(mCurrentKey==null || mCurrentKey.equals(""))
		{
			IO.dbOutD("Process aborted: No key specified.");
			return;
		}
		
		boolean lAllowInsert=true;
		if(!mSettings.app.auto_overwrite)
		{
			if(KV.valueExists(mCurrentKey))
			{
				lAllowInsert=_requestYesNo("Key already exists", "Really overwrite key with name '"+mCurrentKey+"'?");
				if(!lAllowInsert) IO.dbOutD("Insert aborted by user.");
			}
		}
		if(lAllowInsert)
		{
			IO.dbOutD("Inserting new value for key "+mCurrentKey);
			if(KV.insertValue(mCurrentKey, taaOutput.getText(), true)) 
			{
//				_resetContent();
				_updateKeysTable();
			}
		}
	}
	
	private void _openNewTable()
	{
		String lTable=_requestKey("Open Table", "Please enter table name:");
		if(lTable!=null) mSettings.table=lTable;
		KV.open(mSettings.file, mSettings.table);
		_updateTableName();
		_resetContent();
		_updateKeysTable();
	}
	
	private void _deleteFromDb()
	{
		mCurrentKey = lvwSearchResult.getSelectionModel().getSelectedItem();
		
		if(_requestYesNo("Confirmation", "Really delete key with name '"+mCurrentKey+"'?")) 
		{
			if(KV.deleteValue(mCurrentKey)) _updateKeysTable();
		}
		else IO.dbOutD("Delete aborted by user.");
	}
	
	private void _resetContent()
	{
		taaOutput.setText("");
		tfdKey.setText("");
		mCurrentKey=null;
	}
	
	private void _execCommand()
	{
		String lCmd = tfdCommand.getText();
		
		try
		{
			ArrayList<String> lRes=mCommandParser.exec(lCmd.split(" "));
			
			StringBuilder sb = new StringBuilder();
			for(String iRes:lRes)
			{
				sb.append(iRes);
				sb.append(System.lineSeparator());
			}
			
			taaOutput.setText(sb.toString());
			tfdCommand.setText("");
			
			if(mSettings.app.auto_clipboard) _contentToClipboard(false);
			_updateKeysTable();
		}
		catch (Exception e)
		{
			IO.dbOutE(e);
		}
		
	}
	
//	private void _resetKey()
//	{
//		taaOutput.setText("");
//	}
	
//	private void _initKeysTable()
//	{
//		ObservableList<String> lKeys =FXCollections.observableArrayList(KV.getKeysList("%"));
//		lvwSearchResult.setItems(lKeys);
//	}
	
//	private void _safeSettings()
//	{
//		Settings.safeToFile(mSettings, KVNote.configPath);
//	}
	
	private void _resetSearch()
	{
		tfdSearchInput.setText("");
		_updateKeysTable();
	}
	
	private boolean _requestYesNo(String aTitle, String aText)
	{
		mProgramStage.setAlwaysOnTop(false);
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(aTitle);
		alert.setHeaderText(null);
		alert.setContentText(aText);

	    Button yesButton = (Button) alert.getDialogPane().lookupButton( ButtonType.OK );
	    yesButton.setText("_"+yesButton.getText());
	    Button noButton = (Button) alert.getDialogPane().lookupButton( ButtonType.CANCEL );
	    noButton.setText("_"+noButton.getText());
		
		Optional<ButtonType> result = alert.showAndWait();
		
		mProgramStage.setAlwaysOnTop(mSettings.window.always_on_top);
		return result.get() == ButtonType.OK;
	}
	
	
	private static String _requestKey(String aTitle, String aContent)
	{
		String rVal = null;
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle(aTitle);
		dialog.setHeaderText(null);
		dialog.setContentText(aContent);

		dialog.initModality(Modality.APPLICATION_MODAL);
		
		Optional<String> result = dialog.showAndWait();
		
		if(result.isPresent())
		{
			rVal= result.get();
			IO.dbOutV("User entered key: "+rVal);
			return rVal;
		}
		
		return rVal;
	}
	
	private void _searchResultDown()
	{
		int lItem=lvwSearchResult.getSelectionModel().getSelectedIndex();
		
		IO.dbOutD("Item = "+lItem);
		
		if(lItem<0)lvwSearchResult.getSelectionModel().clearAndSelect(0);
		else if(lItem<lvwSearchResult.getItems().size()-1)lvwSearchResult.getSelectionModel().clearAndSelect(lItem+1);
	}

	private void _searchResultUp()
	{
		int lItem=lvwSearchResult.getSelectionModel().getSelectedIndex();
		
		IO.dbOutV("Item = "+lItem);
		
		if(lItem<0) lvwSearchResult.getSelectionModel().clearAndSelect(0);
		else if(lItem>0) lvwSearchResult.getSelectionModel().clearAndSelect(lItem-1);
	}
	
	

	
}