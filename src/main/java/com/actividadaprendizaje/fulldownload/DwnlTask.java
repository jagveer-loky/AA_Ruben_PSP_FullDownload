package com.actividadaprendizaje.fulldownload;

import javafx.concurrent.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;


public class DwnlTask extends Task<Integer> {

    private URL url;
    private File file;

    private static final Logger logger = LogManager.getLogger(DwnlController.class);

    public DwnlTask(String urlText, File file) throws MalformedURLException {
        this.url = new URL(urlText);
        this.file = file;
    }

    @Override
    protected Integer call() throws Exception {
        logger.trace("Descarga " + url.toString() + " iniciada");
        updateMessage("Conectando con el servidor . . .");


        //codigo de desrcarga
        URLConnection urlConnection = url.openConnection();
        double fileSize = urlConnection.getContentLength();
        BufferedInputStream in = new BufferedInputStream(url.openStream());
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        byte dataBuffer[] = new byte[1024];
        int bytesRead;
        int totalRead = 0;
        double downloadProgress = 0;



        while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
            downloadProgress = ((double) totalRead / fileSize);

            updateProgress(downloadProgress, 1);

            DecimalFormat df = new DecimalFormat("##.##");
            df.setRoundingMode(RoundingMode.DOWN);

            updateMessage(downloadProgress * 100 + " %");


            updateMessage(totalRead/1000000 + " MB                /        " +
                    " " + df.format(downloadProgress * 100) + " %");
            fileOutputStream.write(dataBuffer, 0, bytesRead);
            totalRead += bytesRead;

            //TODO
//            downloadByte = urlConnection.getContentLength()*downloadProgress;
//            updateMessage(downloadByte*10024 + "bytes");
           // downloadByte =+ 1024;


            if (isCancelled()) {
                logger.trace("Descarga " + url.toString() + " cancelada");
                return null;
            }
        }

        updateProgress(1,1);
        updateMessage("100 %");

        logger.trace("Descarga " + url.toString() + " finalizada");
        return null;
    }
}
