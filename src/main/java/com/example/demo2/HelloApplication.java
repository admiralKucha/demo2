package com.example.demo2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        var scene = new  Scene(new Pane());

        ViewSwitch.setScene(scene);
        ViewSwitch.switchTo(View.MAIN);

        File file = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\original.jpg");
        BufferedImage image = ImageIO.read(file);
        File outputFile = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo1.jpg");
        ImageIO.write(image, "JPG", outputFile);
        ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
        newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo1.jpg"));

        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args)  {
        launch();
    }
}