package esolang;

import javax.swing.*;
import java.awt.*;

/**
 * Created by maxwelljm19 on 9/28/2017.
 */
public class UserInterface extends JFrame {
    public UserInterface(){
        setSize(new Dimension(512, 128));
        setTitle("-- --- .-. .-. .. ... -.-. --- -.. .");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        ui.setVisible(true);


    }
}
