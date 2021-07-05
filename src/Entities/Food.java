package Entities;

import java.util.Objects;

public class Food {
    private Integer Id;
    private double price;
    private String foodType;
    private String foodName;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return Double.compare(food.getPrice(), getPrice()) == 0 && getFoodType().equals(food.getFoodType()) && getFoodName().equals(food.getFoodName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPrice(), getFoodType(), getFoodName());
    }
}
