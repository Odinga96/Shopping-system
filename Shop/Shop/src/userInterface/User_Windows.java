package userInterface;

import Databse.DbConnect;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import products.Stock;
import users.Admin;
import users.Customer;

import javax.swing.*;

public class User_Windows {

    private Stage window;

    public void add_Addmin(){
         String field_background_color="#79a5ad";
         String field_text_color="#ffffff";

        Graphical_User admin=new Graphical_User();
        window=new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        AnchorPane p =new AnchorPane();
        TextField Name,email;
        PasswordField pass,re_pass;
        Button add,cancel,reset;
        Label NAME,EMAIL,PASS,RE_PASS,TITTLE;



        //The title for the admin adding form

        TITTLE=admin.setLabel("Add Shop Keeper",140,5,350,50,Font.font("Arial",FontWeight.BOLD,25));
        TITTLE.setTextFill(Color.web("#fff"));
        ImageView icon=admin.PutImage("../shop/images/abc1.png",10,0,60,60);
        Pane mypane=new Pane();
        mypane.getChildren().addAll(TITTLE,icon);
        mypane.setStyle("-fx-background-color:#10aeee");
        mypane.setMinSize(500,60);

        //Textfields and labels
        //font for text and labels
        Font labelFont=new Font("New Times Roman",20);

        //labels
        NAME=admin.setLabel("Name",10,70,100,40,labelFont);
        EMAIL=admin.setLabel("Email Address :",10,120,100,40,labelFont);
        PASS=admin.setLabel("Password",10,170,100,40,labelFont);
        RE_PASS=admin.setLabel("Password",10,220,100,40,labelFont);

        //Textfield
        email=admin.setTextfield(150,120,250,40,labelFont,field_background_color,field_text_color);
        Name=admin.setTextfield(150,70,250,40,labelFont,field_background_color,field_text_color);

        //Password field
        pass=admin.setPasswordField(150,170,250,40,labelFont,field_background_color,field_text_color);
        re_pass=admin.setPasswordField(150,220,250,40,labelFont,field_background_color,field_text_color);

        //Button setApps

        add=admin.setButton("Add",100,290,100,40,"#42f4c5");
        cancel=admin.setButton("Cancel",210,290,100,40,"edb6b6");
        reset=admin.setButton("Reset",320,290,100,40,"e2dc5d");

        add.setOnAction(e->{
            String User_Name=Name.getText().trim();
            String password1=pass.getText().trim();
            String password2=re_pass.getText().trim();
            String mail=email.getText().trim();

            if (User_Name.length()>5){
                  if(password1.equals(password2)){
                       if (password1.length()>6){
                             if(mail.length()>3){
                                 Admin newAdmin=new Admin(User_Name,mail,password1);
                                 newAdmin.AddPersonRecord();
                             }else
                               JOptionPane.showMessageDialog(null,"email length has to be more than three");

                       }else
                          JOptionPane.showMessageDialog(null,"Password has atleast 6 characters long");
                  }else
                      JOptionPane.showMessageDialog(null, "The passwords have to match");
            }else
                JOptionPane.showMessageDialog(null,"Username has to be atleast 5 letters long");

        });

        cancel.setOnAction(e->window.close());
        reset.setOnAction(e->{
            Name.setText(null);
            pass.setText(null);
            re_pass.setText(null);
            email.setText(null);
        });


        p.getChildren().addAll(mypane,Name,NAME,EMAIL,PASS,RE_PASS,email,pass,re_pass,add,cancel,reset);
        admin.setStage(p,window,440,400,false,"#257f8e");
    }

    public void add_customer(){
        String field_background_color="#79a5ad";
        String field_text_color="#ffffff";

        Graphical_User  cus=new Graphical_User();
        window=new Stage();
        window.setX(900);
        window.setY(150);
        window.initModality(Modality.APPLICATION_MODAL);
        AnchorPane customer=new AnchorPane();
        TextField Name,email,address_field;
        Label name,tittle,address,mail;
        ImageView customer_Icon,Name_Icon,email_Icon,Address_Icon;
        Button add_customer_button,cancel,reset;



        customer_Icon=cus.PutImage("../shop/images/customers.png",15,5,60,60);
        tittle=cus.setLabel("Add new customer",150,5,350,60,Font.font("Arial",FontWeight.BOLD,25));
        tittle.setTextFill(Color.web("#fff"));

        Pane p=new Pane();
        p.setMinSize(450,60);
        p.getChildren().addAll(customer_Icon,tittle);
        p.setStyle("-fx-background-color: #10aeee");

        Font labelFont=new Font("Arial",16);

        //First field Data
        Name_Icon=cus.PutImage("../shop/images/username.png",5,80,40,40);
        name=cus.setLabel("Name :",50,80,100,40,labelFont);
        Name=cus.setTextfield(160,80,250,40,labelFont,field_background_color,field_text_color);
        Name.setFont(labelFont);

        //Second field Data
        email_Icon=cus.PutImage("../shop/images/email1.png",5,130,40,40);
        mail=cus.setLabel("Email address :",50,130,100,40,labelFont);
        email=cus.setTextfield(160,130,250,40,labelFont,field_background_color,field_text_color);

        //Third fields
        Address_Icon=cus.PutImage("../shop/images/adress.png",5,180,40,40);
        address=cus.setLabel("Postal address :",50,180,100,40,labelFont);
        address_field=cus.setTextfield(160,180,250,40,labelFont,field_background_color,field_text_color);

        //Buton
        add_customer_button=cus.setButton("Add",140,260,80,40,"e2f4c5");
        cancel=cus.setButton("Cancel",230,260,70,40,"edb6b6");
        reset=cus.setButton("Reset",330,260,70,40,"e2dc5d");
        cus.setButtonIcon(add_customer_button,"../shop/images/icons/ok.png",30);
        cus.setButtonIcon(cancel,"../shop/images/icons/cancelicon.png",18);
        cus.setButtonIcon(reset,"../shop/images/icons/resetit.png",30);
        //Buton Actions
        add_customer_button.setOnAction(e->{
            String customer_name=Name.getText().trim();
            String customer_email=email.getText().trim();
            String customer_address=address_field.getText().trim();

            if(customer_name.length()>5){
                if (customer_email.length()>4){
                    if (!(customer_email.contains(" "))){
                        if (customer_address.length()>6){
                            if (customer_address.contains(",")){
                                Customer our_customer=new Customer(customer_name,customer_email,customer_address);
                                our_customer.AddPersonRecord();
                            }else
                                JOptionPane.showMessageDialog(null,"Separate postal address components with a comma");
                        }else
                            JOptionPane.showMessageDialog(null,"Postal address has to be at least 6 characters long");
                    }else
                        JOptionPane.showMessageDialog(null,"Email address cannot contain spaces");
                }else
                    JOptionPane.showMessageDialog(null,"The email address has to be at least 4 characters long");
            }else {
                Name.setStyle("-fx-border-color: red; -fx-border-size: 10; -fx-background-color: "+field_background_color);
                JOptionPane.showMessageDialog(null, "Customer Name has to be at least 5 characters long");
            }
        });
        cancel.setOnAction(e->window.close());
        reset.setOnAction(e->{
            Name.setText(null);
            email.setText(null);
            address_field.setText(null);
        });

        customer.getChildren().addAll(p,Name_Icon,name,Name,email_Icon,email,mail,Address_Icon,address,address_field,add_customer_button,cancel
                                       ,reset);
     cus.setStage(customer,window,440,370,false,"#257f8e");

    }

   public void Buy_On_Credit(){
        String field_background_color="#79a5ad";
        String field_text_color="#ffffff";

        Graphical_User  cus=new Graphical_User();
        window=new Stage();
        window.setX(900);
        window.setY(150);
        window.initModality(Modality.APPLICATION_MODAL);
        AnchorPane customer=new AnchorPane();
        TextField Name,email,address_field;
        Label name,tittle,address,mail;
        ImageView customer_Icon;
        Button add_customer_button,cancel,reset;



        customer_Icon=cus.PutImage("../shop/images/customers.png",15,5,60,60);
        tittle=cus.setLabel("Buy on credit",150,5,350,60,Font.font("Arial",FontWeight.BOLD,25));
        tittle.setTextFill(Color.web("#fff"));

        Pane p=new Pane();
        p.setMinSize(450,60);
        p.getChildren().addAll(customer_Icon,tittle);
        p.setStyle("-fx-background-color: #10aeee");

        Font labelFont=new Font("Arial",16);

        //First field Data
        name=cus.setLabel("Customer Id :",4,80,100,40,labelFont);
        Name=cus.setTextfield(160,80,250,40,labelFont,field_background_color,field_text_color);
        ComboBox  c_id=new ComboBox();
        c_id.setLayoutX(150);
        c_id.setLayoutY(80);
        c_id.setMinSize(250,40);
        c_id.getItems().addAll(144,3552,444,555,6); 

        Name.setFont(labelFont);

        //Second field Data
        mail=cus.setLabel("Cash handed :",4,130,100,40,labelFont);
        email=cus.setTextfield(160,130,250,40,labelFont,field_background_color,field_text_color);

        //Third fields
        address=cus.setLabel("Total charge :",50,180,100,40,labelFont);
        address_field=cus.setTextfield(160,180,250,40,labelFont,field_background_color,field_text_color);

        //Four fields
        Label credit=cus.setLabel("Credit :",50,180,100,40,labelFont);
        address_field=cus.setTextfield(160,180,250,40,labelFont,field_background_color,field_text_color);

        //Buton
        add_customer_button=cus.setButton("Add",140,260,80,40,"e2f4c5");
        cancel=cus.setButton("Cancel",230,260,70,40,"edb6b6");
        reset=cus.setButton("Register customer",330,260,70,40,"e2dc5d");
        //Buton Actions



        customer.getChildren().addAll(p,name,c_id,email,mail,address,address_field,add_customer_button,cancel
                ,reset);
        cus.setStage(customer,window,440,370,false,"#257f8e");

    }
     public void add_product(){
        String field_background_color="#79a5ad";
        String field_text_color="#ffffff";

        Graphical_User  add_stock=new Graphical_User();
        window=new Stage();
        window.initModality(Modality.APPLICATION_MODAL);

        AnchorPane stock=new AnchorPane();
        Pane header=new Pane();

        Label Tittle,P_brand,P_Name,P_Desciption,P_price,P_Quantity,P_Category,P_Unit;
        TextField Product_Name,Product_brand,Product_price,Product_Quantity,Product_Category,Unit_Of_Measure;
        TextArea Product_Description;
        ImageView product1,product2;
        Button add_stock_button,cancel,reset;

        product1=add_stock.PutImage("../shop/images/icons/product2.png",3,5,60,70);
        Tittle=add_stock.setLabel("Add new Product",150,5,350,60,Font.font("Arial",FontWeight.BOLD,25));
        product2=add_stock.PutImage("../shop/images/icons/new.png",370,5,60,70);

        header.setMinSize(450,60);
        header.getChildren().addAll(product1,Tittle,product2);
        header.setStyle("-fx-background-color: #10aeee");

        Font labelfont=new Font("Arial",20);

        //First field control features
        P_Name=add_stock.setLabel("Name",10,70,100,40,labelfont);
        Product_Name=add_stock.setTextfield(120,70,293,40,labelfont,field_background_color,field_text_color);

        P_brand=add_stock.setLabel("Brand",10,120,100,40,labelfont);
        Product_brand=add_stock.setTextfield(120,120,293,40,labelfont,field_background_color,field_text_color);

        //Description field
        P_Desciption=add_stock.setLabel("Description",10,170,100,40,labelfont);
        Product_Description=add_stock.setTextarea(120,170,13,3,labelfont,field_background_color);

        //Category
         P_Category=add_stock.setLabel("Category",10,280,100,40,labelfont);
         Product_Category=add_stock.setTextfield(120,280,40,40,labelfont,field_background_color,field_text_color);
         Product_Category.setMaxSize(100,40);

         //Units
         P_Unit=add_stock.setLabel("Units",225,280,100,40,labelfont);
         Unit_Of_Measure=add_stock.setTextfield(310,280,40,40,labelfont,field_background_color,field_text_color);
         Unit_Of_Measure.setMaxSize(104,40);


        //Price
        P_price=add_stock.setLabel("Unit price",10,330,100,40,labelfont);
        Product_price=add_stock.setTextfield(120,330,40,40,labelfont,field_background_color,field_text_color);
        Product_price.setMaxSize(100,40);

        //Quantity
        P_Quantity=add_stock.setLabel("Quantity:",225,330,100,40,labelfont);
        Product_Quantity=add_stock.setTextfield(310,330,100,40,labelfont,field_background_color,field_text_color);
        Product_Quantity.setMaxSize(104,40);

        //Command Buttons
        add_stock_button=add_stock.setButton("add",120,380,90,40,"e2f4c5");
        cancel=add_stock.setButton("Cancel",222,380,90,40,"edb6b6");
        reset=add_stock.setButton("Reset",322,380,90,40,"e2dc5d");

        add_stock_button.setOnAction(e->{
            String PRODUCT=Product_Name.getText().trim();
            String BRAND=Product_brand.getText().trim();
            String DESCRIPTION=Product_Description.getText().trim();
            String CATEGORY=Product_Category.getText().trim();
            String UNITS=Unit_Of_Measure.getText().trim();
            double PRICE;
            int QUANTITY;

            try {
               PRICE=Double.parseDouble(Product_price.getText().trim());
               QUANTITY=Integer.parseInt(Product_Quantity.getText().trim());

                if (PRODUCT.length()>0){
                     if (BRAND.length()>0){
                         if (DESCRIPTION.length()>0){
                             if (PRICE>0){
                                 if (QUANTITY>0){
                                     if (CATEGORY.length()>0) {
                                         if (UNITS.length()>0) {
                                             DbConnect  confirm=new DbConnect("jdbc:mysql://localhost:3306/shop","root","");
                                             if (confirm.checkExistence("select Brand from products where Brand='"+BRAND+"'")==false) {
                                                 Stock newStock = new Stock(PRODUCT, BRAND, QUANTITY, DESCRIPTION, PRICE, CATEGORY, UNITS);
                                                 newStock.addProducts();
                                             }else add_stock.myalert(BRAND+"  already exists",200,70);
                                         }else add_stock.myalert("  units must be set",200,70);
                                     }else add_stock.myalert("  Category cannot be zero",200,70);
                                 }else add_stock.myalert("  Quantity has to be more than 0",200,70);
                             }else add_stock.myalert("  Price cannot be less than Ksh 1.00",200,70);
                         }else add_stock.myalert("  Description field cannot be empty",200,70);
                     }else add_stock.myalert("  Brand field cannot be empty",200,70);
                }else add_stock.myalert("  Product name has to be more than a character long",200,70);


            }catch (NumberFormatException e2){
                add_stock.myalert("  only numbers allowed for  price and quantity",200,70);
            }

        });
        cancel.setOnAction(e->window.close());
        reset.setOnAction(e->{
            Product_Name.setText(null);
            Product_brand.setText(null);
            Product_Description.setText(null);
            Product_price.setText(null);
            Product_Quantity.setText(null);
        });


        stock.getChildren().addAll(header,P_Name,Product_Name,P_brand,Product_brand,P_Desciption,Product_Description,P_price,
                            Product_price,P_Quantity,Product_Quantity,add_stock_button,cancel,reset,Unit_Of_Measure,P_Unit,P_Category,Product_Category);

        add_stock.setStage(stock,window,450,450,false,"#257f8e");

    }


}
