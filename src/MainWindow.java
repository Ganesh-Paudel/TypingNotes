import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

public class MainWindow extends JFrame {

    MainPanel panel;

    public MainWindow() {
        initializeObjects();
        this.setTitle("Type Notes");
        this.setSize(new Dimension(800, 500));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // this.setBackground(Color.blue);
        this.setResizable(false);
        this.setVisible(true);
        addObjects();

    }

    private void initializeObjects() {
        panel = new MainPanel();
    }

    private void addObjects() {
        this.add(panel);
    }

}
