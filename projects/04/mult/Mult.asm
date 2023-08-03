// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)
//
// This program only needs to handle arguments that satisfy
// R0 >= 0, R1 >= 0, and R0*R1 < 32768.

// Put your code here.

// sum = 0
// pow = 1
// base = R0
// remain = R1
// 
// while remain != 0:
//     if pow & R1 != 0:
//         sum += base
//         remain -= pow
//     base += base
//     pow += pow
//      
// R2 = sum

    // initialize
    @sum
    M=0
    @pow
    M=1
    @R0
    D=M
    @base
    M=D
    @R1
    D=M
    @remain
    M=D

(LOOP)
    // while remain != 0
    @remain
    D=M
    @RET
    D;JEQ

(CHECK)
    // check whether to add
    @pow
    D=M
    @R1
    D=D&M

    // jump to INC if so
    @INC
    D;JEQ

    // update sum and remain if checks are met
    @base
    D=M
    @sum
    M=D+M
    @pow
    D=M
    @remain
    M=M-D

(INC)
    // increase base and pow
    @base
    D=M
    M=D+M
    @pow
    D=M
    M=D+M

    // go to loop
    @LOOP
    0;JMP

(RET)
    @sum
    D=M
    @R2
    M=D

(END)
    @END
    0;JMP
