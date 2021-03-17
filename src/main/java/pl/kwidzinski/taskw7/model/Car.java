package pl.kwidzinski.taskw7.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class Car {
    private Long id;

    @NotEmpty(message = "Brand cannot be empty")
    private String brand;

    @NotEmpty(message = "Model cannot be empty")
    private String model;

    @PastOrPresent(message = "Date cannot be from future")
    @NotNull(message = "Date cannot be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate production;

    private Color color;

    public Car(final Long id, final String brand, final String model, final LocalDate production, final Color color) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.production = production;
        this.color = color;
    }

    public Car(final String brand, final String model, final LocalDate production, final Color color) {
        this.brand = brand;
        this.model = model;
        this.production = production;
        this.color = color;
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

    public Color getColor() {
        return color;
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

    public void setColor(final Color color) {
        this.color = color;
    }

    public LocalDate getProduction() {
        return production;
    }

    public void setProduction(final LocalDate production) {
        this.production = production;
    }
}
