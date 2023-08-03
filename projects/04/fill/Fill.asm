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

// Method1:

// while True:
//     if key_input != 0:
//         for i in range(255):
//             set_black(i)
//     else:
//         for i in range(255):
//             set_clear(i)

// Method2:
// while True:
//     if key_input != 0:
//         set_black(i)
//         i+=1
//     else:
//         set_while(i)
//         i-=1

// Method2:
//     @SCREEN
//     D=A
//     @curr
//     M=D
// (LOOP)
//     // check the keyboard input
//     @KBD
//     D=M
//     @CLEAR
//     D;JEQ
//     
//     @curr
//     D=M
//     @24576
//     D=D-A
//     @LOOP
//     D;JEQ
// 
//     @curr
//     A=M
//     M=-1
//     @curr
//     M=M+1
//     
//     @LOOP
//     0;JMP
// 
// (CLEAR)
//     @curr
//     D=M
//     @16383
//     D=D-A
//     @LOOP
//     D;JEQ

//     @curr
//     A=M
//     M=0
//     @curr
//     M=M-1
// 
//     @LOOP
//     0;JMP
 
// Method1:
(LOOP)
    // reset counter in the beginning of the loop
    @SCREEN
    D=A
    @curr
    M=D
    @i
    M=0

    // check the keyboard input
    @KBD
    D=M
    @CLEAR
    D;JEQ

(BLACK)
    // loop 8192 times to print black
    @curr
    A=M
    M=-1
    @curr
    M=M+1
    @i
    M=M+1
    D=M
    @8192
    D=D-A
    @BLACK
    D;JNE

    @LOOP
    0;JMP

(CLEAR)
    // loop 8192 times to print white
    @curr
    A=M
    M=0
    @curr
    M=M+1
    @i
    M=M+1
    D=M
    @8192
    D=D-A
    @CLEAR
    D;JNE

    @LOOP
    0;JMP
