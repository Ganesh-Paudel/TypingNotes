import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainPanel extends JPanel{

    JButton fileChooseButton;
    JFileChooser fileChooser;
    String filePath = "";
    ReadFile readFile;
    int posX, posY;
    TextPanel textPanel;

    /**
     * initializes the objects,
     * Background, adds button,
     * Starts the thread
     *
     */
    public MainPanel() {
        initializeObjects();
        buttonConfig();
        this.setBackground(Color.lightGray);
        this.setLayout(new BorderLayout());
        addObjects();

    }

    /**
     * Configures the button and overrides actionPerformed method which opens a file Choose dialogbox on click of the
     * Button and then it calls the ReadFile class which will read the whole file for now
     */
    private void buttonConfig() {
        fileChooseButton.setMaximumSize(new Dimension(50, 30));
        fileChooseButton.setPreferredSize(new Dimension(50, 30));
        fileChooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int resultValue = fileChooser.showOpenDialog(MainPanel.this);
                if (resultValue == JFileChooser.APPROVE_OPTION) {
                    filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    System.out.println("File selected: " + fileChooser.getSelectedFile().getAbsolutePath());
                    textPanel.readFile(filePath);
//                    textPanel.printQueue();
                    textPanel.requestFocusInWindow();

                } else if (resultValue == JFileChooser.CANCEL_OPTION) {
                    System.out.println("File selection cancelled.");
                } else if (resultValue == JFileChooser.ERROR_OPTION) {
                    System.out.println("Error occurred while selecting file.");
                } else {
                    System.out.println("Unknown error occurred.");
                }

            }
        });

    }

    /**
     * Initializes the objects
     */
    private void initializeObjects() {
        posX = 600;
        posY = 250;
        fileChooseButton = new JButton("Choose File");
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text files (*.txt)", "txt"));
        textPanel = new TextPanel();
    }

    /**
     * Adds the objects
     */
    private void addObjects() {
        this.add(fileChooseButton, BorderLayout.NORTH);
        this.add(textPanel, BorderLayout.CENTER);

    }



}
