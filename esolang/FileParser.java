package esolang;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

import static java.lang.Integer.parseInt;
import static java.lang.System.*;
import static esolang.CharInterpreter.*;

public class FileParser {

    //What works: morris, right, left, up, down, out, cat, add, multiply, divide (integer), jump, in, if, copy, paste,
    // pointer, loop, outint, goto
    File codeSource;
    Scanner reader;
    Scanner uInput;

    DataStore store;
    private boolean initialized;

    //initialized means that they called MORRIS and the array exists
    public FileParser(File codeSource) {
        this.codeSource = codeSource;
        try {
            reader = new Scanner(codeSource);
        } catch (Exception e) {
            System.out.println("..-. .. .-.. . / -. --- - / ..-. --- ..- -. -..");
        }//File not found
        uInput = new Scanner(in);
    }

    public Scanner getReader() {
        return this.reader;
    }

    public String getFileName() {
        return codeSource.getName();
    }

    public void setStore(DataStore s) {
        store = s;
    }

    public DataStore getStore() {
        return store;
    }

    public String[] readCommand() throws NoSuchElementException {
        String command;
        try {
            command = reader.nextLine();
        } catch (NoSuchElementException e) {
            return new String[0];
        }
        String[] commandArr;
        if (command.length() == 0) {
            String[] errorArray = new String[1];
            errorArray[0] = "...---...";
            return errorArray;
        }
        //if multi-word command, make it the first word
        if (command.contains("/")) {
            commandArr = command.split("/");
        } else {
            commandArr = new String[1];
            commandArr[0] = command;
        }
        //convert the morse into Ascii to make clearer for source coder
        for (int i = 0; i < commandArr.length; i++) {
            String[] letters = commandArr[i].split(" ");
            String recomposed = "";
            for (int j = 0; j < letters.length; j++) {
                letters[j] = CharList.enumToAscii(CharList.morseToEnum(letters[j]));
                recomposed += letters[j];
            }
            //It's a horrible hack, I know.
            commandArr[i] = recomposed;
        }
        //System.out.println(Arrays.toString(commandArr));
        return commandArr;
    }

    public boolean doCommand() throws NullPointerException {
        String[] command;
        try {
            command = readCommand();
        } catch (NullPointerException e) {
            return false;
        }
        //System.out.println(Arrays.toString(command));
        //takes the inputted command and splits into the command and the arguments
        String baseCommand = command[0];
        String[] args = new String[command.length - 1];
        arraycopy(command, 1, args, 0, command.length - 1);

        if (baseCommand.equals("MORRIS")) {
            store = new DataStore(parseInt(args[0]));
            initialized = true;
        } else if (initialized) {
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
            } else if (baseCommand.equals("RANDOM")) {
                store.random();
            } else if (baseCommand.equals("IF")) {
                if (args[0].substring(1).equals("MORE") && store.getByteAtLoc(store.getPointer()) <= parseInt(args[1])) {
                    while (!readCommand()[0].equalsIgnoreCase("ISTOP")) {
                        readCommand();
                    }
                } else if (args[0].substring(1).equals("EQUALS") && store.getByteAtLoc(store.getPointer()) != parseInt(args[1])) {
                    while (!readCommand()[0].equalsIgnoreCase("ISTOP")) {
                        readCommand();
                    }
                } else if (args[0].substring(1).equals("LESS") && store.getByteAtLoc(store.getPointer()) >= parseInt(args[1])) {
                    while (!readCommand()[0].equalsIgnoreCase("ISTOP")) {
                        readCommand();
                    }
                }
            } else if (baseCommand.equals("IN")) {
                out.println(".. -. .--. ..- - .- -... -.-- - .");//INPUTABYTE
                String toInput = uInput.nextLine();
                store.inputValue(toInput);
            } else if (baseCommand.equals("OUT")) {
                store.outputValue();
            } else if (baseCommand.equals("OUTINT")) {
                store.outInt();
            } else if (baseCommand.equals("CAT")) {
                store.cat(Arrays.toString(args));
            } else if (baseCommand.equals("COPY")) {
                store.copy();
            } else if (baseCommand.equals("PASTE")) {
                store.paste();
            } else if (baseCommand.equals("POINTER")) {
                store.showPointer();
            } else if (baseCommand.equals("LOOP")) {
                int loopTimes = store.getByteAtLoc(store.getPointer());
                String startingLine = baseCommand;
                for (int i = 0; i < args.length; i++) {
                    startingLine = startingLine + " " + args[i];
                }
                while (loopTimes > 1) {
                    FileParser loopParser = new FileParser(codeSource);
                    loopParser.setStore(store);
                    loopParser.setInitializedForLoop(true);
                    String cmd = "";

                    //brings the parser to the line where loop is called without doing anything
                    do {
                        cmd = parseCmdInLoop(loopParser);
                    } while (loopParser.getReader().hasNext() && !cmd.equalsIgnoreCase(startingLine));

                    boolean shouldBeInLoop = true;
                    while (loopParser.getReader().hasNext() && shouldBeInLoop) {
                        loopParser.doCommand();
                        if (cmd.equalsIgnoreCase("LSTOP")) {
                            shouldBeInLoop = false;
                        }
                    }
                    loopTimes--;
                    this.setStore(loopParser.getStore());
                    loopParser = null;
                }
                //I'm aware that GOTO is usually evil and makes your code illegible.
                //But this is Morris Code.
                //It's illegible anyways.
                //Use GOTO to your heart's content.
            } else if (baseCommand.equals("GOTO")) {
                int lineNum = store.getByteAtLoc(store.getPointer());
                try {
                    //makes a new scanner that starts at line 0, then skips to the line of the number in the current cell.
                    reader = new Scanner(codeSource);
                    for (int i = 0; i < lineNum; i++) {
                        readCommand();
                    }
                } catch (FileNotFoundException e) {
                }
            }
        } else {
            //"Array not initialized"
            throw new NullPointerException(".- .-. .-. .- / -.-- -. --- / - .. -. .. - .. .- .-.. .. --.. . -..");
        }
        return true;
    }

    public String parseCmdInLoop(FileParser loopParser) {
        String cmd = Arrays.toString(loopParser.readCommand());

        //puts cmd in ascii without brackets or commas from the array
        cmd = cmd.substring(1);
        cmd = cmd.substring(0, cmd.length() - 1);
        while (cmd.contains(",")) {
            cmd = cmd.substring(0, cmd.indexOf(",")) + cmd.substring(cmd.indexOf(",") + 1);
        }
        return cmd;
    }

    public void setInitializedForLoop(boolean initialized) {
        this.initialized = initialized;
    }
}
