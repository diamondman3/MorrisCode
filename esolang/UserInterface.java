package esolang;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by maxwelljm19 on 9/28/2017.
 */
public class UserInterface extends JFrame {

    private static JButton browseButton;
    public UserInterface(){
        setSize(new Dimension(512, 128));
        setTitle("-- --- .-. .-. .. ..._-.-. --- -.. .");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        browseButton=new JButton("-... .-. --- .-- ... .");
    }

    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        ui.setVisible(true);

        ui.dispose();
        JFileChooser fileChooser=new JFileChooser();
        fileChooser.setDialogTitle("-- --- .-. .-. .. ..._-.-. --- -.. .");
        int returnVal=fileChooser.showOpenDialog(browseButton);
        FileParser parser=new FileParser(fileChooser.getSelectedFile());
        try{
                while(parser.getReader().hasNext()){
                    parser.doCommand();
                }
            }
        catch (NullPointerException e){/*nothing*/}
    }
}
