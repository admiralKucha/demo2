package com.example.demo2;

public enum View {
    MAIN("main.fxml"),
    BRIGHTNESS("brightness.fxml"),
    GRAYSCALE("grayscale.fxml"),
    NEGATIVE("negative.fxml"),
    LIMIT("limit.fxml"),
    CONTRAST("contrast.fxml"),
    LCONTRAST("Lcontrast.fxml"),
    GAMMA("gamma.fxml"),
    PSCLR("psecolor.fxml"),
    SOLAR("solarization.fxml"),
    LPF("LPF.fxml"),
    HPF("HPF.fxml"),
    STOCHAST("stochastic.fxml"),
    LAPLACE("Laplace.fxml"),
    MEDIAN("median.fxml"),
    QUAN("quan.fxml"),
    HIST("hist.fxml");


    private String filename;
    View(String filename){
        this.filename = filename;
    }

    public String getFilename(){
        return filename;
    }
}
