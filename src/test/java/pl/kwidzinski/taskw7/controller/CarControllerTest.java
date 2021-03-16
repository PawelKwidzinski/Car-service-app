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
//given
        List<Car> cars = List.of(
                new Car(1L, "VW", "New Beatle", LocalDate.of(2016, 12, 15), Color.WHITE),
                new Car(2L, "VW", "Golf III", LocalDate.of(1996, 2, 18), Color.BLACK),
                new Car(3L, "Toyota", "Yaris", LocalDate.of(2018, 3, 19), Color.BLUE),
                new Car(4L, "Fiat", "Panda", LocalDate.of(2008, 8, 9), Color.BLUE));

        //when
        when(carDao.findAll()).thenReturn(cars);
        mockMvc.perform(MockMvcRequestBuilders.get("/cars/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("car-main"))
                .andExpect(model().attributeExists("allCars"))
                .andExpect(model().attribute("allCars", Matchers.hasSize(4)))
                .andExpect(model().attribute("allCars", Matchers.hasItem(Matchers.allOf(
                        Matchers.<Car>hasProperty("id", Is.is(1L)),
                        Matchers.<Car>hasProperty("brand", Is.is("VW")),
                        Matchers.<Car>hasProperty("model", Is.is("New Beatle")),
                        Matchers.<Car>hasProperty("production", Is.is(LocalDate.of(2016, 12, 15))),
                        Matchers.<Car>hasProperty("color", Is.is(Color.WHITE))))))

                .andExpect(model().attribute("allCars", Matchers.hasItem(Matchers.allOf(
                        Matchers.<Car>hasProperty("id", Is.is(4L)),
                        Matchers.<Car>hasProperty("brand", Is.is("Fiat")),
                        Matchers.<Car>hasProperty("model", Is.is("Panda")),
                        Matchers.<Car>hasProperty("production", Is.is(LocalDate.of(2008, 8, 9))),
                        Matchers.<Car>hasProperty("color", Is.is(Color.BLUE))))));
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
