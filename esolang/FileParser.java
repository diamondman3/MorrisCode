package esolang;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

import static java.lang.Integer.parseInt;
import static java.lang.System.*;
import static esolang.CharInterpreter.*;

public class FileParser {

    //What works: morris, right, left, up, down, out, cat, add, multiply, divide (integer), jump, in, if, copy, paste,
    // pointer
    //todo: What doesn't work: loop, outint
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
    public void setStore(DataStore s){store=s;}
    public DataStore getStore(){return store;}
    public String[] readCommand() throws NoSuchElementException{
        String command;
        try {
            command = reader.nextLine();
        }catch (NoSuchElementException e){return new String[0];}
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
       // System.out.println(Arrays.toString(commandArr));
        return commandArr;
    }

    public boolean doCommand() throws NullPointerException {
        //System.out.println("doCommand started");
        String[] command;
        try {
            command = readCommand();
        }catch (NullPointerException e){return false;}
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
            }else if (baseCommand.equals("OUTINT")) {
                store.outInt();
            }else if (baseCommand.equals("LOOP")) {
                int loopTimes = store.getByteAtLoc(store.getPointer());
                String startingLine = baseCommand;
                for (int i = 0; i < args.length; i++) {
                    startingLine = startingLine + " " + args[i];
                }
                while (loopTimes > 0) {
                    FileParser loopParser = new FileParser(codeSource);
                    loopParser.setStore(store);
                    loopParser.setInitializedForLoop(true);
                    String cmd = "";

                    //brings the parser to the line where loop is called without doing anything
                    do {
                        cmd = Arrays.toString(loopParser.readCommand());

                        //puts cmd in ascii without brackets or commas from the array
                        cmd = cmd.substring(1);
                        cmd = cmd.substring(0, cmd.length() - 1);
                        while (cmd.contains(",")) {
                            cmd = cmd.substring(0, cmd.indexOf(",")) + cmd.substring(cmd.indexOf(",") + 1);
                        }

                    } while (loopParser.getReader().hasNext() && !cmd.equalsIgnoreCase(startingLine));


                    while (loopParser.getReader().hasNext() && !loopParser.readCommand()[0].equalsIgnoreCase("STOP")) {
                        loopParser.doCommand();
                        //only works the last time
                    }
                    loopTimes--;
                    this.store=loopParser.getStore();
                }
            }
        } else {
            //"Array not initialized"
            throw new NullPointerException(".- .-. .-. .- / -.-- -. --- / - .. -. .. - .. .- .-.. .. --.. . -..");
        }
        return true;
    }

    public void setInitializedForLoop(boolean initialized){this.initialized=initialized;}

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
