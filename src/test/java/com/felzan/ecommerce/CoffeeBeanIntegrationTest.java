package com.felzan.ecommerce;

import com.felzan.ecommerce.domain.CoffeeBean;
import com.felzan.ecommerce.repository.CoffeeBeanRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class CoffeeBeanIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CoffeeBeanRepository coffeeBeanRepository;

    @Test
    @DisplayName("When creating a new product should return the same product with id")
    void whenPostingShouldReturn() throws Exception {
        var post = """
                {
                  "name" : "Post Coffee",
                  "description" : "Post coffee bean description",
                  "price" : 20.0,
                  "stockQuantity" : 10
                }
                """;

        ResultActions resultActions = mockMvc.perform(post("/v1/products/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(post));

        resultActions.andExpectAll(
                status().isOk(),
                jsonPath("$.coffeeBeanId", greaterThanOrEqualTo(1)),
                jsonPath("$.name").value("Post Coffee"),
                jsonPath("$.description").value("Post coffee bean description"),
                jsonPath("$.price").value(20.0),
                jsonPath("$.stockQuantity").value(10)
        );
    }

    @Test
    @DisplayName("When get coffee bean by id should return that data")
    void whenGetByIdWithExistingDataShouldReturn() throws Exception {
        CoffeeBean testCoffeeBean = new CoffeeBean();
        testCoffeeBean.setName("Get Coffee");
        testCoffeeBean.setDescription("Test coffee bean description");
        testCoffeeBean.setPrice(10.0);
        testCoffeeBean.setStockQuantity(100);

        CoffeeBean savedCoffeeBean = coffeeBeanRepository.save(testCoffeeBean);

        ResultActions resultActions = mockMvc.perform(get("/v1/products/{id}", savedCoffeeBean.getCoffeeBeanId()).contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpectAll(
                status().isOk(),
                jsonPath("$.name").value("Get Coffee"),
                jsonPath("$.description").value("Test coffee bean description"),
                jsonPath("$.price").value(10.0),
                jsonPath("$.stockQuantity").value(100)
        );
    }

    @Test
    @DisplayName("When get all coffee beans should return everything")
    void whenGetAllShouldReturnAll() throws Exception {
        CoffeeBean testCoffeeBean = new CoffeeBean();
        testCoffeeBean.setName("Get all Coffee");
        testCoffeeBean.setDescription("Test coffee bean description");
        testCoffeeBean.setPrice(10.0);
        testCoffeeBean.setStockQuantity(100);

        coffeeBeanRepository.save(testCoffeeBean);
        testCoffeeBean.setCoffeeBeanId(null);
        coffeeBeanRepository.save(testCoffeeBean);
        testCoffeeBean.setCoffeeBeanId(null);
        coffeeBeanRepository.save(testCoffeeBean);

        ResultActions resultActions = mockMvc.perform(get("/v1/products/").contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpectAll(
                status().isOk(),
                jsonPath("$.length()", greaterThanOrEqualTo(3))
        );
    }

    @Test
    @DisplayName("When deleting a coffee bean should return ok and the data should be gone")
    void whenDeletingOneShouldReturnOK() throws Exception {
        CoffeeBean testCoffeeBean = new CoffeeBean();
        testCoffeeBean.setName("Get Coffee");
        testCoffeeBean.setDescription("Test coffee bean description");
        testCoffeeBean.setPrice(10.0);
        testCoffeeBean.setStockQuantity(100);

        CoffeeBean saved = coffeeBeanRepository.save(testCoffeeBean);

        ResultActions resultActions = mockMvc.perform(delete("/v1/products/{id}", saved.getCoffeeBeanId()).contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpectAll(status().isOk());

        Optional<CoffeeBean> deleted = coffeeBeanRepository.findById(saved.getCoffeeBeanId());
        assertFalse(deleted.isPresent());
    }

    @Test
    @DisplayName("When updating a coffee bean should return the new data")
    void whenUpdatingShouldReturnNewData() throws Exception {
        CoffeeBean oldCoffee = new CoffeeBean();
        oldCoffee.setName("Old Coffee");
        oldCoffee.setDescription("Test coffee bean description");
        oldCoffee.setPrice(10.0);
        oldCoffee.setStockQuantity(100);

        CoffeeBean saved = coffeeBeanRepository.save(oldCoffee);

        var newCoffee = """
                {
                  "name" : "New Coffee",
                  "description" : "Update coffee bean description",
                  "price" : 20.0,
                  "stockQuantity" : 20
                }
                """;

        ResultActions resultActions = mockMvc.perform(put("/v1/products/{id}", saved.getCoffeeBeanId())
                .content(newCoffee)
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpectAll(
                status().isOk(),
                jsonPath("$.coffeeBeanId", greaterThanOrEqualTo(1)),
                jsonPath("$.name").value("New Coffee"),
                jsonPath("$.description").value("Update coffee bean description"),
                jsonPath("$.price").value(20.0),
                jsonPath("$.stockQuantity").value(20)
        );
    }
}