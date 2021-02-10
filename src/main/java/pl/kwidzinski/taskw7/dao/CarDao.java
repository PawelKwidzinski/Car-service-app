package pl.kwidzinski.taskw7.dao;

import pl.kwidzinski.taskw7.model.Car;
import pl.kwidzinski.taskw7.model.Color;

import java.time.LocalDate;
import java.util.List;

interface CarDao {
    void saveCar(Car newCar);
    List<Car> findAll();
    Car getOne(Long id);
    void updateCar(Car newCar);
    void deleteCar(Long id);
    List<Car>findByColor(Color color);
    List<Car>findByDate(LocalDate from, LocalDate to);
}
