package HackAssembler;

import java.util.*;

// a self-initializing class for containing the constants used in a Hack program
public class HackConstants
{
    private static Vector<String> keyList;
    private static Vector<Integer> valueList;
    private static int index;
    
    public static void init()
    {
        keyList = new Vector<String>();
        valueList = new Vector<Integer>();
        index = 16;
        
        // virtual registers
        for(int i = 0; i < 16; i++)
            add(new String("R" + i), i);
        
        // predefined pointers
        add("SP", 0);
        add("LCL", 1);
        add("ARG", 2);
        add("THIS", 3);
        add("THAT", 4);
        
        // I/O pointers
        add("SCREEN", 16384);
        add("KBD", 24576);
    }
    
    
    // label symbols
    public static void add(String k, int v)
    {
        keyList.add(k);
        valueList.add(new Integer(v));
    }
    
    // variable symbols
    public static void add(String k)
    {
        if(get(k) == -1)
            keyList.add(k);
        valueList.add(new Integer(index));
        index++;
    }
    
    // returns associated value, -1 if value is not present.
    public static int get(String k)
    {
        for(int i = 0; i < keyList.size(); i++)
        {
            if(k.equals(keyList.elementAt(i)))
                return valueList.elementAt(i);
        }
        return -1;
    }
    
    public static boolean has(String k)
    {
        for(String str : keyList)
            if(str.equals(k))
                return true;
        return false;
    }
    
    public static void test1()
    {
        for(int i = 0; i < keyList.size(); i++)
        {
            System.out.println(String.format("%s, %s", keyList.elementAt(i), valueList.elementAt(i)));
        }
    }
}