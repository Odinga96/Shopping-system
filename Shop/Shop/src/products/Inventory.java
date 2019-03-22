package products;

import javafx.scene.control.Button;

public class Inventory {
    private String Brand;
    private String Category;
    private double Unit_Price;
    private int Quantity;
    private Button update_button;
    private int Id;


    public Inventory(int id, String brand, String category, double unit_Price, int quantity, Button uddate_button) {
        Brand = brand;
        Category = category;
        Unit_Price = unit_Price;
        Quantity = quantity;
        this.update_button = uddate_button;
        this.Id=id;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public double getUnit_Price() {
        return Unit_Price;
    }

    public void setUnit_Price(double unit_Price) {
        Unit_Price = unit_Price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public Button getUpdate_button() {
        return update_button;
    }

    public void setUpdate_button(Button update_button) {
        this.update_button = update_button;
    }
}
