/*
Ganesh Paudel
1.0.0
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

/*
textpanel which will be the main animation panel which will be the keyarea of the app
 */
public class TextPanel extends JPanel implements KeyListener,Runnable{


    /**
     * Representation of String to be displayed
     * Contains the coordinates along with the word (string)
     * have some methods to change some properties
     */
    private static class stringRepresentation{

        private int xCord, yCord; //x and y coordinates of the string that will be displayed
        private String word; // the actual word
        private boolean active = false; // denotes if the word is currently being typed or not
        private boolean isFalling = false; // denotes if the word is falling or not
        private double alpha = 1.0f; // to have the fading effect initial alpha value to 1
        private double moveY= 0; // for the falling effect but initially just no falling with 0

        /**
         * creates a string representation with the given word
         * @param word the word that is to be stored in this representation
         */
        public stringRepresentation(String word){
            this.word = word;
        }

        /**
         * setter method or mutator method for the x coordinate
         * @param xCord the value of the xcordinate
         */
        public void setXCord(int xCord){
            this.xCord = xCord;
        }

        /**
         * mutator method for the y coordinates
         * @param yCord the y coordinate of the string
         */
        public void setYCord(int yCord){
            this.yCord = yCord;
        }

        /**
         * if the string is going to fall call this function
         */
        public void startFalling(){
            isFalling = true;
            moveY = 5;
        }

        /**
         * if the string is falling changes the y coordinate of the string while slowly fading also doesn't go beyond the range of
         * 430 vertically
         */
        public void updateFalling(){
            if(isFalling){
                yCord += moveY;
                if(alpha > 0){
                    alpha -= 0.01f;
                }
                else{
                    alpha = 0;
                }
                if(yCord >= 430){
                    yCord = 430;

                }
            }
        }


        /**
         * Representation of the class
         * @return about class in strings
         */
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
    Thread thread; // thread for the repaint and update
    ReadFile readFile; //readfile to retrieve the data from the file
    ArrayList<stringRepresentation> strings; // list of the stringrepresentation will be the ones being displayed
    boolean haveFile = false; // to ensure that there won't be any errors while the user just opened and haven't chosen the file, used in if cases
    private String currentWord; // stores the current active word
    private int typeCounter = 0; // counts the no of letters we are in the word
    private int wordCounter = 0; // counts the word we are in the list


    /**
     * Initialize the panel
     * set the minimum and maximum size so that This is displayed properly
     * focusable so that it can get focus and read the keyEvent from the user
     * thread to start the thread
     */
    public TextPanel(){

        this.setBackground(Color.gray);
        this.setFocusable(true); // grab the focus
        this.addKeyListener(this); // adds a keylistener to the panel
        thread = new Thread(this); // starts a thread on the panel
        thread.start(); // starts the thread
    }

    /**
     * Reads the file of the given path
     * @param filePath the path of the file to be read
     */
    public void readFile(String filePath){
        readFile = new ReadFile(filePath); //creates a instance of readfile
        haveFile = true; // changes the havefile to true which means we have the file
        try{
            initializeQueue(); //initializes the list
            initializePositions(); //initializes the postiion of the strings

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
            repaint(); // constantly repaints
            if(haveFile){
                updateFallingWords(); //if we have file it constantly checks for the falling words as well
            }
            try {
                Thread.sleep(16); // ~60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            System.out.println("Running...");
        }
    }

    private void updateFallingWords() { //if the words are fallign then it will call the update falling method which will change the y coordinate
        for(stringRepresentation sr: strings){
            if(sr.isFalling){
                sr.updateFalling();
            }
        }
    }

    /**
     * Update the condition
     * Decrease the x coordinates so that it makes animation like style of the texts moving left
     * Only get's called if the user inputs correct word
     */
    public void update() {
        /*
        i had to do the following because if not concurrent modification of the list occured
         */
        int removeCount = 0; //no of element to remove
        ArrayList<stringRepresentation> toRemove = new ArrayList<>(); //list of item to remove

        /*word counter will always be in bound and also the x.cord will not go -beyond 50 */
        if (wordCounter < strings.size() && strings.get(wordCounter).xCord >= 50) {
            /* go through the whole list and if they are not falling just change the x cordinate and if they fade away then add them to
            removelist
             */
            for (stringRepresentation sr : strings) {
                if(!sr.isFalling){
                    sr.xCord -= 20;
                }
                if (sr.isFalling && sr.alpha <= 0) {
                    toRemove.add(sr);
                    removeCount++;
                }
            }
        }

        /* Remove the words that moved out of bounds */
        strings.removeAll(toRemove);

        /* Adjust wordCounter safely */
        wordCounter = Math.max(0, wordCounter - removeCount);

        /* Ensure wordCounter is valid before accessing elements */
        if (!strings.isEmpty() && wordCounter < strings.size()) {
            /* set the word in the list which is current to active and also set the current word to be that word */
            strings.get(wordCounter).active = true;
            currentWord = strings.get(wordCounter).word;
        } else {
            currentWord = "";
        }

        System.out.println(wordCounter);
        System.out.println(currentWord);
    }


    /**
     *
     * Draws the word and also different color for current word
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        char currentChar; //print the current char so the user won't get lost
        Graphics2D g2d = (Graphics2D) g; //graphics2d
        g2d.setFont(new Font("Arial", Font.PLAIN, 30)); // font of the strings and size

        /* if we have the file */
        if(haveFile) {

            for (stringRepresentation s : strings) {
                /* and while the strings are visible on the screen (in the screen bounds) */
                if (s.xCord <= 800 && s.xCord >= -10) {
                    /* set the color and alpha to be min of 0 and amx of 1 with */
                    g2d.setColor(new Color(0, 0, 0, (int) (Math.max(0, Math.min(1, s.alpha)) * 255)));
                    /* if it's the active word then change the color to red */
                    if(s.active){
                        /* this is to display the current character in the current word */
                        currentChar = s.word.charAt(typeCounter);
                        g2d.setColor(Color.lightGray);
                        g2d.drawString("Current letter: '" + String.valueOf(currentChar) + "'", 200, 150);

                        g2d.setColor(Color.red);//set the color to red
                    }

                    /* draw the string on it's coordinates */
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
        currentWord = strings.get(wordCounter).word;
    }

    /**
     * Sets the position
     * y is constant 250,
     * x changes and starts from 400
     * it changes by step which is determined by the length of the word
     */
    private void initializePositions(){
        int x = 400, y = 250, stepX;

        Font font = new Font("Arial", Font.PLAIN, 30);
        FontMetrics metrics = getFontMetrics(font);
        for(int i = 0; i < strings.size(); i++){
            stepX = metrics.stringWidth(strings.get(i).word);
            strings.get(i).setXCord(x);
            strings.get(i).setYCord(y);
            x += stepX + 20;

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
    public boolean checkKeyPressed(char key) {
        if (typeCounter < currentWord.length() && key == currentWord.charAt(typeCounter)) {
            typeCounter++;

            // If whole word is typed correctly, move to the next one
            if (typeCounter == currentWord.length()) {
                strings.get(wordCounter).active = false;
                strings.get(wordCounter).startFalling();
                wordCounter++;
                if (wordCounter < strings.size()) {
                    currentWord = strings.get(wordCounter).word;
                    typeCounter = 0;
                    strings.get(wordCounter).active = true; // Activate new word
                }
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
            /* update is only called if the key is correct */
            this.update();
        }
        else{

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
