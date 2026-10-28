package software.ulpgc.kata3;

import software.ulpgc.kata3.io.JsonWriter;
import software.ulpgc.kata3.io.TsvReader;
import software.ulpgc.kata3.model.EmployeeReport;
import software.ulpgc.kata3.processing.EmployeeParser;
import software.ulpgc.kata3.processing.EmployeeTransformer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {
        String header = String.join("\t",
                "Name", "Age", "LastWorkingDay", "Department",
                "Education", "Gender", "MaritalStatus",
                "MonthlyIncome", "YearsOfExperience",
                "PercentSalaryHike", "PerformanceRating"
        );

        String laura = String.join("\t",
                "Laura", "28", "31-12-9999", "Development",
                "Masters", "", "Single",
                "2400", "4", "10", "3"
        );

        String pedro = String.join("\t",
                "Pedro", "35", "31-12-9999", "Sales",
                "Graduate", "Male", "Married",
                "2800", "8", "9", "4"
        );

        String invalidEmployee = String.join("\t", "Luis", "32");

        String data = String.join("\n",
                header,
                laura,
                pedro,
                invalidEmployee
        );

        TsvReader reader = new TsvReader();
        EmployeeParser parser = new EmployeeParser();
        EmployeeTransformer transformer = new EmployeeTransformer(parser);
        JsonWriter writer = new JsonWriter();

        Path output = Path.of("output/employees.json");

        try {
            EmployeeReport report;

            try (Reader source = new StringReader(data)) {
                report = transformer.transform(reader.read(source));
            }

            Files.createDirectories(output.getParent());

            try (OutputStream destination = Files.newOutputStream(output)) {
                writer.write(report, destination);
            }

            System.out.println("JSON generado en: " + output);

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}