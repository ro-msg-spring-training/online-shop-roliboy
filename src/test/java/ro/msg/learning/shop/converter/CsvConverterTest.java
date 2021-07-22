package ro.msg.learning.shop.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.msg.learning.shop.model.dto.out.StockOutputDTO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

class CsvConverterTest {
    private StockOutputDTO firstStockOutputDTO;
    private StockOutputDTO secondStockOutputDTO;
    private Collection<StockOutputDTO> stockOutputDTOs;

    @BeforeEach
    void setUp() {
        firstStockOutputDTO = StockOutputDTO.builder()
                .quantity(1)
                .productId(1)
                .productName("product 1")
                .locationId(1)
                .locationName("location 1")
                .build();
        secondStockOutputDTO = StockOutputDTO.builder()
                .quantity(2)
                .productId(2)
                .productName("product 2")
                .locationId(2)
                .locationName("location 2")
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
}