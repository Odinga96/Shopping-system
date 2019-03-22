package userInterface;

import Databse.DbConnect;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import shop.Main;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Graphical_User {
    protected void setButtonIcon(Button button_Name,String icon_link,double size){
        Image  icon=new Image(getClass().getResourceAsStream(icon_link));
        ImageView icon_image=new ImageView(icon);
        icon_image.setFitHeight(size);
        icon_image.setFitWidth(size);
        button_Name.setGraphic(icon_image);
    }

    protected ImageView PutImage(String imagepath, double X, double Y, double height, double width){
        ImageView icon = new ImageView();
        Image image2 = new Image(Main.class.getResourceAsStream(imagepath));

        icon.setImage(image2);
        icon.setLayoutX(X);
        icon.setLayoutY(Y);
        icon.setFitWidth(width);
        icon.setFitHeight(height);
        return icon;
    }

    protected Label setLabel(String label, double X, double Y, double minwidth, double minheight, Font font){
        Label l=new Label(label);
        l.setLayoutX(X);
        l.setLayoutY(Y);
        l.setMinSize(minwidth,minheight);
        l.setFont(font);
        return l;

    }

    protected TextField setTextfield(double X, double Y, double minWidth, double minHeight,Font font,String field_color,String text_color){
        TextField field=new TextField();
        field.setLayoutX(X);
        field.setLayoutY(Y);
        field.setMinSize(minWidth,minHeight);
        field.setFont(font);
        field.setStyle("-fx-background-color: "+field_color +
                ";-fx-text-inner-color: "+text_color);

        return field;
    }

    protected PasswordField setPasswordField(double X, double Y, double minWidth, double minHeight,Font font, String background_color,String text_color){
        PasswordField password=new PasswordField();
        password.setLayoutX(X);
        password.setLayoutY(Y);
        password.setMinSize(minWidth,minHeight);
        password.setFont(font);
        password.setStyle("-fx-background-color: "+background_color+"" +
                            ";-fx-text-inner-color: "+text_color);


        return password;
    }

    protected Button setButton(String button_text, double X, double Y, double minWidth, double minHeight,String background_color){
        Button mybutton=new Button(button_text);
        mybutton.setLayoutX(X);
        mybutton.setLayoutY(Y);
        mybutton.setMinSize(minWidth,minHeight);
        mybutton.setStyle("-fx-background-color: "+background_color);
        mybutton.setFont(new Font("Arial",16));
      return mybutton;
    }

    protected TextArea setTextarea(double X, double Y, int columnCount, int rowCount,Font font,String background_color){
        TextArea textArea=new TextArea();
        textArea.setLayoutX(X);
        textArea.setLayoutY(Y);
        textArea.setPrefColumnCount(columnCount);
        textArea.setPrefRowCount(rowCount);
        textArea.setStyle("text-area-background: "+background_color);
        textArea.setFont(font);
        return textArea;
    }

    public   void setStage(AnchorPane layout, Stage stage, double StageWidth, double StageHeigth, boolean resize, String background_color){
        layout.setStyle("-fx-background-color: "+background_color);
        Scene s=new Scene(layout,StageWidth,StageHeigth);
        stage.setResizable(resize);
        stage.setScene(s);
        stage.show();
    }

    protected Pane setPane(double X, double Y, String Background_color){
        Pane p=new Pane();
        p.setStyle("-fx-background-color: "+Background_color);
        //p.setMinSize(width,height);
        p.setLayoutX(X);
        p.setLayoutY(Y);

        return p;
    }

    protected void showTime(Label date,TextField time){

        Thread clock=new Thread()
        {
            public void run(){
                try {

                    for (;;){
                        Calendar cal=new GregorianCalendar();
                        int day=cal.get(Calendar.DAY_OF_MONTH);
                        int month=cal.get(Calendar.MONTH);
                        int year=cal.get(Calendar.YEAR);

                        date.setText(day+"/"+month+"/"+year);

                        int hour=cal.get(Calendar.HOUR_OF_DAY);
                        int minute=cal.get(Calendar.MINUTE);
                        int second=cal.get(Calendar.SECOND);

                        String notation=(hour<12)?"A.M":"P.M";
                        hour=(hour<=12)?hour:hour-12;

                        String HH=(hour<10)?"0"+hour:""+hour;
                        String MM=(minute<10)?"0"+minute:""+minute;
                        String SS=(second<10)?"0"+second:""+second;

                        time.setText(""+HH+":"+MM+":"+SS+"  "+notation);
                        sleep(1000);
                    }

                }catch (InterruptedException e){
                    System.out.println(e);
                }
            }
        };

        clock.start();
    }

    //LineChart
     public void GenerateGraph(double X, double Y, DbConnect connection,Pane layout_Pane, String Y_label, String X_Label, String tittle,String count_query,String sql_query,String dataName){
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(Y_label);

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel(X_Label);

        LineChart lineChart = new LineChart(xAxis, yAxis);
        lineChart.setTitle(tittle);

        XYChart.Series dataSeries1 = new XYChart.Series();
        dataSeries1.setName(dataName);

        try {
            int count_sales=connection.getCount(count_query);
            connection.connect();
            connection.result = connection.statement.executeQuery(sql_query);
            int count=0;
            double previous_quantity=0;
            String previous_Name="";

            while (connection.result.next()) {
                if (count == 0) {
                    previous_Name = connection.result.getString(1);
                }

                count++;
                String Name = connection.result.getString(1);
                double quantity = Double.parseDouble(connection.result.getString(2));

                if (previous_Name.equals(Name)) {
                    previous_quantity += quantity;
                    previous_Name = Name;
                } else {
                    dataSeries1.getData().add(new XYChart.Data(previous_Name, previous_quantity));
                    previous_Name = Name;
                    previous_quantity = quantity;
                }

                if (count==count_sales)
                    dataSeries1.getData().add(new XYChart.Data(Name, previous_quantity));

            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        lineChart.getData().add(dataSeries1);
        lineChart.setLayoutX(X);
        lineChart.setLayoutY(Y);
        lineChart.setMinSize(700,500);

        layout_Pane.getChildren().addAll(lineChart);

    }

     public   void myalert(String message,int X,int Y){
        AnchorPane alert=new AnchorPane();
        Stage alertStage=new Stage();
        alertStage.setX(X);
        alertStage.setY(Y);


        Label alertmessage=new Label(message);
        alertmessage.setMinSize(300,30);
        alert.setMinSize(400,50);
        alertmessage.setLayoutX(0);
        alertmessage.setLayoutY(0);
        alertmessage.setFont(new Font("Arial",15));
        alertmessage.setAlignment(Pos.CENTER);
          alertStage.initModality(Modality.APPLICATION_MODAL);

        Button Ok=new Button("Ok");
        Ok.setLayoutX(180);
        Ok.setLayoutY(50);
        Ok.setMinSize(50,20);
        Ok.setOnAction(e->alertStage.close());
        alert.getChildren().addAll(alertmessage,Ok);

        setStage(alert,alertStage,400,200,false,"yellow");
    }

}
