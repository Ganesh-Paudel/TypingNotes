import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ReadFile {

    private BufferedReader reader;
    private String line;
    private ArrayList<String> wordsInFile;

    public ReadFile(String filePath) {
        wordsInFile = new ArrayList<>();
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));
            while((line = reader.readLine()) != null){
                String[] words = line.split("\\s+");

                System.out.print("The line: ");
//                System.out.println(line);
                for(String word : words){
                    System.out.print(word + "!!");
                    wordsInFile.add(words[0]);


                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public ArrayList<String> getWords(){
        return wordsInFile;
    }



}
