package login;

import Databse.DbConnect;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import shop.Main;
import userInterface.Admin_Screen;
import userInterface.Shopkeeper;

import javax.swing.*;

public class Logincontroler  {

    @FXML Button login;
    @FXML Button cancel;
    @FXML TextField username;
    @FXML PasswordField pass;
    @FXML ComboBox   rrr;

    public void Login(){
        String Username=username.getText();
        String Password=pass.getText();
        String rank=(String)rrr.getSelectionModel().getSelectedItem();

        String query="select * from  shop_admin where Username='"+Username+"' and pass_code='"+Password+"' and rank='"+rank+"'";
        DbConnect log=new DbConnect("jdbc:mysql://localhost:3306/shop","root","");
        if (rank!=null) {
            if (log.checkExistence(query)) {
                if (rank.equals("Shop keeper")) {
                    Shopkeeper shop = new Shopkeeper();
                    shop.user_logged_in = Username;
                    m.window.close();
                    shop.ShopWindow();
                } else {
                    m.window.setScene(null);
                    Admin_Screen s = new Admin_Screen();
                    s.Management_Screen();
                }
            } else
                JOptionPane.showMessageDialog(null, "User not registered", "successful", JOptionPane.INFORMATION_MESSAGE);
        }else{
            rrr.setStyle("-fx-background-color: red");
            JOptionPane.showMessageDialog(null,"Choose a valid rank");
        }
    }

    public void cancel(){
        m.window.setScene(null);
       m.window.close();
    }

   private Main m=new Main();


}
