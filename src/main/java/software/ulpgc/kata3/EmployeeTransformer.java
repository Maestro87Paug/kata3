package software.ulpgc.kata3;

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

        EmployeeReport.Metadata metadata =
                new EmployeeReport.Metadata(employees.size(), discarded);

        return new EmployeeReport(employees, metadata);
    }
}