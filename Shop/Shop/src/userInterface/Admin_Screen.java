package userInterface;

import Databse.DbConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import products.DailySales;
import products.Inventory;
import users.CustoTable;
import users.Customer;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Admin_Screen {
    private DbConnect sales=new DbConnect("jdbc:mysql://localhost:3306/shop","root","");


    //radio buttun
    private RadioButton Graphs, tabular_format;
    private User_Windows admin_pop_up_screen =new User_Windows();

    private LineChart c;
    private AnchorPane  admipPane,MainPane;
    private Label Tittle,DateToday,start_date,end_date;
    private TextField Time;
    private Pane     heading,Table_GraphsPane,updatepane;
    private VBox activitiesPane;
    private int menuHeight=40,menuWidth=200;
    private int header_height =75;
    private DatePicker  start,End;
    private Button[] update_button=new Button[sales.getCount("select count(*) from products")];Button add_new_Product;

    private Stage adminStage =new Stage();
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private TableView<Inventory> products_table;
    private TableView<CustoTable>  customers_table;
    private ObservableList<Inventory> data; ObservableList<CustoTable>data1;

    private Graphical_User admin_window =new Graphical_User();

    public  void Management_Screen(){
        admipPane=new AnchorPane();
        MainPane=new AnchorPane();
        MainPane.setLayoutX(menuWidth+1);
        MainPane.setLayoutY(header_height +1);
        MainPane.setMinSize(1363-menuWidth,screenSize.height-header_height-28);
        MainPane.setStyle("-fx-background-color: #2c5b5b");

        Table_GraphsPane=new Pane();
        Table_GraphsPane.setLayoutX(3);
        Table_GraphsPane.setLayoutY(40);
        Table_GraphsPane.setMinSize(screenSize.width-209,screenSize.height-header_height-72);
        Table_GraphsPane.setStyle("-fx-background-color: dee8e8");
        MainPane.getChildren().addAll(Table_GraphsPane);

        header();
        adminActivitiesPane();
        admin_window.showTime(DateToday,Time);
        admipPane.getChildren().addAll(heading,activitiesPane,MainPane);
        admin_window.setStage(admipPane,adminStage,1366,700,true,"#78c");
    }

    private void header(){
         Button logout;
         ImageView Admin_Icon;
        heading= admin_window.setPane(0,0,"#30474c");
        Admin_Icon= admin_window.PutImage("../shop/images/abc1.png",3,3,60,70);
        Tittle= admin_window.setLabel("CLINTEX SHOP ADMIN",300,3,500, 70,new Font("Arial",50));
        Tittle.setTextFill(Color.web("white"));
        DateToday= admin_window.setLabel("",1150,3,50,20,new Font("Arial",15));
        DateToday.setTextFill(Color.web("white"));

        Time= admin_window.setTextfield(1230,0,70,25,new Font("Arial",15),"#30474c","white");
        Time.setMaxSize(120,30);
        Time.setEditable(false);

        //Log out button
        logout= admin_window.setButton("sign out",1280,40,70,25,"#e2dc5d");
        logout.setOnAction(e-> adminStage.close());

        //Add admin_pop_up_screen view daily sales and search

        heading.getChildren().addAll(Tittle,Admin_Icon,logout,Time,DateToday);
        heading.setMinSize(1366,header_height);
    }

    private void adminActivitiesPane(){
        activitiesPane=new VBox(3);
        activitiesPane.setLayoutX(0);
        activitiesPane.setLayoutY(header_height+1);
        activitiesPane.setStyle("-fx-background-color: #152828");
        System.out.println(screenSize.height);
        activitiesPane.setMinSize(164,screenSize.height-header_height-27);

        Font menuFont=new Font("Arial",20);
        String menuColor="#476868",menutextColor="#c7eded";

        Label Sales,Customers,Inventory,Employees;
        Sales=new Label("Sales");
        Sales.setMinSize(menuWidth,menuHeight);
        Sales.setFont(menuFont);
        Sales.setStyle("-fx-background-color: "+menuColor);
        Sales.setOnMouseClicked(e->Sales());
        Sales.setTextFill(Color.web(""+menutextColor));

        Customers=new Label("Customers");
        Customers.setMinSize(menuWidth,menuHeight);
        Customers.setFont(menuFont);
        Customers.setStyle("-fx-background-color: "+menuColor);
        Customers.setTextFill(Color.web(""+menutextColor));
        Customers.setOnMouseClicked(e->set_customers());

        Inventory=new Label("Inventory");
        Inventory.setMinSize(menuWidth,menuHeight);
        Inventory.setFont(menuFont);
        Inventory.setStyle("-fx-background-color: "+menuColor);
        Inventory.setTextFill(Color.web(""+menutextColor));
        Inventory.setOnMouseClicked(e->{
            Inventory();
        });
    /*
        Employees=new Label("Employees");
        Employees.setMinSize(menuWidth,menuHeight);
        Employees.setFont(menuFont);
        Employees.setStyle("-fx-background-color: "+menuColor);
        Employees.setTextFill(Color.web(""+menutextColor));
        */
        activitiesPane.getChildren().addAll(Sales,Customers,Inventory);


    }
    public void set_customers(){
        Table_GraphsPane.getChildren().clear();
        if (Graphs instanceof RadioButton && tabular_format instanceof RadioButton)
            MainPane.getChildren().removeAll(Graphs,tabular_format);

        Label  products_view=admin_window.setLabel("Customers",100,5,400,50,new Font("Areial",35));
        products_view.setTextFill(Color.web("#7c7a70"));
        products_view.setAlignment(Pos.CENTER);


        customers_table=new TableView();
        data1= FXCollections.observableArrayList();

        customers_table.setLayoutX(10);
        customers_table.setLayoutY(50);
        customers_table.setMinSize(500,500);
        customers_table.setMaxSize(785,500);

        Customer_data();
        //tabular_format columns
        TableColumn No = new TableColumn("No");
        No.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("No"));
        No.setMaxWidth(60);

        TableColumn Name = new TableColumn("Name");
        Name.setCellValueFactory(new PropertyValueFactory<Customer, String>("Name"));
        Name.setMinWidth(220);
        Name.setMaxWidth(220);

        TableColumn email = new TableColumn("Email");
        email.setCellValueFactory(new PropertyValueFactory<Customer, String>("Email"));
        email.setMinWidth(100);
        email.setMaxWidth(100);

        TableColumn credit = new TableColumn("Maximum credit");
        credit.setCellValueFactory(new PropertyValueFactory<Customer, Double>("credit"));
        credit.setMinWidth(100);
        credit.setMaxWidth(100);

        TableColumn updBtn = new TableColumn("Update");
        updBtn.setCellValueFactory(new PropertyValueFactory<Customer, Button>("update"));
        updBtn.setMinWidth(100);
        updBtn.setMaxWidth(100);


        customers_table.getColumns().addAll(No,Name,credit,email,updBtn);
        customers_table.setItems(data1);

        Table_GraphsPane.getChildren().addAll(customers_table);
    }

    private  void Customer_data(){
        sales.connect();

        try {
            sales.result=sales.statement.executeQuery("select Name,max_credit,email,Id from customers");
            int count=0;
            while (sales.result.next()){

                String  Name=sales.result.getString(1);
                double  credit=Double.parseDouble(sales.result.getString(2));
                String  email=sales.result.getString(3);
                int     Id=sales.result.getInt(4);

                Button  upd=new Button("update");
                data1.add(new CustoTable(++count,Name,email,credit,upd));
                upd.setOnAction(e->{
                   updateCustomers(Name,credit,email,Id);
                });
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private void updateCustomers(String Name_Label,double credit,String email,int Id){
        Name_of_product=admin_window.setLabel(Name_Label,20,3,300,70, Font.font("Arial", FontWeight.BLACK,25));
        Name_of_product.setAlignment(Pos.CENTER);
        Label Credit=admin_window.setLabel("Credit :",5,120,70,40,new Font("Arial",16));
        Label Email=admin_window.setLabel("email :",5,180,70,40,new Font("Arial",16));

        //TextFields
        TextField customer_credit=admin_window.setTextfield(90,120,70,40,new Font("Arial",16),"white","black");
        customer_credit.setMaxSize(140,40);
        customer_credit.setText(Double.toString(credit));

        TextField Mail=admin_window.setTextfield(90,180,70,40,new Font("Arial",16),"white","black");
        Mail.setMaxSize(140,40);
        Mail.setText(email);

        updatepane=new Pane();
        updatepane.setLayoutX(800);
        updatepane.setLayoutY(50);
        updatepane.setMinSize(350,500);
        updatepane.setStyle("-fx-background-color: #9cbdc1");

        Table_GraphsPane.getChildren().add(updatepane);

        //Buttons
        Button edit1,edit2,edit3;
        String bgColor="#6b6d70";
        edit1=admin_window.setButton("Edit",250,120,90,40,bgColor);
        edit1.setTextFill(Color.web("white"));
        edit1.setOnAction(e->{
            if (customer_credit.getText().length()>0){
                String  query="update customers set max_credit="+customer_credit.getText()+" where Id="+Id;
                if (sales.performUpdate(query))
                    admin_window.myalert(Name_Label+"'s credit  updated successfully  to :"+customer_credit.getText(),680,300);
            }
        });

        edit2=admin_window.setButton("Edit",250,180,90,40,bgColor);
        edit2.setTextFill(Color.web("white"));
        edit2.setOnAction(e->{
            if (Mail.getText().length()>0){
                String  query="update customers set email='"+Mail.getText()+"' where Id="+Id;
                if(sales.performUpdate(query))
                    admin_window.myalert(Name_Label+"'s  category updated successfully  to :"+Mail.getText(),680,300);

            }
        });

        updatepane.getChildren().addAll(Credit,Email,customer_credit,edit1,edit2,Name_of_product,Mail);

    }

    public void Inventory(){
        Table_GraphsPane.getChildren().clear();
        if (Graphs instanceof RadioButton && tabular_format instanceof RadioButton)
            MainPane.getChildren().removeAll(Graphs,tabular_format);

        Label  products_view=admin_window.setLabel("PRODUCTS IN STOCK",100,5,400,50,new Font("Areial",35));
        products_view.setTextFill(Color.web("#7c7a70"));
        products_view.setAlignment(Pos.CENTER);

        Label   edit=admin_window.setLabel("Edit products",850,5,350,50,new Font("Areial",30));
        edit.setTextFill(Color.web("#7c7a70"));
        edit.setAlignment(Pos.CENTER);

        add_new_Product= admin_window.setButton("ADD NEW PRODUCT",50,5,150,25,"#c9e2e5");
            add_new_Product.setOnAction(e->{
                User_Windows   product=new User_Windows();
                product.add_product();
        });

        products_table=new TableView();
        data= FXCollections.observableArrayList();


        updatepane=new Pane();
        updatepane.setLayoutX(800);
        updatepane.setLayoutY(50);
        updatepane.setMinSize(350,500);
        updatepane.setStyle("-fx-background-color: #9cbdc1");

        products_table.setLayoutX(10);
        products_table.setLayoutY(50);
        products_table.setMinSize(500,500);
        products_table.setMaxSize(785,500);

        Table_Data();
        //tabular_format columns
        TableColumn No = new TableColumn("No");
        No.setCellValueFactory(new PropertyValueFactory<Inventory, Integer>("Id"));
        No.setMaxWidth(60);

        TableColumn Name = new TableColumn("Name");
        Name.setCellValueFactory(new PropertyValueFactory<Inventory, String>("Brand"));
        Name.setMinWidth(220);
        Name.setMaxWidth(220);

        TableColumn Quantity = new TableColumn("Unit price");
        Quantity.setCellValueFactory(new PropertyValueFactory<Inventory, Double>("Unit_Price"));
        Quantity.setMinWidth(100);
        Quantity.setMaxWidth(100);

        TableColumn Unit_Price = new TableColumn("Quantity");
        Unit_Price.setCellValueFactory(new PropertyValueFactory<Inventory, Integer>("Quantity"));
        Unit_Price.setMinWidth(100);
        Unit_Price.setMaxWidth(100);

        TableColumn Category = new TableColumn("Category");
        Category.setCellValueFactory(new PropertyValueFactory<Inventory, String>("Category"));
        Category.setMinWidth(140);
        Category.setMaxWidth(140);

        TableColumn Update = new TableColumn("Update");
        Update.setCellValueFactory(new PropertyValueFactory<Inventory, Button>("update_button"));
        Update.setMinWidth(160);
        Update.setMaxWidth(160);

        products_table.getColumns().addAll(No,Name,Category,Unit_Price,Quantity,Update);
        products_table.setItems(data);

        MainPane.getChildren().add(add_new_Product);
        Table_GraphsPane.getChildren().addAll(products_table,updatepane,products_view,edit);
    }
    private int index=0;
    private void Table_Data(){
        sales.connect();

        try {
            sales.result=sales.statement.executeQuery("select Id,Brand,Category,Unit_price,Quantity from products order by Quantity asc");
            while (sales.result.next()){

                String  Brand=sales.result.getString(2);
                int     Id=Integer.parseInt(sales.result.getString(1));
                String  category=sales.result.getString(3);

                double  Unit_Price=Double.parseDouble(sales.result.getString(4));
                int     quantity=Integer.parseInt(sales.result.getString(5));
                Button update_button=setUpdate_button(index,Brand,Unit_Price,category,quantity,Id);
                update_button.setMinSize(100,30);

                data.add(new Inventory(index+1,Brand, category, Unit_Price,quantity,update_button));
                index++;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    private Button setUpdate_button(int no,String Name,double p_price,String p_category, int p_quantity,int Id){
       Button update_button=new Button("Update");
        update_button.setOnAction(e->{
            if (Name_of_product instanceof Label)
                  updatepane.getChildren().clear();
            editPane(no,Name,p_price,p_category,p_quantity,Id,update_button);
        });
        return update_button;
    }
    private Label Name_of_product;
    private void editPane(int no,String Name_Label,double price,String category, int quantity,int Id,Button update_b){
        Name_of_product=admin_window.setLabel(Name_Label,20,3,300,70, Font.font("Arial", FontWeight.BLACK,25));
        Name_of_product.setAlignment(Pos.CENTER);
        Label Price=admin_window.setLabel("Price :",5,120,70,40,new Font("Arial",16));
        Label Category=admin_window.setLabel("Category :",5,180,70,40,new Font("Arial",16));
        Label Quantity=admin_window.setLabel("Quantity",5,240,70,40,new Font("Arial",16));

        //TextFields
        TextField product_price=admin_window.setTextfield(90,120,70,40,new Font("Arial",16),"white","black");
        product_price.setMaxSize(140,40);
        product_price.setText(Double.toString(price));

        TextField product_category=admin_window.setTextfield(90,180,70,40,new Font("Arial",16),"white","black");
        product_category.setMaxSize(140,40);
        product_category.setText(category);

        TextField product_quantity=admin_window.setTextfield(90,240,70,40,new Font("Arial",16),"white","black");
        product_quantity.setMaxSize(140,40);
        product_quantity.setText(Integer.toString(quantity));

        //Buttons
        Button edit1,edit2,edit3;
        String bgColor="#6b6d70";
        edit1=admin_window.setButton("Edit",250,120,90,40,bgColor);
        edit1.setTextFill(Color.web("white"));
        edit1.setOnAction(e->{
               if (product_price.getText().length()>0){
                  String  query="update products set Unit_price="+product_price.getText()+" where Id="+Id;
                  if (sales.performUpdate(query))
                      admin_window.myalert(Name_Label+" Price  updated successfully  to :"+product_price.getText(),680,300);
               }
        });

        edit2=admin_window.setButton("Edit",250,180,90,40,bgColor);
        edit2.setTextFill(Color.web("white"));
        edit2.setOnAction(e->{
            if (product_category.getText().length()>0){
                String  query="update products set Category='"+product_category.getText()+"' where Id="+Id;
                if(sales.performUpdate(query))
                    admin_window.myalert(Name_Label+" category updated successfully  to :"+product_category.getText(),680,300);

            }
        });

        edit3=admin_window.setButton("Edit",250,240,90,40,bgColor);
        edit3.setTextFill(Color.web("white"));
        edit3.setOnAction(e->{
            if (product_quantity.getText().length()>0){
                String  query="update products set Quantity="+product_quantity.getText()+" where Id="+Id;
                if (sales.performUpdate(query))
                    admin_window.myalert(Name_Label+" quantity Updated successfull  to :"+product_quantity.getText(),680,300);
            }
        });

        Button delete_Product=admin_window.setButton("Delete ",90,300,250,40,"#d14238");
        delete_Product.setFont(new Font("Arial",20));
        delete_Product.setTextFill(Color.web("white"));
        delete_Product.setOnAction(e->{

           Inventory   mm=new Inventory(no,Name_Label,category,price,quantity,update_b);
               products_table.getItems().remove(mm);
               sales.delete("delete from products where Id=" + Id);

        });


        updatepane.getChildren().addAll(Name_of_product,Price,Category,Quantity,product_category,product_price,product_quantity,edit1,edit2,edit3,delete_Product);
    }


    private void Sales(){
        start_date=new Label("From: ");
        start_date.setLayoutX(85);
        start_date.setLayoutY(20);
        start_date.setMinSize(100,30);
        start_date.setFont(new Font("Arial",20));
        start=new DatePicker();
        End=new DatePicker();
        start.setLayoutX(154);
        start.setLayoutY(20);
        start.setMinHeight(30);

        end_date=new Label("To: ");
        end_date.setLayoutX(347);
        end_date.setLayoutY(20);
        end_date.setMinSize(100,30);
        end_date.setFont(new Font("Arial",20));
        End.setLayoutX(389);
        End.setLayoutY(20);
        End.setMinHeight(30);

        start.setOnAction(event -> {
            if (End.getValue() != null) {
                if (Graphs.isSelected()) {
                    Table_GraphsPane.getChildren().clear();
                    Table_GraphsPane.getChildren().addAll(start_date, end_date, start, End);

                    admin_window.GenerateGraph(25, 50, sales, Table_GraphsPane, "Total sales", "Products", "Sales report",
                            "select count( * )from sales where date_of_purchase between cast('" + start.getValue() + "' as date) and" +
                                    " cast('" + End.getValue() + "' as date) order by Product_Name",
                            "select Product_Name, Quantity from sales where date_of_purchase between cast('" + start.getValue() + "' as date)" +
                                    "and cast('" + End.getValue() + "' as date) order by Product_Name", "Products sold ");

                }else
                    GenerateTable(start.getValue()+"",End.getValue()+"");
            }
        });

        End.setOnAction(e->{

                if (start.getValue() != null) {
                    if (Graphs.isSelected()) {
                    Table_GraphsPane.getChildren().clear();
                    Table_GraphsPane.getChildren().addAll(start_date, end_date, start, End);

                    admin_window.GenerateGraph(25, 50, sales, Table_GraphsPane, "Total sales", "Products", "Sales report",
                            "select count( * )from sales where date_of_purchase between cast('" + start.getValue() + "' as date) and" +
                                    " cast('" + End.getValue() + "' as date) order by Product_Name",
                            "select Product_Name, Quantity from sales where date_of_purchase between cast('" + start.getValue() + "' as date)" +
                                    "and cast('" + End.getValue() + "' as date) order by Product_Name", "Products sold ");

                } else
                    GenerateTable(start.getValue()+"",End.getValue()+"");
            }
        });

        String color="#deefef";
        Font  radio=new Font("Arial",16);
        ToggleGroup g=new ToggleGroup();
        int radioheight=30;

        //Clearing panes
        Table_GraphsPane.getChildren().clear();
        if (add_new_Product instanceof Button)
                MainPane.getChildren().removeAll(add_new_Product);

        //Setting up radio buttons
        Graphs=new RadioButton("View graphical sales report");
        Graphs.setLayoutX(20);
        Graphs.setLayoutY(10);
        Graphs.setMinHeight(radioheight);
        Graphs.setFont(radio);
        Graphs.setToggleGroup(g);
        Graphs.setTextFill(Color.web(color));
        Graphs.setSelected(true);

        if (Graphs.isSelected()){
            Table_GraphsPane.getChildren().clear();
            Table_GraphsPane.getChildren().addAll(start,End,start_date,end_date);
            admin_window.GenerateGraph(25,50,sales,Table_GraphsPane,"Total sales","Products","Sales report",
                    "select count( * )from sales order by Product_Name",
                    "select Product_Name, Quantity from sales order by Product_Name", "Products sold ");
        }
        Graphs.setOnAction(e->{
            Table_GraphsPane.getChildren().clear();
            Table_GraphsPane.getChildren().addAll(start,End,start_date,end_date);
            admin_window.GenerateGraph(20,50,sales,Table_GraphsPane,"Total sales","Products","Sales report",
                    "select count( * )from sales order by Product_Name",
                      "select Product_Name, Quantity from sales order by Product_Name", "Products sold ");



        });

        tabular_format =new RadioButton("View tabular report");
        tabular_format.setLayoutX(300);
        tabular_format.setLayoutY(10);
        tabular_format.setMinHeight(radioheight);
        tabular_format.setFont(radio);
        tabular_format.setToggleGroup(g);
        tabular_format.setTextFill(Color.web(color));
        tabular_format.setOnAction(e->{
            if (tabular_format.isSelected())
                GenerateTable("1900-01-01",getDate());
        });

        MainPane.getChildren().addAll(Graphs, tabular_format);
    }

    protected String getDate() {
        String date;
        Date today=new Date();
        SimpleDateFormat datefmt=new SimpleDateFormat("YYYY-MM-dd");
        date=datefmt.format(today);

        return date;
    }

    private void GenerateTable(String from, String to){

        Table_GraphsPane.getChildren().clear();

            TableView sales_table =new TableView();

            TableColumn    Name=new TableColumn("Name");
            Name.setCellValueFactory(new PropertyValueFactory<DailySales,String>("Name"));
            Name.setMinWidth(200);

            TableColumn    Quantity=new TableColumn("Quantity");
            Quantity.setCellValueFactory(new PropertyValueFactory<DailySales,Integer>("quantity"));
            Quantity.setMaxWidth(200);
            Quantity.setMinWidth(200);

            TableColumn    Amount=new TableColumn("Amount(Ksh)");
            Amount.setCellValueFactory(new PropertyValueFactory<DailySales,Double>("amount"));
            Amount.setMaxWidth(200);
            Amount.setMinWidth(200);

            ObservableList<DailySales>  data= FXCollections.observableArrayList();
            genarateTodays_Sale_data(data, from, to);


            sales_table.getColumns().addAll(Name,Quantity,Amount);
            sales_table.setItems(data);

            sales_table.setLayoutX(50);
            sales_table.setLayoutY(70);

        Table_GraphsPane.getChildren().addAll(start,End,start_date,end_date,sales_table);
    }

    private void genarateTodays_Sale_data(ObservableList a,String from_date,String to_date){

        sales.connect();
        try {
            int Quantity=0;
            String  Brand="";

            ResultSet set =sales.statement.executeQuery("select Brand,Quantity from sales where" +
                    " date_of_purchase between cast('"+from_date+"' as date) and cast('"+to_date+"' as date) order by Brand");

            int total_quantity=0,init_Brand=0,sum_quantity=0,Sum_income=0;
            String previous_brand="";
            while(set.next()){
                Brand=set.getString(1);
                Quantity=set.getInt(2);

                if (init_Brand==0){previous_brand=Brand; init_Brand++;}

                if (Brand.equals(previous_brand)){
                    total_quantity+=Quantity;
                    sum_quantity+=Quantity;
                    previous_brand=Brand;
                }else {
                    double unit_price=sales.getPrice("select Unit_price from products where Brand='"+previous_brand+"'");
                    double total_revenue=((total_quantity+Quantity)*unit_price);
                    Sum_income+=total_revenue;
                    a.add(new DailySales(previous_brand,total_quantity+Quantity,total_revenue));
                    total_quantity=0;
                    previous_brand=Brand;
                }
            }
            double q=sales.getPrice("select Unit_price from products where Brand='"+previous_brand+"'");
            a.add(new DailySales(Brand,total_quantity+Quantity,(q*(total_quantity+Quantity))));
            a.add(new DailySales("Total ",sum_quantity+Quantity,Sum_income+(q*(total_quantity+Quantity))));

        }catch (Exception e){
            e.printStackTrace();
        }

}

}
