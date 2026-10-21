package software.ulpgc.kata3.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import software.ulpgc.kata3.model.EmployeeReport;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class JsonWriter {

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .disableHtmlEscaping()
            .create();

    public void write(EmployeeReport report, OutputStream output)
            throws IOException {

        Writer writer = new OutputStreamWriter(
                output, StandardCharsets.UTF_8
        );

        writer.write(gson.toJson(report));
        writer.flush();
    }
}