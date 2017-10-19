package esolang;

public class CharInterpreter
{
    
/**
 * Enumeration class CharList - List of characters and the morse code equivalents
 * 
 * @author (your name here)
 * @version (version number or date here)
 */
    public enum CharList
    {
    CHAR_A, CHAR_B, CHAR_C, CHAR_D, CHAR_E, CHAR_F, CHAR_G, CHAR_H, CHAR_I, CHAR_J, 
        CHAR_K, CHAR_L, CHAR_M, CHAR_N, CHAR_O, CHAR_P, CHAR_Q, CHAR_R, CHAR_S, CHAR_T, 
        CHAR_U, CHAR_V, CHAR_W, CHAR_X, CHAR_Y, CHAR_Z, CHAR_0, CHAR_1, CHAR_2, CHAR_3, 
        CHAR_4, CHAR_5, CHAR_6, CHAR_7, CHAR_8, CHAR_9
    }

    public CharInterpreter()
    {

    }
    
    public static String enumToAscii(CharList character){
        switch(character){
            case CHAR_A:
                return "A";

            case CHAR_B:
                return "B";
           
            case CHAR_C:
                return "C";

            case CHAR_D:
                return "D";

            case CHAR_E:
                return "E";

            case CHAR_F:
                return "F";

            case CHAR_G:
                return "G";

            case CHAR_H:
                return "H";

            case CHAR_I:
                return "I";

            case CHAR_J:
                return "J";

            case CHAR_K:
                return "K";

            case CHAR_L:
                return "L";

            case CHAR_M:
                return "M";

            case CHAR_N:
                return "N";

            case CHAR_O:
                return "O";

            case CHAR_P:
                return "P";

            case CHAR_Q:
                return "Q";

            case CHAR_R:
                return "R";

            case CHAR_S:
                return "S";

            case CHAR_T:
                return "T";

            case CHAR_U:
                return "U";

            case CHAR_V:
                return "V";

            case CHAR_W:
                return "W";

            case CHAR_X:
                return "X";

            case CHAR_Y:
                return "Y";

            case CHAR_Z:
                return "Z";

            case CHAR_0:
                return "0";

            case CHAR_1:
                return "1";

            case CHAR_2:
                return "2";

            case CHAR_3:
                return "3";

            case CHAR_4:
                return "4";

            case CHAR_5:
                return "5";

            case CHAR_6:
                return "6";

            case CHAR_7:
                return "7";

            case CHAR_8:
                return "8";

            case CHAR_9:
                return "9";
                
            default:
                return "...---...";
        }
    }
    
    public static String enumToMorseCode(CharList character){
        switch(character){
            case CHAR_A:
                return ".-";

            case CHAR_B:
                return "-...";
           
            case CHAR_C:
                return "-.-.";

            case CHAR_D:
                return "-..";

            case CHAR_E:
                return ".";

            case CHAR_F:
                return "..-.";

            case CHAR_G:
                return "--.";

            case CHAR_H:
                return "....";

            case CHAR_I:
                return "..";

            case CHAR_J:
                return ".---";

            case CHAR_K:
                return "-.-";

            case CHAR_L:
                return ".-..";

            case CHAR_M:
                return "--";

            case CHAR_N:
                return ".-";

            case CHAR_O:
                return "---";

            case CHAR_P:
                return ".--.";

            case CHAR_Q:
                return "--.-";

            case CHAR_R:
                return ".-.";

            case CHAR_S:
                return "...";

            case CHAR_T:
                return "-";

            case CHAR_U:
                return "..-";

            case CHAR_V:
                return "...-";

            case CHAR_W:
                return ".--";

            case CHAR_X:
                return "-..-";

            case CHAR_Y:
                return "-.--";

            case CHAR_Z:
                return "--..";

            case CHAR_0:
                return "-----";

            case CHAR_1:
                return ".----";

            case CHAR_2:
                return "..---";

            case CHAR_3:
                return "...--";

            case CHAR_4:
                return "....-";

            case CHAR_5:
                return ".....";

            case CHAR_6:
                return "-....";

            case CHAR_7:
                return "--...";

            case CHAR_8:
                return "---..";

            case CHAR_9:
                return "----.";
                
            default:
                return "...---...";
        }
    }

    public static CharList morseCodeToEnum(String input){
        if(input.equals(".-")){
            return CharList.CHAR_A;
        }else if(input.equals("-...")){
            return CharList.CHAR_B;

        }else if(input.equals("-.-.")){
            return CharList.CHAR_C;

        }else if(input.equals("-..")){
            return CharList.CHAR_D;

        }else if(input.equals(".")){
            return CharList.CHAR_E;

        }else if(input.equals("..-.")){
            return CharList.CHAR_F;

        }else if(input.equals("--.")){
            return CharList.CHAR_G;

        }else if(input.equals("....")){
            return CharList.CHAR_H;

        }else if(input.equals("..")){
            return CharList.CHAR_I;

        }else if(input.equals(".---")){
            return CharList.CHAR_J;

        }else if(input.equals("-.-")){
            return CharList.CHAR_K;

        }else if(input.equals(".-..")){
            return CharList.CHAR_L;

        }else if(input.equals("--")){
            return CharList.CHAR_M;

        }else if(input.equals("-.")){
            return CharList.CHAR_N;

        }else if(input.equals("---")){
            return CharList.CHAR_O;

        }else if(input.equals(".--.")){
            return CharList.CHAR_P;

        }else if(input.equals("--.-")){
            return CharList.CHAR_Q;

        }else if(input.equals(".-.")){
            return CharList.CHAR_R;

        }else if(input.equals("...")){
            return CharList.CHAR_S;

        }else if(input.equals("-")){
            return CharList.CHAR_T;

        }else if(input.equals("..-")){
            return CharList.CHAR_U;

        }else if(input.equals("...-")){
            return CharList.CHAR_V;

        }else if(input.equals(".--")){
            return CharList.CHAR_W;

        }else if(input.equals("-..-")){
            return CharList.CHAR_X;

        }else if(input.equals("-.--")){
            return CharList.CHAR_Y;

        }else if(input.equals("--..")){
            return CharList.CHAR_Z;

        }else if(input.equals("-----")){
            return CharList.CHAR_0;

        }else if(input.equals(".----")){
            return CharList.CHAR_1;

        }else if(input.equals("..---")){
            return CharList.CHAR_2;

        }else if(input.equals("...--")){
            return CharList.CHAR_3;

        }else if(input.equals("....-")){
            return CharList.CHAR_4;

        }else if(input.equals(".....")){
            return CharList.CHAR_5;

        }else if(input.equals("-....")){
            return CharList.CHAR_6;

        }else if(input.equals("--...")){
            return CharList.CHAR_7;

        }else if(input.equals("---..")){
            return CharList.CHAR_8;

        }else if(input.equals("----.")){
            return CharList.CHAR_9;

        }else{
            return CharList.CHAR_0;
        }
    }

    public static CharList asciiToEnum(char input){
        String inString=input+"";
        inString=inString.toUpperCase();
        if(inString.equals("A")){
            return CharList.CHAR_A;
        }else if(inString.equals("B")){
            return CharList.CHAR_B;

        }else if(inString.equals("C")){
            return CharList.CHAR_C;

        }else if(inString.equals("D")){
            return CharList.CHAR_D;

        }else if(inString.equals("E")){
            return CharList.CHAR_E;

        }else if(inString.equals("F")){
            return CharList.CHAR_F;

        }else if(inString.equals("G")){
            return CharList.CHAR_G;

        }else if(inString.equals("H")){
            return CharList.CHAR_H;

        }else if(inString.equals("I")){
            return CharList.CHAR_I;

        }else if(inString.equals("J")){
            return CharList.CHAR_J;

        }else if(inString.equals("K")){
            return CharList.CHAR_K;

        }else if(inString.equals("L")){
            return CharList.CHAR_L;

        }else if(inString.equals("M")){
            return CharList.CHAR_M;

        }else if(inString.equals("N")){
            return CharList.CHAR_N;

        }else if(inString.equals("O")){
            return CharList.CHAR_O;

        }else if(inString.equals("P")){
            return CharList.CHAR_P;

        }else if(inString.equals("Q")){
            return CharList.CHAR_Q;

        }else if(inString.equals("R")){
            return CharList.CHAR_R;

        }else if(inString.equals("S")){
            return CharList.CHAR_S;

        }else if(inString.equals("T")){
            return CharList.CHAR_T;

        }else if(inString.equals("U")){
            return CharList.CHAR_U;

        }else if(inString.equals("V")){
            return CharList.CHAR_V;

        }else if(inString.equals("W")){
            return CharList.CHAR_W;

        }else if(inString.equals("X")){
            return CharList.CHAR_X;

        }else if(inString.equals("Y")){
            return CharList.CHAR_Y;

        }else if(inString.equals("Z")){
            return CharList.CHAR_Z;

        }else if(inString.equals("0")){
            return CharList.CHAR_0;

        }else if(inString.equals("1")){
            return CharList.CHAR_1;

        }else if(inString.equals("2")){
            return CharList.CHAR_2;

        }else if(inString.equals("3")){
            return CharList.CHAR_3;

        }else if(inString.equals("4")){
            return CharList.CHAR_4;

        }else if(inString.equals("5")){
            return CharList.CHAR_5;

        }else if(inString.equals("6")){
            return CharList.CHAR_6;

        }else if(inString.equals("7")){
            return CharList.CHAR_7;

        }else if(inString.equals("8")){
            return CharList.CHAR_8;

        }else if(inString.equals("9")){
            return CharList.CHAR_9;

        }else{
            return CharList.CHAR_0;
        }
    }
 }


