package ro.msg.learning.shop.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ro.msg.learning.shop.converter.JsonConverter;
import ro.msg.learning.shop.model.domain.Address;
import ro.msg.learning.shop.model.dto.in.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//TODO: populate database with dbunit instead of using the rest api
//NOTE: tests work if ran one by one (otherwise one of them will fail because the /drop doesn't work
//      and entities get inserted again)
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebAppConfiguration
class OrderControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        var firstLocation = LocationInputDTO.builder()
                .name("location 1")
                .address(new Address("a", "b", "c", "d"))
                .build();
        var secondLocation = LocationInputDTO.builder()
                .name("location 2")
                .address(new Address("e", "f", "g", "h"))
                .build();

        var locations = List.of(firstLocation, secondLocation);

        var firstProduct = ProductInputDTO.builder()
                .categoryId(0)
                .description("description 1")
                .imageUrl("image url 1")
                .name("name 1")
                .price(BigDecimal.valueOf(1))
                .supplierId(0)
                .weight(1.0)
                .build();

        var secondProduct = ProductInputDTO.builder()
                .categoryId(0)
                .description("description 2")
                .imageUrl("image url 2")
                .name("name 2")
                .price(BigDecimal.valueOf(2))
                .supplierId(0)
                .weight(2.0)
                .build();

        var products = List.of(firstProduct, secondProduct);


        var firstLocationFirstProduct = StockInputDTO.builder()
                .locationId(1)
                .productId(1)
                .quantity(1)
                .build();

        var firstLocationSecondProduct = StockInputDTO.builder()
                .locationId(1)
                .productId(2)
                .quantity(1)
                .build();

        var secondLocationFirstProduct = StockInputDTO.builder()
                .locationId(2)
                .productId(1)
                .quantity(1)
                .build();

        var secondLocationSecondProduct = StockInputDTO.builder()
                .locationId(2)
                .productId(2)
                .quantity(1)
                .build();

        var stocks = List.of(
                firstLocationFirstProduct,
                firstLocationSecondProduct,
                secondLocationFirstProduct,
                secondLocationSecondProduct);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        mockMvc.perform(get("/api/test/populate/drop"));
        mockMvc.perform(post("/api/test/populate/locations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConverter.toJson(locations)));
        mockMvc.perform(post("/api/test/populate/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConverter.toJson(products)));
        mockMvc.perform(post("/api/test/populate/stocks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConverter.toJson(stocks)));
    }

    @Test
    void createValidOrder_productsInStock_orderIsCreated() throws Exception {
        var order = OrderInputDTO.builder()
                .timestamp(LocalDateTime.now())
                .address(new Address("a", "b", "c", "d"))
                .products(List.of(
                        OrderDetailInputDTO.builder()
                                .productId(1)
                                .quantity(1)
                                .build(),
                        OrderDetailInputDTO.builder()
                                .productId(2)
                                .quantity(1)
                                .build()))
                .build();

        this.mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConverter.toJson(order)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    void createValidOrder_productsOutOfStock_orderIsNotCreated() throws Exception {
        var order = OrderInputDTO.builder()
                .timestamp(LocalDateTime.now())
                .address(new Address("a", "b", "c", "d"))
                .products(List.of(
                        OrderDetailInputDTO.builder()
                                .productId(1)
                                .quantity(100)
                                .build(),
                        OrderDetailInputDTO.builder()
                                .productId(2)
                                .quantity(100)
                                .build()))
                .build();

        this.mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConverter.toJson(order)))
                .andExpect(status().is4xxClientError());
    }
}
