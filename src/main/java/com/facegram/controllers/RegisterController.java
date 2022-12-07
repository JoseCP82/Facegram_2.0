package com.facegram.controllers;

import com.facegram.log.Log;
import com.facegram.model.DAO.UserDAO;
import com.facegram.model.dataobject.User;
import com.facegram.utils.message.ConfirmMessage;
import com.facegram.utils.message.ErrorMessage;
import com.facegram.utils.message.InfoMessage;
import com.facegram.utils.message.Message;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegisterController extends Controller {

    /**
     * Atributos de la clase Register
     */
    private Stage stage;

    /**
     * Elementos FXML de la clase Register
     */
    @FXML private Button btnClose;
    @FXML private TextField tfName;
    @FXML private TextField tfPassword;
    @FXML private AnchorPane anchorLogin;
    private double xOffSet = 0;
    private double yOffSet = 0;

    /**
     * Método que hace drageable un stage
     */
    @FXML
    private void makeStageDragable() {
        anchorLogin.setOnMousePressed((event) -> {
            xOffSet = event.getSceneX();
            yOffSet = event.getSceneY();
        });
        anchorLogin.setOnMouseDragged((event) -> {
            App.stage.setX(event.getScreenX() - xOffSet);
            App.stage.setY(event.getScreenY() - yOffSet);
            App.stage.setOpacity(0.8f);
        });
        anchorLogin.setOnDragDone((event) -> {
            App.stage.setOpacity(1.0f);
        });
        anchorLogin.setOnMouseReleased((event) -> {
            App.stage.setOpacity(1.0f);
        });
    }

    /**
     * Encripta cualquier cadena usando SHA256
     */
    public static String encrypt(String s) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        }catch(NoSuchAlgorithmException e) {
            Log.warningLogging(e + "");
        }
        byte[] bytes = md.digest(s.getBytes());
        StringBuffer sb = new StringBuffer();

        for(byte b : bytes){
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * Permite al usuario registrarse si no existe o iniciar sesión si ya existe
     * @throws IOException
     */
    @FXML public void register() throws IOException {
        String name = tfName.getText();
        String password = tfPassword.getText();
        if(!name.equals("") && !password.equals("")){
            User u = new User(name,password);
            UserDAO uDAO = new UserDAO(u);
            uDAO.setPassword(encrypt(password));
            if(uDAO.get(name).getId()==-1){
                Message m = new ConfirmMessage("El usuario no existe.\n ¿Desea crearlo?");
                m.showMessage();
                if(((ConfirmMessage)m).getBt()==ButtonType.OK){
                    uDAO.insert();
                    new InfoMessage("Usuario añadido").showMessage();
                    permanentUser=uDAO.get(name);
                    changeFeed();
                }
            }else{
                if(uDAO.get(name).getName().equals(name) && uDAO.get(name).getPassword().equals(encrypt(password))){
                    Message m = new InfoMessage("Sesión iniciada");
                    m.showMessage();
                    permanentUser=uDAO.get(name);
                    changeFeed();
                }else{
                    new ErrorMessage("El nombre o contraseña son incorrectos").showMessage();
                }
            }
        }else{
            new ErrorMessage("Los campos no pueden estar vacíos, introduzca información").showMessage();
        }
    }

    /**
     * Redirige a la pantalla del Feed
     * @throws IOException
     */
    @FXML public void changeFeed() throws IOException {
        this.stage = (Stage) this.btnClose.getScene().getWindow();
        this.stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("feed.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 480);
        Stage s = new Stage();
        s.setScene(scene);
        s.setResizable(false);
        s.initStyle(StageStyle.UNDECORATED);
        s.show();
    }

    /**
     * Minimiza la aplicación a la barra de tareas
     */
    @FXML private void minimizeWindow() {
        this.stage = (Stage) this.btnClose.getScene().getWindow();
        this.stage.setIconified(true);
    }

    /**
     * Finaliza la aplicación
     */
    @FXML private void closeApp() {
        Message ms = new ConfirmMessage("¿Seguro que desea salir?");
        ms.showMessage();
        if(((ConfirmMessage) ms).getBt() == ButtonType.OK) {
            Log.infoLogging("Aplicación finalizada.");
            this.stage = (Stage) this.btnClose.getScene().getWindow();
            this.stage.close();
        }
    }
}
