package HackAssembler;

import java.io.*;
import java.util.*;

public class FileWriter
{
    public static final int NEGATIVE_SHIFT = (int)Math.pow(2, 13);
    public static final String C_INSTRUCTION_PREFIX = "111";
    
    public static void save(String inFile, Vector<String> asmList)
	{
        String outFileName = inFile.replaceAll(".asm", ".hack");
		PrintWriter outFile;
		
		try
		{
			outFile = new PrintWriter(outFileName);
            
			for(String str : asmList)
				outFile.println(getInstruction(str));
			outFile.close();
		}
		catch(FileNotFoundException fnfEx){System.out.println("File not found exception");}
		catch(Exception ex){System.out.println("Exception");}
	}
    
    public static String getInstruction(String str)
    {
        if(str.charAt(0) == '@')
            return getAInstruction(str);
        return getCInstruction(str);
    }
    
    // converts the passed string to it's A instruction binary form. Does not error check
    public static String getAInstruction(String str)
    {
        int val = new Integer(str.substring(1));
        return "0" + getBinaryString(val);
    }
    
    // converts the passed string to it's C instruction binary form. Does not error check
    public static String getCInstruction(String str)
    {
        return C_INSTRUCTION_PREFIX + getComp(str) + getDest(str) + getJump(str);
    }
    
    // returns the the passed intiger as a signed 15-digit binary string
    private static String getBinaryString(int decimal)
    {
        int isNegative = 0;
        if(decimal < 0)
        {
            isNegative = 1;
            decimal += NEGATIVE_SHIFT;
        }
        
        String bin = Integer.toBinaryString(decimal);
        // two seperate statements to not mess up unsigned numbers
        if(isNegative == 1)
            bin = String.format("%d%14s", isNegative, bin);
        else
            bin = String.format("%15s", bin);
        bin = bin.replaceAll(" ", "0");
        return bin;
    }
    
    // return the destination section of a C instruction
    private static String getDest(String instructionStr)
    {
        String a = "0";
        String d = "0";
        String m = "0";
        int index = instructionStr.indexOf("=");
        
        if(index != -1)
        {
            String destStr = instructionStr.substring(0, index);
            if(destStr.contains("A"))
                a = "1";
            if(destStr.contains("D"))
                d = "1";
            if(destStr.contains("M"))
                m = "1";
        }
        
        return a + d + m;
    }
    
    // return the computation section (acccccc) of a C instruction
    private static String getComp(String instructionStr)
    {
        String returnVal = "0000000";
        int startPos = instructionStr.indexOf("=") + 1;
        int endPos = instructionStr.indexOf(";");
        if(endPos == -1)
            endPos = instructionStr.length();
        String cStr = instructionStr.substring(startPos, endPos);
        // a = 0
        if(     cStr.equals("0"))    returnVal = "0101010";
        else if(cStr.equals("1"))    returnVal = "0111111";
        else if(cStr.equals("-1"))   returnVal = "0111010";
        
        else if(cStr.equals("D"))    returnVal = "0001100";
        else if(cStr.equals("A"))    returnVal = "0110000";
        else if(cStr.equals("!D"))   returnVal = "0001101";
        else if(cStr.equals("!A"))   returnVal = "0110001";
        else if(cStr.equals("-D"))   returnVal = "0001111";
        else if(cStr.equals("-A"))   returnVal = "0110011";
        
        else if(cStr.equals("D+1"))  returnVal = "0011111";
        else if(cStr.equals("A+1"))  returnVal = "0110111";
        else if(cStr.equals("D-1"))  returnVal = "0001110";
        else if(cStr.equals("A-1"))  returnVal = "0110010";
        else if(cStr.equals("D+A"))  returnVal = "0000010";
        else if(cStr.equals("D-A"))  returnVal = "0010011";
        else if(cStr.equals("A-D"))  returnVal = "0000111";
        else if(cStr.equals("D&A"))  returnVal = "0000000";
        else if(cStr.equals("D|A"))  returnVal = "0010101";
        
        // a = 1; replaces A with M
        else if(cStr.equals("M"))    returnVal = "1110000";
        else if(cStr.equals("!M"))   returnVal = "1110001";
        else if(cStr.equals("-M"))   returnVal = "1110011";
        else if(cStr.equals("M+1"))  returnVal = "1110111";
        else if(cStr.equals("M-1"))  returnVal = "1110010";
        else if(cStr.equals("D+M"))  returnVal = "1000010";
        else if(cStr.equals("D-M"))  returnVal = "1010011";
        else if(cStr.equals("M-D"))  returnVal = "1000111";
        else if(cStr.equals("D&M"))  returnVal = "1000000";
        else if(cStr.equals("D|M"))  returnVal = "1010101";
        
        return returnVal;
    }
    
    // return the jump segment of a C instruction
    private static String getJump(String instructionStr)
    {
        String returnStr = "000";
        int index = instructionStr.indexOf(";");
        
        if(index != -1)
        {
            String jumpStr = instructionStr.substring(index + 1);
            if(jumpStr.equals("JGT"))
                returnStr = "001";
            else if(jumpStr.equals("JEQ"))
                returnStr = "010";
            else if(jumpStr.equals("JGE"))
                returnStr = "011";
            else if(jumpStr.equals("JLT"))
                returnStr = "100";
            else if(jumpStr.equals("JNE"))
                returnStr = "101";
            else if(jumpStr.equals("JLE"))
                returnStr = "110";
            else if(jumpStr.equals("JMP"))
                returnStr = "111";
        }
        
        return returnStr;
    }

    
    
    public static void test1()
    {
        System.out.println(" " + getBinaryString(28));
        System.out.println(getAInstruction("@28"));
        System.out.println(" " + getBinaryString(-1));
        System.out.println(getAInstruction("@-1"));
    }
    
    public static void test2()
    {
        System.out.println(getCInstruction("D|M"));
        // should be 1111 0101 0100 0000
    }
    
    
    public static void main(String[] args)
    {
        test1();
        //test2();
    }
}