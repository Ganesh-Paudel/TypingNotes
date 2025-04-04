/*
Ganesh Paudel
1.0.0
 */
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

/*
The following class is a panel which acts as the parent panel for almost all objects in the application
 */
public class MainPanel extends JPanel{

    JButton fileChooseButton; //button which will trigger the file choosign system
    JFileChooser fileChooser; //popUp for the user to click the user
    String filePath = ""; //read the filepath which the user chooses
    TextPanel textPanel; //textpanel which will have the text displayed on the screen

    /**
     * initializes the objects,
     * Background, adds button,
     * Starts the thread
     *
     */
    public MainPanel() {

        initializeObjects(); // initializes button, filechooser and button
        buttonConfig(); // all  the settings regarding button from appearance to it's function
        this.setBackground(Color.lightGray); //background of the wholepanel
        this.setLayout(new BorderLayout());// setting border layout as i want button to be up top and the text to be the next thing on the screen
        addObjects(); // adds button and textpanel

    }

    /**
     * Configures the button and overrides actionPerformed method which opens a file Choose dialogbox on click of the
     * Button and then it calls the ReadFile class which will read the whole file for now
     */
    private void buttonConfig() {

        /*
        Adds a action listener whenever it is clicked the fileChooser will be triggered and it will popUP and the fileSelected will be used to call the readFile
        which reads the file
         */
        fileChooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int resultValue = fileChooser.showOpenDialog(MainPanel.this);// shows the dialogbox in the relative to mainPanel
                if (resultValue == JFileChooser.APPROVE_OPTION) { //if valid option
                    filePath = fileChooser.getSelectedFile().getAbsolutePath(); // get the absolute filepath
                    System.out.println("File selected: " + fileChooser.getSelectedFile().getAbsolutePath()); //prints for debugging purpose
                    textPanel.readFile(filePath); //calls the readfile of textPanel which reads all the words from the file
//                    textPanel.printQueue();
                    textPanel.requestFocusInWindow(); // requests the focus to the textPanel so that it can now listen to events

                } else if (resultValue == JFileChooser.CANCEL_OPTION) { // if the user closes the popUP without selecting
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

        fileChooseButton = new JButton("Choose File"); //button to choose the files
        fileChooser = new JFileChooser(); // filechooser dialog box
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text files (*.txt)", "txt")); //only accepts the txt files for now
        textPanel = new TextPanel(); // panel for the text, string
    }

    /**
     * Adds the objects
     */
    private void addObjects() {
        this.add(fileChooseButton, BorderLayout.NORTH); //adds the button to the top of the panel
        this.add(textPanel, BorderLayout.CENTER); // takes the rest of the area available

    }



}
