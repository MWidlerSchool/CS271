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
            if(fileName.endsWith(".vm"))
            {
                getStrings();
            }
            else
            {
                fileName = "";
                String errMsg = "Target file must be a .vm file.";
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
    
    // load the strings all .vm files in a folder file
    public static Vector<String> loadFolder(JFrame parent)
    {
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setAcceptAllFileFilterUsed(false);
        int returnVal = fc.showOpenDialog(parent);
        String folderName = "";
        stringList = new Vector<String>();
        
        if (returnVal == JFileChooser.APPROVE_OPTION) 
        {
            file = fc.getSelectedFile();
            folderName = file.getAbsolutePath();
            String[] fileArr = file.list();
            for(String curName : fileArr)
            {
                curName = folderName + "/" + curName;
                if(curName.endsWith(".vm"))
                {
                    fileName = curName;
                    file = new File(fileName);
                    getStrings(false);
                }
                // no else because non .vm files are rejected, and there's probably a bunch of them
            }
            
        } 
        else 
        {           
            fileName = "";
            String errMsg = "Unable to load folder.";
            JOptionPane.showMessageDialog(parent, errMsg, "Woah, Bummer", JOptionPane.ERROR_MESSAGE);
        }
        return stringList;
    }
    
    // load stringList with the contents of a text file
    public static void getStrings(){getStrings(true);}
    public static void getStrings(boolean clearFirst)
    {
        if(clearFirst)
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