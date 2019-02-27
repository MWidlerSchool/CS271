package VMCompiler;

import java.util.*;

// a self-initializing class for containing the constants used in a Hack program
public enum VMConstants
{
    SP(0),              // = RAM[0], stack pointer address
    LCL(1),             // = RAM[1], local pointer address
    ARG(2),             // = RAM[2], argument pointer address
    THIS(3),            // = RAM[3], this pointer address
    THAT(4),            // = RAM[4], that pointer address
    // RAM[5]-[12] hold the value(s) for temp
    // RAM[13] - [15] are general purpose registers
    // RAM[16] - [255] are for static variables
    STACK_BASE(256),    // = RAM[256]
    HEAP_BASE(2048),    // = RAM[2048]
    
    ADD(1001),
    SUB(1002),
    NEG(1003),
    EQ(1004),
    GT(1005),
    LT(1006),
    AND(1007),
    OR(1008),
    NOT(1009),
    
    PUSH(2000),
    POP(2001),
    CONSTANT(2002);
    
    public int value;
    
    private VMConstants(int v)
    {
        value = v;
    }
    
    public boolean isArithmeticOrLogical()
    {
        if(this.ordinal() >= ADD.ordinal() && this.ordinal() <= NOT.ordinal())
            return true;
        return false;
    }
    
    // essentially a constructor
    public static VMConstants get(String str)
    {
        str = str.toUpperCase();
        if(str.equals("ADD"))
            return ADD;
        else if(str.equals("SUB"))
            return SUB;
        else if(str.equals("NEG"))
            return NEG;
        else if(str.equals("EQ"))
            return EQ;
        else if(str.equals("GT"))
            return GT;
        else if(str.equals("LT"))
            return LT;
        else if(str.equals("AND"))
            return AND;
        else if(str.equals("OR"))
            return OR;
        else if(str.equals("NOT"))
            return NOT;
        else if(str.equals("PUSH"))
            return PUSH;
        else if(str.equals("POP"))
            return POP;
        else if(str.equals("CONSTANT"))
            return CONSTANT;
        return null;
    }
}