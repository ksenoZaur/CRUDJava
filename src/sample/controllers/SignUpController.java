package sample.controllers;

import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.DatabaseHandler;
import sample.Employee;
import sample.Position;

public class SignUpController {
    // Fields
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TextField signUpLastname;
    @FXML
    private TextField signUpFirstname;
    @FXML
    private Button signUpCloseButton;
    @FXML
    private Button signUpButton;
    @FXML
    private CheckBox signUpCheckBoxWoman;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField signUpThirdname;
    @FXML
    private CheckBox signUpCheckBoxMan;
    @FXML
    private ComboBox signUpPosition;
    @FXML
    private TextField loginField;

    // Methods

    // Закрыть текущее окно
    @FXML
    void closeButtonAction(ActionEvent event) {
        Node source = (Node) event.getSource();
        Window theStage = source.getScene().getWindow();
        ((Stage)theStage).close();
    }

    @FXML
    void initialize() {
        // Инициализация выпадающего списка должностей
        this.comboBoxInit();
        this.signUpButton.setOnAction(this::signUpNewUser);
    }

    private void comboBoxInit() {
        // Получаем список должностей из БД
        DatabaseHandler dbHandler = new DatabaseHandler();
        ObservableList<Position> list = dbHandler.getPosition();
        // Заполняем выпадающий список
        this.signUpPosition.setItems(list);
    }

    // Регистрация нового пользователя
    private void signUpNewUser( ActionEvent event ) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        String firstname = this.signUpFirstname.getText();
        String lastname =  this.signUpLastname.getText();
        String thirdname = this.signUpThirdname.getText();
        String password = this.passwordField.getText();
        String username = this.loginField.getText();
        String position = "";
        String sex = "";

        if( this.signUpCheckBoxMan.isSelected() ){
            sex = "M";
        } else {
            sex = "W";
        }

        Position currentPosition = (Position)this.signUpPosition.getSelectionModel().getSelectedItem();

        if( currentPosition == null ) {
            position = "";
        } else {
            position = currentPosition.getIdPosition();
        }

        Employee employee = new Employee( firstname, lastname,
                thirdname, sex, password, username, position);

        if( employee.getFirstname().trim().equals("") ){
            System.out.println("Не указано имя");
            Platform.runLater(() -> this.signUpFirstname.requestFocus());
        } else if( employee.getPassword().trim().equals("") ){
            System.out.println("Не указано пароль");
            Platform.runLater(() -> this.passwordField.requestFocus());
        } else if( employee.getUsername().trim().equals("") ){
            System.out.println("Не указан логин");
            Platform.runLater(() -> this.loginField.requestFocus());
        } else {
            // Добавляем пользователя
            dbHandler.signUpUser(employee);
            // Закрываем окно регистрации
            Node source = (Node) event.getSource();
            Window theStage = source.getScene().getWindow();
            ((Stage)theStage).close();
        }
    }

    // Обработка нажатий на чекбоксы выбора пола
    public void checkBoxManAction(ActionEvent actionEvent) {
        this.signUpCheckBoxMan.setSelected( true );
        this.signUpCheckBoxWoman.setSelected( false );
    }

    public void checkBoxWomanAction(ActionEvent actionEvent) {
        this.signUpCheckBoxWoman.setSelected( true );
        this.signUpCheckBoxMan.setSelected( false );
    }
}
