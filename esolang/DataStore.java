package esolang;

import java.lang.*;
import java.util.*;
import esolang.CharInterpreter.*;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;

/**
 * Created by maxwelljm19 on 9/19/2017.
 */

public class DataStore {

    private byte heldByte=0;
    //Byte used for the COPY/PASTE commands.
    public byte[] store;
    //all of the acutal data, length assigned at the constructor
    public int pointer;
    //location of data modified

    //Arguments split with underscores, not spaces

    //Sets the length of the store. Will be called at the start of the program by morse for "MORRIS (length)"
    public DataStore(int bytes){
        store=new byte[bytes];
        pointer=0;
    }

    public int getPointer(){return pointer;}
    public int getByteAtLoc(int byteNum){return store[byteNum];}

    //used to increase the value of the pointer by 1. Wraps to 0 if at the end.
    //Called by morse for "RIGHT"
    public void incrementPointer(){
        if(pointer==store.length-1){
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
            pointer=store.length-1;
        }
        else{
            pointer-=1;
        }
    }

    //Used to move the pointer to a specific location. Called by "JUMP (pointer location)"
    public void jumpPointer(int locationTo){
        if(store.length>locationTo) {
            pointer = locationTo;
        }
        else{
            throw new ArrayIndexOutOfBoundsException(".--- ..- -- .--. .. -. --. - --- ---"+
               "..- - --- ..-. -... --- ..- -. -.. ... ...- .- .-.. ..- .");
            //"jumping to out of bounds value"
        }
    }

    //Adds 1 to a byte at the location of pointer
    //overflows at 127
    //Called by "UP"
    public void incrementByte(){
        store[pointer]+=1;
    }

    //Subtracts 1 from a byte at the location of pointer
    //underflows at -128
    //Called by "DOWN"
    public void decrementByte(){
        store[pointer]-=1;
    }

    //Sets a byte to the number specified.
    //Called by "SET (number)"
    public void setByte(int input){
        store[pointer]=(byte)input;
    }

    //Takes 2 cells and operates on them, then sets the value pointed to to the sum.
    //Called by "ADD (cell0) (cell1), SUBTRACT (cell0) (cell1), etc.
    public byte operateTwoCellsToPointer(int cell0, int cell1, char function){
        switch (function){
            case 'a':
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

    //Increases the size of the array by 1
    //Called by EXTEND
    public void extend(){
        byte[] temp=new byte[store.length+1];
        int i;
        for(i=0; i<store.length; i++){
            temp[i]=store[i];
        }
        for(int j=i; j<temp.length; j++){
            temp[j]=0;
        }
        store=temp;
    }

    //Decreses the size of the array by 1
    //if pointer is at last index, decreases it by 1
    //Deletes last value in array
    //Called by RETRACT
    public void retract(){
        byte[] temp=new byte[store.length-1];
        if(pointer==store.length-1){
            decrementPointer();
        }
        for(int i=0; i<temp.length; i++){
            temp[i]=store[i];
        }
        store=temp;
    }

    //Generates a random number between 0 and store[pointer].
    //Called by RANDOM
    public void random(){
        store[pointer]=(byte)(((256+store[pointer])%256)*Math.random());
    }

    //Takes a byte and stores it at the pointer. Byte must be written in morse code.
    //Called by "IN (value)"
    //Allows for overflows, but can only take 3 digits of input.
    public boolean inputValue(String input) {
        //cuts into single words
        String[] inputAscii;
        inputAscii=input.split(" ");
        for(int i=0; i<inputAscii.length; i++){
            inputAscii[i]=CharList.enumToAscii(CharList.morseToEnum(inputAscii[i]));
        }
        //cuts into single characters
        int[] values=new int[inputAscii.length];
        for(int i =0; i<inputAscii.length; i++){
            try{
                values[i]=parseInt(inputAscii[i]);
            }
            catch (Exception e){
                return false;
            }
        }

        //changes multiple digits into one number
        String concatonateThisLittleBastard="";
        for(int i=0; i<values.length; i++){
            concatonateThisLittleBastard+=(values[i]+ "");
        }

        //actually sets the data
        store[pointer]=(byte)parseInt(concatonateThisLittleBastard);
        return true;
    }

    //IF method here deprecated, called in FileParser. It's inconsistent, but it works.

    //Takes a byte at the pointer and prints it. Don't have morse for all chars, so will be in uni.
    //Called by OUT

    public void outputValue(){

        int outAsInteger;
        if(store[pointer]<0){
            outAsInteger=(256+store[pointer]);
        }
        else {
            outAsInteger=(store[pointer]);
        }
        System.out.print((char) outAsInteger);
    }

    //prints the output as an integer, called by OUTINT
    public void outInt(){
        System.out.print((int)store[this.pointer]+"");
    }

    //Echos an input in Ascii.
    //Called by CAT (input)
    //Only works with preset Morris code characters in CharList
    public void cat(String input){
        //all this is to make sure it only outputs the text
        while(input.contains("[0")){
            input=input.substring(0, input.indexOf("[0"))+ input.substring(input.indexOf("[0") + 2);
        }
        while(input.contains("[")){
            input=input.substring(0, input.indexOf("["))+ input.substring(input.indexOf("[") + 1);
        }
        while(input.contains(",")){
            input=input.substring(0, input.indexOf(","))+ input.substring(input.indexOf(",") + 1);
        }
        while(input.contains(", 0")){
            input=input.substring(0, input.indexOf(", 0"))+ " " + input.substring(input.indexOf(", 0") + 3);
        }
        input=input.substring(0, input.length()-1);
        System.out.print(input);
    }

    //Adds a line break. Just for convenience instead of having to CAT nothing every time.
    //Called by FEED
    public void feed(){
        System.out.println("");
    }

    //Takes the current byte and makes it heldByte. Does not change the byte at pointer. Called by COPY
    public void copy(){heldByte=store[pointer];}

    //does the opposite of copy, called by PASTE
    public void paste(){store[pointer]=heldByte;}

    //prints the location of the pointer, called by POINTER
    public void showPointer(){
        System.out.print(this.pointer+"");
    }

    //executes the code, skips to the line with the "loop" command the number of times in the cell pointed to when called, ends at LSTOP
    public void loop(int loopsTotal){
        //all in FileParser. It works, and I'd rather not tamper with it any more.
    }
}
