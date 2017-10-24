package esolang;

public class CharInterpreter {

    /**
     * Enumeration class CharList - List of characters and the morse code equivalents
     *
     * @author (your name here)
     * @version (version number or date here)
     */
    public enum CharList {
        CHAR_A("A", ".-"),
        CHAR_B("B", "-..."),
        CHAR_C("C", "-.-."),
        CHAR_D("D", "-.."),
        CHAR_E("E", "."),
        CHAR_F("F", "..-."),
        CHAR_G("G", "--."),
        CHAR_H("H", "...."),
        CHAR_I("I", ".."),
        CHAR_J("J", ".---"),
        CHAR_K("K", "-.-"),
        CHAR_L("L", ".-.."),
        CHAR_M("M", "--"),
        CHAR_N("N", ".-"),
        CHAR_O("O", "---"),
        CHAR_P("P", ".--."),
        CHAR_Q("Q", "--.-"),
        CHAR_R("R", ".-."),
        CHAR_S("S", "..."),
        CHAR_T("T", "-"),
        CHAR_U("U", "..-"),
        CHAR_V("V", "...-"),
        CHAR_W("W", ".--"),
        CHAR_X("X", "-..-"),
        CHAR_Y("Y", "-.--"),
        CHAR_Z("Z", "--.."),
        CHAR_0("0", "-----"),
        CHAR_1("1", ".----"),
        CHAR_2("2", "..---"),
        CHAR_3("3", "...--"),
        CHAR_4("4", "....-"),
        CHAR_5("5", "....."),
        CHAR_6("6", "-...."),
        CHAR_7("7", "--..."),
        CHAR_8("8", "---.."),
        CHAR_9("9", "----.");

        private String ascii;
        private String morse;

        CharList(String ascii, String morse) {
            setAscii(ascii);
            setMorse(morse);
        }

        public void setAscii(String newAscii) {
            ascii = newAscii;
        }

        public void setMorse(String newMorse) {
            ascii = newMorse;
        }

        public CharList asciiToEnum(String ascii){

        }

        public String enumToAscii(CharList charInEnum){

        }

        public CharList morseToEnum(String morse){

        }

        public String enumToMorse(String charInEnum){

        }

    }

    public CharInterpreter() {}
}