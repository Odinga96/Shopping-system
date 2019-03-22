package users;

import javax.swing.*;

public class Admin extends Person {
   private String Password;
    public Admin(String Name, String email,String user_password) {
        super(Name, email);
        this.Password=user_password;
    }

    private String getPassword() {
        return Password;
    }

    @Override
   public void AddPersonRecord() {
        String insert_query="INSERT INTO shop_admin(Username,email,pass_code) VALUES('"+this.getUsername()+"','"+this.getEmail()+"','"+this.getPassword()+"')";
        if(personData.performUpdate(insert_query)){
            JOptionPane.showMessageDialog(null,this.getUsername()+"successfully added");
        }
    }


}
