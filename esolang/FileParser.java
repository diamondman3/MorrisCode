package esolang; /**
 * Created by maxwelljm19 on 9/19/2017.
 */
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class FileParser {
    File codeSource;
    Scanner reader;
    Scanner uInput;

    DataStore store;
    private boolean initialized;

    boolean started;//if START is used.
    private String followStartCue="CONTINUE";
    //initialized means that they called MORRIS and the array exists
    public FileParser(String fileLocation){
        codeSource=new File(fileLocation);
        reader=new Scanner(/*codeSource*/System.in);//TODO: implement user-specified codeSource
        uInput=new Scanner(System.in);
    }

    public String[] readCommand(){
        String command=reader.nextLine();
        String[] commandArr;
        if(command.length()==0){
            String[] errorArray=new String[1];
            errorArray[0]="...---...";
            return errorArray;
        }
        //if multi-word command, make it the first word
        if(command.contains("_")) {
            commandArr=command.split("_");
        }
        else{
            commandArr=new String[0];
            commandArr[0]=command;
        }
        //convert the morse into unicode to make clearer for source coder
        for (int i=0; i<commandArr.length; i++){
            String[] letters=commandArr[i].split(" ");
            String recomposed="";
            for(int j=0; j<letters.length; j++){
                letters[j]=CharInterpreter.enumToUnicode(CharInterpreter.morseCodeToEnum(letters[j]));
                recomposed+=letters[j];
            }
            commandArr[i]=recomposed;
        }
        return commandArr;
    }

    public void doCommand() throws NullPointerException {
        String[] command=readCommand();
        //takes the inputted command and splits into the command and the arguments
        String baseCommand=command[0];
        String [] args= new String[command.length-1];
            for(int i=1; i<command.length; i++){
                args[i-1]=command[i];
            }
        if(baseCommand.equals("MORRIS")){
            store=new DataStore(parseInt(args[0]));
            initialized=true;
        }
        else{
            if(!initialized){
                //"Array not initialized"
                throw new NullPointerException(".- .-. .-. .- -.-- -. --- - .. -. .. - .. .- .-.. .. --.. . -..");
            }
            if(baseCommand.equals("RIGHT")){
                store.incrementPointer();
            }
            else if(baseCommand.equals("LEFT")){
                store.decrementPointer();
            }
            else if(baseCommand.equals("JUMP")){
                store.jumpPointer(parseInt(args[0]));
            }
            else if (baseCommand.equals("UP")){
                store.incrementByte();
            }
            else if(baseCommand.equals("DOWN")){
                store.decrementByte();
            }
            else if(baseCommand.equals("SET")){
                if (args.length>0) {
                    store.setByte(parseInt(args[0]));
                }
                else{
                    throw new NullPointerException("-. --- ...- .- .-.. ..- . - --- ... . - - ---");
                    //NO VALUE TO SET TO
                }
            }
            else if (baseCommand.equals("ADD")){
                if(args.length>1){
                    int val1=store.getByteAtLoc(parseInt(args[0]));
                    int val2=store.getByteAtLoc(parseInt(args[2]));
                    store.setByte(val1+val2);
                }
                else{
                    throw new NullPointerException("-. --- .. -. .--. ..- -");
                    //NOINPUT
                }
            }
            else if (baseCommand.equals("SUBTRACT")){
                if(args.length>1){
                    int val1=store.getByteAtLoc(parseInt(args[0]));
                    int val2=store.getByteAtLoc(parseInt(args[2]));
                    store.setByte(val1-val2);
                }
                else{
                    throw new NullPointerException("-. --- .. -. .--. ..- -");
                    //NOINPUT
                }
            }
            else if (baseCommand.equals("MULTIPLY")){
                if(args.length>1){
                    int val1=store.getByteAtLoc(parseInt(args[0]));
                    int val2=store.getByteAtLoc(parseInt(args[2]));
                    store.setByte(val1*val2);
                }
                else{
                    throw new NullPointerException("-. --- .. -. .--. ..- -");
                    //NOINPUT
                }
            }
            else if (baseCommand.equals("DIVIDE")){
                if(args.length>1){
                    int val1=store.getByteAtLoc(parseInt(args[0]));
                    int val2=store.getByteAtLoc(parseInt(args[2]));
                    store.setByte(val1/val2);
                }
                else{
                    throw new NullPointerException("-. --- .. -. .--. ..- -");
                    //NOINPUT
                }
            }
            else if (baseCommand.equals("IF")){
                if(store.getByteAtLoc(store.getPointer())==parseInt(args[0])){
                    followStartCue="SKIP";
                    //TODO: If not this, skip the lines between START and STOP
                }
                else{
                    followStartCue="CONTINUE";
                }
            }
            else if (baseCommand.equals("IN")){
                System.out.println(".. -. .--. ..- - .- -... -.-- - .");//INPUTABYTE
                store.setByte(uInput.nextByte());
            }
            else if (baseCommand.equals("OUT")){
                System.out.print(store.getByteAtLoc(store.getPointer()));
            }
            else if(baseCommand.equals("CAT")){
                System.out.print(args);
            }
            else if(baseCommand.equals("LOOP")){
                followStartCue="LOOP"+ args;
            }
            else if(baseCommand.equals("START")){
                started=true;
                if (followStartCue.equals("SKIP")){
                    //Likely source of errors
                    //idk if this skips a line every time or what
                    while(!readCommand()[0].equals("STOP")){
                        reader.nextLine();
                    }
                }
                else if(followStartCue.startsWith("LOOP")){
                    int loopIterations=parseInt(followStartCue.substring("LOOP".length()));
                    List<String> linesToLoopThrough=new ArrayList<String>();
                    while(!readCommand()[0].equals("STOP")){
                        linesToLoopThrough.add(reader.nextLine());
                    }
                    do{
                        for(int i=0; i<linesToLoopThrough.size(); i++){
                            doCommand(linesToLoopThrough.get(i));
                        }
                        loopIterations-=1;
                    }
                    while(loopIterations>0);
                }
            }
        }
    }
    public void doCommand(String input) throws NullPointerException {
        //Would be too complicated to allow Start/Stop commands
        String[] command=input.split(" ");
        //takes the inputted command and splits into the command and the arguments
        String baseCommand=command[0];
        String [] args= new String[command.length-1];
        for(int i=1; i<command.length; i++){
            args[i-1]=command[i];
        }
        if(baseCommand.equals("MORRIS")){
            store=new DataStore(parseInt(args[0]));
            initialized=true;
        }
        else{
            if(!initialized){
                //"Array not initialized"
                throw new NullPointerException(".- .-. .-. .- -.-- -. --- - .. -. .. - .. .- .-.. .. --.. . -..");
            }
            if(baseCommand.equals("RIGHT")){
                store.incrementPointer();
            }
            else if(baseCommand.equals("LEFT")){
                store.decrementPointer();
            }
            else if(baseCommand.equals("JUMP")){
                store.jumpPointer(parseInt(args[0]));
            }
            else if (baseCommand.equals("UP")){
                store.incrementByte();
            }
            else if(baseCommand.equals("DOWN")){
                store.decrementByte();
            }
            else if(baseCommand.equals("SET")){
                if (args.length>0) {
                    store.setByte(parseInt(args[0]));
                }
                else{
                    throw new NullPointerException("-. --- ...- .- .-.. ..- . - --- ... . - - ---");
                    //NO VALUE TO SET TO
                }
            }
            else if (baseCommand.equals("ADD")){
                if(args.length>1){
                    int val1=store.getByteAtLoc(parseInt(args[0]));
                    int val2=store.getByteAtLoc(parseInt(args[2]));
                    store.setByte(val1+val2);
                }
                else{
                    throw new NullPointerException("-. --- .. -. .--. ..- -");
                    //NOINPUT
                }
            }
            else if (baseCommand.equals("SUBTRACT")){
                if(args.length>1){
                    int val1=store.getByteAtLoc(parseInt(args[0]));
                    int val2=store.getByteAtLoc(parseInt(args[2]));
                    store.setByte(val1-val2);
                }
                else{
                    throw new NullPointerException("-. --- .. -. .--. ..- -");
                    //NOINPUT
                }
            }
            else if (baseCommand.equals("MULTIPLY")){
                if(args.length>1){
                    int val1=store.getByteAtLoc(parseInt(args[0]));
                    int val2=store.getByteAtLoc(parseInt(args[2]));
                    store.setByte(val1*val2);
                }
                else{
                    throw new NullPointerException("-. --- .. -. .--. ..- -");
                    //NOINPUT
                }
            }
            else if (baseCommand.equals("DIVIDE")){
                if(args.length>1){
                    int val1=store.getByteAtLoc(parseInt(args[0]));
                    int val2=store.getByteAtLoc(parseInt(args[2]));
                    store.setByte(val1/val2);
                }
                else{
                    throw new NullPointerException("-. --- .. -. .--. ..- -");
                    //NOINPUT
                }
            }
            else if (baseCommand.equals("IF")){
                if(store.getByteAtLoc(store.getPointer())==parseInt(args[0])){
                    followStartCue="SKIP";
                    //TODO: If not this, skip the lines between START and STOP
                }
                else{
                    followStartCue="CONTINUE";
                }
            }
            else if (baseCommand.equals("IN")){
                System.out.println(".. -. .--. ..- - .- -... -.-- - .");//INPUTABYTE
                store.setByte(uInput.nextByte());
            }
            else if (baseCommand.equals("OUT")){
                System.out.print(store.getByteAtLoc(store.getPointer()));
            }
            else if(baseCommand.equals("CAT")){
                System.out.print(args);
            }
            else if(baseCommand.equals("LOOP")){
                followStartCue="LOOP"+ args;
            }
        }
    }
}
