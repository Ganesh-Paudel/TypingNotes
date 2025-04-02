import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile {

    private BufferedReader reader;
    private static int currentPosition;
    private static final int CHUNK_SIZE = 200;
    public ReadFile(String filePath) {
        currentPosition = 0;

        try {
            reader = new BufferedReader(new java.io.FileReader(filePath));
            if (reader.ready())
                reader.skip(currentPosition);

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public String getCharacters() throws IOException {

        char[] buffer = new char[CHUNK_SIZE];
        int charsRead = reader.read(buffer, 0, CHUNK_SIZE);
        if(charsRead == -1) return null;

        currentPosition += charsRead;
        String str = new String (buffer, 0, charsRead);
        StringBuilder newStr = new StringBuilder();

        boolean spaceAdded = false;

        for(char c : str.toCharArray()){
            if (c == ' ') {
                if(!spaceAdded && newStr.length() > 0) {
                    newStr.append(' ');
                    spaceAdded = true;
                }
            }
            else if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || c == '.'){
                newStr.append(c);
                spaceAdded = false;
            }
        }


        return newStr.toString();
    }



}
