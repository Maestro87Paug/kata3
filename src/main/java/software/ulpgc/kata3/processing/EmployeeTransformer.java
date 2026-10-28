package software.ulpgc.kata3.processing;

import software.ulpgc.kata3.model.Employee;
import software.ulpgc.kata3.model.EmployeeReport;

import java.util.ArrayList;
import java.util.List;

public class EmployeeTransformer {

    private final EmployeeParser parser;

    public EmployeeTransformer(EmployeeParser parser) {
        this.parser = parser;
    }

    public EmployeeReport transform(List<String> lines) {
        List<Employee> employees = new ArrayList<>();
        int discarded = 0;

        // La primera línea es la cabecera.
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);

            if (line.isBlank()) {
                continue;
            }

            try {
                employees.add(parser.parse(line));
            } catch (IllegalArgumentException e) {
                discarded++;
            }
        }

        return new EmployeeReport(
                employees,
                new EmployeeReport.Metadata(
                        employees.size(),
                        discarded
                )
        );
    }
}