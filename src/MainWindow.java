import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

public class MainWindow extends JFrame {

    MainPanel panel;

    /**
     * Constructor which initializes all the objects, sets the title, size and shows the frame then adds the objects
     */
    public MainWindow() {
        initializeObjects();
        this.setTitle("Type Notes");
        this.setSize(new Dimension(800, 500));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // this.setBackground(Color.blue);
        this.setResizable(false);
        this.setFocusable(true);
        this.setVisible(true);
        addObjects();

    }

    /**
     * Initializes all the objects in the class
     */
    private void initializeObjects() {
        panel = new MainPanel();
    }

    /**
     * Adds all the objects or elements in the frame
     */
    private void addObjects() {
        this.add(panel);
    }

}
