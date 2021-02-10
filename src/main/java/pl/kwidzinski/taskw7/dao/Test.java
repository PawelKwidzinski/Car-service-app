package pl.kwidzinski.taskw7.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kwidzinski.taskw7.model.Car;
import pl.kwidzinski.taskw7.model.Color;

import java.time.LocalDate;

@Component
class Test {

    private CarDao carDao;

    @Autowired
    Test(final CarDao carDao) {
        this.carDao = carDao;
//        carDao.saveCar(new Car("Tesla", "Model x", Color.ORANGE, LocalDate.of(2019,9, 15)));
//        carDao.findAll().forEach(System.out::println);
//        carDao.updateCar(new Car(2L,"Mazda", "CX-8", Color.BLACK, LocalDate.of(2018,4, 12)));
//        System.out.println(carDao.findByDate(LocalDate.of(2015, 1, 1), LocalDate.of(2019, 1, 1)));
        carDao.findByColor(Color.BLACK).forEach(System.out::println);
//        System.out.println(carDao.getOne(2L));
    }
}
