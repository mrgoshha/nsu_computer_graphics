import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReader {
    String data;

     FileReader() {
        try {
            Path path = Paths.get("src/main/resources/about.txt");
            data = Files.readString(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
     }

     String getText(){
         return data;
     }
}


