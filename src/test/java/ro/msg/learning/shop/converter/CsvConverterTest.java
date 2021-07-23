package ro.msg.learning.shop.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.msg.learning.shop.model.dto.StockDTO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

class CsvConverterTest {
    private StockDTO firstStockDTO;
    private StockDTO secondStockDTO;
    private Collection<StockDTO> stockDTOS;

    @BeforeEach
    void setUp() {
        firstStockDTO = StockDTO.builder()
                .quantity(1)
                .productId(1)
                .productName("product 1")
                .locationId(1)
                .locationName("location 1")
                .build();
        secondStockDTO = StockDTO.builder()
                .quantity(2)
                .productId(2)
                .productName("product 2")
                .locationId(2)
                .locationName("location 2")
                .build();
        stockDTOS = List.of(firstStockDTO, secondStockDTO);
    }

    @Test
    void deserializesSerializedStockDTOCollection() throws IOException {
        var outputStream = new ByteArrayOutputStream();
        CsvConverter.toCsv(StockDTO.class, stockDTOS, outputStream);

        var inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        var deserializedStockDTOs = CsvConverter.fromCsv(StockDTO.class, inputStream);

        Assertions.assertEquals(
                stockDTOS,
                deserializedStockDTOs,
                "deserializing a serialized collection of objects should result in an identical collection");
    }
}