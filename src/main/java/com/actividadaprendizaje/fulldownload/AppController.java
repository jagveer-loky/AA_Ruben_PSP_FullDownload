package com.actividadaprendizaje.fulldownload;


import com.svalero.multithread.util.R;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AppController {//getiona la ventan principal y dicha
    private static final Logger logger = LogManager.getLogger(com.svalero.multithread.DwnlController.class);
    public TextField tfUrl;// el nombre que le hemos puesto en la app
    public TabPane tpDwnls;

    private Map<String, com.svalero.multithread.DwnlController> allDwnls;

    public AppController() {
        allDwnls = new HashMap<>();
    }

    @FXML
    public void launchDownload(ActionEvent event) {
        String urlText = tfUrl.getText();//coge la url de la caja
        tfUrl.clear();//la vacia
        tfUrl.requestFocus();// enfoco para poder meter otra url

        launchDownload(urlText);// llama a este otro metodo que se llama igual pero esta sobrecargado
    }

    private void launchDownload(String url) {//cuando le doy aqui añade la descarga(lo que hemos puesto en onclick)
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(R.getUI("dwnl.fxml"));

            com.svalero.multithread.DwnlController dwnlController = new com.svalero.multithread.DwnlController(url);
            loader.setController(dwnlController);
            VBox downloadBox = loader.load();

            String filename = url.substring(url.lastIndexOf("/") + 1);//coge el ultimo parametro despues de la barra
            //y lo pone en la descarga
            tpDwnls.getTabs().add(new Tab(filename, downloadBox));//aqui lo que hace es añadir el tab(pestaña)
            //lo añade diagmos en el tpDwls que he creado y crea un nuevo tab

            allDwnls.put(url, dwnlController);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @FXML
    public void stopAllDownloads() {
        for (com.svalero.multithread.DwnlController dwnlController : allDwnls.values())
            dwnlController.stop();
    }

    @FXML
    public void readDLC() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        try {
            List<String> urls = Files.readAllLines(file.toPath());
            urls.forEach(this::launchDownload);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    @FXML
    public void viewLog(ActionEvent event) throws IOException {
        Desktop desktop = Desktop.getDesktop();
        File log = new File("C:\\Users\\Rayben\\Desktop\\AA_Ruben_PSP-feature\\multidescargas.log");
        desktop.open(log);
    }
}
