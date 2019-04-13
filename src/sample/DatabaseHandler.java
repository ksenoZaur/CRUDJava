package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;

import java.sql.*;

public class DatabaseHandler extends Configs {
    // Fields
    Connection dbConnection;

    // Methods
    // Подключение к БД
    public Connection getDbConnection()
            throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost +
                ":" + dbPort + "/" + dbName +
                "?verifyServerCertificate=false" +
                "&useSSL=false" +
                "&requireSSL=false" +
                "&useLegacyDatetimeCode=false" +
                "&amp" +
                "&serverTimezone=UTC";

        Class.forName("com.mysql.cj.jdbc.Driver");
        this.dbConnection = DriverManager.getConnection(connectionString,
                dbUser, dbPass);
        return this.dbConnection;
    }

    // Регистрация нового пользователя
    public void signUpUser(Employee employee) {
        String insert = "INSERT INTO " + Const.EMLPOYEE_TABLE + "(" +
                Const.FIRSTNAME + "," + Const.LASTNAME + "," + Const.THIRDNAME +
                "," + Const.SEX + "," + Const.ID_POSITION + ")" +
                "VALUES(?,?,?,?,?)";

        String createUser = "{call new_user(?,?)}";

        try {
            // Добавляем запись о работнике
            Connection conn = this.getDbConnection();
            PreparedStatement prSt = conn.prepareStatement(insert);

            prSt.setString(1, employee.getFirstname());
            prSt.setString(2, employee.getLastname());
            prSt.setString(3, employee.getThirdname());
            prSt.setString(4, employee.getSex());
            prSt.setString(5, employee.getPosition());
            prSt.executeUpdate();

            // Создание пользователя БД
            CallableStatement stmt = conn.prepareCall(createUser);
            // Входные параметры - логин и пароль
            stmt.setString(1, employee.getUsername());
            stmt.setString(2, employee.getPassword());
            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                this.dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Возвращает список должностей
    public ObservableList<Position> getPosition() {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.POSITION_TABLE;

        ObservableList<Position> listPosition = FXCollections.observableArrayList();
        try {
            Statement statement = this.getDbConnection().createStatement();
            resSet = statement.executeQuery(select);
            while (resSet.next()) {

                String namePosition = resSet.getString(Const.POSITION_NAME);
                String idPosition = resSet.getString(Const.ID_POSITION);
                listPosition.add(new Position(idPosition, namePosition));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                this.dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return listPosition;
    }

    // Проверка, что пользователь хранится в таблице работников
    public int checkUser(String username, String password) {
        String sql = "{? = call findUser(?,?)}";
        int outputValue = 0;
        try {
            CallableStatement stmt = this.getDbConnection().prepareCall(sql);
            stmt.registerOutParameter(1, Types.INTEGER);
            stmt.setString(2, username);
            stmt.setString(3, password);
            stmt.execute();
            outputValue = stmt.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                this.dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return outputValue;
    }

    public Employee getEmployee(int idUser) {
        Employee result = null;
        Statement statement = null;
        ResultSet resSet = null;

        String select = "SELECT * FROM " +
                Const.EMLPOYEE_TABLE +
                " WHERE " + idUser +
                "=" + Const.ID_EMLPOYEE;

        try {
            statement = this.getDbConnection().createStatement();
            resSet = statement.executeQuery(select);

            if (resSet.next()) {
                String id = resSet.getString(Const.ID_EMLPOYEE);
                String firstname = resSet.getString(Const.FIRSTNAME);
                String lastname = resSet.getString(Const.LASTNAME);
                String thirdname = resSet.getString(Const.THIRDNAME);
                String sex = resSet.getString(Const.SEX);
                String id_position = resSet.getString(Const.ID_POSITION);

                result = new Employee(firstname, lastname, thirdname,
                        sex, lastname, firstname, id_position, id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                this.dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void addNewPosition(Position position) {
        String insert = "INSERT INTO " + this.dbName + "." + Const.POSITION_TABLE + "(" + Const.POSITION_NAME + ")" + " VALUE(?)";
        try {
            PreparedStatement prSt = this.getDbConnection().prepareStatement(insert);
            prSt.setString(1, position.getNamePosition());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                this.dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updatePosition(Position position) {
        String update = "UPDATE " + this.dbName
                + "." + Const.POSITION_TABLE + " SET "
                + Const.POSITION_NAME + " = ? WHERE "
                + Const.ID_POSITION + " = ?";
        try {
            PreparedStatement prSt = this.getDbConnection().prepareStatement(update);

            prSt.setString(1, position.getNamePosition());
            prSt.setString(2, position.getIdPosition());

            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                this.dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void removePosition(Integer idPosition) {
        String remove = "DELETE FROM " + this.dbName
                + "." + Const.POSITION_TABLE + " WHERE "
                + Const.ID_POSITION + " = ?";
        try {
            PreparedStatement prSt = this.getDbConnection().prepareStatement(remove);
            prSt.setInt(1, idPosition);
            prSt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                this.dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ObservableList<Employee> getEmployee() {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.EMLPOYEE_TABLE;

        ObservableList<Employee> listResult = FXCollections.observableArrayList();
        try {

            Statement statement = this.getDbConnection().createStatement();
            resSet = statement.executeQuery(select);
            Position positionReal = null;

            while (resSet.next()) {
                positionReal = null;
                String id = resSet.getString(Const.ID_EMLPOYEE);
                String firstname = resSet.getString(Const.FIRSTNAME);
                String thirdname = resSet.getString(Const.THIRDNAME);
                String lastname = resSet.getString(Const.LASTNAME);
                String sex = resSet.getString(Const.SEX);
                String position = resSet.getString(Const.ID_POSITION);
                // СПОРНЫЙ УЧАСТОК
                if( position != null ) {
                    positionReal = this.getPosition(Integer.valueOf(position));
                }
                Employee employee = new Employee(firstname, lastname, thirdname,
                        sex, lastname, firstname,positionReal, id);
                employee.setPosition( position );
                listResult.add( employee );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                this.dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return listResult;
    }

    public Position getPosition( int id ){
        Position result = null;
        Statement statement = null;
        ResultSet resSet = null;

        String select = "SELECT * FROM " +
                Const.POSITION_TABLE +
                " WHERE " + id +
                "=" + Const.ID_POSITION;

        try {
            statement = this.getDbConnection().createStatement();
            resSet = statement.executeQuery(select);

            if (resSet.next()) {
                String idPosition = resSet.getString(Const.ID_POSITION);
                String namePosition = resSet.getString(Const.POSITION_NAME);

                result = new Position(idPosition, namePosition);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                this.dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public ObservableList<Street> getStreet() {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.STREET_TABLE;

        ObservableList<Street> listEmployee = FXCollections.observableArrayList();
        try {
            Statement statement = this.getDbConnection().createStatement();
            resSet = statement.executeQuery(select);
            while (resSet.next()) {

                String nameStreet = resSet.getString(Const.NAME_STREET);
                String idStreet = resSet.getString(Const.ID_STREET);
                listEmployee.add(new Street(idStreet, nameStreet));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                this.dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listEmployee;
    }

    public void addNewEmployee(Employee employee) {
        String insert = "INSERT INTO " + this.dbName + "."
                + Const.EMLPOYEE_TABLE
                + " (" + Const.FIRSTNAME + ","
                + Const.THIRDNAME + ","
                + Const.LASTNAME + ","
                + Const.SEX + ","
                + Const.ID_POSITION
                + ")" + " VALUES(?,?,?,?,?)";
        try {
            PreparedStatement prSt = this.getDbConnection().prepareStatement(insert);
            prSt.setString(1, employee.getFirstname());
            prSt.setString(2, employee.getThirdname());
            prSt.setString(3, employee.getLastname());
            prSt.setString(4, employee.getSex());
            prSt.setString(5, employee.getPosition());
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                this.dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeEmployee(Integer idEmp) {
        String remove = "DELETE FROM " + this.dbName
                + "." + Const.EMLPOYEE_TABLE + " WHERE "
                + Const.ID_EMLPOYEE + " = ?";
        try {
            PreparedStatement prSt = this.getDbConnection().prepareStatement(remove);
            prSt.setInt(1, idEmp);
            prSt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                this.dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateEmployee(Employee employee) {
        String update = "UPDATE " + this.dbName
                + "." + Const.EMLPOYEE_TABLE + " SET "
                + Const.FIRSTNAME + " = ? ,"
                + Const.THIRDNAME + " = ? ,"
                + Const.LASTNAME + " = ? ,"
                + Const.SEX + " = ? ,"
                + Const.ID_POSITION + " = ?"
                + " WHERE " + Const.ID_EMLPOYEE + " = ?";
        try {
            PreparedStatement prSt = this.getDbConnection().prepareStatement(update);

            prSt.setString(1, employee.getFirstname());
            prSt.setString(2, employee.getThirdname());
            prSt.setString(3, employee.getLastname());
            prSt.setString(4, employee.getSex());
            prSt.setString(5, employee.getPosition());
            prSt.setString(6, employee.getId());

            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                this.dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addNewStreet(Street street) {
        String insert = "INSERT INTO " +
                this.dbName + "." +
                Const.STREET_TABLE + "(" +
                Const.NAME_STREET + ")" +
                " VALUE(?)";
        try {
            PreparedStatement prSt = this.getDbConnection().prepareStatement(insert);
            prSt.setString(1, street.getNameStreet() );
            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                this.dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeStreet(Integer idStreet) {
        String remove = "DELETE FROM " + this.dbName
                + "." + Const.STREET_TABLE + " WHERE "
                + Const.ID_STREET + " = ?";
        try {
            PreparedStatement prSt = this.getDbConnection().prepareStatement(remove);
            prSt.setInt(1, idStreet);
            prSt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                this.dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateStreet(Street street) {
        String update = "UPDATE " + this.dbName
                + "." + Const.STREET_TABLE + " SET "
                + Const.NAME_STREET + " = ? WHERE "
                + Const.ID_STREET + " = ?";
        try {
            PreparedStatement prSt = this.getDbConnection().prepareStatement(update);

            prSt.setString(1, street.getNameStreet());
            prSt.setString(2, street.getIdStreet());

            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                this.dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Клиент

    public ObservableList<Client> getCilent() {
        ResultSet resSet;
        String select = "SELECT * FROM " + Const.CLIENT_TABLE;

        ObservableList<Client> list = FXCollections.observableArrayList();
        try {
            Statement statement = this.getDbConnection().createStatement();
            resSet = statement.executeQuery(select);
            while (resSet.next()) {

                String idClient = resSet.getString(Const.ID_CLIENT);
                String firstnameClient = resSet.getString(Const.FIRSTNAME_CLIENT);
                String thirdnameClient = resSet.getString(Const.THIRDNAME);
                String lastnameClient = resSet.getString(Const.SECONDNAME);
                String sexClient = resSet.getString(Const.SEX);
                String phoneClient = resSet.getString(Const.PHONE_NUMBER);
                String emailClient = resSet.getString(Const.EMAIL);
                String birthdayClient = resSet.getString(Const.BIRTHDAY);

                list.add(new Client(firstnameClient, lastnameClient, thirdnameClient, sexClient,
                        phoneClient, emailClient,birthdayClient, idClient));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                this.dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return list;
    }

    public void addNewClient(Client client) {
        String insert = "INSERT INTO " + this.dbName + "."
                + Const.CLIENT_TABLE
                + " (" + Const.FIRSTNAME_CLIENT + ","
                + Const.THIRDNAME + ","
                + Const.SECONDNAME + ","
                + Const.SEX + ","
                + Const.PHONE_NUMBER + ","
                + Const.EMAIL + ","
                + Const.BIRTHDAY
                + ")" + " VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement prSt = this.getDbConnection().prepareStatement(insert);

            prSt.setString(1, client.getFirstname());
            prSt.setString(2, client.getThirdname());
            prSt.setString(3, client.getLastname());
            prSt.setString(4, client.getSex());
            prSt.setString(5, client.getPhoneNumber());
            prSt.setString(6, client.getEmail());
            prSt.setString(7, client.getBirthday());

            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                this.dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeClient(Integer id) {
        String remove = "DELETE FROM " + this.dbName
                + "." + Const.CLIENT_TABLE + " WHERE "
                + Const.ID_CLIENT + " = ?";
        try {
            PreparedStatement prSt = this.getDbConnection().prepareStatement(remove);
            prSt.setInt(1, id);
            prSt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                this.dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateClient(Client client) {
        String update = "UPDATE " + this.dbName
                + "." + Const.CLIENT_TABLE + " SET "
                + Const.FIRSTNAME_CLIENT + " = ? ,"
                + Const.THIRDNAME+ " = ? ,"
                + Const.SECONDNAME + " = ? ,"
                + Const.SEX + " = ? ,"
                + Const.EMAIL + " = ? ,"
                + Const.BIRTHDAY + " = ? ,"
                + Const.PHONE_NUMBER + " = ?"
                + " WHERE " + Const.ID_CLIENT + " = ?";
        try {
            PreparedStatement prSt = this.getDbConnection().prepareStatement(update);

            prSt.setString(1, client.getFirstname());
            prSt.setString(2, client.getThirdname());
            prSt.setString(3, client.getLastname());
            prSt.setString(4, client.getSex());
            prSt.setString(5, client.getEmail());
            prSt.setString(6, client.getBirthday());
            prSt.setString(7, client.getPhoneNumber());
            prSt.setString(8, client.getId());

            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                this.dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Контракт
    public ObservableList<Contract> getContract() {
        ResultSet resSet;
        String select = "SELECT * FROM " + Const.CONTRACT_TABLE;

        ObservableList<Contract> listResult = FXCollections.observableArrayList();
        try {

            Statement statement = this.getDbConnection().createStatement();
            resSet = statement.executeQuery(select);

            Client client = null;
            Employee employee = null;

            while (resSet.next()) {
                client = null;
                employee = null;

                String id = resSet.getString(Const.ID_CONTRACT);
                String number = resSet.getString(Const.CONTRACT_NUMBER);
                String start = resSet.getString(Const.DATE_START_RENT);
                String end = resSet.getString(Const.DATE_END_RENT);
                String idEmployee = resSet.getString(Const.ID_EMPLOYEE);
                String idClient = resSet.getString(Const.ID_CLIENT_CONTRACT);
                String conclusion = resSet.getString(Const.DATE_OF_CONCLUSION);
                String price = resSet.getString(Const.TOTAL_PRICE);

                // СПОРНЫЙ УЧАСТОК
                if( idClient != null ) {
                    client = this.getClient( Integer.valueOf(idClient ) );
                }
                if( idEmployee != null ) {
                    employee = this.getEmployee( Integer.valueOf( idEmployee ) );
                }

                Contract contract = new Contract(number, conclusion, start, end,
                        client, employee, Double.valueOf( price ), id );

                listResult.add( contract );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                this.dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return listResult;
    }

    private Client getClient(Integer id) {
        Client result = null;
        Statement statement;
        ResultSet resSet;

        String select = "SELECT * FROM " +
                Const.CLIENT_TABLE +
                " WHERE " + id +
                "=" + Const.ID_CLIENT;
        try {
            statement = this.getDbConnection().createStatement();
            resSet = statement.executeQuery(select);

            if (resSet.next()) {
                String idClient = resSet.getString(Const.ID_CLIENT);
                String firstnameClient = resSet.getString(Const.FIRSTNAME_CLIENT);
                String thirdnameClient = resSet.getString(Const.THIRDNAME);
                String lastnameClient = resSet.getString(Const.SECONDNAME);
                String sexClient = resSet.getString(Const.SEX);
                String phoneClient = resSet.getString(Const.PHONE_NUMBER);
                String emailClient = resSet.getString(Const.EMAIL);
                String birthdayClient = resSet.getString(Const.BIRTHDAY);

                result = new Client(firstnameClient, lastnameClient, thirdnameClient, sexClient,
                        phoneClient, emailClient,birthdayClient, idClient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                this.dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void removeContract(Integer id) {
        String remove = "DELETE FROM " + this.dbName
                + "." + Const.CONTRACT_TABLE + " WHERE "
                + Const.ID_CONTRACT + " = ?";
        try {
            PreparedStatement prSt = this.getDbConnection().prepareStatement(remove);
            prSt.setInt(1, id);
            prSt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                this.dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void addNewContract(Contract contract) {
        String insert = "INSERT INTO " + this.dbName + "."
                + Const.CONTRACT_TABLE
                + " (" + Const.CONTRACT_NUMBER + ","
                + Const.DATE_START_RENT+ ","
                + Const.DATE_END_RENT + ","
                + Const.ID_CLIENT_CONTRACT+ ","
                + Const.ID_EMPLOYEE + ","
                + Const.TOTAL_PRICE+ ","
                + Const.DATE_OF_CONCLUSION
                + ")" + " VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement prSt = this.getDbConnection().prepareStatement(insert);

            prSt.setString(1, contract.getNumber());
            prSt.setString(2, contract.getDateStartRent());
            prSt.setString(3, contract.getDateEndRent());
            prSt.setString(4, contract.getClient().getId());
            prSt.setString(5, contract.getEmployee().getId());
            prSt.setDouble(6, contract.getTotalPrice());
            prSt.setString(7, contract.getDateOfConclusion());

            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                this.dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateContract(Contract contract) {
        String update = "UPDATE " + this.dbName
                + "." + Const.CONTRACT_TABLE + " SET "
                + Const.CONTRACT_NUMBER + " = ? ,"
                + Const.DATE_START_RENT+ " = ? ,"
                + Const.DATE_END_RENT + " = ? ,"
                + Const.ID_CLIENT_CONTRACT + " = ? ,"
                + Const.ID_EMPLOYEE + " = ? ,"
                + Const.TOTAL_PRICE + " = ? ,"
                + Const.DATE_OF_CONCLUSION + " = ?"
                + " WHERE " + Const.ID_CONTRACT + " = ?";
        try {
            PreparedStatement prSt = this.getDbConnection().prepareStatement(update);

            prSt.setString(1, contract.getNumber());
            prSt.setString(2, contract.getDateStartRent());
            prSt.setString(3, contract.getDateEndRent());
            prSt.setString(4, contract.getClient().getId());
            prSt.setString(5, contract.getEmployee().getId());
            prSt.setDouble(6, contract.getTotalPrice());
            prSt.setString(7, contract.getDateOfConclusion());
            prSt.setString(8, contract.getId());

            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                this.dbConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

