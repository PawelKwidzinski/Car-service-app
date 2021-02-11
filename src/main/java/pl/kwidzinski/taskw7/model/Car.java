package pl.kwidzinski.taskw7.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class Car {
    private Long id;
    @NotEmpty(message = "Brand cannot be empty")
    private String brand;
    @NotEmpty(message = "Model cannot be empty")
    private String model;
    private Color carColor;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate production;

    public Car(final Long id, final String brand, final String model, final Color carColor, final LocalDate production) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.carColor = carColor;
        this.production = production;
    }

    public Car(final String brand, final String model, final Color carColor, final LocalDate production) {
        this.brand = brand;
        this.model = model;
        this.carColor = carColor;
        this.production = production;
    }

    public Car() {
    }

    public Long getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public Color getCarColor() {
        return carColor;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setBrand(final String brand) {
        this.brand = brand;
    }

    public void setModel(final String model) {
        this.model = model;
    }

    public void setCarColor(final Color carColor) {
        this.carColor = carColor;
    }

    public LocalDate getProduction() {
        return production;
    }

    public void setProduction(final LocalDate production) {
        this.production = production;
    }
}
