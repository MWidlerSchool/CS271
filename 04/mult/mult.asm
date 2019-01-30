// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)

(START)		// initialize values
@R0
D = M
@val
M = D		// value copied from R0
@R1
D = M
@countdown
M = D		// countdown copied from R1
@R2
M = 0		// R2 stores sum, initialized to zero

(LOOP)		// main loop
@countdown
D = M
@END
D; JEQ		// jump to end if countdown has reached zero
@val		// get the value
D = M		// store it in D
@R2		// get the sum
M = M + D	// add the value to the sum, store it in M
@countdown	
M = M - 1	// decrement the countdown
@LOOP
0; JMP		// jump to loop

(END)
@END
0; JMP		// infinite loop