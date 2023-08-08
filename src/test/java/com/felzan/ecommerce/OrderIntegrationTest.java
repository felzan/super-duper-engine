package com.felzan.ecommerce;

import com.felzan.ecommerce.domain.CoffeeBean;
import com.felzan.ecommerce.domain.Role;
import com.felzan.ecommerce.domain.User;
import com.felzan.ecommerce.repository.CoffeeBeanRepository;
import com.felzan.ecommerce.repository.OrderRepository;
import com.felzan.ecommerce.repository.UserRepository;
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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class OrderIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OrderRepository repository;
    @Autowired
    private CoffeeBeanRepository coffeeBeanRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("When creating a new order should return the same order with id")
    void whenPostingShouldReturn() throws Exception {
        createUser();
        createCoffeeBean();
        var post = """
                {
                   "items": [
                     {
                       "coffeeBeanId": 1,
                       "quantity": 1,
                       "price": 14.9
                     }
                   ],
                   "userId": 1,
                   "totalPrice": 14.9,
                   "shippingAddress": "Street..."
                }
                """;

        ResultActions resultActions = mockMvc.perform(post("/v1/orders/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(post));
        resultActions.andExpectAll(
                status().isOk());

        resultActions = mockMvc.perform(get("/v1/orders/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(post));

        resultActions.andExpectAll(
                status().isOk(),
                jsonPath("$.length()", greaterThanOrEqualTo(1)),
                jsonPath("$.[-1].orderId", greaterThanOrEqualTo(1)),
                jsonPath("$.[-1].orderDate", notNullValue()),
                jsonPath("$.[-1].status", is("PLACED")),
                jsonPath("$.[-1].totalPrice", notNullValue()),
                jsonPath("$.[-1].shippingAddress", is("Street...")),
                jsonPath("$.[-1].user", notNullValue()),
                jsonPath("$.[-1].user.userId", greaterThanOrEqualTo(1)),
                jsonPath("$.[-1].user.role", is("CUSTOMER")),
                jsonPath("$.[-1].items.length()", is(1)),
                jsonPath("$.[-1].items[0].orderItemId", is(1)),
                jsonPath("$.[-1].items[0].quantity", greaterThanOrEqualTo(1)),
                jsonPath("$.[-1].items[0].itemPrice", closeTo(14.9, 10)),
                jsonPath("$.[-1].items[0].coffeeBean", notNullValue()),
                jsonPath("$.[-1].items[0].coffeeBean.coffeeBeanId", greaterThanOrEqualTo(1))
        );
        resultActions.andDo(print());
    }

    private User createUser() {
        User user = new User();
        user.setUsername("someuser");
        user.setPassword("pass");
        user.setEmail("user@email.com");
        user.setRole(Role.CUSTOMER);
        return userRepository.save(user);
    }

    private CoffeeBean createCoffeeBean() {
        CoffeeBean testCoffeeBean = new CoffeeBean();
        testCoffeeBean.setName("Get Coffee");
        testCoffeeBean.setDescription("Test coffee bean description");
        testCoffeeBean.setPrice(10.0);
        testCoffeeBean.setStockQuantity(100);
        return coffeeBeanRepository.save(testCoffeeBean);
    }
}