package users;

import javafx.scene.control.Button;

public class CustoTable {


    private  int No;
    private String Name;
    private String email;
    private double credit;
    private Button update;



    public CustoTable(int no, String name, String email, double credit, Button update) {
        No = no;
        Name = name;
        this.email = email;
        this.credit = credit;
        this.update =update;
    }

    public Button getUpdate() {
        return update;
    }

    public void setUpdate(Button update) {
        this.update = update;
    }

    public int getNo() {
        return No;
    }

    public void setNo(int no) {
        No = no;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }
}
