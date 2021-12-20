package com.actividadaprendizaje.fulldownload;

import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.MalformedURLException;

;


public class DwnlController {
    public TextField tfUrlD;
    public Label lbByte;
    public Label lbStatus;
    public ListView listFail;
    public ProgressBar pbProgress;
    private String urlText;
    private DwnlTask dwnlTask;


    private Logger logger = LogManager.getLogger(DwnlController.class);


    public DwnlController(String urlText) {
        logger.info("Descarga " + urlText + " creada");
        this.urlText = urlText;
    }




    @FXML
    public void start(ActionEvent event) {

        try {

            FileChooser fileChooser = new FileChooser();
            //TODO
           // File file = fileChooser.showSaveDialog(tfUrlD.getScene().getWindow());
              File file = new File(tfUrlD.getText());
            if (file == null)
                return;//pide al usuario un fichero



            dwnlTask = new DwnlTask(urlText, file);

            pbProgress.progressProperty().unbind();
            pbProgress.progressProperty().bind(dwnlTask.progressProperty());

            dwnlTask.stateProperty().addListener((observableValue, oldState, newState) -> {
                System.out.println(observableValue.toString());
                if (newState == Worker.State.SUCCEEDED) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("La descarga ha terminado");
                    alert.show();
                }
            });

            dwnlTask.messageProperty().addListener((observableValue, oldValue, newValue) -> lbStatus.setText(newValue));

            new Thread(dwnlTask).start();

        } catch (MalformedURLException e) {
            e.printStackTrace();
            logger.error("URL mal formada", e.fillInStackTrace());
        }
    }



    @FXML
    public void stop(ActionEvent event) {
        stop();
    }

    public void stop() {
        if (dwnlTask != null)
            dwnlTask.cancel();//llama al cancel del task
    }

    public String getUrlText() {
        return urlText;
    }
}
