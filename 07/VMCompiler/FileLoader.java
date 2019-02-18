package VMCompiler;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.*;

public class FileLoader
{
    private static JFileChooser fc = new JFileChooser("..");
    private static String fileName = "";
    private static File file;
    private static Vector<String> stringList = new Vector<String>();
  
    public static String getFileName(){return fileName;}
    public static Vector<String> getStringList(){return stringList;}
    
    // load the strings from a text (.asm) file
    public static Vector<String> loadFile(JFrame parent)
    {
        int returnVal = fc.showOpenDialog(parent);
        
        if (returnVal == JFileChooser.APPROVE_OPTION) 
        {
            file = fc.getSelectedFile();
            fileName = file.getAbsolutePath();
            if(fileName.endsWith(".asm"))
            {
                getStrings();
            }
            else
            {
                fileName = "";
                String errMsg = "Target file must be a .asm file.";
                JOptionPane.showMessageDialog(parent, errMsg, "Woah, Bummer", JOptionPane.ERROR_MESSAGE);
            }
            
        } 
        else 
        {           
            fileName = "";
            String errMsg = "Unable to load file.";
            JOptionPane.showMessageDialog(parent, errMsg, "Woah, Bummer", JOptionPane.ERROR_MESSAGE);
        }
        return stringList;
    }
    
    // load stringList with the contents of a text file
    public static void getStrings()
    {
        stringList = new Vector<String>();  // clear the string list
        if(file == null)
            return;
        
        try
		{
			Scanner inFile = new Scanner(file);
            
            // load initial strings
			while(inFile.hasNext())
            {
                stringList.add(inFile.nextLine());
            }
            
            inFile.close();
        }
		catch(Exception ex)
		{
			System.out.println(ex.toString());
			System.out.println(ex.getStackTrace());
		}
    }

}