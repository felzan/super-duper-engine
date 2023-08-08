package com.felzan.ecommerce;

import com.felzan.ecommerce.domain.Role;
import com.felzan.ecommerce.domain.User;
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
class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository repository;

    @Test
    @DisplayName("When creating a new user should return the same user with id")
    void whenPostingShouldReturn() throws Exception {
        var post = """
                {
                  "username" : "NewUsername",
                  "password" : "NewPassword",
                  "email" : "someemail@email.com",
                  "role" : "CUSTOMER"
                }
                """;

        ResultActions resultActions = mockMvc.perform(post("/v1/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(post));

        resultActions.andExpectAll(
                status().isOk(),
                jsonPath("$.userId", greaterThanOrEqualTo(1)),
                jsonPath("$.username").value("NewUsername"),
                jsonPath("$.email").value("someemail@email.com"),
                jsonPath("$.role").value("CUSTOMER")
        );
    }

    @Test
    @DisplayName("When get user by id should return that data")
    void whenGetByIdWithExistingDataShouldReturn() throws Exception {
        User user = new User();
        user.setUsername("someuser");
        user.setPassword("pass");
        user.setEmail("user@email.com");
        user.setRole(Role.CUSTOMER);
        var saved = repository.save(user);

        ResultActions resultActions = mockMvc.perform(get("/v1/users/{id}", saved.getUserId()).contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpectAll(
                status().isOk(),
                jsonPath("$.userId", greaterThanOrEqualTo(1)),
                jsonPath("$.username").value("someuser"),
                jsonPath("$.email").value("user@email.com"),
                jsonPath("$.role").value("CUSTOMER")
        );
    }

    @Test
    @DisplayName("When get all users should return everything")
    void whenGetAllShouldReturnAll() throws Exception {
        User user = new User();
        user.setUsername("someuser");
        user.setPassword("pass");
        user.setEmail("user@email.com");
        user.setRole(Role.CUSTOMER);
        var saved = repository.save(user);

        repository.save(saved);
        saved.setUserId(null);
        repository.save(saved);
        saved.setUserId(null);
        repository.save(saved);

        ResultActions resultActions = mockMvc.perform(get("/v1/users/").contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpectAll(
                status().isOk(),
                jsonPath("$.length()", greaterThanOrEqualTo(3))
        );
    }

    @Test
    @DisplayName("When deleting a user should return ok and the data should be gone")
    void whenDeletingOneShouldReturnOK() throws Exception {
        User user = new User();
        user.setUsername("someuser");
        user.setPassword("pass");
        user.setEmail("user@email.com");
        user.setRole(Role.CUSTOMER);
        var saved = repository.save(user);;

        ResultActions resultActions = mockMvc.perform(delete("/v1/users/{id}", saved.getUserId()).contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpectAll(status().isOk());

        Optional<User> deleted = repository.findById(saved.getUserId());
        assertFalse(deleted.isPresent());
    }

}