/*
Ganesh Paudel
1.0.0
 */

import java.awt.Dimension;
import javax.swing.JFrame;

/*
 *MainWindow which will act as an frame for the whole application
 * It has a panel inside which all the compoenents will be placed
 */
public class MainWindow extends JFrame {

    MainPanel panel = new MainPanel();

    /**
     * Constructor which initializes all the objects, sets the title, size and shows the frame then adds the objects
     */
    public MainWindow() {

        this.setTitle("Type Notes"); //title of the frame
        this.setSize(new Dimension(800, 500)); //size of the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ensures it closes when the user clicks the close button
        this.setResizable(false);// fixed size no change

//        this.setFocusable(true);
        this.setVisible(true);// makes sure it is visible on the screen
        this.add(panel);

    }


}
