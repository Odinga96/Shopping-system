package products;

public class Sales extends Products {
    private int customer_id;

    public Sales(String name, String brand, int quantity, int customer_id) {
        super(name, brand, quantity);
        this.customer_id = customer_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    @Override
   public void addProducts() {
        String salesUpdateQuery="INSERT INTO sales(Product_name,Brand,Quantity,Customer_id,date_of_purchase) VALUES('"
                +this.getName()+"','"+this.getBrand()+"','"+this.getQuantity()+"','"+this.getCustomer_id()+"','"+this.getDate()+"')";

        add_product.performUpdate(salesUpdateQuery);
    }
}
