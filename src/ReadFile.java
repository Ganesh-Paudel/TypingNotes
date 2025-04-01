import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile {

    public ReadFile(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new java.io.FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
