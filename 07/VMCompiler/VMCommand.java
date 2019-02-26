package VMCompiler;

import java.util.*;

// a self-initializing class for containing the constants used in a Hack program
public class VMCommand
{
    // makes sure we issue unique variable names
    private static int logicIndex = 0;
    
    public static void init()
    {
        logicIndex = 0;
    }
    
    
    
    // returns a list with a copy of the second concatenated onto a copy of the first
    public static Vector<String> concat(Vector<String> v1, Vector<String> v2)
    {
        Vector<String> v3 = new Vector<String>();
        for(String str : v1)
            v3.add(str);
        for(String str : v2)
            v3.add(str);
        return v3;
    }
    
    // initialize the stack pointer
    public static Vector<String> initStack()
    {
        Vector<String> insList = new Vector<String>();
        insList.add("// Initialize stack");
        insList.add("@256");
        insList.add("D=A");
        insList.add("@0");
        insList.add("M=D");
        return insList;
    }
    
    // infinite loop for end
    public static Vector<String> terminal()
    {
        Vector<String> insList = new Vector<String>();
        int index = getLogicIndex();
        insList.add("\n// Terminal loop");
        insList.add(declareLogicIndex(index));
        insList.add(loadLogicIndex(index));
        insList.add("0;JMP");
        return insList;
    }
    
    // pushes a passed int onto the stack
    public static Vector<String> pushConstant(int val)
    {
        Vector<String> insList = new Vector<String>();
        insList.add("\n// Push constant on to the stack");
        insList.add("@" + val);
        insList.add("D=A");
        insList = concat(insList, pushD(false));
        return insList;
    }
    
    // pushes contents of the D register onto the stack
    public static Vector<String> pushD(){return pushD(true);}
    public static Vector<String> pushD(boolean noSupercall)
    {
        Vector<String> insList = new Vector<String>();
        if(noSupercall)
            insList.add("\n// Push D register on to the stack");
        insList.add("@0");
        insList.add("A=M");
        insList.add("M=D");
        insList.add("@0");
        insList.add("M=M+1");
        return insList;
    }
    
    // pops top of stack into A register
    public static Vector<String> pop(){return pop(true);}
    public static Vector<String> pop(boolean noSupercall)
    {
        Vector<String> insList = new Vector<String>();
        if(noSupercall)
            insList.add("\n// Pop top of stack into the A register");
        insList.add("@0");
        insList.add("M=M-1");
        insList.add("A=M");
        insList.add("A=M");
        return insList;
    }
    
    // pops top of stack into D register, then next into A register
    public static Vector<String> pop2()
    {
        Vector<String> insList = new Vector<String>();
        insList.add("\n// Pop top two items in stack into D and A, respectively.");
        insList = pop(false);
        insList.add("D=A");
        insList = concat(insList, pop());
        return insList;
    }
    
    private static String getArithmenticComment(VMConstants opType)
    {
        String comment = "";
        switch(opType)
        {
            case NEG : comment = "\n// NEG operation"; break;
            case NOT : comment = "\n// NOT operation"; break;
            case ADD : comment = "\n// ADD operation"; break;
            case SUB : comment = "\n// SUB operation"; break;
            case AND : comment = "\n// ADD operation"; break;
            case OR :  comment = "\n// OR operation"; break;
            case EQ :  comment = "\n// EQ comparison"; break;
            case GT :  comment = "\n// GT comparison"; break; 
            case LT :  comment = "\n// LT comparison"; break;
            default :  comment = "\n// Unknown operation!"; break;
        }
        return comment;
    }
    
    // arithmetic and logical operators
    public static Vector<String> arithmeticOp(VMConstants opType)
    {
        Vector<String> insList = new Vector<String>();

        // unary operators
        if(opType == VMConstants.NEG || opType == VMConstants.NOT)
        {
            insList = pop();
            insList.add("D=A");
            insList.add(getArithmenticComment(opType));
            switch(opType)
            {
                case NEG : insList.add("D=-D"); break;
                case NOT : insList.add("D=!D"); break;
            }
            insList = concat(insList, pushD());
        }
        // binary operators
        else
        { 
            insList = pop2();
            insList.add(getArithmenticComment(opType));
            switch(opType)
            {
                case ADD : insList.add("D=A+D"); break;
                case SUB : insList.add("D=A-D"); break;
                case AND : insList.add("D=A&D"); break;
                case OR :  insList.add("D=A|D"); break;
                case EQ : 
                case GT : 
                case LT :  insList = logicOp(insList, opType); 
                           break;
            }
            insList = concat(insList, pushD());
        }
        return insList;
    }
    
    
    // adds the commands for a jump
    public static Vector<String> logicOp(Vector<String> insList, VMConstants opType)
    {
        int trueIndex = getLogicIndex();
        int finallyIndex = getLogicIndex();
        
        insList.add("D=A-D");
        insList.add(loadLogicIndex(trueIndex));
        switch(opType)
        {
            case EQ : insList.add("D;JEQ"); break;
            case GT : insList.add("D;JGT"); break;
            case LT : insList.add("D;JLT"); break;
        }
        
        // false
        insList.add("D=0");
        insList.add(loadLogicIndex(finallyIndex));
        insList.add("0;JMP");
        
        // true
        insList.add(declareLogicIndex(trueIndex));
        insList.add("D=-1");
        
        // finally
        insList.add(declareLogicIndex(finallyIndex));
        
        return insList;
    }
    
    // for setting constants
    public static int getLogicIndex()
    {
        int val = logicIndex;
        logicIndex += 1;
        return val;
    }
    
    // load a constant into the A register
    public static String loadLogicIndex(int i)
    {
        return "@LOGIC_INDEX_" + i;
    }
    
    // declare a jump point
    public static String declareLogicIndex(int i)
    {
        return "(LOGIC_INDEX_" + i + ")";
    }
}