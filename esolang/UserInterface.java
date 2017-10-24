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

    public FileParser parser;
    JFileChooser fileChooser;
    public UserInterface(){
        setSize(new Dimension(512, 128));
        setTitle("-- --- .-. .-. .. ..._-.-. --- -.. .");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void setupUI() {
        setVisible(true);
        dispose();
        fileChooser=new JFileChooser();
        fileChooser.setDialogTitle("-- --- .-. .-. .. ..._-.-. --- -.. .");
        makeParser();
        try{
                while(parser.getReader().hasNext()){
                    parser.doCommand();
                }
            }
        catch (NullPointerException e){/*nothing*/}
    }

    public void makeParser(){
        parser=new FileParser(fileChooser.getSelectedFile());
    }
    public File getParser(){
        return getParser();
    }

}
