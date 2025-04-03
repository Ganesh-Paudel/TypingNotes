import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

public class TextPanel extends JPanel implements KeyListener,Runnable{


    /**
     * Representation of String to be displayed
     * Contains the coordinates along with the word (string)
     * have some methods to change some properties
     */
    private static class stringRepresentation{
        private int xCord, yCord;
        private String word;
        public stringRepresentation(String letter){
            this.word = letter;
        }

        public void setXCord(int xCord){
            this.xCord = xCord;
        }
        public void setYCord(int yCord){
            this.yCord = yCord;
        }

        @Override
        public String toString(){
            return "Value: " + this.word + " X: " + xCord + " Y: " + yCord;
        }
    }

    /**
     * Thread for a thread to run and repaint the screen
     * readFile is a class that reads the content of the file provided
     * List of strings to be displayed
     * currentChar to keep track of current char and to check if the user typed the correct character
     * currentWord to keep track of current word in the program that is being typed
     * typeCounter to keep track of how many types did the user made
     * wordCounter to keep track where are we in the list
     */
    Thread thread;
    ReadFile readFile;
    ArrayList<stringRepresentation> strings;
    boolean haveFile = false;
    static char currentChar;
    static String currentWord;
    static int typeCounter = 0;
    static int wordCounter = 0;


    /**
     * Initialize the panel
     * set the minimum and maximum size so that This is displayed properly
     * focusable so that it can get focus and read the keyEvent from the user
     * thread to start the thread
     */
    public TextPanel(){
        this.setMinimumSize(new Dimension(800, 200));
        this.setMaximumSize(new Dimension(800, 200));
        this.setBackground(Color.gray);
        this.setFocusable(true);
        this.addKeyListener(this);
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Reads the file of the given path
     * @param filePath the path of the file to be read
     */
    public void readFile(String filePath){
        readFile = new ReadFile(filePath);
        haveFile = true;
        try{
            initializeQueue();
            initializePositions();

        }catch (IOException e){
            e.printStackTrace();
        }
    }


    /**
     * Runs the program and repaints
     */
    @Override
    public void run() {
        while(true){
            repaint();
//            System.out.println("Running...");
        }
    }

    /**
     * Update the condition
     * Decrease the x coordinates so that it makes animation like style of the texts moving left
     * Only get's called if the user inputs correct word
     */
    public void update(){
        ArrayList<stringRepresentation> toRemove = new ArrayList<>();
        for(stringRepresentation sr: strings){
            sr.xCord -= 30;
            if(sr.xCord <-10){
                toRemove.add(sr);
            }
        }
        strings.removeAll(toRemove);

    }


    /**
     *
     * Draws the word and also different color for current word
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.white);
        g2d.setFont(new Font("Arial", Font.PLAIN, 30));
        if(haveFile) {
            for (stringRepresentation s : strings) {
                if (s.xCord <= 800 && s.xCord >= -10) {
                    g2d.setColor(Color.black);
                    if(s.xCord == 400){
                        currentWord = s.word;
                        g2d.setColor(Color.red);
                    }

                    g2d.drawString(s.word, s.xCord, s.yCord);
                }

            }
        }
    }

    /**
     * Initializes the queue so that the queue contains all the elements with the words on the file
     * @throws IOException if the file doesnot exist in the given file path
     */
    private void initializeQueue() throws IOException {
        ArrayList<String> characters = readFile.getWords();
        strings = new ArrayList<>();

        /*
        Goes through the whole list of characters which is a list of string and adds a new stringRepresenation for each word
         */
        for(int i = 0; i < characters.size(); i++){
            strings.add(new stringRepresentation(characters.get(i)));
        }
    }

    /**
     * Sets the position
     * y is constant 250,
     * x changes and starts from 400
     * it changes by step which is determined by the length of the word
     */
    private void initializePositions(){
        int x = 400, y = 250, stepX;

        for(int i = 0; i < strings.size(); i++){
            stepX = strings.get(i).word.length() * 5;
            strings.get(i).setXCord(x);
            x += stepX;
        }
    }

    /**
     * for debugging purpose
     * prints the queue with it's tostring method
     */
    public void printQueue(){
        for(stringRepresentation sr: strings){
            System.out.println(sr.toString());
        }
    }

    /**
     * Checks the key pressed character is the same as the current character and returns a boolean true if it is correct and false if it is not
     * @param key the character pressed by the user
     * @return true if the character is the same as the current one and false if it is not
     */
    public static boolean  checkKeyPressed(char key){
        if(key == currentWord.charAt(wordCounter)){
            wordCounter++;
            if(wordCounter == currentWord.length()){
                wordCounter = 0;
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * Methods of KeyListener interface
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Check if the key is current character and updates the list if it is
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        char key = e.getKeyChar();
        System.out.println(key);
        if(checkKeyPressed(key)){
//            typeCounter++;
//            if(typeCounter == 100){
//
//            }
            this.update();
        }
        else{

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
