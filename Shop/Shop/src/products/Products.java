package products;


import Databse.DbConnect;

import java.text.SimpleDateFormat;
import java.util.Date;

abstract class Products {
    private String Name;
    private String Brand;
    private int quantity;
    private String Date;

    protected DbConnect add_product;

    public Products(String name, String brand, int quantity) {
        Name = name;
        Brand = brand;
        this.quantity = quantity;
       add_product=new DbConnect("jdbc:mysql://localhost:3306/shop","root","");
    }

    protected String getName() {
        return Name;
    }

    protected String getBrand() {
        return Brand;
    }


    protected int getQuantity() {
        return quantity;
    }

    protected String getDate() {
        Date  today=new Date();
        SimpleDateFormat datefmt=new SimpleDateFormat("YYYY-MM-dd");
        this.Date=datefmt.format(today);

        return Date;
    }
    abstract void addProducts();
}
