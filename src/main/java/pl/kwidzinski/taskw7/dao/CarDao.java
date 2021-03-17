package pl.kwidzinski.taskw7.dao;

import pl.kwidzinski.taskw7.model.Car;
import pl.kwidzinski.taskw7.model.Color;

import java.time.LocalDate;
import java.util.List;

public interface CarDao {
    int saveCar(Car newCar);

    List<Car> findAll();

    Car getOne(Long id);

    int updateCar(Car newCar);

    int deleteCar(Long id);

    List<Car> findByColor(Color color);

    List<Car> findByBrand(String brand);

    List<Car> findByDate(LocalDate from, LocalDate to);
}
