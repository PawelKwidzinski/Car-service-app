package pl.kwidzinski.taskw7.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Car {
    private Long id;
    private String brand;
    private String model;
    private Color color;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate productionDate;

    public Car(final Long id, final String brand, final String model, final Color color, final LocalDate productionDate) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.productionDate = productionDate;
    }

    public Car(final String brand, final String model, final Color color, final LocalDate productionDate) {
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.productionDate = productionDate;
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

    public LocalDate getProductionDate() {
        return productionDate;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", color=" + color +
                ", productionDate=" + productionDate +
                '}';
    }
}
