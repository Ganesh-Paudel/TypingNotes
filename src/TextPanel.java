import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class TextPanel extends JPanel implements KeyListener,Runnable{

    private static class stringRepresentation{
        private int xCord, yCord;
        private char letter;
        public stringRepresentation(char letter){
            this.letter = letter;
        }

        public void setXCord(int xCord){
            this.xCord = xCord;
        }
        public void setYCord(int yCord){
            this.yCord = yCord;
        }

        @Override
        public String toString(){
            return "Value: " + letter + " X: " + xCord + " Y: " + yCord;
        }
    }

    Thread thread;
    ReadFile readFile;
    ArrayList<stringRepresentation> strings;
    boolean haveFile = false;
    static char currentChar;
    static int typeCounter = 0;



    public TextPanel(){
        this.setMinimumSize(new Dimension(800, 200));
        this.setMaximumSize(new Dimension(800, 200));
        this.setBackground(Color.gray);
        this.setFocusable(true);
        this.addKeyListener(this);
        thread = new Thread(this);
        thread.start();
    }

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


    @Override
    public void run() {
        while(true){
            repaint();
//            System.out.println("Running...");
        }
    }

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
                        currentChar = s.letter;
                        g2d.setColor(Color.red);
                    }

                    String str = String.valueOf(s.letter);
                    g2d.drawString(str, s.xCord, s.yCord);
                }

            }
        }
    }

    private void initializeQueue() throws IOException {
        String characters = readFile.getCharacters();
        strings = new ArrayList<>();

        for(int i = 0; i < characters.length(); i++){
            strings.add(new stringRepresentation(characters.charAt(i)));
        }
    }

    private void initializePositions(){
        int x = 400, y = 250, stepX = 30;

        for(stringRepresentation sr: strings){
            sr.setXCord(x);
            sr.setYCord(y);
            x += stepX;
        }
    }

    public void printQueue(){
        for(stringRepresentation sr: strings){
            System.out.println(sr.toString());
        }
    }

    public static boolean  checkKeyPressed(char key){
        if(key == currentChar){
            return true;
        }
        return false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

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
        };

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
