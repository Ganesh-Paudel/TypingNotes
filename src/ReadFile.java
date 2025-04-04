/*
Ganesh Paudel
1.0.0
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

/*
Reads the content of the file
 */
public class ReadFile {

    private ArrayList<String> wordsInFile; //list to store the word of  the file

    /**
     * Reads the file from the given filePath
     * @param filePath the path of the file to be read
     */
    public ReadFile(String filePath) {
        wordsInFile = new ArrayList<>(); // to store the words initialize the list
        try {
            /* buffered reader which reads from the given file */
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));
            String line;
            /* goes through every line in the file till the end */
            while((line = reader.readLine()) != null){
                /* while it is not empty */
                if(!line.trim().isEmpty()){
                    /* splits the word without any empty strings */
                    String[] words = line.split("\\s+");

                    /* adds all the words in the list */
                    wordsInFile.addAll(Arrays.stream(words)
                            .filter(word -> !word.isEmpty())
                            .toList());
                }

            }
            /* closes the buffered reader */
            reader.close();

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    /*
    getter for the list of words
     */
    public ArrayList<String> getWords(){
        return wordsInFile;
    }



}
