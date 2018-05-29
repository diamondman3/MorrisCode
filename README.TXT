PROJECT TITLE: Morris Code
PURPOSE OF PROJECT: Provide a useful* language for the old-fashioned.
VERSION or DATE: 1.1.1 as of May 17, 2018
HOW TO START THIS PROJECT: Compile with the main class of esolang.MorrisCode. Run the program and select a piece of code (in .txt format) from the popup window to run.
AUTHORS: diamondman3
USER INSTRUCTIONS: Morris Code is a tape-based, 1-dimensional programming language similar to Brainfuck. Commands are each written on one line, with the arguments following them as follows:

If you've compiled it as a Jar and are running it outside of an IDE, use this command in the command line to start it:
java -jar MorrisCode.jar

Otherwise, use the provided batch file after placing it in the same folder as the jar file.

Command arg0 arg1 arg2...
All text is written in Morse code, with a space separating characters and a slash separating words, as follows:
- .... .. ... / .. ... / .- / -.-. --- -- -- .- -. -..

The current working commands are:

MORRIS n: Initialize the tape with n cells. This must be performed at the start of the program.

RIGHT: Increment the pointer by 1 cell.
LEFT: Decrement the pointer by 1 cell. LEFT and RIGHT can wrap from cell 0 on the tape to the end of the tape.
JUMP n: Place the pointer at cell n.

UP: Increment the cell at the pointer by 1.
DOWN: Decrement the cell at the pointer by 1.
SET n: Set the value at the pointer to n.

EXTEND: Increase the length of the tape by 1. The new value is set to 0, and at the furthest right point on the tape.
RETRACT: Decreases the length of the tape by 1. This deletes the value in the final position on the tape.

ADD a b: Pop the values at cells a and b, add them, and push the sum to the current cell.
SUBTRACT a b: Pop the values at cells a and b, subtract the value at b from the value at a, and push the difference to the current cell.
MULTIPLY a b: Pop the values at cells a and b, multiply them, and push the product to the current cell.
DIVIDE a b: Pop the values at cells a and b, divide the value at a by the value at b, and push the quotient to the current cell. This is integer division.
RANDOM: Pushes a random value between 0 and 255 to the current cell.

IN n: Takes a byte n (in morse code) from the console and sets the current cell to n.
OUT: Prints the value at the pointer to the console in ASCII.
OUTINT: Prints the value at the pointer to the console as an integer.
POINTER: Prints the index of the cell which the pointer is at.
CAT text: Takes the text (in morse code) and prints it to the console in ASCII. Easy mode.
FEED: Prints a line break.

IF MORE/EQUALS/LESS n: If the current cell is more than/equals/is less than n, execute the code until the next STOP command. You can not have nested IFs.
ISTOP: Ends an IF statement.

COPY: Stores the data at the current cell.
PASTE: Takes COPIED data and pushes it to the current cell. Initialized at 0.

LOOP: Does the commands until an LSTOP command as many times as the value at the pointer when the loop is started. You can not have nested LOOPs.
LSTOP: Ends a LOOP statement.
GOTO: Be a heretic. Jump to the line of code equal to the value of the current cell, starting at 0.

COMMENT anytext: The program should ignore anything written after "COMMENT / " (with the command, but not necessarily the comment, in morse code).
