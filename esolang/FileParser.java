package esolang; /**
 * Created by maxwelljm19 on 9/19/2017.
 */
import java.io.*;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class FileParser {
    File codeSource;
    Scanner reader;
    public boolean initialized;
    public FileParser(String fileLocation){
        codeSource=new File(fileLocation);
    }

    public String readCommand(){

        return "...---...";
    }

    public String readArgs(){

        return "";
    }

    public void doCommand(){
        String command=readCommand();
        String args=readArgs();
        if(command.equals("MORRIS")){
            DataStore store=new DataStore(parseInt(readArgs()));
            initialized=true;
        }
        else{
            if(!initialized){
                //"Array not initialized"
                throw new NullPointerException(".- .-. .-. .- -.-- -. --- - .. -. .. - .. .- .-.. .. --.. . -..");
            }

        }
    }
}
