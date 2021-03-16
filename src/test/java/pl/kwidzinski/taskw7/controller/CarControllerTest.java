package pl.kwidzinski.taskw7.controller;

import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.kwidzinski.taskw7.dao.CarDao;
import pl.kwidzinski.taskw7.model.Car;
import pl.kwidzinski.taskw7.model.Color;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarDao carDao;

    @Test
    void should_return_list_of_cars() throws Exception {

    }

    @Test
    void displayForm() {
    }

    @Test
    void addCar() {
    }

    @Test
    void editCar() {
    }

    @Test
    void updateCar() {
    }

    @Test
    void deleteCar() {
    }

    @Test
    void displaySite() {
    }

    @Test
    void findByColor() {
    }

    @Test
    void findByBrand() {
    }

    @Test
    void findByDate() {
    }
}
