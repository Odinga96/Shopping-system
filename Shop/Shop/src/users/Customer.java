package users;

import javax.swing.*;

public class Customer extends Person {
     private String Address;

     private double maxCredit;
     private int count;


    public Customer(String Name, String email, String address) {
        super(Name, email);
        this.Address = address;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Customer(int counter,String Name, String email, double maxCredit) {
        super(Name, email);
        this.maxCredit = maxCredit;
        this.count=counter;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public double getMaxCredit() {
        return maxCredit;
    }

    public void setMaxCredit(double maxCredit) {
        this.maxCredit = maxCredit;
    }

    private String getAddress() {
        return Address;
    }

    @Override
    public  void AddPersonRecord() {
       String insert_query="INSERT INTO customers(Name,Address,Email) VALUES('"+this.getUsername()+"','"+this.getAddress()+"','"+this.getEmail()+"')";
        //DbConnect admin=new DbConnect("jdbc:mysql://localhost:3306/shop","root","");
        if(personData.performUpdate(insert_query))
            JOptionPane.showMessageDialog(null,this.getUsername()+"successfully added");
    }
}
