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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

    private List<Car> prepared_data() {
        return List.of(
                new Car(1L, "VW", "New Beatle", LocalDate.of(2016, 12, 15), Color.WHITE),
                new Car(2L, "VW", "Golf III", LocalDate.of(1996, 2, 18), Color.BLACK),
                new Car(3L, "Toyota", "Yaris", LocalDate.of(2018, 3, 19), Color.BLUE),
                new Car(4L, "Fiat", "Panda", LocalDate.of(2008, 8, 9), Color.BLUE),
                new Car(5L, "Nissan", "Qashqai", LocalDate.of(2018, 5, 15), Color.YELLOW),
                new Car(6L, "Honda", "Civic", LocalDate.of(2020, 7, 4), Color.WHITE),
                new Car(7L, "Mazda", "Cx-30", LocalDate.of(2019, 4, 7), Color.BLACK));
    }

    @Test
    void should_return_list_of_cars() throws Exception {
        //given
        List<Car> cars = prepared_data();

        //when
        when(carDao.findAll()).thenReturn(cars);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/cars/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("car-main"))
                .andExpect(model().attributeExists("allCars"))
                .andExpect(model().attribute("allCars", Matchers.hasSize(7)))
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
    void should_add_car_and_redirect_to_cars_list() throws Exception {
        //given
        Car carToAdd = new Car("Skoda", "Fabia", LocalDate.of(1999, 4, 8), Color.WHITE);

        //when
        when(carDao.saveCar(any(Car.class))).thenReturn(1);

        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/cars/add")
                .param("brand", carToAdd.getBrand())
                .param("model", carToAdd.getModel())
                .param("production", String.valueOf(carToAdd.getProduction()))
                .param("color", String.valueOf(carToAdd.getColor())))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(view().name("redirect:/cars/list"));
    }

    @Test
    void should_update_car_and_redirect_to_cars_list() throws Exception {
        //given
        Car carToUpdate = new Car("Skoda", "Superb", LocalDate.of(2011, 7, 4), Color.BLACK);

        //when
        when(carDao.updateCar(any(Car.class))).thenReturn(1);

        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/cars/edit")
                .param("brand", carToUpdate.getBrand())
                .param("model", carToUpdate.getModel())
                .param("production", String.valueOf(carToUpdate.getProduction()))
                .param("color", String.valueOf(carToUpdate.getColor())))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(view().name("redirect:/cars/list"));
    }

    @Test
    void should_delete_car_and_redirect_to_cars_list() throws Exception {
        //given
        Car catToDelete = new Car("Skoda", "Superb", LocalDate.of(2011, 7, 4), Color.BLACK);
        catToDelete.setId(1L);
        Long id = catToDelete.getId();

        // when
        when(carDao.deleteCar(anyLong())).thenReturn(1);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/cars/delete/" + id)
                .param("id", String.valueOf(catToDelete.getId())))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(view().name("redirect:/cars/list"));
    }

    @Test
    void should_return_cars_by_white_color() throws Exception {
        //given
        List<Car> cars = prepared_data();
        String colorToSearch = "white";

        //when
        when(carDao.findByColor(Color.valueOf(colorToSearch.toUpperCase()))).thenReturn(cars);

        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/cars/find/color")
                .param("color", "WHITE"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("car-main"))
                .andExpect(model().attributeExists("allCars"))
                .andExpect(model().attribute("allCars", Matchers.hasSize(7)))

                .andExpect(model().attribute("allCars", Matchers.hasItem(Matchers.allOf(
                        Matchers.<Car>hasProperty("id", Is.is(1L)),
                        Matchers.<Car>hasProperty("brand", Is.is("VW")),
                        Matchers.<Car>hasProperty("model", Is.is("New Beatle")),
                        Matchers.<Car>hasProperty("production", Is.is(LocalDate.of(2016, 12, 15))),
                        Matchers.<Car>hasProperty("color", Is.is(Color.WHITE))))))

                .andExpect(model().attribute("allCars", Matchers.hasItem(Matchers.allOf(
                        Matchers.<Car>hasProperty("id", Is.is(6L)),
                        Matchers.<Car>hasProperty("brand", Is.is("Honda")),
                        Matchers.<Car>hasProperty("model", Is.is("Civic")),
                        Matchers.<Car>hasProperty("production", Is.is(LocalDate.of(2020, 7, 4))),
                        Matchers.<Car>hasProperty("color", Is.is(Color.WHITE))))));
    }

    @Test
    void should_return_cars_by_brand() throws Exception {
        //given
        List<Car> cars = prepared_data();
        String brandToSearch = "VW";

        //when
        when(carDao.findByBrand(brandToSearch)).thenReturn(cars);

        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/cars/find/brand")
                .param("brand", brandToSearch))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("car-main"))
                .andExpect(model().attributeExists("allCars"))
                .andExpect(model().attribute("allCars", Matchers.hasSize(7)))

                .andExpect(model().attribute("allCars", Matchers.hasItem(Matchers.allOf(
                        Matchers.<Car>hasProperty("id", Is.is(1L)),
                        Matchers.<Car>hasProperty("brand", Is.is("VW")),
                        Matchers.<Car>hasProperty("model", Is.is("New Beatle")),
                        Matchers.<Car>hasProperty("production", Is.is(LocalDate.of(2016, 12, 15))),
                        Matchers.<Car>hasProperty("color", Is.is(Color.WHITE))))))

                .andExpect(model().attribute("allCars", Matchers.hasItem(Matchers.allOf(
                        Matchers.<Car>hasProperty("id", Is.is(2L)),
                        Matchers.<Car>hasProperty("brand", Is.is("VW")),
                        Matchers.<Car>hasProperty("model", Is.is("Golf III")),
                        Matchers.<Car>hasProperty("production", Is.is(LocalDate.of(1996, 2, 18))),
                        Matchers.<Car>hasProperty("color", Is.is(Color.BLACK))))));
    }

    @Test
    void should_return_cars_by_date() throws Exception {
        //given
        List<Car> cars = prepared_data();
        String from = "2018-01-01";
        String to = "2020-12-31";

        //when
        when(carDao.findByDate(LocalDate.parse(from), LocalDate.parse(to))).thenReturn(cars);

        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/cars/find/date")
                .param("from", "2018-01-01")
                .param("to", "2020-12-31"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(view().name("car-main"))
                .andExpect(model().attributeExists("allCars"))
                .andExpect(model().attribute("allCars", Matchers.hasSize(7)))
                .andExpect(model().attribute("allCars", Matchers.hasItem(Matchers.allOf(
                        Matchers.<Car>hasProperty("id", Is.is(3L)),
                        Matchers.<Car>hasProperty("brand", Is.is("Toyota")),
                        Matchers.<Car>hasProperty("model", Is.is("Yaris")),
                        Matchers.<Car>hasProperty("production", Is.is(LocalDate.of(2018, 3, 19))),
                        Matchers.<Car>hasProperty("color", Is.is(Color.BLUE))))))

                .andExpect(model().attribute("allCars", Matchers.hasItem(Matchers.allOf(
                        Matchers.<Car>hasProperty("id", Is.is(5L)),
                        Matchers.<Car>hasProperty("brand", Is.is("Nissan")),
                        Matchers.<Car>hasProperty("model", Is.is("Qashqai")),
                        Matchers.<Car>hasProperty("production", Is.is(LocalDate.of(2018, 5, 15))),
                        Matchers.<Car>hasProperty("color", Is.is(Color.YELLOW))))))
                .andExpect(model().attribute("allCars", Matchers.hasItem(Matchers.allOf(
                        Matchers.<Car>hasProperty("id", Is.is(7L)),
                        Matchers.<Car>hasProperty("brand", Is.is("Mazda")),
                        Matchers.<Car>hasProperty("model", Is.is("Cx-30")),
                        Matchers.<Car>hasProperty("production", Is.is(LocalDate.of(2019, 4, 7))),
                        Matchers.<Car>hasProperty("color", Is.is(Color.BLACK))))))

                .andExpect(model().attribute("allCars", Matchers.hasItem(Matchers.allOf(
                        Matchers.<Car>hasProperty("id", Is.is(6L)),
                        Matchers.<Car>hasProperty("brand", Is.is("Honda")),
                        Matchers.<Car>hasProperty("model", Is.is("Civic")),
                        Matchers.<Car>hasProperty("production", Is.is(LocalDate.of(2020, 7, 4))),
                        Matchers.<Car>hasProperty("color", Is.is(Color.WHITE))))));
    }
}
