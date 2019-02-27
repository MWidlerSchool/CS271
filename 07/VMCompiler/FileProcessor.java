package VMCompiler;

import java.util.*;
import java.util.regex.Pattern;

public class FileProcessor
{
    public static Vector<String> compile(Vector<String> stringList)
    {
        VMCommand.init();
        stringList = cleanStrings(stringList);                  // clean strings
        Vector<VMLine> lineList = convertStrings(stringList);   // convert to constants and ints
        stringList = convertLineList(lineList);                 // convert to .asm
        
        return stringList;
    }
    
    // returns asm code
    public static Vector<String> convertLineList(Vector<VMLine> lineList)
    {
        // initialize the stack pointer
        Vector<String> asmList = VMCommand.initStack();
        
        // add the code
        for(VMLine line : lineList)
            asmList = VMCommand.concat(asmList, convertLineItem(line));
        
        // add terminating infinite loop
        asmList = VMCommand.concat(asmList, VMCommand.terminal());
        return asmList;
    }
    
    public static Vector<String> convertLineItem(VMLine line)
    {
        Vector<String> asmList = new Vector<String>();
        
        // single-word commands
        if(line.arg1.isArithmeticOrLogical())
        {
            asmList = VMCommand.arithmeticOp(line.arg1);
        }
        else    // multi-word commands
        {
            // pushes
            if(line.arg1 == VMConstants.PUSH)
            {
                if(line.arg2 == VMConstants.CONSTANT)
                {
                    asmList = VMCommand.pushConstant(line.arg3);
                }
                else
                {
                    asmList = VMCommand.pushSegment(line.arg2, line.arg3);
                }
            }
            // pops
            else if(line.arg1 == VMConstants.POP)
            {
                asmList = VMCommand.popSegment(line.arg2, line.arg3);
            }
        }
        
        return asmList;
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
    
    // converts processed string list into VMLine obj list
    public static Vector<VMLine> convertStrings(Vector<String> stringList)
    {
        Vector<VMLine> lineList = new Vector<VMLine>();
        
        for(String str : stringList)
            lineList.add(new VMLine(str));
        
        return lineList;
    }
    
    
    private static void test1(Vector<String> stringList)
    {
        for(String str : stringList)
        {
            System.out.println(str);
        }
    }
    
    private static void test2(Vector<String> stringList, Vector<VMLine> lineList)
    {
        for(int i = 0; i < stringList.size(); i++)
        {
            VMLine line = lineList.elementAt(i);
            String lineStr = line.arg1.toString();
            if(line.arguments == 3)
            {
                lineStr += ", ";
                lineStr += line.arg2 + "";
                lineStr += ", ";
                lineStr += "" + line.arg3;
                
                
                //String.format(", %s, %d", line.arg2.toString(), line.arg3);
            }
            System.out.println(stringList.elementAt(i));
            System.out.println(lineStr);
        }
    }
    
    public static void main(String[] args)
    {
        //test1();
    }
}