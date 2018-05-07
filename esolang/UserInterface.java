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
    final JFileChooser fileChooser;

    public UserInterface(){


        setSize(new Dimension(512, 128));
        setTitle("-- --- .-. .-. .. ..._-.-. --- -.. .");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(false);

        fileChooser=new JFileChooser(System.getProperty("user.home")+"/Desktop");
        fileChooser.setVisible(true);
        fileChooser.setDialogTitle("-- --- .-. .-. .. ..._-.-. --- -.. .");
        int itNeedsThisToWorkIDontKnowWhy=fileChooser.showOpenDialog(null);
        makeParser();
        //creates and draws the actual file chooser

        //FileParser only reads one command at a time, read until the file is done.
        try{
            while(parser.getReader().hasNext()){
                parser.doCommand();
            }
        }
        catch (NullPointerException e){/*when file is over*/}
    }

    public void makeParser(){
        parser=new FileParser(fileChooser.getSelectedFile());
    }
    public FileParser getParser(){
        return parser;
    }

}
