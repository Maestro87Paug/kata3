package software.ulpgc.kata3.test;

import org.junit.jupiter.api.Test;
import software.ulpgc.kata3.Employee;
import software.ulpgc.kata3.EmployeeParser;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeParserTest {

    private final EmployeeParser parser = new EmployeeParser();

    @Test
    void should_parse_a_complete_line() {
        String line = String.join("\t",
                "John", "37", "31-12-9999", "R&D", "Doctorate",
                "Male", "Single", "2090", "6", "15", "3"
        );

        Employee employee = parser.parse(line);

        assertEquals("John", employee.name());
        assertEquals(37, employee.age());
        assertEquals("R&D", employee.department());
    }

    @Test
    void should_return_null_for_blank_fields() {
        String line = String.join("\t",
                "George", "36", "31-12-9999", "Dev", "Masters",
                "", "Married", "5237", "6", "13", "3"
        );

        Employee employee = parser.parse(line);

        assertNull(employee.gender());
    }

    @Test
    void should_throw_when_line_has_missing_fields() {
        String line = String.join("\t", "John", "37");

        assertThrows(
                ArrayIndexOutOfBoundsException.class,
                () -> parser.parse(line)
        );
    }

    @Test
    void should_throw_when_number_is_invalid() {
        String line = String.join("\t",
                "John", "abc", "31-12-9999", "R&D", "Doctorate",
                "Male", "Single", "2090", "6", "15", "3"
        );

        assertThrows(NumberFormatException.class, () -> parser.parse(line));
    }
}
