package pl.kwidzinski.taskw7.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.kwidzinski.taskw7.dao.CarDao;
import pl.kwidzinski.taskw7.model.Car;
import pl.kwidzinski.taskw7.model.Color;

import java.util.List;

@Controller
@RequestMapping("/cars")
public class CarController {
    private CarDao carDao;

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
    public String addCar(Model model) {
        model.addAttribute("colorsToAdd", Color.values());
        model.addAttribute("car_to_fill", new Car());
        return "car-form";
    }

    @PostMapping("/add")
    public String addCar(Car newCar) {
        carDao.saveCar(newCar);
        return "redirect:/cars/list";
    }

    @GetMapping("/edit/{id}")
    public String editCar(@PathVariable Long id, Model model) {
        Car carToEdit = carDao.getOne(id);
        model.addAttribute("colorsToAdd", Color.values());
        model.addAttribute("carToEdit", carToEdit);
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


}
