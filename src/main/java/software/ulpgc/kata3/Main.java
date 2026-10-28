package software.ulpgc.kata3;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import software.ulpgc.kata3.io.JsonWriter;
import software.ulpgc.kata3.io.TsvReader;
import software.ulpgc.kata3.model.EmployeeReport;
import software.ulpgc.kata3.processing.EmployeeParser;
import software.ulpgc.kata3.processing.EmployeeTransformer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {
        Path input = Path.of("src/main/resources/employees.tsv");
        Path output = Path.of("output/employees.json");

        TsvReader reader = new TsvReader();
        EmployeeParser parser = new EmployeeParser();
        EmployeeTransformer transformer = new EmployeeTransformer(parser);
        JsonWriter writer = new JsonWriter();

        Gson gson = new GsonBuilder().serializeNulls().create();

        try {
            EmployeeReport fileReport;
            EmployeeReport memoryReport;

            try (Reader source = Files.newBufferedReader(
                    input, StandardCharsets.UTF_8
            )) {
                fileReport = transformer.transform(reader.read(source));
            }

            String data = Files.readString(
                    input, StandardCharsets.UTF_8
            );

            try (Reader source = new StringReader(data)) {
                memoryReport = transformer.transform(reader.read(source));
            }

            if (!fileReport.equals(memoryReport)) {
                throw new IOException(
                        "Fichero y memoria producen resultados diferentes"
                );
            }

            EmployeeReport emptyReport;

            try (Reader source = new StringReader("")) {
                emptyReport = transformer.transform(reader.read(source));
            }

            if (!emptyReport.employees().isEmpty()
                    || emptyReport.metadata().validRecords() != 0
                    || emptyReport.metadata().discardedRecords() != 0) {
                throw new IOException(
                        "El resultado de la entrada vacía es incorrecto"
                );
            }

            Files.createDirectories(output.getParent());

            try (OutputStream destination = Files.newOutputStream(output)) {
                writer.write(fileReport, destination);
            }

            String generatedJson = Files.readString(
                    output, StandardCharsets.UTF_8
            );

            if (!JsonParser.parseString(generatedJson)
                    .equals(gson.toJsonTree(fileReport))) {
                throw new IOException(
                        "El JSON generado no coincide con los datos"
                );
            }

            System.out.println("JSON generado y verificado en: " + output);

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}