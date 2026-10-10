package software.ulpgc.kata3;

import java.util.Arrays;

public class EmployeeParser {

    public Employee parse(String line) {
        String[] fields = line.split("\t");

        if (fields.length > 11) {
            throw new IllegalArgumentException(
                    "El registro tiene más de 11 columnas"
            );
        }

        fields = Arrays.copyOf(fields, 11);

        return new Employee(
                text(fields[0]),
                number(fields[1]),
                text(fields[2]),
                text(fields[3]),
                text(fields[4]),
                text(fields[5]),
                text(fields[6]),
                number(fields[7]),
                number(fields[8]),
                number(fields[9]),
                number(fields[10])
        );
    }

    private String text(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return value.trim();
    }

    private Integer number(String value) {
        String content = text(value);

        if (content == null) {
            return null;
        }

        return Integer.valueOf(content);
    }
}