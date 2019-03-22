package products;

public class DailySales {
    private String Name;
    private int  quantity;
    private double amount;

    public DailySales(String name, int quantity, double ammount) {
        Name = name;
        this.quantity = quantity;
        this.amount = ammount;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
