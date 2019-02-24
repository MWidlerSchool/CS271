package VMCompiler;

import java.util.*;

// a self-initializing class for containing the constants used in a Hack program
public enum VMConstants
{
    SP(0),              // = RAM[0]
    LCL(1),             // = RAM[1]
    ARG(2),             // = RAM[2]
    THIS(3),            // = RAM[3]
    THAT(4),            // = RAM[4]
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
    NOT(1009);
    
    public int value;
    
    private VMConstants(int v)
    {
        value = v;
    }
}