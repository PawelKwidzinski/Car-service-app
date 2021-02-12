package pl.kwidzinski.taskw7.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pl.kwidzinski.taskw7.model.Car;
import pl.kwidzinski.taskw7.model.Color;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Repository
public class CarDaoImpl implements CarDao {

    private final JdbcTemplate jdbcTemplate;

    public CarDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveCar(final Car toSave) {
        String sql = "INSERT INTO cars (brand, model, production, color) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, toSave.getBrand(), toSave.getModel(), toSave.getProduction(),
                toSave.getColor().toString());
    }

    @Override
    public List<Car> findAll() {
        String sql = "SELECT * FROM cars";
        final List<Map<String, Object>> dataFromDB = jdbcTemplate.queryForList(sql);
        return dbToPojoMapper(dataFromDB);
    }

    @Override
    public Car getOne(final Long id) {
        String sql = "SELECT * FROM cars WHERE car_id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Car(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        LocalDate.parse(rs.getString(4)),
                        Color.valueOf(rs.getString(5))),
                id);
    }

    @Override
    public void updateCar(final Car toUpdate) {
        String sql = "UPDATE cars SET cars.brand = ?, cars.model = ?, cars.production = ?, cars.color = ? " +
                "WHERE car_id = ?";
        jdbcTemplate.update(sql, toUpdate.getBrand(), toUpdate.getModel(),
                toUpdate.getProduction(), toUpdate.getColor().toString(), toUpdate.getId());
    }

    @Override
    public void deleteCar(final Long id) {
        String sql = "DELETE FROM cars WHERE car_id = ?";
        jdbcTemplate.update(sql, id);

    }

    @Override
    public List<Car> findByDate(final LocalDate from, final LocalDate to) {
        String sql = "SELECT * FROM cars WHERE production > ? AND production < ?";
        final List<Map<String, Object>> datesFromDB = jdbcTemplate.queryForList(sql, from.toString(), to.toString());
        return dbToPojoMapper(datesFromDB);
    }

    @Override
    public List<Car> findByBrand(String brand) {
        String sql = "SELECT * FROM cars WHERE brand = ?";
        final List<Map<String, Object>> brandFromDB = jdbcTemplate.queryForList(sql, brand);
        return dbToPojoMapper(brandFromDB);
    }

    @Override
    public List<Car> findByColor(final String color) {
        String sql = "SELECT * FROM cars WHERE color = ?";
        final List<Map<String, Object>> colorsFromDB = jdbcTemplate.queryForList(sql, color);
        return dbToPojoMapper(colorsFromDB);
    }

    private List<Car> dbToPojoMapper(List<Map<String, Object>> dataFromDB) {
        List<Car> carList = new ArrayList<>();

        dataFromDB.forEach(element -> {
            Car car = new Car();
            car.setId(Long.parseLong(String.valueOf(element.get("car_id"))));
            car.setBrand(String.valueOf(element.get("brand")));
            car.setModel(String.valueOf(element.get("model")));
            car.setProduction(Date.valueOf(String.valueOf(element.get("production"))).toLocalDate());
            car.setColor(Color.valueOf(String.valueOf(element.get("color"))));

            carList.add(car);
        });
        return carList;
    }
}
