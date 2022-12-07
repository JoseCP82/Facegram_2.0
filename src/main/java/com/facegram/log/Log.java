package com.facegram.log;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Log {

    /**
     * Atributos de clase
     */
    static Logger logger = Logger.getLogger(Log.class.getName());

    /**
     * Genera el log con nivel Info y muestra la información
     * @param message Mensaje a mostrar
     */
    public static void infoLogging(String message) {
        //saveLog();
        logger.setLevel(Level.INFO);
        logger.log(Level.INFO,message);
    }

    /**
     * Genera el log con nivel Warning y muestra la información
     * @param message Mensaje a mostrar
     */
    public static void warningLogging(String message) {
        //saveLog();
        logger.setLevel(Level.WARNING);
        logger.log(Level.WARNING,message);
    }

    private static void saveLog() {
        try {
            InputStream configFile= Log.class.getResourceAsStream("logging.properties");
            LogManager.getLogManager().readConfiguration(configFile);
        }catch(SecurityException | IOException | NullPointerException e) {
            //new ErrorMessage("Logging system not initialized").showMessage();
        }
        logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    }
}
