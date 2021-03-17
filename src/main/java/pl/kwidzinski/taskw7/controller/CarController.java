package pl.kwidzinski.taskw7.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.kwidzinski.taskw7.dao.CarDao;
import pl.kwidzinski.taskw7.model.Car;
import pl.kwidzinski.taskw7.model.Color;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/cars")
public class CarController {
    private final CarDao carDao;

    @Autowired
    public CarController(final CarDao carDao) {
        this.carDao = carDao;
    }

    @GetMapping("/list")
    public String getCars(Model model) {
        List<Car> cars = carDao.findAll();
        model.addAttribute("allCars", cars);
        return "car-main";
    }

    @GetMapping("/add")
    public String displayForm(Model model) {
        model.addAttribute("car", new Car());
        model.addAttribute("colors", Color.values());
        return "car-form";
    }

    @PostMapping("/add")
    public String addCar(@Validated Car newCar, BindingResult result) {
        if (result.hasErrors()) {
            return "car-form";
        }
        carDao.saveCar(newCar);
        return "redirect:/cars/list";
    }

    @GetMapping("/edit/{id}")
    public String editCar(@PathVariable Long id, Model model) {
        Car carToEdit = carDao.getOne(id);
        model.addAttribute("colors", Color.values());
        model.addAttribute("car", carToEdit);
        return "edit-form";
    }

    @PostMapping("/edit")
    public String updateCar(@Validated Car car, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("car", car);
            return "edit-form";
        }
        carDao.updateCar(car);
        return "redirect:/cars/list";
    }


    @GetMapping("/delete/{id}")
    public String deleteCar(@PathVariable Long id) {
        carDao.deleteCar(id);
        return "redirect:/cars/list";
    }

    @GetMapping("/find")
    public String displaySite() {
        return "car-find";
    }

    @PostMapping("/find/color")
    public String findByColor(@RequestParam(value = "color") String color, Model model) {
        final List<Car> carsColor = carDao.findByColor(Color.valueOf(color.toUpperCase()));
        model.addAttribute("allCars", carsColor);
        return "car-main";
    }

    @PostMapping("/find/brand")
    public String findByBrand(@RequestParam(value = "brand") String brand, Model model) {
        final List<Car> byBrand = carDao.findByBrand(brand);
        if (byBrand.size() == 0) {
            model.addAttribute("notFound", "");
        }
        model.addAttribute("allCars", byBrand);
        return "car-main";
    }

    @PostMapping("find/date")
    public String findByDate(@RequestParam(value = "from") String from,
                            @RequestParam(value = "to") String to, Model model) {
        try {
            final List<Car> byDate = carDao.findByDate(LocalDate.parse(from), LocalDate.parse(to));
            if (byDate.size() == 0 || LocalDate.parse(from).isAfter(LocalDate.parse(to))) {
                model.addAttribute("notFoundInRange", "");
            }
            model.addAttribute("allCars", byDate);
        } catch (DateTimeException ex) {
            model.addAttribute("errorInput", "");
            System.out.println(ex.getMessage());
        }
        return "car-main";
    }
}

