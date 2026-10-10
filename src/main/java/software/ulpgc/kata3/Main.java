package software.ulpgc.kata3;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws IOException {
        Path input = Path.of("src/main/resources/employees.tsv");
        Path output = Path.of("output/employees.json");

        TsvReader reader = new TsvReader();
        EmployeeParser parser = new EmployeeParser();
        EmployeeTransformer transformer = new EmployeeTransformer(parser);
        JsonWriter writer = new JsonWriter();

        EmployeeReport report;

        try (Reader source = Files.newBufferedReader(
                input, StandardCharsets.UTF_8)) {

            report = transformer.transform(reader.read(source));
        }

        Files.createDirectories(output.getParent());

        try (OutputStream destination = Files.newOutputStream(output)) {
            writer.write(report, destination);
        }

        System.out.println("JSON generado en: " + output);
    }
}