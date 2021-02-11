package pl.kwidzinski.taskw7.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.kwidzinski.taskw7.model.Car;
import pl.kwidzinski.taskw7.model.Color;

import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DbConfig {

    private DataSource dataSource;

    @Autowired
    public DbConfig(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public JdbcTemplate getJdbcTemplate (){
        return new JdbcTemplate(dataSource);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        final String sqlDropTable = "DROP TABLE IF EXISTS cars";
        getJdbcTemplate().update(sqlDropTable);

        final String sqlCreateTable = "create TABLE cars(car_id int AUTO_INCREMENT not null, brand varchar(255)," +
                "model varchar(255), color varchar (25), production date, PRIMARY KEY (car_id))";
        getJdbcTemplate().update(sqlCreateTable);

        final String sqlInsertCars = "INSERT INTO cars (car_id, brand, model, color, production) VALUES (?, ?, ?, ?, ?)";
        initCarsDB().forEach(car -> getJdbcTemplate().update(sqlInsertCars, car.getId(), car.getBrand(), car.getModel(),
                car.getCarColor().toString(), car.getProduction()));
    }

    private List<Car> initCarsDB(){
        List<Car> carsDB = new ArrayList<>();
        carsDB.add(new Car(1L, "Skoda", "Fabia", Color.RED, LocalDate.of(2000, 5,2)));
        carsDB.add(new Car(2L, "Volvo", "XC60", Color.WHITE, LocalDate.of(2021, 1,2)));
        carsDB.add(new Car(3L, "Toyota", "Yaris", Color.BLACK, LocalDate.of(2015, 7,12)));
        carsDB.add(new Car(4L, "Audi", "A8", Color.YELLOW, LocalDate.of(2016, 8,21)));
        carsDB.add(new Car(5L, "Fiat", "Tipo", Color.BLACK, LocalDate.of(2014, 4,28)));
        return carsDB;
    }
}
