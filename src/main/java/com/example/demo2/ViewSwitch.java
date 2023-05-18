package com.example.demo2;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class ViewSwitch {

    public static Scene scene;

    public static void setScene(Scene scene){
        ViewSwitch.scene = scene;
    }

    public  static  void switchTo(View view) throws IOException {
        Parent root = FXMLLoader.load(ViewSwitch.class.getResource(view.getFilename()));
        scene.setRoot(root);
    }

}
