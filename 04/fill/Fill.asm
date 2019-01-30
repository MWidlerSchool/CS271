// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.

// calculate size of screen
(INIT)			// initialize values
@colorVal
M = 0
@WHITE			
M = 0			// constant for white
@0
D = !A
@BLACK
M = D			// constant for black
@8192
D = A
@maxIteration
M = D			// constant for maximum iteration ( = [258 * 512] / 16)


(MAIN_LOOP)		// set values for main loop
@incr			// increment
M = 0
@SCREEN
D = A
@scrLoc			// scrLoc starts at beginning of screen
M = D

(IF_KEY_UP)
@KBD
D = M			// store keyboard input in data register
@KEY_UP	
D; JEQ			// if no key is held, jump to KEY_UP, otherwise will fall into KEY_DOWN

(KEY_DOWN)
@BLACK
D = M
@colorVal
M = D			// set colorVal to black
@PAINT_LOOP
0; JMP			// jump to paint loop

(KEY_UP)
@WHITE
D = M
@colorVal
M = D			// set colorVal to white, fall off into paint loop

(PAINT_LOOP)		// main loop
@incr
D = M
@maxIteration
D = D - M
@MAIN_LOOP
D; JEQ			// Back to top if incr == 8192

@SCREEN			// load screen address
D = A			// store in D
@incr			
D = D + M		// add increment
@scrLoc
M = D			// store to scrLoc
@colorVal
D = M			// load colorVal to D
@scrLoc			// load scrLoc
A = M			// load stored address as address
M = D			// paint

@incr
M = M + 1		// post increment

@PAINT_LOOP
0; JMP			// jump to PAINT_LOOP
