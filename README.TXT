PROJECT TITLE: Morris Code
PURPOSE OF PROJECT: Provide a useful* language for the old-fashioned.
VERSION or DATE: 1.0.1 as of February 12, 2018
HOW TO START THIS PROJECT: Open the file and select a piece of code (in .txt format) to run.
AUTHORS: diamondman3
USER INSTRUCTIONS: Morris Code is a tape-based, 1-dimensional programming language similar to Brainfuck. Commands are each written on one line, with the arguments following them as follows:

Command arg0 arg1 arg2...
All text is written in Morse code, with a space separating characters and a slash separating words, as follows:
- .... .. ... / .. ... / .- / -.-. --- -- -- .- -. -..

Comments have not been implemented yet.

The current working commands are:

MORRIS n: Initialize the tape with n cells. This must be performed at the start of the program.
RIGHT: Increment the pointer by 1 cell.
LEFT: Decrement the pointer by 1 cell. LEFT and RIGHT can wrap from cell 0 on the tape to the end of the tape.
JUMP n: Place the pointer at cell n.
UP: Increment the cell at the pointer by 1.
DOWN: Decrement the cell at the pointer by 1.
SET n: Set the value at the pointer to n.
ADD a b: Pop the values at cells a and b, add them, and push the sum to the current cell.
SUBTRACT a b: Pop the values at cells a and b, subtract the value at b from the value at a, and push the difference to the current cell.
MULTIPLY a b: Pop the values at cells a and b, multiply them, and push the product to the current cell.
DIVIDE a b: Pop the values at cells a and b, divide the value at a by the value at b, and push the quotient to the current cell. This is integer division.
OUT: Prints the value at the pointer to the console in ASCII.
OUTINT: Prints the value at the pointer to the console as an integer.
POINTER: Prints the index of the cell which the pointer is at.
CAT text: Takes the text (in morse code) and prints it to the console in ASCII. Easy mode.
IN n: Takes a byte n (in morse code) from the console and sets the current cell to n.
IF MORE/EQUALS/LESS n: If the current cell is more than/equals/is less than n, execute the code until the next STOP command.
ISTOP: Ends an IF statement.
COPY: Stores the data at the current cell.
PASTE: Takes COPIED data and pushes it to the current cell. Initialized at 0.
LOOP: Does the commands until an LSTOP command as many times as the value at the pointer when the loop is started.
LSTOP: Ends a LOOP statement.
GOTO: Be a heretic. Jump to the line of code equal to the value of the current cell, starting at 0.
