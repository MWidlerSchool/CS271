package VMCompiler;

import java.util.*;
import java.util.regex.Pattern;

public class FileProcessor
{
    public static Vector<String> compile(Vector<String> stringList)
    {
        stringList = cleanStrings(stringList);
   //     stringList = setConstants(stringList);
        return stringList;
    }
    
    // remove comments, leading whitespace, and trailing whitespace
    public static Vector<String> cleanStrings(Vector<String> stringList)
    {
        Vector<String> newList = new Vector<String>();
        
        // remove line comments
        for(String str : stringList)
        {
            String newStr = "";
            // a one-character command will be ignored, but this is less than the minimum legal command
            for(int i = 0; i < str.length() - 1; i++)
            {
                if(str.charAt(i) == '/' && str.charAt(i + 1) == '/')
                    break;
                newStr = newStr + str.charAt(i);
                if(i == str.length() - 2)
                    newStr += str.charAt(i + 1);
            }
            // remove leading and trailing whitespace and add to new list.
            if(newStr.length() > 0)
                newList.add(newStr.trim());
        }
        
        return newList;
    }
    /*
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
                newList.add(str);
            }
            // is a constant declaration
            else
            {
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
    }*/
    
    
    private static void test1()
    {

    }
    
    public static void main(String[] args)
    {
        test1();
    }
}