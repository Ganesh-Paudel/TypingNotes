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
        private boolean active = false;
        private boolean isFalling = false;
        private double alpha = 1.0f;
        private double moveY= 0;



        public stringRepresentation(String letter){
            this.word = letter;
        }

        public void setXCord(int xCord){
            this.xCord = xCord;
        }
        public void setYCord(int yCord){
            this.yCord = yCord;
        }

        public void startFalling(){
            isFalling = true;
            moveY = 5;
        }

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

//                    moveY = -moveY * 0.8f;

//                    if(Math.abs(moveY) < 0.1f){
//                        moveY = 0;
//                    }
                }
//                if (yCord <= 300 && moveY < 0 && Math.abs(moveY) < 1) {
//                    moveY = -moveY * 0.8f;
//                }
            }
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
    private char currentChar;
    private String currentWord;
    private int typeCounter = 0;
    private int wordCounter = 0;
    private boolean drawWordAnimation = false;
    private String drawingWord;


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
            if(haveFile){
                updateFallingWords();
            }
            try {
                Thread.sleep(16); // ~60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            System.out.println("Running...");
        }
    }

    private void updateFallingWords() {
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
        int removeCount = 0;
        ArrayList<stringRepresentation> toRemove = new ArrayList<>();

        // Ensure wordCounter does not go out of bounds
        if (wordCounter < strings.size() && strings.get(wordCounter).xCord >= 50) {
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

        // Remove the words that moved out of bounds
        strings.removeAll(toRemove);

        // Adjust wordCounter safely
        wordCounter = Math.max(0, wordCounter - removeCount);

        // Ensure wordCounter is valid before accessing elements
        if (!strings.isEmpty() && wordCounter < strings.size()) {
            strings.get(wordCounter).active = true;
            currentWord = strings.get(wordCounter).word;
        } else {
            currentWord = "";  // Avoid out-of-bounds error
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
        char currentChar;
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.white);
        g2d.setFont(new Font("Arial", Font.PLAIN, 30));
        if(haveFile) {
            for (stringRepresentation s : strings) {
                if (s.xCord <= 800 && s.xCord >= -10) {
                    g2d.setColor(new Color(0, 0, 0, (int) (Math.max(0, Math.min(1, s.alpha)) * 255)));
                    if(s.active){
                        currentChar = s.word.charAt(typeCounter);
                        g2d.setColor(Color.lightGray);
                        g2d.drawString("Current letter: '" + String.valueOf(currentChar) + "'", 200, 150);
                        g2d.setColor(Color.red);
                    }
                    int drawY = (int) (s.yCord + s.moveY);

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
