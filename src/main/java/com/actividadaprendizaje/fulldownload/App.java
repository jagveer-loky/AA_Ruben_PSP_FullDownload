package com.actividadaprendizaje.fulldownload;

import com.svalero.multithread.util.R;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage stage) throws Exception {//al heredar del Application te obliga a tener el metodo star
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(R.getUI("fullDwnl.fxml"));//aqui carga la pricipal llama a la clase R metodo getUI
        loader.setController(new AppController());//añade el controller
        ScrollPane vbox = loader.load();//componente padre ScrollPane(caja grande) que donde mete la otra caja

        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.setTitle("Downloader");
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public static void main(String[] args) {
        launch();
    }//lanza el metodo launch de Application
}
