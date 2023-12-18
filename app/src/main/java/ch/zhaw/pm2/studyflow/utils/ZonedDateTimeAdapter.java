package ch.zhaw.pm2.studyflow.utils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The ZonedDateTimeAdapter generates form the json input a {@link ZonedDateTime}.
 */
public class ZonedDateTimeAdapter extends TypeAdapter<ZonedDateTime> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_ZONED_DATE_TIME;

    /**
     * This method generates a {@link ZonedDateTime} after writing.
     */
    @Override
    public void write(JsonWriter out, ZonedDateTime value) throws IOException {
        out.value(FORMATTER.format(value));
    }

    /**
     * This method generates a {@link ZonedDateTime} after reading.
     */
    @Override
    public ZonedDateTime read(JsonReader in) throws IOException {
        String value = in.nextString();
        return ZonedDateTime.parse(value, FORMATTER);
    }
}
