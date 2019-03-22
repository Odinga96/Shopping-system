package userInterface;

import Databse.DbConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import products.DailySales;
import products.Sales;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Shopkeeper {
    public String user_logged_in;

    private double sellfield_height=20;

    private DbConnect products=new DbConnect("jdbc:mysql://localhost:3306/shop","root","");
    private Graphical_User shop=new Graphical_User();
    private Stage shopStage=new Stage();
    private AnchorPane   window=new AnchorPane();
    private Pane header,receipt_pane;
    private Font shopfont=new Font("Arial",20);
    private Label Todays_Date,User_name;
    private TextArea receipt_area;
    private TextField Total_Charges,Total_Cash,timearea;

    private AnchorPane sellPane;
    private TabPane tabPane;
    private Tab Sell,ViewSales;
    private ScrollPane itemsPane;
    private Pane[] categories=new Pane[products.getCount("select Count(Distinct Category) from products")];

    //Variables used for purchase
    private int count_products=products.getCount("select count(*) from products");     //Gives total product count
    private String[] product_Name=new String[count_products-1];
    private String[] brand=new String[count_products-1];
    private int[]    quantity=new int[count_products-1],avilable_quantity=new int[count_products-1];
    private int count_bought_products =0;
    private String[]  product_id=new String[count_products-1];
    private TableView sales_table;

    private double tabpanewidth=0.74;

    private Dimension size= Toolkit.getDefaultToolkit().getScreenSize();

    public void ShopWindow(){

        header();
        ReceiptPane();
        SaleTabs_setUP();

        shop.showTime(Todays_Date,timearea);
        window.getChildren().addAll(header,receipt_pane,tabPane);
        shop.setStage(window,shopStage,size.width,size.height,true,"#78c");
    }

    private void header(){
        header=shop.setPane(0,0,"#30474c");
        Label shopTitle;

        shopTitle=shop.setLabel("CLINTEX SHOPPING SYSTEM ",180,5,350,70,shopfont);
        shopTitle.setFont(Font.font("Arial", FontWeight.BLACK,30));
        shopTitle.setTextFill(Color.web("#fff"));


        User_name=shop.setLabel(user_logged_in,1000,3,150,20,new Font("Arial",15));
        User_name.setTextFill(Color.web("white"));

        Todays_Date=shop.setLabel("",0.943975*size.width,3,50,20,new Font("Arial",15));
        Todays_Date.setTextFill(Color.web("white"));

        timearea=shop.setTextfield(0.8625*size.width,0,100,25,new Font("Arial",15),"#304741","white");
        timearea.setMaxSize(125,30);
        timearea.setEditable(false);

        //Log out button
        Button logout=shop.setButton("sign out",0.9375*size.width,40,70,25,"#e2dc5d");
        logout.setOnAction(e->shopStage.close());

        //Add customer view daily sales_table and search
        Button  add_customer=shop.setButton("ADD NEW CUSTOMER",0.8*size.width,40,150,25,"ecd");
        add_customer.setOnAction(e->{
            User_Windows customer=new User_Windows();
            customer.add_customer();
        });

        ImageView cart=shop.PutImage("../shop/images/cart.png",3,3,60,60);

        header.getChildren().addAll(shopTitle,cart,logout,add_customer,timearea,Todays_Date,User_name);
        header.setMinSize(size.width,60);

    }

    private  void ReceiptPane(){
          receipt_pane=shop.setPane((tabpanewidth*size.width)+5,77,"#516b61");
          receipt_pane.setMinSize(((1-tabpanewidth)*size.width)-9,size.height*0.835);

          Label receipt=shop.setLabel("Receipt",100,0,300,30,new Font("Arial",25));
          receipt.setTextFill(Color.web("white"));

          //Receipt text Area
          receipt_area=shop.setTextarea(5,25,(int)(size.width*0.021874),(int)(size.height*0.05),shopfont,"red");
          receipt_area.setFont(new Font("Arial",11));
          receipt_area.setEditable(false);
          receipt_area.setText( "\t\t\t\t\t\tClintex Shop"+timearea.getText()+"\n" +
                                "    ___________________________________________________");

          //Receipt butttons
          Label total_charge=shop.setLabel("Total charge :",0,0.71*size.height,200,25,Font.font("New Times Roman",FontWeight.BLACK,15));
          total_charge.setMaxSize(100,25);
          Total_Charges=shop.setTextfield(120,0.71*size.height,70,26,shopfont,"white","black");
          Total_Charges.setMaxSize(102,25);
          Total_Charges.setFont(new Font("Arial",13));
          Total_Charges.setText("0.0");

          Label Cash_Label=shop.setLabel("Cash :",235,0.71*size.height,70,30,Font.font("New Times Roman",FontWeight.BLACK,15));
          Total_Cash=shop.setTextfield(300,0.71*size.height,60,26,shopfont,"white","black");
          Total_Cash.setMaxSize(102,26);
          Total_Cash.setFont(new Font("Arial",13));
          Total_Cash.setText("0.00");



          Button sell=shop.setButton("Sell",.65*(receipt_pane.getMinWidth()),0.76*size.height,120,40,"green");
          sell.setOnAction(e->sellProducts());
          Button Reset_Receipt=shop.setButton("Reset",.15*(receipt_pane.getMinWidth()),0.76*size.height,120,40,"yellow");
          Reset_Receipt.setOnAction(e->{
              receipt_area.setText("\t\t\t\t\tClintex Shop\t"+timearea.getText()+"\n" +
                                  "    _______________________________________________");

              reset();
          });
          receipt_pane.getChildren().addAll(receipt,receipt_area,total_charge,Total_Charges,Cash_Label,Total_Cash,sell,Reset_Receipt);
    }

    private void SaleTabs_setUP(){
        tabPane=new TabPane();
        tabPane.setLayoutX(3);
        tabPane.setLayoutY(78);
        tabPane.setMinSize(tabpanewidth*size.width,size.height);
        tabPane.setTabMinWidth(120);
        tabPane.setTabMinHeight(25);

        itemsPane=new ScrollPane();

        Sell=new Tab("Sell");
        ViewSales=new Tab("Today's sales_table");
        ViewSales.setClosable(false);
        Sell.setClosable(false);
        tabPane.getTabs().addAll(Sell,ViewSales);



        sellTab();
        Todays_salesPane();
    }

      private void sellTab(){
         itemsPane=new ScrollPane();

         sellPane=new AnchorPane();
         //sellPane.setMinSize(975,593);
         sellPane.setStyle("-fx-background-color: #124");
         itemsPane.setContent(sellPane);
         Sell.setContent(itemsPane);

         double height=Category_Panes()+200;

         if (height>593)
         sellPane.setMinSize(.7375*size.width,height);
         else
             sellPane.setMinSize(.7375*size.width,.796*size.height);

    }
    private void Todays_salesPane(){
       ScrollPane sales_pane=new ScrollPane();

        AnchorPane Todays_sale=new AnchorPane();
        Todays_sale.setMinSize(.7375*size.width,.796*size.height);
        Todays_sale.setStyle("-fx-background-color: #124");
        sales_pane.setContent(Todays_sale);
        ViewSales.setContent(sales_pane);

        Pane showdata=shop.setPane(2,3,"aed0e2");
        showdata.setMinSize(.7375*size.width,.796*size.height);
        Todays_sale.getChildren().addAll(showdata);

        ToggleGroup   g=new ToggleGroup();
        RadioButton   table=new RadioButton("Table format");
        table.setLayoutX(20);
        table.setLayoutY(30);
        table.setTextFill(Color.web("#100"));
        table.setToggleGroup(g);
        table.setSelected(true);
        table.setFont(new Font("Arial",20));

        RadioButton   grapgh=new RadioButton(" View in graph format");
        grapgh.setFont(new Font("Arial",20));

        if (table.isSelected())
            Todays_Sales_Table(showdata);

        table.setOnAction(e->{
            showdata.getChildren().clear();
            showdata.getChildren().addAll(table,grapgh);

            Todays_Sales_Table(showdata);
        });


        grapgh.setLayoutX(150);
        grapgh.setLayoutY(30);
        grapgh.setTextFill(Color.web("#100"));
        grapgh.setToggleGroup(g);
        grapgh.setOnAction(e->{
            if (sales_table instanceof TableView)
                showdata.getChildren().removeAll(sales_table);

             shop.GenerateGraph(20,50,products,showdata,"Total quantity","Products","Sales report",
                "select count( * )from sales where date_of_purchase=cast('"+getDate()+"' as date) order by Product_name",
                "select Product_Name, Quantity from sales where date_of_purchase=cast('"+getDate()+"' as date) order by Product_name", "Products sold ");

        });

        showdata.getChildren().addAll(table,grapgh);
    }
    private void Todays_Sales_Table(Pane showdata){
        sales_table =new TableView();

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
        genarateTodays_Sale_data(data);


        sales_table.getColumns().addAll(Name,Quantity,Amount);
        sales_table.setItems(data);

        sales_table.setLayoutX(50);
        sales_table.setLayoutY(70);

        showdata.getChildren().addAll(sales_table);

    }
    private void genarateTodays_Sale_data(ObservableList a){

        products.connect();
        try {
            int Quantity=0;
            String  Brand="";

            ResultSet set =products.statement.executeQuery("select Brand,Quantity from sales where" +
                                         " date_of_purchase=cast('"+getDate()+"' as date) order by Brand");

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
                      double unit_price=products.getPrice("select Unit_price from products where Brand='"+previous_brand+"'");
                      double total_revenue=((total_quantity)*unit_price);
                      Sum_income+=total_revenue;
                      a.add(new DailySales(previous_brand,total_quantity,total_revenue));

                      System.out.println(Brand);
                      total_quantity=Quantity;
                      sum_quantity+=Quantity;
                      previous_brand=Brand;
                  }
            }
            double q=products.getPrice("select Unit_price from products where Brand='"+previous_brand+"'");
            a.add(new DailySales(Brand,total_quantity,(q*(total_quantity))));
            a.add(new DailySales("Total ",sum_quantity,Sum_income+(q*(total_quantity))));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Gets the date
    private String getDate() {
        String date;
        Date today=new Date();
        SimpleDateFormat datefmt=new SimpleDateFormat("YYYY-MM-dd");
        date=datefmt.format(today);

        return date;
    }

      private double Category_Panes(){

          int count=1,X=5,Y=50,PaneNo=0,PanLayoutY=5,PaneHeight=3+(int)sellfield_height*2,init_cat=0;
        try {
            products.connect();
            products.result=products.statement.executeQuery("select Id,Brand,Unit_price,Category,Product_Name from products where not Quantity=0  order By Category");
            String PREVIOUS_CATEGORY="Beverages";

            while (products.result.next()){

                //Initializing the previous Category variable
                if(init_cat==0) { PREVIOUS_CATEGORY=products.result.getString(4); init_cat++; }

                String Id=products.result.getString(1);
                String BRAND=products.result.getString(2);
                String PRICE=products.result.getString(3);
                String CATEGORY=products.result.getString(4);
                String Product_Name=products.result.getString(5);

                // receipt_area.setText();
                if (CATEGORY.equals(PREVIOUS_CATEGORY)){
                    productsField(PaneNo,PanLayoutY,X,Y,CATEGORY,count,BRAND,PRICE,Id,Product_Name);
                    PREVIOUS_CATEGORY=CATEGORY;

                }else{
                    Y=50;

                    if (count!=1) { PaneHeight += sellfield_height+10; }else PaneHeight+=20;
                    PanLayoutY+=PaneHeight;
                    count=1;

                    categories[PaneNo].setMinSize(1175,PaneHeight-2);
                    PaneNo++;
                    productsField(PaneNo,PanLayoutY,X,Y,CATEGORY,count,BRAND,PRICE,Id,Product_Name);
                    PREVIOUS_CATEGORY=CATEGORY;
                    PaneHeight=3+(int)sellfield_height*2;
                   // count=0;
                }

                if (count==3){
                    PaneHeight+=sellfield_height+10;
                    count=0;
                     X=5;
                     Y+=sellfield_height+10;
                }
                count++;
            }
        }catch (SQLException e){
             e.printStackTrace();
        }

        return PanLayoutY+PaneHeight;
  }

  private void productsField(int PaneNo,int PaneLayoutY,int X , int Y,String Category_Label,int Count,String Name, String Price,String Id, String Product_Name){
        if (!(categories[PaneNo] instanceof  Pane)){
              categories[PaneNo]=new Pane();
              categories[PaneNo].setLayoutX(2);
              categories[PaneNo].setLayoutY(PaneLayoutY);
              categories[PaneNo].setMinWidth(0.734375*size.width);

              String Category_Colors="#1be";
            double Category_height=25;

              Label  Title=new Label(Category_Label);
              Title.setLayoutX(0);
              Title.setLayoutY(3);
              Title.setFont(new Font("Arial",20));
              Title.setTextFill(Color.web(Category_Colors));
              Title.setMinSize(0.706442166*size.width,Category_height);
              Title.setAlignment(Pos.CENTER);

              Separator  separate=new Separator();
              separate.setOrientation(Orientation.HORIZONTAL);
              separate.setLayoutX(3);
              separate.setLayoutY(Category_height+2);
              separate.setMinSize(0.706442166*size.width,3);

              int LabelX=10,LabeLY=(int)Category_height+8;

                  for (int i=0;i<3;i++){
                      Label product_Name=new Label("Name");
                      Label product_Unit_Price=new Label("Unit_Price");
                      Label product_Quatity=new Label("Quantity");

                      product_Name.setTextFill(Color.web("yellow"));
                      product_Name.setLayoutX(LabelX);
                      product_Name.setLayoutY(LabeLY);

                      product_Unit_Price.setTextFill(Color.web("yellow"));
                      product_Unit_Price.setLayoutX(LabelX+150);
                      product_Unit_Price.setLayoutY(LabeLY);

                      product_Quatity.setTextFill(Color.web("yellow"));
                      product_Quatity.setLayoutX(LabelX+150+150);
                      product_Quatity.setLayoutY(LabeLY);

                      LabelX+=384;

                    categories[PaneNo].getChildren().addAll(product_Name,product_Unit_Price,product_Quatity);
                  }
              categories[PaneNo].getChildren().addAll(Title,separate);
              sellPane.getChildren().add(categories[PaneNo]);
        }

        CheckBox Name_Of_Product=new CheckBox();
        TextField Price_Of_Product,Quantity;
      if (Count==1){
          Name_Of_Product.setText(Name);
          Name_Of_Product.setLayoutX(X);
          Name_Of_Product.setLayoutY(Y);
          Name_Of_Product.setMinSize(144,sellfield_height);
          Name_Of_Product.setTextFill(Color.web("cyan"));

          Price_Of_Product=shop.setTextfield(X+160,Y,80,sellfield_height,new Font("Arial",12),"white","black");
          Price_Of_Product.setEditable(false);
          Price_Of_Product.setMaxSize(80,sellfield_height);
          Price_Of_Product.setText(Price);

          Quantity=shop.setTextfield(X+160+160,Y,50,sellfield_height,new Font("Arial",12),"white","black");
          Quantity.setMaxSize(50,sellfield_height);

          categories[PaneNo].getChildren().addAll(Name_Of_Product,Price_Of_Product,Quantity);
          categories[PaneNo].setStyle("-fx-background-color: #178");

          selectItems(Name_Of_Product,Quantity,Price_Of_Product,Id,Product_Name);
      }else if(Count==2){
          Name_Of_Product=new CheckBox(Name);
          Name_Of_Product.setLayoutY(Y);
          Name_Of_Product.setLayoutX(X+328+60);
          Name_Of_Product.setMinSize(100,sellfield_height);

          Name_Of_Product.setTextFill(Color.web("cyan"));
          Price_Of_Product=shop.setTextfield(X+448+60,Y,80,sellfield_height,new Font("Arial",12),"white","black");
          Price_Of_Product.setEditable(false);
          Price_Of_Product.setMaxSize(80,20);
          Price_Of_Product.setText(Price);

          Quantity=shop.setTextfield(X+560+60,Y,50,sellfield_height,new Font("Arial",12),"white","black");
          Quantity.setMaxSize(50,20);

          categories[PaneNo].getChildren().addAll(Name_Of_Product,Price_Of_Product,Quantity);
          categories[PaneNo].setStyle("-fx-background-color: #178");
          selectItems(Name_Of_Product,Quantity,Price_Of_Product,Id,Product_Name);

      }else if(Count==3){
          Name_Of_Product=new CheckBox(Name);
          Name_Of_Product.setLayoutY(Y);
          Name_Of_Product.setLayoutX(X+660+60);
          Name_Of_Product.setMinSize(100,sellfield_height);

          Name_Of_Product.setTextFill(Color.web("cyan"));
          Price_Of_Product=shop.setTextfield(X+795+60,Y,80,sellfield_height,new Font("Arial",12),"white","black");
          Price_Of_Product.setEditable(false);
          Price_Of_Product.setMaxSize(80,sellfield_height);
          Price_Of_Product.setText(Price);

          Quantity=shop.setTextfield(X+893+60,Y,50,sellfield_height,new Font("Arial",12),"white","black");
          Quantity.setMaxSize(50,sellfield_height);

          categories[PaneNo].getChildren().addAll(Name_Of_Product,Price_Of_Product,Quantity);
          categories[PaneNo].setStyle("-fx-background-color: #178");

         selectItems(Name_Of_Product,Quantity,Price_Of_Product,Id,Product_Name);
      }


  }

  private void selectItems(CheckBox item,TextField quantity,TextField price,String Id,String Product_Name){

           item.setOnAction(e->{
               if (item.isSelected()){
                     if (quantity.getText().length()>0)
                          item.setSelected(true);
                     else{ item.setSelected(false);
                     JOptionPane.showMessageDialog(null,"Insert the quantity");}

            if (item.isSelected()){
                try {
                    double charge=Double.parseDouble(price.getText().trim());
                    int    needed_quantity=Integer.parseInt(quantity.getText().trim());
                    double charges=charge*needed_quantity;
                    double current_charges=charges+Double.parseDouble(Total_Charges.getText());

                    //If statement to check if the quantity is available for sale
                    int available_quantity=products.get_quantity("select Quantity from products where Id="+Id);
                    if (available_quantity>=needed_quantity){

                    for (int i=0;i<=3;i++)
                    {   int lenght=(i==0)?item.getText().length():(i==1)?Integer.toString(needed_quantity).length()
                                   :(i==2)?Double.toString(charge).length():Double.toString(current_charges).length();
                        int space=(i==0)?40:(i==1)?10:(i==2)?10:10;
                        //int space

                        String receipt=receipt_area.getText();
                        if(i==0)
                            receipt_area.setText(receipt+"\n\t"+item.getText());
                        else if(i==1)
                            receipt_area.setText(receipt+needed_quantity);
                        else if (i==2)
                            receipt_area.setText(receipt+charge);
                        else
                            receipt_area.setText(receipt+charges);

                        for (int j=lenght;j<space;j++){
                            receipt_area.setText(receipt_area.getText()+" ");
                        }receipt_area.setText(receipt_area.getText()+"|");
                    }
                    Total_Charges.setText(Double.toString(current_charges));

                    //Set the array values
                   setProducts(Product_Name,item.getText(),needed_quantity,Id,available_quantity);


                    }else {
                        JOptionPane.showMessageDialog(null, "Only " + available_quantity +"  "+item.getText()+ " are in stock");
                        item.setSelected(false);
                    }

                }catch (Exception ee){
                    JOptionPane.showMessageDialog(null,"Quantity can only be whole numbers"+e);
                }
            }
            //if item was selected unselect it
               }else
               {
                   item.setSelected(false);
                   receipt_area.getText().replaceAll(receipt_area.getText(),item.getText()+"          "+quantity.getText());
               }

        });
  }
  //Reset * array values
  private void reset(){
      Total_Cash.setText("0.00");
      Total_Charges.setText("0.00");
      count_bought_products =0;
      for (int i=0; i<count_products;i++){
          product_Name[i]=null;
          brand[i]=null;
          quantity[i]=0;
          product_id[i]=null;
          avilable_quantity[i]=0;

      }
  }

  private void setProducts(String P_Name,String P_Brand,int P_quantity,String p_Id,int quantity_in_stock){
      product_Name[count_bought_products]=P_Name;
      brand[count_bought_products]=P_Brand;
      quantity[count_bought_products]=P_quantity;
      product_id[count_bought_products]=p_Id;
      avilable_quantity[count_bought_products]=quantity_in_stock;

      count_bought_products++;
  }

  private void sellProducts(){

        double cost=Double.parseDouble(Total_Charges.getText());
        double cash_handed=Double.parseDouble(Total_Cash.getText());

        if(cost>0) {
            if (cash_handed >= cost) {
                for (int i = 0; i < count_bought_products; i++) {
                    Sales new_Sale = new Sales(product_Name[i], brand[i], quantity[i], 1);
                    new_Sale.addProducts();

                    String update_query = "update products set Quantity=" + (avilable_quantity[i] - quantity[i]) + " where Id=" + product_id[i];
                    products.performUpdate(update_query);
                }
                //receipt_area.setFont(new Font("Arial",30));
                receipt_area.setText(receipt_area.getText() +
                        "\n\t ---------------------------------------------------------------------------\n\t"
                        + " Total cash:\t\t\t\t\t\t Ksh: " + cash_handed + "\n\t"
                        + " Total cost:\t\t\t\t\t\t Ksh: " + cost + "\n\t"
                        + " Balance   :\t\t\t\t\t\t Ksh: " + (cash_handed - cost) + "\n\t"
                        + " -----------------------------------------------------------------------------\n\t"
                        + " Date      :" + Todays_Date.getText() + "\t\t\t\t time: " + timearea.getText() + "\n\t"
                        + " -----------------------------------------------------------------------------\n\t"
                        + "Thanks for shopping with us welcome again     \n\t"
                        + "Served by  :****************" + User_name.getText()+"*******************\n\t"
                        + "------------------------------------------------------------------------------");
                reset();
                ShopWindow();
            } else {
                JOptionPane.showMessageDialog(null, "Cash is not enough");
                User_Windows credit = new User_Windows();
                credit.Buy_On_Credit();
            }
        }else
            JOptionPane.showMessageDialog(null,"No product has been selected");


  }


}
