package esolang;

/**
 * Created by maxwelljm19 on 9/19/2017.
 */

//TODO: Add input and output
public class DataStore {

    public byte[] store;
    //all of the acutal data, length assigned at the constructor
    public int pointer;
    //location of data modified
    public CharInterpreter interpreter;
    //Probably a better way to do this.

    //Sets the length of the store. Will be called at the start of the program by morse for "MORRIS (length)"
    public DataStore(int bytes){
        store=new byte[bytes];
    }

    //used to increase the value of the pointer by 1. Wraps to 0 if at the end.
    //Called by morse for "RIGHT"
    public void incrementPointer(){
        if(pointer==store.length){
            pointer=0;
        }
        else{
            pointer+=1;
        }
    }

    //used to increase the value of the pointer by 1. Wraps to 0 if at the end.
    //Called by morse for "LEFT"
    public void decrementPointer(){
        if(pointer==0){
            pointer=store.length;
        }
        else{
            pointer-=1;
        }
    }

    //Used to move the pointer to a specific location. Called by "JUMP (pointer location)"
    public void jumpPointer(int locationTo){
        try{
            pointer=locationTo;
        }
        //for when they inevitably see what happens
        catch(ArrayIndexOutOfBoundsException e){
            System.out.println(". - - -   . . -   - -   . - - .   . .   - .   - - .   -   - - -   - - -"+
               ". . -   -   - - -   . . - .   - . . .   - - -   . . -   - .   - . .   . . .   . . . -   "+
               ". -   . - . .   . . -   .  ");
            //"jumping to out of bounds value"
        }
    }

    //Adds 1 to a byte at the location of pointer
    //Called by "UP"
    public byte incrementByte(){
        store[pointer]+=1;
        return store[pointer];
    }

    //Subtracts 1 from a byte at the location of pointer
    //Called by "DOWN"
    public byte decrementByte(){
        store[pointer]-=1;
        return store[pointer];
    }

    //Sets a byte to the number specified.
    //Called by "SET (number)"
    public byte setByte(int input){
        store[pointer]=(byte)input;
        return store[pointer];
    }

    //Takes 2 cells and operates on them, then sets the value pointed to to the sum.
    //Called by "ADD (cell0) (cell1), SUBTRACT (cell0) (cell1), etc.
    public byte operateTwoCellsToPointer(int cell0, int cell1, char function){
        switch (function){
            case 'p':
                store[pointer]=(byte)(store[cell0]+store[cell1]);
                break;
            case 's':
                store[pointer]=(byte)(store[cell0]-store[cell1]);
                break;
            case 'm':
                store[pointer]=(byte)(store[cell0]*store[cell1]);
                break;
            case 'd':
                store[pointer]=(byte)(store[cell0]/store[cell1]);
                break;
            default:
                //"invalid operation"
                System.out.print(".. -. ...- .- .-.. .. -.. --- .--. ."+
                   " .-. .- - .. --- -.");
        }
        return store[pointer];
    }

    //Takes a byte at the pointer and checks it against the given value.
    //Called by "IF (input) START", then ended with "STOP", or IF NOT (input) START.
    public boolean parseIf(int input, boolean callIfTrue){
        if (store[pointer]==(byte)input){
            return true;
        }
        return false;
    }

    //Takes a byte and stores it at the pointer. Byte must be written in morse code.
    //Called by "IN (value)"
    public boolean inputValue(String input) {
        String[] uni = new String[3];
        int i = 0;
        if (input.contains(" ")) {
            while (input.contains(" ") && i <= 2) {
                uni[i] = input.substring(0, input.indexOf(" "));
                input = input.substring(input.indexOf(" "));
                i += 1;
            }
            if (input != "") {
                System.out.println("-... -.-- - .     .. -. -.. . -..-     --- ..- -     --- ..-.     .-. .- -. --. .");
                return false;
            }
        }
        else {
            uni[0] = input;
        }
        for (int j=0; j<uni.length; j++){
            uni[j]=interpreter.enumToUnicode(interpreter.morseCodeToEnum(uni[j]));
        }
    }
}
