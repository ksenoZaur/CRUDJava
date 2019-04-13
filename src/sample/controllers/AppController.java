package sample.controllers;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import sample.*;

public class AppController {
    @FXML
    public TextField newPositionNameField;
    @FXML
    public Button addNewPositionButton;
    @FXML
    public TabPane subMenuTabPane;
    @FXML
    public TextField newNamePositionField;
    @FXML
    public Tab changeTab;
    @FXML
    public Tab removeTab;
    @FXML
    public TextField idDelPositionField;
    @FXML
    public TextField idChangePositionField;
    @FXML
    public TableView<Employee> employeeTable;
    public TextField newEmpFirstname;
    public Button newEmpAddButton;
    public TextField newEmpLastname;
    public TextField newEmpThirdname;

    public ComboBox newEmpPosition;
    public ComboBox newEmpSexCombo;
    @FXML
    public TextField delEmpIDField;
    public Tab removeTabEmpl;
    public Tab changeTabEmpl;
    public Button changeEmpButton;
    public TextField cEmpFNameField;
    public TextField cEmpLNameField;
    public TextField cEmpIdField;
    public TextField cEmpTNameField;
    public ComboBox cEmpPositionCombo;
    public ComboBox cEmpSexCombo;

    @FXML
    private Button closeButton;
    @FXML
    private TableView<Position> positionTable;

    // Улица
    public TextField removeStreetIDField;
    public TextField newStreetNameField;
    public TextField changeStreetNameField;
    public TextField changeStreetIDField;
    public TableView<Street> streetTable;
    public Tab removeStreetTab;
    public Tab changeStreetTab;

    // Клиенты
    public TableView<Client> clientTable;
    public TextField addClientFirstameField;
    public Button addClientButton;
    public TextField addClientLastnameField;
    public TextField addClientThirdnameField;
    public ComboBox addClientSexCombo;
    public TextField addClientEmailField;
    public TextField addClientPhoneField;
    public DatePicker addClientBirthdayDate;
    public ComboBox changeClientSexCombo;
    public Tab removeTabClient;
    public Tab changeTabClient;
    public TextField delClientIDField;
    public TextField changeClientFirstameField;
    public TextField changeClientLastnameField;
    public TextField changeClientID;
    public TextField changeClientThirdnameField;
    public TextField changeClientEmailField;
    public TextField changeClientPhoneField;
    public DatePicker changeClientBirthdayDate;

    // Контракты
    public TableView<Contract> contractTable;
    public Tab changeTabContract;
    public Tab removeTabContract;
    public TextField delClontractIDField;
    public Button changeContractButton;
    public TextField changeContractNumberField;
    public TextField changeContractID;
    public ComboBox changeContractEmployeeCombo;
    public TextField changeContractPriceField;
    public DatePicker changeContractDateOfConc;
    public DatePicker changeContractStartDate;
    public DatePicker changeContractEndDate;
    public ComboBox changeContractClientCombo;
    public ComboBox addContractClientCombo;
    public DatePicker addContractDateOfConc;
    public TextField addContractPriceField;
    public ComboBox addContractEmployeeCombo;
    public DatePicker addContractEndDate;
    public DatePicker addContractStartDate;
    public Button addContractButton;
    public TextField addContractNumberField;


    @FXML
    void closeButtonAction(ActionEvent event) {

    }
    @FXML
    void initialize() {
        // Создание колонок таблицы должностей
        Collection< TableColumn<Position, String>> positionTableColumns = this.makeTablePosition();
        this.positionTable.getColumns().addAll( positionTableColumns );
        // Заполнение таблицы данными
        this.makePositionTable();

        // Создание колонок таблицы сотрудников
        Collection< TableColumn<Employee, String>> employeeTableColumns = this.makeTableEmployee();
        this.employeeTable.getColumns().addAll( employeeTableColumns );
        // Заполнение таблицы данными
//        this.makeEmployeeTable();

        this.addNewPositionButton.setOnAction( event -> {
            if( !this.newPositionNameField.getText().trim().equals("") ){
                DatabaseHandler databaseHandler = new DatabaseHandler();
                Position position = new Position( this.newPositionNameField.getText() );
                databaseHandler.addNewPosition( position );
                this.makePositionTable();
            } else {
                System.out.println("Не все обязательные поля заполнены.");
                Platform.runLater(() -> this.newPositionNameField.requestFocus());
            }
        });

        this.positionTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                System.out.println("Выбрана запись с ключом " + newSelection.getIdPosition());
                if( this.removeTab.isSelected() ){
                    this.idDelPositionField.setText( newSelection.getIdPosition() );
                } else if( this.changeTab.isSelected() ){
                    this.idChangePositionField.setText( newSelection.getIdPosition() );
                    this.newNamePositionField.setText( newSelection.getNamePosition() );
                }
            }
        });

        this.employeeTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                System.out.println("Выбрана запись с ключом " +  newSelection.getId());
                if( this.removeTabEmpl.isSelected() ){
                    this.delEmpIDField.setText( newSelection.getId() );
                } else if( this.changeTabEmpl.isSelected() ){
                    this.cEmpFNameField.setText( newSelection.getFirstname());
                    this.cEmpIdField.setText( newSelection.getId() );
                    this.cEmpTNameField.setText( newSelection.getThirdname());
                    this.cEmpLNameField.setText( newSelection.getLastname());
                    this.cEmpPositionCombo.getSelectionModel().select( newSelection.getPositionObject() );
                    this.cEmpSexCombo.getSelectionModel().select(newSelection.getSex());
                }
            }
        });

        // Создание колонок таблицы улиц
        Collection< TableColumn<Street, String>> streetTableColumns = this.makeTableStreet();
        this.streetTable.getColumns().addAll( streetTableColumns );
        // Заполнение таблицы данными
        this.makeStreetTable();
        // Слушатель выделения строки
        this.streetTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                System.out.println("Выбрана запись с ключом " + newSelection.getIdStreet());
                if( this.removeStreetTab.isSelected() ){

                    this.removeStreetIDField.setText( newSelection.getIdStreet() );

                } else if( this.changeStreetTab.isSelected() ){

                    this.changeStreetIDField.setText( newSelection.getIdStreet() );
                    this.changeStreetNameField.setText( newSelection.getNameStreet() );

                }
            }
        });

        // Клиенты
        Collection< TableColumn<Client, String>> clientTableColumns = this.makeTableClient();
        this.clientTable.getColumns().addAll( clientTableColumns );
        this.makeClientTable();
        this.clientTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                System.out.println("Выбрана запись с ключом " + newSelection.getId());
                if( this.removeTabClient.isSelected() ){

                    this.delClientIDField.setText( newSelection.getId() );

                } else if( this.changeTabClient.isSelected() ){

                    this.changeClientFirstameField.setText( newSelection.getFirstname() );
                    this.changeClientThirdnameField.setText( newSelection.getThirdname() );
                    this.changeClientLastnameField.setText( newSelection.getLastname() );
                    this.changeClientSexCombo.getSelectionModel().select( newSelection.getSex());
                    this.changeClientID.setText( newSelection.getId() );
                    this.changeClientPhoneField.setText( newSelection.getPhoneNumber() );
                    this.changeClientEmailField.setText( newSelection.getEmail());
                    this.changeClientBirthdayDate.getEditor().setText( newSelection.getBirthday() );

                }
            }
        });
        // Окнончание клиентов

        // Контракты
        Collection< TableColumn<Contract, String>> contractTableColumns = this.makeTableContract();
        this.contractTable.getColumns().addAll( contractTableColumns );
        this.makeContractTable();
        this.contractTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                System.out.println("Выбрана запись с ключом " + newSelection.getId());
                if( this.removeTabContract.isSelected() ){

                    this.delClontractIDField.setText( newSelection.getId() );

                } else if( this.changeTabContract.isSelected() ){

                    this.changeContractID.setText( newSelection.getId() );
                    this.changeContractNumberField.setText( newSelection.getNumber() );
                    this.changeContractStartDate.getEditor().setText( newSelection.getDateStartRent() );
                    this.changeContractEndDate.getEditor().setText( newSelection.getDateEndRent());
                    this.changeContractEmployeeCombo.setConverter(
                            new StringConverter() {
                                private Map<String, Object> map = new HashMap<>();

                                @Override
                                public String toString(Object t) {
                                    if (t != null) {
                                        String str = t.toString();
                                        map.put(str, t);
                                        return str;
                                    } else {
                                        return "";
                                    }
                                }

                                @Override
                                public Object fromString(String string) {
                                    if (!map.containsKey(string)) {
                                        changeContractEmployeeCombo.setValue(null);
                                        changeContractEmployeeCombo.getEditor().clear();
                                        return null;
                                    }
                                    return map.get(string);
                                }
                            });
                    this.changeContractEmployeeCombo.getSelectionModel().select( newSelection.getEmployee() );
//                   int index =  this.changeContractEmployeeCombo.getItems().indexOf( newSelection.getEmployee() );
//                    this.changeContractEmployeeCombo.getSelectionModel().select( index );


                    this.changeContractClientCombo.getSelectionModel().select( newSelection.getClient() );
                    this.changeContractPriceField.setText( String.valueOf( newSelection.getTotalPrice()));
                    this.changeContractDateOfConc.getEditor().setText( newSelection.getDateOfConclusion() );

                }
            }
        });

        // Окнончание контрактов

        this.comboBoxInit();
        this.sexComboInit();
        this.employeeComboInit();
        this.clientComboInit();
    }


    // Должности
    public void makePositionTable(){
        // Получаем список должностей
        DatabaseHandler dbHandler = new DatabaseHandler();
        ObservableList<Position> list = dbHandler.getPosition();
        // Помещаем данные в таблицу
        this.positionTable.setItems(list);
    }

    public void changeButtonAction(ActionEvent event) {
        if( !this.newNamePositionField.getText().trim().equals("") &&
                !this.idChangePositionField.getText().trim().equals("")) {
            Position position = new Position(this.idChangePositionField.getText(),
                    this.newNamePositionField.getText() );
            DatabaseHandler databaseHandler = new DatabaseHandler();
            databaseHandler.updatePosition( position );
            this.makePositionTable();
        } else {
            System.out.println("Не все обязательные поля заполнены.");
            Platform.runLater(() -> this.newNamePositionField.requestFocus());
        }
    }

    public void removeButtonAction(ActionEvent event) {
        if( !this.idDelPositionField.getText().trim().equals("") ){
            DatabaseHandler databaseHandler = new DatabaseHandler();
            databaseHandler.removePosition( Integer.valueOf( this.idDelPositionField.getText()) );
            this.makePositionTable();
        } else {
            System.out.println("Не все обязательные поля заполнены.");
            Platform.runLater(() -> this.idDelPositionField.requestFocus());
        }
    }

    public Collection< TableColumn<Position, String>>  makeTablePosition(){

        Collection< TableColumn<Position, String>> listPosition = new ArrayList<>();
        // Создаем колонки таблицы
        TableColumn<Position, String> idPositionColumn = new TableColumn<>( "ID Должности" );
        TableColumn<Position, String> namePositionColumn = new TableColumn<>( "Название должности" );

        listPosition.add( idPositionColumn );
        listPosition.add( namePositionColumn );

        idPositionColumn.setCellValueFactory(new PropertyValueFactory<Position, String>("idPosition"));
        namePositionColumn.setCellValueFactory(new PropertyValueFactory<Position, String>("namePosition"));

        return listPosition;
    }

    // Работники
    public void makeEmployeeTable(){
        // Получаем список должностей
        DatabaseHandler dbHandler = new DatabaseHandler();
        ObservableList<Employee> list = dbHandler.getEmployee();
        // Помещаем данные в таблицу
        this.employeeTable.setItems( list );
    }

    public void addNewEmpButtonAction(ActionEvent event) {
        if( !this.newEmpFirstname.getText().trim().equals("") ){
            DatabaseHandler databaseHandler = new DatabaseHandler();

            String idPosition = null;
            String firstname = this.newEmpFirstname.getText();
            String thirdname = null;
            String lastname = null;
            String sex = null;

            Position position = ((Position) this.newEmpPosition.getValue());
            if( position != null ){
                idPosition = position.getIdPosition();
            }
            if( !this.newEmpThirdname.getText().trim().equals("")){
                thirdname = this.newEmpThirdname.getText();
            }
            if( !this.newEmpLastname.getText().trim().equals("")){
                lastname = this.newEmpLastname.getText();
            }
            if( !this.newEmpSexCombo.getValue().toString().equals("")){
                sex = this.newEmpSexCombo.getValue().toString();
            }

            Employee employee = new Employee( firstname, lastname, thirdname,
                    sex, lastname, firstname, idPosition);

            databaseHandler.addNewEmployee( employee );
            this.makeEmployeeTable();
        } else {
            System.out.println("Не все обязательные поля заполнены.");
            Platform.runLater(() -> this.newEmpFirstname.requestFocus());
        }
    }

    public void removeEmpAction(ActionEvent event) {
        if( !this.delEmpIDField.getText().trim().equals("") ){
            DatabaseHandler databaseHandler = new DatabaseHandler();
            databaseHandler.removeEmployee( Integer.valueOf( this.delEmpIDField.getText()) );
            this.makeEmployeeTable();
        } else {
            System.out.println("Не все обязательные поля заполнены.");
            Platform.runLater(() -> this.delEmpIDField.requestFocus());
        }
    }

    public void cEmpButtonAction(ActionEvent event) {
        if( !this.cEmpFNameField.getText().trim().equals("") &&
                !this.cEmpIdField.getText().trim().equals("")) {

            String idPosition = null;
            String firstname = this.cEmpFNameField.getText();
            String thirdname = null;
            String lastname = null;
            String id = this.cEmpIdField.getText();
            String sex = null;

            Position position = ((Position) this.cEmpPositionCombo.getValue());
            if( position != null ){
                idPosition = position.getIdPosition();
            }
            if( !this.cEmpTNameField.getText().trim().equals("")){
                thirdname = this.cEmpTNameField.getText();
            }
            if( !this.cEmpLNameField.getText().trim().equals("")){
                lastname = this.cEmpLNameField.getText();
            }
            if( !this.cEmpSexCombo.getValue().toString().equals("")){
                sex = this.cEmpSexCombo.getValue().toString();
            }

            Employee employee = new Employee( firstname, lastname, thirdname,
                    sex, lastname, firstname, idPosition, id);

            DatabaseHandler databaseHandler = new DatabaseHandler();
            databaseHandler.updateEmployee( employee );
            this.makeEmployeeTable();
            this.employeeTable.refresh();
            System.out.println("Данные обновлены.");
        } else {
            System.out.println("Не все обязательные поля заполнены.");
            Platform.runLater(() -> this.cEmpFNameField.requestFocus());
        }
    }

    private void comboBoxInit() {
        // Получаем список должностей из БД
        DatabaseHandler dbHandler = new DatabaseHandler();
        ObservableList<Position> list = dbHandler.getPosition();
        // Заполняем выпадающий список
        this.newEmpPosition.setItems(list);
        this.newEmpPosition.getSelectionModel().select(0);

        this.cEmpPositionCombo.setItems(list);
        this.cEmpPositionCombo.getSelectionModel().select(0);

    }

    private void sexComboInit(){
        this.newEmpSexCombo.getItems().setAll("M","W");
        this.newEmpSexCombo.getSelectionModel().select(0);

        this.cEmpSexCombo.getItems().setAll("M","W");
        this.cEmpSexCombo.getSelectionModel().select(0);

        this.addClientSexCombo.getItems().setAll("M","W");
        this.addClientSexCombo.getSelectionModel().select(0);

        this.changeClientSexCombo.getItems().setAll("M","W");
        this.changeClientSexCombo.getSelectionModel().select(0);
    }

    private void employeeComboInit() {
        // Получаем список должностей из БД
        DatabaseHandler dbHandler = new DatabaseHandler();
        ObservableList<Employee> list = dbHandler.getEmployee();

        // Заполняем выпадающий список
        this.changeContractEmployeeCombo.setItems(list);
        this.changeContractEmployeeCombo.getSelectionModel().select(0);

        // Заполняем выпадающий список
        this.addContractEmployeeCombo.setItems(list);
        this.addContractEmployeeCombo.getSelectionModel().select(0);

        this.employeeTable.setItems( list );
    }

    private void clientComboInit() {
        // Получаем список должностей из БД
        DatabaseHandler dbHandler = new DatabaseHandler();
        ObservableList<Client> list = dbHandler.getCilent();

        // Заполняем выпадающий список
        this.changeContractClientCombo.setItems(list);
        this.changeContractClientCombo.getSelectionModel().select(0);

        // Заполняем выпадающий список
        this.addContractClientCombo.setItems(list);
        this.addContractClientCombo.getSelectionModel().select(0);
    }


    private Collection<TableColumn<Employee, String>> makeTableEmployee() {

        Collection< TableColumn<Employee, String>> listEmp = new ArrayList<>();
        // Создаем колонки таблицы
        TableColumn<Employee, String> idColumn = new TableColumn<>( "ID Работника" );
        TableColumn<Employee, String> firstnameColumn = new TableColumn<>( "Имя" );
        TableColumn<Employee, String> thirdnameColumn = new TableColumn<>( "Отчество" );
        TableColumn<Employee, String> lastnameColumn = new TableColumn<>( "Фамилия" );
        TableColumn<Employee, String> sexColumn = new TableColumn<>( "Пол" );
        TableColumn<Employee, String> positionColumn = new TableColumn<>( "Должность" );

        listEmp.add( idColumn );
        listEmp.add( firstnameColumn );
        listEmp.add( thirdnameColumn );
        listEmp.add( lastnameColumn );
        listEmp.add( sexColumn );
        listEmp.add( positionColumn );

        idColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("id"));
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("firstname"));
        thirdnameColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("thirdname"));
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("lastname"));
        sexColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("sex"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("positionReal"));

        return listEmp;
    }

   // События кнопок вкладки "Улицы"

    private Collection<TableColumn<Street, String>> makeTableStreet() {
        Collection< TableColumn<Street, String>> listPosition = new ArrayList<>();
        // Создаем колонки таблицы
        TableColumn<Street, String> idPositionColumn = new TableColumn<>( "ID Улицы" );
        TableColumn<Street, String> namePositionColumn = new TableColumn<>( "Название улицы" );

        listPosition.add( idPositionColumn );
        listPosition.add( namePositionColumn );

        idPositionColumn.setCellValueFactory(new PropertyValueFactory<Street, String>("idStreet"));
        namePositionColumn.setCellValueFactory(new PropertyValueFactory<Street, String>("nameStreet"));

        return listPosition;
    }

    private void makeStreetTable() {
        // Получаем список должностей
        DatabaseHandler dbHandler = new DatabaseHandler();
        ObservableList<Street> list = dbHandler.getStreet();
        // Помещаем данные в таблицу
        this.streetTable.setItems(list);
    }

    public void addStreetAction(ActionEvent event) {
        if( !this.newStreetNameField.getText().trim().equals("") ){
            DatabaseHandler databaseHandler = new DatabaseHandler();
            Street street = new Street( this.newStreetNameField.getText() );
            databaseHandler.addNewStreet( street );
            this.makeStreetTable();
        } else {
            System.out.println("Не все обязательные поля заполнены.");
            Platform.runLater(() -> this.newStreetNameField.requestFocus());
        }
    }

    public void changeStreetAction(ActionEvent event) {
        if( !this.changeStreetNameField.getText().trim().equals("") &&
                !this.changeStreetIDField.getText().trim().equals("")) {
            Street street = new Street(this.changeStreetIDField.getText(),
                    this.changeStreetNameField.getText() );
            DatabaseHandler databaseHandler = new DatabaseHandler();
            databaseHandler.updateStreet( street );
            this.makeStreetTable();
        } else {
            System.out.println("Не все обязательные поля заполнены.");
            Platform.runLater(() -> this.changeStreetNameField.requestFocus());
        }
    }

    public void removeStreetAction(ActionEvent event) {
        if( !this.removeStreetIDField.getText().trim().equals("") ){
            DatabaseHandler databaseHandler = new DatabaseHandler();
            databaseHandler.removeStreet( Integer.valueOf( this.removeStreetIDField.getText()) );
            this.makeStreetTable();
        } else {
            System.out.println("Не все обязательные поля заполнены.");
            Platform.runLater(() -> this.removeStreetIDField.requestFocus());
        }
    }

    // Клиенты
    public void makeClientTable(){

        DatabaseHandler dbHandler = new DatabaseHandler();
        ObservableList<Client> list = dbHandler.getCilent();
        this.clientTable.setItems(list);
    }

    private Collection<TableColumn<Client, String>> makeTableClient() {
        Collection< TableColumn<Client, String>> list = new ArrayList<>();
        // Создаем колонки таблицы
        TableColumn<Client, String> idClientColumn = new TableColumn<>( "ID Клиента" );
        TableColumn<Client, String> nameClientColumn = new TableColumn<>( "Имя" );
        TableColumn<Client, String> lastnameClientColumn = new TableColumn<>( "Фамилия" );
        TableColumn<Client, String> thirdnameClientColumn = new TableColumn<>( "Отчество" );
        TableColumn<Client, String> sexClientColumn = new TableColumn<>( "Пол" );
        TableColumn<Client, String> phoneClientColumn = new TableColumn<>( "Номер телефона" );
        TableColumn<Client, String> emailClientColumn = new TableColumn<>( "Email" );
        TableColumn<Client, String> birthdayClientColumn = new TableColumn<>( "Дата рождения" );

        list.add( idClientColumn );
        list.add( nameClientColumn );
        list.add( lastnameClientColumn );
        list.add( thirdnameClientColumn );
        list.add( sexClientColumn );
        list.add( phoneClientColumn );
        list.add( emailClientColumn );
        list.add( birthdayClientColumn );

        idClientColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("id"));
        nameClientColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("firstname"));
        lastnameClientColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("lastname"));
        thirdnameClientColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("thirdname"));
        sexClientColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("sex"));
        phoneClientColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("phoneNumber"));
        emailClientColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("email"));
        birthdayClientColumn.setCellValueFactory(new PropertyValueFactory<Client, String>("birthday"));

        return list;
    }

    public void addClientAction(ActionEvent event) {
        if( !this.addClientFirstameField.getText().trim().equals("") &&
                !this.addClientPhoneField.getText().trim().equals("") ){

            DatabaseHandler databaseHandler = new DatabaseHandler();

            String firstname = this.addClientFirstameField.getText();
            String thirdname = null;
            String lastname = null;
            String sex = null;
            String phone = null;
            String email = null;
            String birthday = null;

            if( !this.addClientPhoneField.getText().trim().equals("") ){
                phone = this.addClientPhoneField.getText();
            }

            if( !this.addClientThirdnameField.getText().trim().equals("")){
                thirdname = this.addClientThirdnameField.getText();
            }

            if( !this.addClientLastnameField.getText().trim().equals("")){
                lastname = this.addClientLastnameField.getText();
            }

            if( !this.addClientPhoneField.getText().trim().equals("")){
                phone = this.addClientPhoneField.getText();
            }

            if( !this.addClientEmailField.getText().trim().equals("")){
                email = this.addClientEmailField.getText();
            }

            if( !this.addClientBirthdayDate.getValue().toString().equals("")){
                birthday = this.addClientBirthdayDate.getValue().toString();
            }

            if( !this.addClientSexCombo.getValue().toString().equals("")){
                sex = this.addClientSexCombo.getValue().toString();
            }

            Client client = new Client( firstname, lastname, thirdname,
                    sex, phone, email, birthday);

            databaseHandler.addNewClient( client );
            this.makeClientTable();
        } else {
            System.out.println("Не все обязательные поля заполнены.");
        }
    }

    public void changeClientAction(ActionEvent event) {
        if( !this.changeClientFirstameField.getText().trim().equals("") &&
                !this.changeClientID.getText().trim().equals("") &&
            !this.changeClientPhoneField.getText().trim().equals("")) {

            String firstname = this.changeClientFirstameField.getText();
            String thirdname = null;
            String lastname = null;
            String sex = null;
            String phone = this.changeClientPhoneField.getText();
            String email = null;
            String birthday = null;
            String id = this.changeClientID.getText();

            if( !this.changeClientThirdnameField.getText().trim().equals("")){
                thirdname = this.changeClientThirdnameField.getText();
            }

            if( !this.changeClientLastnameField.getText().trim().equals("")){
                lastname = this.changeClientLastnameField.getText();
            }

            if( !this.changeClientEmailField.getText().trim().equals("")){
                email = this.changeClientEmailField.getText();
            }

            if( !this.changeClientBirthdayDate.getValue().toString().equals("")){
                birthday = this.changeClientBirthdayDate.getValue().toString();
            }

            if( !this.changeClientSexCombo.getValue().toString().equals("")){
                sex = this.changeClientSexCombo.getValue().toString();
            }

            Client client = new Client( firstname, lastname, thirdname,
                    sex, phone, email, birthday, id);

            DatabaseHandler databaseHandler = new DatabaseHandler();
            databaseHandler.updateClient( client );
            this.makeClientTable();
        } else {
            System.out.println("Не все обязательные поля заполнены.");
        }
    }

    public void removeClientAction(ActionEvent event) {
        if( !this.delClientIDField.getText().trim().equals("") ){
            DatabaseHandler databaseHandler = new DatabaseHandler();
            databaseHandler.removeClient( Integer.valueOf( this.delClientIDField.getText()) );
            this.makeClientTable();
        } else {
            System.out.println("Не все обязательные поля заполнены.");
            Platform.runLater(() -> this.delClientIDField.requestFocus());
        }
    }

    public void changeCLientTab(Event event) {
        Platform.runLater(() -> {
            clientTable.getSelectionModel().clearSelection();
        });
    }

    // Контракты
    private void makeContractTable() {

        DatabaseHandler dbHandler = new DatabaseHandler();
        ObservableList<Contract> list = dbHandler.getContract();
        this.contractTable.setItems(list);

    }

    private Collection<TableColumn<Contract, String>> makeTableContract() {
        Collection< TableColumn<Contract, String>> list = new ArrayList<>();
        // Создаем колонки таблицы
        TableColumn<Contract, String> idContractColumn = new TableColumn<>( "ID Контракта" );
        TableColumn<Contract, String> numberContractColumn = new TableColumn<>( "Номер" );
        TableColumn<Contract, String> dateStartContractColumn = new TableColumn<>( "Дата начала аренды" );
        TableColumn<Contract, String> dateEndContractColumn = new TableColumn<>( "Дата окончания аренды" );
        TableColumn<Contract, String> employeeContractColumn = new TableColumn<>( "Заключено сотрудником" );
        TableColumn<Contract, String> clientContractColumn = new TableColumn<>( "Заключено с клиентом" );
        TableColumn<Contract, String> priceContractColumn = new TableColumn<>( "Общая стоимость" );
        TableColumn<Contract, String> dateConclusionContractColumn = new TableColumn<>( "Дата заключения" );

        list.add( idContractColumn );
        list.add( numberContractColumn );
        list.add( dateStartContractColumn );
        list.add( dateEndContractColumn );
        list.add( employeeContractColumn );
        list.add( clientContractColumn );
        list.add( priceContractColumn );
        list.add( dateConclusionContractColumn );

        idContractColumn.setCellValueFactory(new PropertyValueFactory<Contract, String>("id"));
        numberContractColumn.setCellValueFactory(new PropertyValueFactory<Contract, String>("number"));
        dateStartContractColumn.setCellValueFactory(new PropertyValueFactory<Contract, String>("dateStartRent"));
        dateEndContractColumn.setCellValueFactory(new PropertyValueFactory<Contract, String>("dateEndRent"));
        employeeContractColumn.setCellValueFactory(new PropertyValueFactory<Contract, String>("employee"));
        clientContractColumn.setCellValueFactory(new PropertyValueFactory<Contract, String>("client"));
        priceContractColumn.setCellValueFactory(new PropertyValueFactory<Contract, String>("totalPrice"));
        dateConclusionContractColumn.setCellValueFactory(new PropertyValueFactory<Contract, String>("dateOfConclusion"));

        return list;
    }

    public void changeContractTab(Event event) {
        Platform.runLater(() -> {
            contractTable.getSelectionModel().clearSelection();
        });
    }

    public void removeContrctAction(ActionEvent event) {
        if( !this.delClontractIDField.getText().trim().equals("") ){
            DatabaseHandler databaseHandler = new DatabaseHandler();
            databaseHandler.removeContract( Integer.valueOf( this.delClontractIDField.getText()) );
            this.makeContractTable();
        } else {
            System.out.println("Не все обязательные поля заполнены.");
            Platform.runLater(() -> this.delClontractIDField.requestFocus());
        }

    }

    public void changeContrctAction(ActionEvent event) {
        if( !this.changeContractNumberField.getText().trim().equals("") &&
                !this.changeContractStartDate.getValue().toString().trim().equals("") &&
                !this.changeContractEndDate.getValue().toString().trim().equals("") &&
                !this.changeContractDateOfConc.getValue().toString().trim().equals("") &&
                !this.changeContractID.getText().trim().equals("")){

            String id = this.changeContractID.getText();

            String number = this.changeContractNumberField.getText();
            String start = this.changeContractStartDate.getValue().toString();
            String end = this.changeContractEndDate.getValue().toString();
            String conclusion = this.changeContractDateOfConc.getValue().toString();
            String price = String.valueOf( 0 );

            Employee employee = null;
            Client client = null;

            if( !this.changeContractPriceField.getText().trim().equals("") ){
                price = this.changeContractPriceField.getText();
            }

            if( !this.changeContractEmployeeCombo.getValue().toString().equals("")){
                employee = (Employee) this.changeContractEmployeeCombo.getValue();
            }

            if( !this.changeContractClientCombo.getValue().toString().equals("")){
                client = (Client) this.changeContractClientCombo.getValue();
            }

            Contract contract = new Contract(number, conclusion,
                    start, end, client, employee,
                    Double.valueOf( price ), id);

            DatabaseHandler databaseHandler = new DatabaseHandler();
            databaseHandler.updateContract( contract );
            this.makeContractTable();
        } else {
            System.out.println("Не все обязательные поля заполнены.");
        }
    }

    public void addContrctAction(ActionEvent event) {
        if( !this.addContractNumberField.getText().trim().equals("") &&
                !this.addContractStartDate.getValue().toString().trim().equals("") &&
                !this.addContractEndDate.getValue().toString().trim().equals("") &&
                !this.addContractDateOfConc.getValue().toString().trim().equals("")){

            DatabaseHandler databaseHandler = new DatabaseHandler();

            String number = this.addContractNumberField.getText();
            String start = this.addContractStartDate.getValue().toString();
            String end = this.addContractEndDate.getValue().toString();
            String price = String.valueOf( 0 );
            String conclusion = this.addContractDateOfConc.getValue().toString();

            Employee employee = null;
            Client client = null;

            if( !this.addContractPriceField.getText().trim().equals("") ){
                price = this.addContractPriceField.getText();
            }

            if( !this.addContractEmployeeCombo.getValue().toString().equals("")){
                employee = (Employee) this.addContractEmployeeCombo.getValue();
            }

            if( !this.addContractClientCombo.getValue().toString().equals("")){
                client = (Client) this.addContractClientCombo.getValue();
            }

            Contract contract = new Contract(number, conclusion,
                    start, end, client, employee, Double.valueOf( price ));

            databaseHandler.addNewContract( contract );
            this.makeContractTable();
        } else {
            System.out.println("Не все обязательные поля заполнены.");
        }
    }
}



