package shop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import userInterface.Shopkeeper;

public class Main extends Application {
    public Stage window=new Stage();
    @Override
    public void start(Stage primaryStage) throws Exception{

        //Parent root = FXMLLoader.load(getClass().getResource("../login/login.fxml"));
        //window.setTitle("login");

        Shopkeeper   sh=new Shopkeeper();
        sh.ShopWindow();
        //window.setScene(new Scene(root));
        //window.setResizable(false);
        //window.show();

    }


    public static void main(String[] args) {

        launch(args);
    }
}
