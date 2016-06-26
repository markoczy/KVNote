package mkz.test;

import mkz.kvnote.db.DbHandler;
import mkz.kvnote.parser.KVParser;
import mkz.util.io.IO;

public class TestMain
{
	public static void main(String[] args)
	{
//		IO.jOut("Hello user", false);
//		
//		String s = IO.jIn("say a value: ",false);
//		IO.jOut("Value is: "+s);
		
//		DbHandler lKv = new DbHandler();
//		lKv.open("vals.db","T_VALUES2"); 
//		
//		
//		String lPref="0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789";
//		for(int i=0;i<10;i++)
//		{
//			lKv.insertValue("test"+i, lPref+i, true);
//		}
//		
//		for(String iStr:lKv.getValuesList("test%")) IO.jOut(iStr);
		
		
//		KVParser lP = new KVParser("vals.db","T_VALUES2");
//		
//		try
//		{
//			ArrayList<String> lRet = lP.exec(new String[]{"s","t1","hello db"});
//			
//			IO.jOut("Ret is: "+lRet.get(0));
//		}
//		catch (Exception e)
//		{
//			IO.dbOutE(e);
//		}
		
		
		DbHandler.getInstance().open();
		KVParser lParser = new KVParser();
		boolean lExit = false;
		while(!lExit)
		{				
			String lVal=IO.jIn("<< ", false);
			
			if(!lVal.equals("exit"))
			{
				try
				{
					for(String iStr:lParser.exec(lVal.split(" "))) 
					{
						if(iStr.startsWith("OK#") || iStr.startsWith("NOK#")) IO.jOut("># "+iStr);
						else IO.jOut(">> "+iStr);
					}
				}
				catch (Exception e)
				{
					IO.dbOutE(e);
				}
			}
			else lExit=true;
			
		}
		
		
		
	}
}
