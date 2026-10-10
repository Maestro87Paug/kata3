package software.ulpgc.kata3;

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