package software.ulpgc.kata3.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class TsvReader {

    public List<String> read(Reader source) throws IOException {
        BufferedReader reader = new BufferedReader(source);
        List<String> lines = new ArrayList<>();

        String line;

        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }

        return lines;
    }
}