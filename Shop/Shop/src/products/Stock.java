package products;

import userInterface.Graphical_User;

import javax.swing.*;

public class Stock extends Products{
    private String Description;
    private double price;
    private String Category,Unit_Of_measure;

    public Stock(String name, String brand, int quantity, String description, double price, String category, String unit_Of_measure) {
        super(name, brand, quantity);
        Description = description;
        this.price = price;
        Category = category;
        Unit_Of_measure = unit_Of_measure;
    }

    public String getUnit_Of_measure() { return Unit_Of_measure; }
    public String getCategory() { return Category; }
    public String getDescription() {
        return Description;
    }
    public double getPrice() {
        return price;
    }

    Graphical_User   alert=new Graphical_User();

    @Override
   public void addProducts() {
        String addProductsQuery="INSERT INTO products(Product_name,Brand,Description,Unit_price,Quantity,dateAdded,Category,Measurement_Unit) VALUES('"
                +this.getName()+"','"+this.getBrand()+"','"+this.getDescription()+"','"+this.getPrice()+"','"+this.getQuantity()
                +"','"+this.getDate()+"','"+getCategory()+"','"+getUnit_Of_measure()+"')";
        if(add_product.performUpdate(addProductsQuery))
              alert.myalert(""+this.getBrand()+"added successfully",300,100);
    }
}
