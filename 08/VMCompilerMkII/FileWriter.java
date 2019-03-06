package VMCompilerMkII;

import java.io.*;
import java.util.*;

// takes a passed filename, 
public class FileWriter
{
    public static boolean save(String inFile, Vector<String> asmList)
	{
        String outFileName = inFile.replaceAll(".vm", ".asm");
		PrintWriter outFile;
		try
		{
			outFile = new PrintWriter(outFileName);
			for(String str : asmList)
				outFile.println(str);
			outFile.close();
		}
		catch(Exception ex)
        {
            return false;
        }
        return true;
	}
    
}