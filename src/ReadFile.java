import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ReadFile {

    private ArrayList<String> wordsInFile;

    public ReadFile(String filePath) {
        wordsInFile = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8));
            String line;
            while((line = reader.readLine()) != null){
//                line+=".";
                if(!line.trim().isEmpty()){
                    String[] words = line.split("\\s+");

//                System.out.print("The line: ");
//                System.out.println(line);
                    //                    System.out.print(word + "!!");
                    wordsInFile.addAll(Arrays.stream(words)
                            .filter(word -> !word.isEmpty())
                            .toList());
                }

            }
            reader.close();

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public ArrayList<String> getWords(){
        return wordsInFile;
    }



}
