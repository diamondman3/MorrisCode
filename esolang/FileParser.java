package esolang;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.parseInt;
import static java.lang.System.*;
import static esolang.CharInterpreter.*;

public class FileParser {

    //What works: morris, right, left, up, down, out, cat, add, multiply, divide (integer), jump, in, if, copy, paste,
    // pointer, outint
    //todo: What doesn't work: loop
    File codeSource;
    Scanner reader;
    Scanner uInput;

    DataStore store;
    private boolean initialized;

    boolean started;//if START is used.
    //initialized means that they called MORRIS and the array exists
    public FileParser(File codeSource){
        this.codeSource=codeSource;
        try {
            reader = new Scanner(codeSource);
        }catch (Exception e){/*doing nothing is intentional*/}
        uInput=new Scanner(in);
    }

    public Scanner getReader(){return reader;}
    public String getFileName(){return codeSource.getName();}
    public String[] readCommand(){
        String command=reader.nextLine();
        String[] commandArr;
        if(command.length()==0){
            String[] errorArray=new String[1];
            errorArray[0]="...---...";
            return errorArray;
        }
        //if multi-word command, make it the first word
        if(command.contains("/")) {
            commandArr=command.split("/");
        }
        else{
            commandArr=new String[1];
            commandArr[0]=command;
        }
        //convert the morse into Ascii to make clearer for source coder
        for (int i=0; i<commandArr.length; i++){
            String[] letters=commandArr[i].split(" ");
            String recomposed="";
            for(int j=0; j<letters.length; j++){
                letters[j]=CharList.enumToAscii(CharList.morseToEnum(letters[j]));
                recomposed+=letters[j];
            }
            commandArr[i]=recomposed;
        }
        System.out.println(Arrays.toString(commandArr));
        return commandArr;
    }

    public void doCommand() throws NullPointerException {
        String[] command=readCommand();
        //takes the inputted command and splits into the command and the arguments
        String baseCommand=command[0];
        String [] args= new String[command.length-1];
        arraycopy(command, 1, args, 0, command.length - 1);

        if(baseCommand.equals("MORRIS")){
            store=new DataStore(parseInt(args[0]));
            initialized=true;
        }
        else if (initialized) {
            if (baseCommand.equals("RIGHT")) {
                store.incrementPointer();
            } else if (baseCommand.equals("LEFT")) {
                store.decrementPointer();
            } else if (baseCommand.equals("JUMP")) {
                store.jumpPointer(parseInt(args[0]));
            } else if (baseCommand.equals("UP")) {
                store.incrementByte();
            } else if (baseCommand.equals("DOWN")) {
                store.decrementByte();
            } else if (baseCommand.equals("SET")) {
                if (args.length > 0) {
                    store.setByte(parseInt(args[0]));
                } else {
                    throw new NullPointerException("-. --- ...- .- .-.. ..- . - --- ... . - - ---");
                    //NO VALUE TO SET TO
                }
            } else if (baseCommand.equals("ADD")) {
                if (args.length > 1) {
                    int val1 = parseInt(args[0]);
                    int val2 = parseInt(args[1]);
                    store.operateTwoCellsToPointer(val1, val2, 'a');
                } else {
                    throw new NullPointerException("-. --- .. -. .--. ..- -");
                    //NOINPUT
                }
            } else if (baseCommand.equals("SUBTRACT")) {
                if (args.length > 1) {
                    int val1 = parseInt(args[0]);
                    int val2 = parseInt(args[1]);
                    store.operateTwoCellsToPointer(val1, val2, 's');
                } else {
                    throw new NullPointerException("-. --- .. -. .--. ..- -");
                    //NOINPUT
                }
            } else if (baseCommand.equals("MULTIPLY")) {
                if (args.length > 1) {
                    int val1 = parseInt(args[0]);
                    int val2 = parseInt(args[1]);
                    store.operateTwoCellsToPointer(val1, val2, 'm');
                } else {
                    throw new NullPointerException("-. --- .. -. .--. ..- -");
                    //NOINPUT
                }
            } else if (baseCommand.equals("DIVIDE")) {
                if (args.length > 1) {
                    int val1 = parseInt(args[0]);
                    int val2 = parseInt(args[1]);
                    store.operateTwoCellsToPointer(val1, val2, 'd');
                } else {
                    throw new NullPointerException("-. --- .. -. .--. ..- -");
                    //NOINPUT
                }
            }else if (baseCommand.equals("RANDOM")){
                store.random();
            } else if (baseCommand.equals("IF")) {
                if (args[0].substring(1).equals("MORE") && store.getByteAtLoc(store.getPointer()) <= parseInt(args[1])) {
                    while (!readCommand()[0].equalsIgnoreCase("STOP")) {
                        readCommand();
                    }
                } else if (args[0].substring(1).equals("EQUALS") && store.getByteAtLoc(store.getPointer()) != parseInt(args[1])) {
                    while (!readCommand()[0].equalsIgnoreCase("STOP")) {
                        readCommand();
                    }
                } else if (args[0].substring(1).equals("LESS") && store.getByteAtLoc(store.getPointer()) >= parseInt(args[1])) {
                    while (!readCommand()[0].equalsIgnoreCase("STOP")) {
                        readCommand();
                    }
                }
            }else if (baseCommand.equals("LOOP")){
                int loopTimes=store.getByteAtLoc(store.getPointer());
                String startingLine=baseCommand;
                FileParser loopParser=new FileParser(codeSource);
                for(int i = 0; i<args.length; i++){
                    startingLine=startingLine+" "+args[i];
                }
                System.out.println(startingLine);
                while(loopTimes>0){
                    String cmd="";
                    do{
                        //gives entire source code
                        cmd=loopParser.getReader().nextLine();
                        System.out.println(cmd);
                    }while(loopParser.getReader().hasNext()&&!cmd.equals(startingLine));

                    System.out.println("/ -.-. .... . -.-. -.- / ... - --- .--. /");

                    while(loopParser.getReader().hasNext()&&!loopParser.readCommand()[0].equalsIgnoreCase("STOP")){
                        System.out.println(loopParser.readCommand()[0]);
                        loopParser.doCommand();
                    }
                    loopTimes--;
                }
            } else if (baseCommand.equals("IN")) {
                out.println(".. -. .--. ..- - .- -... -.-- - .");//INPUTABYTE
                String toInput = uInput.nextLine();
                store.inputValue(toInput);
            } else if (baseCommand.equals("OUT")) {
                store.outputValue();
            } else if (baseCommand.equals("CAT")) {
                store.cat(Arrays.toString(args));
            }else if (baseCommand.equals("COPY")) {
                store.copy();
            }else if (baseCommand.equals("PASTE")){
                store.paste();
            }else if (baseCommand.equals("POINTER")){
                store.showPointer();
            }else if (baseCommand.equals("OUTINT"))
                store.outInt();
        } else {
            //"Array not initialized"
            throw new NullPointerException(".- .-. .-. .- / -.-- -. --- / - .. -. .. - .. .- .-.. .. --.. . -..");
        }
    }
//    public void doCommand(String input) throws NullPointerException {
//        //Would be too complicated to allow Start/Stop commands
//        String[] command=readCommand();
//        //takes the inputted command and splits into the command and the arguments
//        String baseCommand=command[0];
//        String [] args= new String[command.length-1];
//        arraycopy(command, 1, args, 0, command.length - 1);
//        if(baseCommand.equals("MORRIS")){
//            store=new DataStore(parseInt(args[0]));
//            initialized=true;
//        }
//        else if (initialized) {
//            if (baseCommand.equals("RIGHT")) {
//                store.incrementPointer();
//            } else if (baseCommand.equals("LEFT")) {
//                store.decrementPointer();
//            } else if (baseCommand.equals("JUMP")) {
//                store.jumpPointer(parseInt(args[0]));
//            } else if (baseCommand.equals("UP")) {
//                store.incrementByte();
//            } else if (baseCommand.equals("DOWN")) {
//                store.decrementByte();
//            } else if (baseCommand.equals("SET")) {
//                if (args.length > 0) {
//                    store.setByte(parseInt(args[0]));
//                } else {
//                    throw new NullPointerException("-. --- ...- .- .-.. ..- . - --- ... . - - ---");
//                    //NO VALUE TO SET TO
//                }
//            } else if (baseCommand.equals("ADD")) {
//                if (args.length > 1) {
//                    int val1 = parseInt(args[0]);
//                    int val2 = parseInt(args[1]);
//                    store.operateTwoCellsToPointer(val1, val2, 'a');
//                }
//                else {
//                    throw new NullPointerException("-. --- .. -. .--. ..- -");
//                    //NOINPUT
//                }
//            } else if (baseCommand.equals("SUBTRACT")) {
//                if (args.length > 1) {
//                    int val1 = parseInt(args[0]);
//                    int val2 = parseInt(args[1]);
//                    store.operateTwoCellsToPointer(val1, val2, 's');
//                }
//                else {
//                    throw new NullPointerException("-. --- .. -. .--. ..- -");
//                    //NOINPUT
//                }
//            } else if (baseCommand.equals("MULTIPLY")) {
//                if (args.length > 1) {
//                    int val1 = parseInt(args[0]);
//                    int val2 = parseInt(args[1]);
//                    store.operateTwoCellsToPointer(val1, val2, 'm');
//                }
//                else {
//                    throw new NullPointerException("-. --- .. -. .--. ..- -");
//                    //NOINPUT
//                }
//            } else if (baseCommand.equals("DIVIDE")) {
//                if (args.length > 1) {
//                    int val1 = parseInt(args[0]);
//                    int val2 = parseInt(args[1]);
//                    store.operateTwoCellsToPointer(val1, val2, 'd');
//                }
//                else {
//                    throw new NullPointerException("-. --- .. -. .--. ..- -");
//                    //NOINPUT
//                }
//            } else if (baseCommand.equals("IF")) {
//                if (store.getByteAtLoc(store.getPointer()) == parseInt(args[0])) {
//                    followStartCue = "SKIP";
//                    //TODO: If not this, skip the lines between START and STOP
//                } else {
//                    followStartCue = "CONTINUE";
//                }
//            } else if (baseCommand.equals("IN")) {
//                out.println(".. -. .--. ..- - .- -... -.-- - .");//INPUTABYTE
//                store.inputValue(uInput.nextLine());
//            } else if (baseCommand.equals("OUT")) {
//                store.outputValue();
//            } else if (baseCommand.equals("CAT")) {
//                store.cat(Arrays.toString(args));
//            }
//        } else {
//            //"Array not initialized"
//            throw new NullPointerException(".- .-. .-. .- -.-- -. --- - .. -. .. - .. .- .-.. .. --.. . -..");
//        }
//    }
}
