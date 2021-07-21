package ro.msg.learning.shop.converter;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.msg.learning.shop.model.dto.out.StockOutputDTO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

// TODO: i'm not sure if i like the constants
//       maybe find a cleaner way to write these
class CsvConverterTest {
    private StockOutputDTO firstStockOutputDTO;
    private StockOutputDTO secondStockOutputDTO;
    private Collection<StockOutputDTO> stockOutputDTOs;

    private static final String QUANTITY_FIELD = "quantity";
    private static final String LOCATION_ID_FIELD = "locationId";
    private static final String LOCATION_NAME_FIELD = "locationName";
    private static final String PRODUCT_ID_FIELD = "productId";
    private static final String PRODUCT_NAME_FIELD = "productName";
    private static final List<String> COLUMN_NAMES = List.of(
            QUANTITY_FIELD, LOCATION_ID_FIELD, LOCATION_NAME_FIELD, PRODUCT_ID_FIELD, PRODUCT_NAME_FIELD);

    private static final Integer FIRST_STOCK_QUANTITY = 42;
    private static final Integer FIRST_STOCK_LOCATION_ID = 1;
    private static final String FIRST_STOCK_LOCATION_NAME = "product 1";
    private static final Integer FIRST_STOCK_PRODUCT_ID = 2;
    private static final String FIRST_STOCK_PRODUCT_NAME = "location 1";
    private static final List<String> FIRST_STOCK_VALUES = List.of(
            FIRST_STOCK_QUANTITY.toString(),
            FIRST_STOCK_LOCATION_ID.toString(),
            '"' + FIRST_STOCK_LOCATION_NAME + '"',
            FIRST_STOCK_PRODUCT_ID.toString(),
            '"' + FIRST_STOCK_PRODUCT_NAME + '"');

    private static final Integer SECOND_STOCK_QUANTITY = 1337;
    private static final Integer SECOND_STOCK_LOCATION_ID = 3;
    private static final String SECOND_STOCK_LOCATION_NAME = "product 2";
    private static final Integer SECOND_STOCK_PRODUCT_ID = 4;
    private static final String SECOND_STOCK_PRODUCT_NAME = "location 2";
    private static final List<String> SECOND_STOCK_VALUES = List.of(
            SECOND_STOCK_QUANTITY.toString(),
            SECOND_STOCK_LOCATION_ID.toString(),
            '"' + SECOND_STOCK_LOCATION_NAME + '"',
            SECOND_STOCK_PRODUCT_ID.toString(),
            '"' + SECOND_STOCK_PRODUCT_NAME + '"');

    @BeforeEach
    void setUp() {
        firstStockOutputDTO = StockOutputDTO.builder()
                .quantity(FIRST_STOCK_QUANTITY)
                .productId(FIRST_STOCK_PRODUCT_ID)
                .productName(FIRST_STOCK_PRODUCT_NAME)
                .locationId(FIRST_STOCK_LOCATION_ID)
                .locationName(FIRST_STOCK_LOCATION_NAME)
                .build();
        secondStockOutputDTO = StockOutputDTO.builder()
                .quantity(SECOND_STOCK_QUANTITY)
                .productId(SECOND_STOCK_PRODUCT_ID)
                .productName(SECOND_STOCK_PRODUCT_NAME)
                .locationId(SECOND_STOCK_LOCATION_ID)
                .locationName(SECOND_STOCK_LOCATION_NAME)
                .build();
        stockOutputDTOs = List.of(firstStockOutputDTO, secondStockOutputDTO);
    }

    @Test
    void deserializesSerializedStockDTOCollection() throws IOException {
        var outputStream = new ByteArrayOutputStream();
        CsvConverter.toCsv(StockOutputDTO.class, stockOutputDTOs, outputStream);

        var inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        var deserializedStockDTOs = CsvConverter.fromCsv(StockOutputDTO.class, inputStream);

        Assertions.assertEquals(
                stockOutputDTOs,
                deserializedStockDTOs,
                "deserializing a serialized collection of objects should result in an identical collection");
    }

    @Test
    void serializesStockDTOCollection() throws IOException {
        var outputStream = new ByteArrayOutputStream();

        CsvConverter.toCsv(StockOutputDTO.class, stockOutputDTOs, outputStream);

        var output = outputStream.toString(StandardCharsets.UTF_8);

        Pattern pattern = Pattern.compile("([^,]+),".repeat(4) + "([^\n]+)\n?");
        Matcher matcher = pattern.matcher(output);

        Assertions.assertTrue(
                matcher.find(),
                "output should contain header row");

        Assertions.assertEquals(
                COLUMN_NAMES.size(),
                matcher.groupCount(),
                String.format("output should contain %d columns", COLUMN_NAMES.size()));

        Assertions.assertEquals(
                COLUMN_NAMES,
                IntStream.range(1, COLUMN_NAMES.size() + 1).mapToObj(matcher::group).toList(),
                "output should contain correct column names");


        Assertions.assertTrue(
                matcher.find(),
                "output should contain first data row");

        Assertions.assertEquals(
                FIRST_STOCK_VALUES.size(),
                matcher.groupCount(),
                String.format("output should contain %d columns", FIRST_STOCK_VALUES.size()));

        Assertions.assertEquals(
                FIRST_STOCK_VALUES,
                IntStream.range(1, COLUMN_NAMES.size() + 1).mapToObj(matcher::group).toList(),
                "output should contain first stock values");


        Assertions.assertTrue(
                matcher.find(),
                "output should contain second data row");

        Assertions.assertEquals(
                SECOND_STOCK_VALUES.size(),
                matcher.groupCount(),
                String.format("output should contain %d columns", SECOND_STOCK_VALUES.size()));

        Assertions.assertEquals(
                SECOND_STOCK_VALUES,
                IntStream.range(1, SECOND_STOCK_VALUES.size() + 1).mapToObj(matcher::group).toList(),
                "output should contain second stock values");
    }
}

