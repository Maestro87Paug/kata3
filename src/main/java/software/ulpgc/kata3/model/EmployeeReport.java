package software.ulpgc.kata3.model;

import java.util.List;

public record EmployeeReport(
        List<Employee> employees,
        Metadata metadata
) {

    public record Metadata(
            int validRecords,
            int discardedRecords
    ) {
    }
}