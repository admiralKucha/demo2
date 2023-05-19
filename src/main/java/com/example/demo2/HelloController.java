package com.example.demo2;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Arrays;


public class HelloController {
    private static int[][] matrix1;
    private static int[][] matrix2;
    private static int[] matrixres = new int[9];

    protected int chclor(int color, int ch){
        if (color + ch > 255){
            color = 255;
        } else if ( color + ch < 0) {
            color = 0;
        } else {
            color = color + ch;
        }
        return color;
    }
    protected int negatlimit(int color, int p){
        if (color >= p){
            return 255-color;
        }
        else {
            return color;
        }

    }
    protected int chlimit(int color, int p){
        if (color >= p){
            return 255;
        }
        else {
            return 0;
        }

    }
    protected int inputClr(String inputText){
        int value = 0;
        try {
            value = Integer.parseInt(inputText);
        } catch (NumberFormatException e) {
        }
        return value;
    }
    @FXML
    private Slider slider;
    @FXML
    private TextField inputField;
    @FXML
    private TextField inputField1;
    @FXML
    private TextField inputField2;
    @FXML
    private TextField inputField3;
    @FXML
    private Slider slider1;
    @FXML
    protected void save() throws IOException {
        File file = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg");
        BufferedImage image = ImageIO.read(file);
        File outputFile = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo1.jpg");
        ImageIO.write(image, "JPG", outputFile);
        ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
        newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo1.jpg"));
    }
    @FXML
    protected void exit() throws IOException {
        ViewSwitch.switchTo(View.MAIN);
        ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
        newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo1.jpg"));
    }
    @FXML
    protected void grayscale() throws IOException {
        File file = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo1.jpg");
        BufferedImage image = ImageIO.read(file);
        int height = (int)image.getHeight();
        int width = (int)image.getWidth();
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                int color = image.getRGB(x, y);
                int alpha = (color >> 24) & 0xff;
                int red = (color >> 16) & 0xff;
                int green = (color >> 8) & 0xff;
                int blue = color & 0xff;
                int gray = (int) (0.3*red+0.59*green+0.11*blue);
                int newColor = (alpha << 24) | (gray << 16) | (gray << 8) | gray;
                image.setRGB(x, y, newColor);
                }
            }
        File outputFile = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg");
        ImageIO.write(image, "JPG", outputFile);
        ViewSwitch.switchTo(View.GRAYSCALE);
        ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
        newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
    }
    @FXML
    protected void toBr() throws IOException {
        ViewSwitch.switchTo(View.BRIGHTNESS);
        File file = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo1.jpg");
        BufferedImage image = ImageIO.read(file);
        File outputFile = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg");
        ImageIO.write(image, "JPG", outputFile);
        ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
        newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
    }
    @FXML
    protected void toHist() throws IOException {

        ViewSwitch.switchTo(View.HIST);
        int[] numbers = new int[255];
        File file = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo1.jpg");
        BufferedImage image = ImageIO.read(file);
        int height = (int)image.getHeight();
        int width = (int)image.getWidth();
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                int color = image.getRGB(x, y);
                int alpha = (color >> 24) & 0xff;
                int red = (color >> 16) & 0xff;
                int green = (color >> 8) & 0xff;
                int blue = color & 0xff;
                int gray = (int) (0.3*red+0.59*green+0.11*blue);
                numbers[gray] = numbers[gray]+1;
            }
        }
        int max = 0;
        for(int y = 0; y < 255; y++) {
            if (max < numbers[y]){
                max = numbers[y];
            }
        }
        double diff = width/215;
        for(int x = 0; x < width-52; x++) {
            int i = (int)(x/diff);
            int le = height*numbers[i]/max;
            for(int y = 0; y < le; y++) {
                int newColor =  (i << 16) | (i << 8) | i;
                image.setRGB(x, y, newColor);
            }
        }
        File outputFile = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg");
        ImageIO.write(image, "JPG", outputFile);
        ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
        newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
    }
    @FXML
    protected void brightness() throws IOException {
        File file = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo1.jpg");
        int update = (int)slider.getValue();
        BufferedImage image = ImageIO.read(file);
        int height = (int)image.getHeight();
        int width = (int)image.getWidth();
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                int color = image.getRGB(x, y);
                int alpha = (color >> 24) & 0xff;
                int red = chclor((color >> 16) & 0xff, update);
                int green = chclor((color >> 8) & 0xff, update);
                int blue = chclor(color & 0xff, update);
                int newColor = (alpha << 24) | (red << 16) | (green << 8) | blue;
                image.setRGB(x, y, newColor);
            }
        }
        File outputFile = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg");
        ImageIO.write(image, "JPG", outputFile);
        ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
        newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
    }
    @FXML
    protected void refresh() throws IOException {
        File file = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\original.jpg");
        BufferedImage image = ImageIO.read(file);
        File outputFile = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo1.jpg");
        ImageIO.write(image, "JPG", outputFile);
        ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
        newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo1.jpg"));
    }
    @FXML
    protected void negative() throws IOException {
        File file = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo1.jpg");
        int update = (int)slider.getValue();
        BufferedImage image = ImageIO.read(file);
        int height = (int)image.getHeight();
        int width = (int)image.getWidth();
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                int color = image.getRGB(x, y);
                int alpha = (color >> 24) & 0xff;
                int red = negatlimit((color >> 16) & 0xff, update);
                int green = negatlimit((color >> 8) & 0xff, update);
                int blue = negatlimit(color & 0xff, update);
                int newColor = (alpha << 24) | (red << 16) | (green << 8) | blue;
                image.setRGB(x, y, newColor);
            }
        }
        File outputFile = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg");
        ImageIO.write(image, "JPG", outputFile);
        ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
        newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
    }
    @FXML
    protected void toNeg() throws IOException {
        ViewSwitch.switchTo(View.NEGATIVE);
        File file = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo1.jpg");
        BufferedImage image = ImageIO.read(file);
        File outputFile = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg");
        ImageIO.write(image, "JPG", outputFile);
        ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
        newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
    }
    @FXML
    protected void toLim() throws IOException {
        ViewSwitch.switchTo(View.LIMIT);
        File file = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo1.jpg");
        BufferedImage image = ImageIO.read(file);
        File outputFile = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg");
        ImageIO.write(image, "JPG", outputFile);
        ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
        newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
    }
    @FXML
    protected void toConrast() throws IOException {
        ViewSwitch.switchTo(View.CONTRAST);
        File file = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo1.jpg");
        BufferedImage image = ImageIO.read(file);
        File outputFile = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg");
        ImageIO.write(image, "JPG", outputFile);
        ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
        newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
    }
    @FXML
    protected void toLPF() throws IOException {
        ViewSwitch.switchTo(View.LPF);
        File file = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo1.jpg");
        BufferedImage image = ImageIO.read(file);
        int height = (int)image.getHeight();
        int width = (int)image.getWidth();
        matrix1 = new int[height+2][width+2];
        matrix2 = new int[height+2][width+2];
        for (int i = 0; i < height+2; i++) {
            matrix1[i][0] = 255;
            matrix1[i][width+1] = 255;
        }
        for (int i = 0; i < width+2; i++) {
            matrix1[0][i] = 255;
            matrix1[height+1][i] = 255;
        }
        try {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int color = image.getRGB(x, y);
                    int red = (color >> 16) & 0xff;
                    int green = (color >> 8) & 0xff;
                    int blue = color & 0xff;
                    int gray = (int) (0.3*red+0.59*green+0.11*blue);
                    int newColor =  (gray << 16) | (gray << 8) | gray;
                    image.setRGB(x, y, newColor);
                    matrix1[y+1][x+1] = gray;
                }
            }
            ImageIO.write(image, "jpg", new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
            ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
            newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void toHPF() throws IOException {
        ViewSwitch.switchTo(View.HPF);
        File file = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo1.jpg");
        BufferedImage image = ImageIO.read(file);
        int height = (int)image.getHeight();
        int width = (int)image.getWidth();
        matrix1 = new int[height+2][width+2];
        matrix2 = new int[height+2][width+2];
        for (int i = 0; i < height+2; i++) {
            matrix1[i][0] = 255;
            matrix1[i][width+1] = 255;
        }
        for (int i = 0; i < width+2; i++) {
            matrix1[0][i] = 255;
            matrix1[height+1][i] = 255;
        }
        try {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int color = image.getRGB(x, y);
                    int red = (color >> 16) & 0xff;
                    int green = (color >> 8) & 0xff;
                    int blue = color & 0xff;
                    int gray = (int) (0.3*red+0.59*green+0.11*blue);
                    int newColor =  (gray << 16) | (gray << 8) | gray;
                    image.setRGB(x, y, newColor);
                    matrix1[y+1][x+1] = gray;
                }
            }
            ImageIO.write(image, "jpg", new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
            ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
            newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void toMedian() throws IOException {
        ViewSwitch.switchTo(View.MEDIAN);
        File file = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo1.jpg");
        BufferedImage image = ImageIO.read(file);
        int height = (int)image.getHeight();
        int width = (int)image.getWidth();
        matrix1 = new int[height+2][width+2];
        matrix2 = new int[height+2][width+2];
        for (int i = 0; i < height+2; i++) {
            matrix1[i][0] = 255;
            matrix1[i][width+1] = 255;
        }
        for (int i = 0; i < width+2; i++) {
            matrix1[0][i] = 255;
            matrix1[height+1][i] = 255;
        }
        try {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int color = image.getRGB(x, y);
                    int red = (color >> 16) & 0xff;
                    int green = (color >> 8) & 0xff;
                    int blue = color & 0xff;
                    int gray = (int) (0.3*red+0.59*green+0.11*blue);
                    int newColor =  (gray << 16) | (gray << 8) | gray;
                    image.setRGB(x, y, newColor);
                    matrix1[y+1][x+1] = gray;
                }
            }
            ImageIO.write(image, "jpg", new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
            ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
            newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void toStochastic() throws IOException {
        ViewSwitch.switchTo(View.STOCHAST);
        File file = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo1.jpg");
        BufferedImage image = ImageIO.read(file);
        int height = (int)image.getHeight();
        int width = (int)image.getWidth();
        matrix1 = new int[height+2][width+2];
        matrix2 = new int[height+2][width+2];
        for (int i = 0; i < height+2; i++) {
            matrix1[i][0] = 255;
            matrix1[i][width+1] = 255;
        }
        for (int i = 0; i < width+2; i++) {
            matrix1[0][i] = 255;
            matrix1[height+1][i] = 255;
        }
        try {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int color = image.getRGB(x, y);
                    int red = (color >> 16) & 0xff;
                    int green = (color >> 8) & 0xff;
                    int blue = color & 0xff;
                    int gray = (int) (0.3*red+0.59*green+0.11*blue);
                    int newColor =  (gray << 16) | (gray << 8) | gray;
                    image.setRGB(x, y, newColor);
                    matrix1[y+1][x+1] = gray;
                }
            }
            ImageIO.write(image, "jpg", new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
            ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
            newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void toLaplace() throws IOException {
        ViewSwitch.switchTo(View.LAPLACE);
        File file = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo1.jpg");
        BufferedImage image = ImageIO.read(file);
        int height = (int)image.getHeight();
        int width = (int)image.getWidth();
        matrix1 = new int[height+2][width+2];
        matrix2 = new int[height+2][width+2];
        for (int i = 0; i < height+2; i++) {
            matrix1[i][0] = 255;
            matrix1[i][width+1] = 255;
        }
        for (int i = 0; i < width+2; i++) {
            matrix1[0][i] = 255;
            matrix1[height+1][i] = 255;
        }
        try {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int color = image.getRGB(x, y);
                    int red = (color >> 16) & 0xff;
                    int green = (color >> 8) & 0xff;
                    int blue = color & 0xff;
                    int gray = (int) (0.3*red+0.59*green+0.11*blue);
                    int newColor =  (gray << 16) | (gray << 8) | gray;
                    image.setRGB(x, y, newColor);
                    matrix1[y+1][x+1] = gray;
                }
            }
            ImageIO.write(image, "jpg", new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
            ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
            newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void Laplace() throws IOException {
        int[][] matrix = {
                {0, 1, 0},
                {1, -4, 1},
                {0, 1, 0}
        };
        File file2 = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg");
        BufferedImage image2 = ImageIO.read(file2);
        int height = (int)image2.getHeight();
        int width = (int)image2.getWidth();
        matrix2 = new int[height+2][width+2];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int res = 0;
                for(int i = 0; i<3; i++){
                    for(int j = 0; j < 3; j++){
                        res += matrix1[y+i][x+j] *matrix[i][j];
                    }
                }
                res = Math.abs(Math.min(255, res));
                image2.setRGB(x, y, (res << 16) | (res << 8) | res);
                matrix2[y+1][x+1] = res;
            }
        }
        matrix1 = matrix2.clone();
        ImageIO.write(image2, "jpg", new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
        ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
        newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
    }
    @FXML
    protected void HPF9() throws IOException {
        int[][] matrix = {
                {-1, -1, -1},
                {-1, 9, -1},
                {-1, -1, -1}
        };
        File file2 = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg");
        BufferedImage image2 = ImageIO.read(file2);
        int height = (int)image2.getHeight();
        int width = (int)image2.getWidth();
        matrix2 = new int[height+2][width+2];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int res = 0;
                for(int i = 0; i<3; i++){
                    for(int j = 0; j < 3; j++){
                        res += matrix1[y+i][x+j] *matrix[i][j];
                    }
                }
                res = Math.max(0, Math.min(255, res));
                image2.setRGB(x, y, (res << 16) | (res << 8) | res);
                matrix2[y+1][x+1] = res;
            }
        }
        matrix1 = matrix2.clone();
        ImageIO.write(image2, "jpg", new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
        ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
        newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
    }
    @FXML
    protected void HPF5() throws IOException {
        int[][] matrix = {
                {0, -1, 0},
                {-1, 5, -1},
                {0, -1, 0}
        };
        File file2 = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg");
        BufferedImage image2 = ImageIO.read(file2);
        int height = (int)image2.getHeight();
        int width = (int)image2.getWidth();
        matrix2 = new int[height+2][width+2];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int res = 0;
                for(int i = 0; i<3; i++){
                    for(int j = 0; j < 3; j++){
                        res += matrix1[y+i][x+j] *matrix[i][j];
                    }
                }
                res = Math.max(0, Math.min(255, res));
                image2.setRGB(x, y, (res << 16) | (res << 8) | res);
                matrix2[y+1][x+1] = res;
            }
        }
        matrix1 = matrix2.clone();
        ImageIO.write(image2, "jpg", new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
        ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
        newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
    }
    @FXML
    protected void median() throws IOException {
        File file2 = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg");
        BufferedImage image2 = ImageIO.read(file2);
        int height = (int)image2.getHeight();
        int width = (int)image2.getWidth();

        matrix2 = new int[height+2][width+2];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                for(int i = 0; i<3; i++){
                    for(int j = 0; j < 3; j++){
                        matrixres[3*i+j] =  matrix1[y+i][x+j];
                    }
                }
                Arrays.sort(matrixres);
                image2.setRGB(x, y, (matrixres[5] << 16) | (matrixres[5] << 8) | matrixres[5]);
                matrix2[y+1][x+1] = matrixres[5];
            }
        }
        matrix1 = matrix2.clone();
        ImageIO.write(image2, "jpg", new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
        ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
        newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
    }
    @FXML
    protected void stochastic() throws IOException {
        File file2 = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg");
        BufferedImage image2 = ImageIO.read(file2);
        int height = (int)image2.getHeight();
        int width = (int)image2.getWidth();
        int outVal;
        matrix2 = new int[height+2][width+2];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if(matrix1[y+1][x+1] < 128){outVal = 0;}
                else{outVal = 255;}
                outVal = matrix1[y+1][x+1] - outVal;
                matrix2[y+1][x+2] += (8*outVal)/17;
                matrix2[y+2][x] += (3*outVal)/17;
                matrix2[y+2][x+1] += (5*outVal)/17;
                matrix2[y+2][x+2] += (1*outVal)/17;
                outVal = Math.max(0, Math.min(255, matrix2[y+1][x+1]+matrix1[y+1][x+1]));
                matrix2[y+1][x+1] =outVal;
                image2.setRGB(x, y, (outVal << 16) | (outVal << 8) | outVal);
            }
        }
        matrix1 = matrix2.clone();
        ImageIO.write(image2, "jpg", new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
        ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
        newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
    }
    @FXML
    protected void HPF10() throws IOException {
        int[][] matrix = {
                {1, -2, 1},
                {-2, 5, -2},
                {1, -2, 1}
        };
        File file2 = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg");
        BufferedImage image2 = ImageIO.read(file2);
        int height = (int)image2.getHeight();
        int width = (int)image2.getWidth();
        matrix2 = new int[height+2][width+2];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int res = 0;
                for(int i = 0; i<3; i++){
                    for(int j = 0; j < 3; j++){
                        res += matrix1[y+i][x+j] *matrix[i][j];
                    }
                }
                res = Math.max(0, Math.min(255, res));
                image2.setRGB(x, y, (res << 16) | (res << 8) | res);
                matrix2[y+1][x+1] = res;
            }
        }
        matrix1 = matrix2.clone();
        ImageIO.write(image2, "jpg", new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
        ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
        newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
    }
    @FXML
    protected void LPF9() throws IOException {
        File file2 = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg");
        BufferedImage image2 = ImageIO.read(file2);
        int height = (int)image2.getHeight();
        int width = (int)image2.getWidth();
        matrix2 = new int[height+2][width+2];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int res = 0;
                for(int i = 0; i<3; i++){
                    for(int j = 0; j < 3; j++){
                        res += matrix1[y+i][x+j];
                    }
                }
                res = res / 9;
                image2.setRGB(x, y, (res << 16) | (res << 8) | res);
                matrix2[y+1][x+1] = res;
            }
        }
        matrix1 = matrix2.clone();
        ImageIO.write(image2, "jpg", new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
        ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
        newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
    }
    @FXML
    protected void LPF10() throws IOException {
        int[][] matrix = {
                {1, 1, 1},
                {1, 2, 1},
                {1, 1, 1}
        };
        File file2 = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg");
        BufferedImage image2 = ImageIO.read(file2);
        int height = (int)image2.getHeight();
        int width = (int)image2.getWidth();
        matrix2 = new int[height+2][width+2];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int res = 0;
                for(int i = 0; i<3; i++){
                    for(int j = 0; j < 3; j++){
                        res += matrix1[y+i][x+j] * matrix[i][j];
                    }
                }
                res = res / 10;
                image2.setRGB(x, y, (res << 16) | (res << 8) | res);
                matrix2[y+1][x+1] = res;
            }
        }
        matrix1 = matrix2.clone();
        ImageIO.write(image2, "jpg", new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
        ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
        newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
    }
    @FXML
    protected void LPF16() throws IOException {
        int[][] matrix = {
                {1, 2, 1},
                {2, 4, 2},
                {1, 2, 1}
        };
        File file2 = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg");
        BufferedImage image2 = ImageIO.read(file2);
        int height = (int)image2.getHeight();
        int width = (int)image2.getWidth();
        matrix2 = new int[height+2][width+2];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int res = 0;
                for(int i = 0; i<3; i++){
                    for(int j = 0; j < 3; j++){
                        res += matrix1[y+i][x+j] * matrix[i][j];
                    }
                }
                res = res / 16;
                image2.setRGB(x, y, (res << 16) | (res << 8) | res);
                matrix2[y+1][x+1] = res;
            }
        }
        matrix1 = matrix2.clone();
        ImageIO.write(image2, "jpg", new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
        ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
        newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
    }
    @FXML
    protected void toLConrast() throws IOException {
        ViewSwitch.switchTo(View.LCONTRAST);
        File file = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo1.jpg");
        BufferedImage image = ImageIO.read(file);
        File outputFile = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg");
        ImageIO.write(image, "JPG", outputFile);
        ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
        newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
    }
    @FXML
    protected void toGamma() throws IOException {
        ViewSwitch.switchTo(View.GAMMA);
        File file = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo1.jpg");
        BufferedImage image = ImageIO.read(file);
        File outputFile = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg");
        ImageIO.write(image, "JPG", outputFile);
        ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
        newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
    }
    @FXML
    protected void toSol() throws IOException {
        ViewSwitch.switchTo(View.SOLAR);
        File file = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo1.jpg");
        BufferedImage image = ImageIO.read(file);
        File outputFile = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg");
        ImageIO.write(image, "JPG", outputFile);
        ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
        newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
    }
    @FXML
    protected void solar() throws IOException {
        File file = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo1.jpg");
        float A = Math.max(0, Math.min(255, inputClr(inputField1.getText())));
        float B = Math.max(0, Math.min(255, inputClr(inputField2.getText())));
        float C = Math.max(0, Math.min(255, inputClr(inputField3.getText())));
        float b = 255 / (A+B-(A+C)/(A*A+C*C));
        float a = -b/C;
        BufferedImage image = ImageIO.read(file);
        int height = (int)image.getHeight();
        int width = (int)image.getWidth();
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                int color = image.getRGB(x, y);
                int alpha = (color >> 24) & 0xff;
                int red = (color >> 16) & 0xff;
                red = (int) (a*red*red +b*red);
                int green = (color >> 8) & 0xff;
                green = (int) (a*green*green +b*green);
                int blue = color & 0xff;
                blue = (int) (a*blue*blue +b*blue);

                    int newColor = (alpha << 24) | (red << 16) | (green << 8) | blue;
                    image.setRGB(x, y, newColor);
                }
            }

        File outputFile = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg");
        ImageIO.write(image, "JPG", outputFile);
        ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
        newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
    }
    @FXML
    protected void toPSCLR() throws IOException {
        ViewSwitch.switchTo(View.PSCLR);
        File file = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo1.jpg");
        BufferedImage image = ImageIO.read(file);
        int height = (int)image.getHeight();
        int width = (int)image.getWidth();
        for(int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int color = image.getRGB(x, y);
                int alpha = (color >> 24) & 0xff;
                int red = (color >> 16) & 0xff;
                int green = (color >> 8) & 0xff;
                int blue = color & 0xff;
                int gray = (int) (0.3 * red + 0.59 * green + 0.11 * blue);
                int newColor = (alpha << 24) | (gray << 16) | (gray << 8) | gray;
                image.setRGB(x, y, newColor);
            }
        }
        File outputFile = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg");
        ImageIO.write(image, "JPG", outputFile);
        ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
        newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
    }
    @FXML
    protected void toQuan() throws IOException {
        ViewSwitch.switchTo(View.QUAN);
        File file = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo1.jpg");
        BufferedImage image = ImageIO.read(file);
        int height = (int)image.getHeight();
        int width = (int)image.getWidth();
        for(int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int color = image.getRGB(x, y);
                int alpha = (color >> 24) & 0xff;
                int red = (color >> 16) & 0xff;
                int green = (color >> 8) & 0xff;
                int blue = color & 0xff;
                int gray = (int) (0.3 * red + 0.59 * green + 0.11 * blue);
                int newColor = (alpha << 24) | (gray << 16) | (gray << 8) | gray;
                image.setRGB(x, y, newColor);
            }
        }
        File outputFile = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg");
        ImageIO.write(image, "JPG", outputFile);
        ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
        newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
    }
    @FXML
    protected void paint3() throws IOException {
        File file = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo3.jpg");
        BufferedImage image = ImageIO.read(file);
        File outputFile = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg");
        ImageIO.write(image, "JPG", outputFile);
        ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
        newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
    }
    @FXML
    protected void quan() throws IOException {
        File file = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg");
        String inputText = inputField.getText();
        int value = 0;
        try {
            value = Integer.parseInt(inputText);
        } catch (NumberFormatException e) {
            return;
        }
        if (value == 0){value = 1;}
        int piece = 256 / value;
        float mid = (float)piece / 2;
        System.out.println(piece);
        System.out.println(mid);
        BufferedImage image = ImageIO.read(file);
        int height = (int)image.getHeight();
        int width = (int)image.getWidth();
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                int color = image.getRGB(x, y);
                int alpha = (color >> 24) & 0xff;
                int red = (color >> 16) & 0xff;
                int t = (int) ((int) (red/piece * mid) + mid);
                int newColor = (alpha << 24) | (t << 16) | (t << 8) | t;
                image.setRGB(x, y, newColor);
            }
        }
        File outputFile = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo3.jpg");
        ImageIO.write(image, "JPG", outputFile);
        ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
        newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo3.jpg"));
    }
    @FXML
    protected void PSCLR() throws IOException {
        File file = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg");
        int q1 = (int)slider.getValue();
        int q2 = (int)slider1.getValue();
        int inputTextR = Math.max(0, Math.min(255, inputClr(inputField1.getText())));
        int inputTextG = Math.max(0, Math.min(255, inputClr(inputField2.getText())));
        int inputTextB = Math.max(0, Math.min(255, inputClr(inputField3.getText())));

        if (q1 > q2){
            int buf = q1;
            q1 = q2;
            q2 = buf;
        }
        BufferedImage image = ImageIO.read(file);
        int height = (int)image.getHeight();
        int width = (int)image.getWidth();
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                int color = image.getRGB(x, y);
                int alpha = (color >> 24) & 0xff;
                int gray = (color >> 16) & 0xff;
                if (gray > q1 && gray < q2){
                    int newColor = (alpha << 24) | (inputTextR << 16) | (inputTextG << 8) | inputTextB;
                    image.setRGB(x, y, newColor);
                }
            }
        }
        File outputFile = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg");
        ImageIO.write(image, "JPG", outputFile);
        ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
        newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
    }
    @FXML
    protected void contrast() throws IOException {
        File file = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo1.jpg");
        int q1 = (int)slider.getValue();
        int q2 = (int)slider1.getValue();
        if (q1 > q2){
            int buf = q1;
            q1 = q2;
            q2 = buf;
        }
        else if (q1 == q2){
            q2 = q2 + 1;
        }
        BufferedImage image = ImageIO.read(file);
        int height = (int)image.getHeight();
        int width = (int)image.getWidth();
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                int color = image.getRGB(x, y);
                int alpha = (color >> 24) & 0xff;
                int red = (color >> 16) & 0xff;
                int green = (color >> 8) & 0xff;
                int blue = color & 0xff;
                int gray = (int) (0.3*red+0.59*green+0.11*blue);
                if (gray <q1) gray = 0;
                else if ( gray > q2) gray = 255;
                else gray = (255 * (gray - q1) / (q2 - q1));
                int newColor = (alpha << 24) | (gray << 16) | (gray << 8) | gray;
                image.setRGB(x, y, newColor);
            }
        }
        File outputFile = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg");
        ImageIO.write(image, "JPG", outputFile);
        ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
        newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
    }
    @FXML
    protected void gamma() throws IOException {
        File file = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo1.jpg");
        String inputText = inputField.getText();
        float value = 0;
        try {
            value = Float.parseFloat(inputText);
        } catch (NumberFormatException e) {
            return;
        }

        BufferedImage image = ImageIO.read(file);
        int height = (int)image.getHeight();
        int width = (int)image.getWidth();
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                int color = image.getRGB(x, y);
                int alpha = (color >> 24) & 0xff;
                int red = (color >> 16) & 0xff;
                int green = (color >> 8) & 0xff;
                int blue = color & 0xff;
                int gray = (int) (0.3*red+0.59*green+0.11*blue);

                float t = (float)gray/255;

                gray = (int) (255 * Math.pow(t, value));

                int newColor = (alpha << 24) | (gray << 16) | (gray << 8) | gray;
                image.setRGB(x, y, newColor);
            }
        }
        File outputFile = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg");
        ImageIO.write(image, "JPG", outputFile);
        ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
        newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
    }
    @FXML
    protected void Lcontrast() throws IOException {
        File file = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo1.jpg");
        int q1 = (int)slider.getValue();
        int q2 = (int)slider1.getValue();
        if (q1 > q2){
            int buf = q1;
            q1 = q2;
            q2 = buf;
        }
        BufferedImage image = ImageIO.read(file);
        int height = (int)image.getHeight();
        int width = (int)image.getWidth();
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                int color = image.getRGB(x, y);
                int alpha = (color >> 24) & 0xff;
                int red = (color >> 16) & 0xff;
                int green = (color >> 8) & 0xff;
                int blue = color & 0xff;
                int gray = (int) (0.3*red+0.59*green+0.11*blue);
                gray = gray * (q2 - q1) / 255 + q1;
                int newColor = (alpha << 24) | (gray << 16) | (gray << 8) | gray;
                image.setRGB(x, y, newColor);
            }
        }
        File outputFile = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg");
        ImageIO.write(image, "JPG", outputFile);
        ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
        newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
    }
    @FXML
    protected void limit() throws IOException{
        File file = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo1.jpg");
        BufferedImage image = ImageIO.read(file);
        int update = (int)slider.getValue();
        int height = (int)image.getHeight();
        int width = (int)image.getWidth();
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                int color = image.getRGB(x, y);
                int alpha = (color >> 24) & 0xff;
                int red = (color >> 16) & 0xff;
                int green = (color >> 8) & 0xff;
                int blue = color & 0xff;
                int gray = chlimit((int) (0.3*red+0.59*green+0.11*blue), update);
                int newColor = (alpha << 24) | (gray << 16) | (gray << 8) | gray;
                image.setRGB(x, y, newColor);
            }
        }
        File outputFile = new File("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg");
        ImageIO.write(image, "JPG", outputFile);
        ImageView newImageView = (ImageView) ViewSwitch.scene.lookup("#image");
        newImageView.setImage(new Image("D:\\ImageProcessing\\demo2\\src\\main\\resources\\images\\photo2.jpg"));
    }


}
