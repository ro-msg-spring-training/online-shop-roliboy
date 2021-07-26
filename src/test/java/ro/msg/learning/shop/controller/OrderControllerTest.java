package ro.msg.learning.shop.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ro.msg.learning.shop.converter.JsonConverter;
import ro.msg.learning.shop.model.domain.Address;
import ro.msg.learning.shop.model.dto.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//TODO: populate database with dbunit instead of using the rest api
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebAppConfiguration
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
class OrderControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        var firstLocation = LocationDTO.builder()
                .name("location 1")
                .address(new Address("a", "b", "c", "d"))
                .build();
        var secondLocation = LocationDTO.builder()
                .name("location 2")
                .address(new Address("e", "f", "g", "h"))
                .build();

        var locations = List.of(firstLocation, secondLocation);

        var firstProduct = ProductDTO.builder()
                .categoryId(0)
                .description("description 1")
                .imageUrl("image url 1")
                .name("name 1")
                .price(BigDecimal.valueOf(1))
                .supplierId(0)
                .weight(1.0)
                .build();

        var secondProduct = ProductDTO.builder()
                .categoryId(0)
                .description("description 2")
                .imageUrl("image url 2")
                .name("name 2")
                .price(BigDecimal.valueOf(2))
                .supplierId(0)
                .weight(2.0)
                .build();

        var products = List.of(firstProduct, secondProduct);


        var firstLocationFirstProduct = StockDTO.builder()
                .locationId(1)
                .productId(1)
                .quantity(1)
                .build();

        var firstLocationSecondProduct = StockDTO.builder()
                .locationId(1)
                .productId(2)
                .quantity(1)
                .build();

        var secondLocationFirstProduct = StockDTO.builder()
                .locationId(2)
                .productId(1)
                .quantity(1)
                .build();

        var secondLocationSecondProduct = StockDTO.builder()
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

        mockMvc.perform(get("/test/populate/drop"));
        mockMvc.perform(post("/test/populate/locations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConverter.toJson(locations)));
        mockMvc.perform(post("/test/populate/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConverter.toJson(products)));
        mockMvc.perform(post("/test/populate/stocks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConverter.toJson(stocks)));
    }

    @Test
    void createOrderWhenProductsInStock() throws Exception {
        var order = OrderDTO.builder()
                .timestamp(LocalDateTime.now())
                .address(new Address("a", "b", "c", "d"))
                .products(List.of(
                        OrderDetailDTO.builder()
                                .productId(1)
                                .quantity(0)
                                .build(),
                        OrderDetailDTO.builder()
                                .productId(2)
                                .quantity(0)
                                .build()))
                .build();

        this.mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConverter.toJson(order)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    void createOrderProductsOutOfStock() throws Exception {
        var order = OrderDTO.builder()
                .timestamp(LocalDateTime.now())
                .address(new Address("a", "b", "c", "d"))
                .products(List.of(
                        OrderDetailDTO.builder()
                                .productId(1)
                                .quantity(100)
                                .build(),
                        OrderDetailDTO.builder()
                                .productId(2)
                                .quantity(100)
                                .build()))
                .build();

        this.mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConverter.toJson(order)))
                .andExpect(status().is4xxClientError());
    }
}
