package Databse;

import userInterface.Graphical_User;

import javax.swing.*;
import java.sql.*;

public class DbConnect {
    public Statement statement;
    public ResultSet result;
    public Connection con;

    private String Url;
    private String username;
    private String password;

    public DbConnect(String url,String Username, String Password){
        this.Url=url;
        this.username=Username;
        this.password=Password;
    }

    public void connect(){

        try {
            con= DriverManager.getConnection(this.Url, this.username,this.password);
            statement=con.createStatement();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Mysql Connection not established"+e,"Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean checkExistence(String sql_query) {
        connect();
        boolean exists=false;
        try {
            result = statement.executeQuery(sql_query);
            if (result.next()) {
                exists=true;
            }
            //con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Mysql Connection not established", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return exists;

    }

    Graphical_User  alert=new Graphical_User();
    //The function to be used to insert data to the database
    public boolean performUpdate(String update_query){
        connect();
        boolean condition=false;
        try {
            //result=statement.executeUpdate(update_query);
            statement.executeUpdate(update_query);
            condition=true;
            con.close();
        }catch(SQLException e){
             JOptionPane.showMessageDialog(null,"faile to update database"+e);
        }

        return condition;
    }

    public int getCount(String query){
        connect();
        int count=0;
           try {
               result=statement.executeQuery(query);
               while (result.next()){
                   count=result.getInt(1);
               }

               con.close();
           }catch (SQLException e){
               JOptionPane.showMessageDialog(null,"Error "+e);
           }
        return count;
    }

    public int get_quantity(String quantity_query){
        connect();
        int quantity=0;
        try {
            result=statement.executeQuery(quantity_query);
            while (result.next()){
                quantity=result.getInt(1);
            }

            con.close();
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,"Error "+e);
        }

        return  quantity;
    }

    public double getPrice(String price_query){
        connect();
        double price=0;

        try {
            result=statement.executeQuery(price_query);
            while (result.next()){
                price=result.getInt(1);
            }

        }catch (SQLException e){
            JOptionPane.showMessageDialog(null,"Error "+e);
        }

        return price;
    }

    public void delete(String delete_query){
        connect();
        try {
            statement.executeUpdate(delete_query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
