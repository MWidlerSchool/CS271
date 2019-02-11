package HackAssembler;

import java.util.*;
import java.util.regex.Pattern;

public class FileProcessor
{
    public static Vector<String> compile(Vector<String> stringList)
    {
        stringList = cleanStrings(stringList);
        stringList = setConstants(stringList);
        return stringList;
    }
    
    // remove whitespace and comments from strings, set constants
    public static Vector<String> cleanStrings(Vector<String> stringList)
    {
        Vector<String> newList = new Vector<String>();
        
        // remove line comments
        for(String str : stringList)
        {
            String newStr = "";
            // a one-character command will be ignored, but the minimum legal Hack command is two characters.
            for(int i = 0; i < str.length() - 1; i++)
            {
                if(str.charAt(i) == '/' && str.charAt(i + 1) == '/')
                    break;
                newStr = newStr + str.charAt(i);
                if(i == str.length() - 2)
                    newStr += str.charAt(i + 1);
            }
            if(newStr.length() > 0)
                newList.add(newStr);
        }
        stringList = newList;
        newList = new Vector<String>();
        
        // remove all whitespace, removing empty lines
        String newStr = "";
        for(String str : stringList)
        {
            newStr = str.replaceAll("\\s", "");
            if(newStr.length() > 0)
                newList.add(newStr);
        }
        
        return newList;
    }
    
    // removes constant declarations, updates the constant list, and replaces variables with literals. Assumes list has been cleaned.
    private static Vector<String> setConstants(Vector<String> stringList)
    {
        Vector<String> newList = new Vector<String>();
        String str;
        // set constants
        for(int i = 0; i < stringList.size(); i++)
        {
            str = stringList.elementAt(i);
            // is a command
            if(str.charAt(0) != '(')
            {
                newList.add(str);/*
                // variable
                if(str.charAt(0) == '@' && Character.isLetter(str.charAt(1)))
                    HackConstants.add(str.substring(1, str.length()));*/
            }
            // is a constant declaration
            else
            {
         //       int refLoc = getNextCommand(stringList, i + 1);
           //     HackConstants.add(str.substring(1, str.length() - 1), refLoc);
                HackConstants.add(str.substring(1, str.length() - 1), newList.size());
            }
        }
        stringList = newList;
        newList = new Vector<String>();
        // set variables
        for(int i = 0; i < stringList.size(); i++)
        {
            str = stringList.elementAt(i);
            if(str.charAt(0) == '@' && Character.isLetter(str.charAt(1)) && HackConstants.has(str.substring(1)) == false)
                HackConstants.add(str.substring(1, str.length()));
        }
        
        // replace variables
        String newStr = "";
        for(String curStr : stringList)
        {
            if(curStr.charAt(0) == '@' && Character.isLetter(curStr.charAt(1)))
                newStr = "@" + HackConstants.get(curStr.substring(1));
            else
                newStr = curStr;
            newList.add(newStr);
        }
        return newList;
    }
    
    
    private static void test1()
    {
        Vector<String> stringList = new Vector<String>();
        stringList.add("  ..");
        stringList.add("");
        stringList.add("  ");
        stringList.add("//  ..");
        stringList.add("Dude");
        
        stringList = cleanStrings(stringList);
        for(String str : stringList)
            System.out.println(str);
    }
    
    public static void main(String[] args)
    {
        test1();
    }
}