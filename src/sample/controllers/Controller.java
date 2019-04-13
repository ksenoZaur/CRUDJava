package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import sample.DatabaseHandler;
import sample.Employee;

import java.io.IOException;

public class Controller {
    // Fields
    @FXML
    public Button authButton;
    public Button loginSignUpButton;
    public TextField loginField;
    public PasswordField passwordField;

    DatabaseHandler databaseHandler;
    String streetName;

    static Employee user;
    // Methods
    public Controller(){

    }

    @FXML
    public void initialize(){
        // Вызов окна регистрации
        this.loginSignUpButton.setOnAction( event -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/views/signUp.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene( new Scene( root ));
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.showAndWait();
        });

        //Вход
        this.authButton.setOnAction( event -> {
            String loginText = this.loginField.getText().trim();
            String passwordText = this.passwordField.getText().trim();

            if( !loginText.equals("") && !passwordText.equals("")){
                this.loginUser( loginText, passwordText);
            } else {
                System.out.println("Имеются незаполненные поля!");
            }
        });
    }

    // Метод входа пользователя
    private void loginUser(String loginText, String passwordText) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        int idUser;
        if( (idUser = dbHandler.checkUser(loginText, passwordText)) > 0){
            System.out.println("Вход произведен.");
            user =  dbHandler.getEmployee( idUser);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/sample/views/app.fxml"));
            try {
                fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = fxmlLoader.getRoot();
            Stage stage = new Stage();
            stage.setHeight( 600 );
            stage.setWidth( 800 );
            stage.setMinHeight(600);
            stage.setMinWidth(800);
            stage.setScene( new Scene( root ));
            stage.showAndWait();
        } else {
            System.out.println("Вход не выполнен.");
        }
    }

    // Закрыть текущее окно
    public void closeButtonAction(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Window theStage = source.getScene().getWindow();
        ((Stage)theStage).close();
    }

    public void enter(KeyEvent keyEvent) {
        KeyCode key = keyEvent.getCode();
        if (key == KeyCode.ENTER) {
            String loginText = this.loginField.getText().trim();
            String passwordText = this.passwordField.getText().trim();

            if( !loginText.equals("") && !passwordText.equals("")){
                this.loginUser( loginText, passwordText);
            } else {
                System.out.println("Имеются незаполненные поля!");
            }
        }
    }
}
