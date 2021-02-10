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
        String sql = "INSERT INTO cars (brand, model, color, production_date) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, toSave.getBrand(), toSave.getModel(), toSave.getColor().toString(),
                toSave.getProductionDate());
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
                        Color.valueOf(rs.getString(4)),
                        LocalDate.parse(rs.getString(5))),
                id);
    }

    @Override
    public void updateCar(final Car toUpdate) {
        String sql = "UPDATE cars SET cars.brand = ?, cars.model = ?, cars.color = ?, cars.production_date = ? " +
                "WHERE car_id = ?";
        jdbcTemplate.update(sql, toUpdate.getBrand(), toUpdate.getModel(), toUpdate.getColor().toString(),
                Date.valueOf(toUpdate.getProductionDate()), toUpdate.getId());
    }

    @Override
    public void deleteCar(final Long id) {
        String sql = "DELETE FROM cars WHERE car_id = ?";
        jdbcTemplate.update(sql, id);

    }

    @Override
    public List<Car> findByDate(final LocalDate from, final LocalDate to) {
        String sql = "SELECT * FROM cars WHERE production_date > ? AND production_date < ?";
        final List<Map<String, Object>> datesFromDB = jdbcTemplate.queryForList(sql, from.toString(), to.toString());
        return dbToPojoMapper(datesFromDB);
    }

    @Override
    public List<Car> findByColor(final Color color) {
        String sql = "SELECT * FROM cars WHERE color = ?";
        final List<Map<String, Object>> colorsFromDB = jdbcTemplate.queryForList(sql, color.toString());
        return dbToPojoMapper(colorsFromDB);
    }

    private List<Car> dbToPojoMapper(List<Map<String, Object>> dataFromDB) {
        List<Car> carList = new ArrayList<>();

        dataFromDB.forEach(element -> carList.add(new Car(
                Long.parseLong(String.valueOf(element.get("car_id"))),
                String.valueOf(element.get("brand")),
                String.valueOf(element.get("model")),
                Color.valueOf(String.valueOf(element.get("color"))),
                LocalDate.parse(String.valueOf(element.get("production_date")))
        )));
        return carList;
    }
}
