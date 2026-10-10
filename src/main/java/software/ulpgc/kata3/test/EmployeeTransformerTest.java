package software.ulpgc.kata3.test;

import org.junit.jupiter.api.Test;
import software.ulpgc.kata3.EmployeeParser;
import software.ulpgc.kata3.EmployeeReport;
import software.ulpgc.kata3.EmployeeTransformer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeTransformerTest {

    private final EmployeeTransformer transformer =
            new EmployeeTransformer(new EmployeeParser());

    private String line(String... fields) {
        return String.join("\t", fields);
    }

    @Test
    void should_transform_valid_lines_skipping_header() {
        EmployeeReport report = transformer.transform(List.of(
                line("Name", "Age", "Day", "Dept", "Edu", "Gender", "Marital", "Income", "Exp", "Hike", "Rating"),
                line("John", "37", "31-12-9999", "R&D", "Doctorate", "Male", "Single", "2090", "6", "15", "3")
        ));

        assertEquals(1, report.employees().size());
        assertEquals(1, report.metadata().validRecords());
        assertEquals(0, report.metadata().discardedRecords());
    }

    @Test
    void should_discard_invalid_lines() {
        EmployeeReport report = transformer.transform(List.of(
                line("Name", "Age", "Day", "Dept", "Edu", "Gender", "Marital", "Income", "Exp", "Hike", "Rating"),
                line("John", "37"),
                line("Sandra", "33", "31-12-9999", "Dev", "Undergraduate", "Female", "Married", "2909", "1", "11", "3")
        ));

        assertEquals(1, report.metadata().validRecords());
        assertEquals(1, report.metadata().discardedRecords());
    }

    @Test
    void should_skip_blank_lines() {
        EmployeeReport report = transformer.transform(List.of(
                line("Name", "Age", "Day", "Dept", "Edu", "Gender", "Marital", "Income", "Exp", "Hike", "Rating"),
                "   ",
                line("Sandra", "33", "31-12-9999", "Dev", "Undergraduate", "Female", "Married", "2909", "1", "11", "3")
        ));

        assertEquals(1, report.employees().size());
        assertEquals(0, report.metadata().discardedRecords());
    }
}
