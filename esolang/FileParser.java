package esolang; /**
 * Created by maxwelljm19 on 9/19/2017.
 */
import java.io.*;
import java.util.Scanner;
public class FileParser {
    File codeSource;
    Scanner reader;
    public FileParser(String fileLocation){
        codeSource=new File(fileLocation);
    }
}
