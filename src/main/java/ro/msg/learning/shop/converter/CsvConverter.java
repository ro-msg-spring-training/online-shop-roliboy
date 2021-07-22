package ro.msg.learning.shop.converter;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

public final class CsvConverter {
    private CsvConverter() {

    }

    public static <T> Collection<T> fromCsv(Class<T> dataType, InputStream inputStream)
            throws IOException {
        CsvMapper mapper = new CsvMapper().enable(CsvParser.Feature.SKIP_EMPTY_LINES);
        MappingIterator<T> reader = mapper
                .readerFor(dataType)
                .with(mapper.schemaFor(dataType).withSkipFirstDataRow(true))
                .readValues(inputStream);
        return reader.readAll();
    }

    public static void toCsv(Class<?> dataType, Collection<?> elements, OutputStream outputStream)
            throws IOException {
        CsvMapper mapper = new CsvMapper();
        SequenceWriter writer = mapper
                .writerFor(dataType)
                .with(mapper.schemaFor(dataType).withUseHeader(true))
                .writeValues(outputStream);
        writer.writeAll(elements);
    }
}
