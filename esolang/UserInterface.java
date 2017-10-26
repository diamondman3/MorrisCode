package esolang;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

/**
 * Created by maxwelljm19 on 9/28/2017.
 */
public class UserInterface extends JFrame {

    public FileParser parser=null;
    JFileChooser fileChooser;
    public UserInterface(){
        setSize(new Dimension(512, 128));
        setTitle("-- --- .-. .-. .. ..._-.-. --- -.. .");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String [] args) {
        UserInterface ui = new UserInterface();
        ui.setVisible(false);
        ui.fileChooser=new JFileChooser();
        ui.fileChooser.setDialogTitle("-- --- .-. .-. .. ..._-.-. --- -.. .");
        ui.makeParser();
        try{
            while(ui.parser.getReader().hasNext()){
                ui.parser.doCommand();
            }
        }
        catch (NullPointerException e){/*nothing*/}
    }

    public void makeParser(){
        parser=new FileParser(fileChooser.getSelectedFile());
    }
    public FileParser getParser(){
        return parser;
    }

}
