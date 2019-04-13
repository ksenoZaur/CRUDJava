package sample;

import java.util.Objects;

public class Employee {
    // Fields
    private String id;
    private String firstname;
    private String lastname;
    private String thirdname;
    private String sex;
    private String password;
    private String username;
    private String position;

    private Position positionReal;

// Methods
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Employee(String firstname, String lastname, String thirdname,
                    String sex, String password, String username, String position, String id) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.thirdname = thirdname;
        this.sex = sex;
        this.password = password;
        this.username = username;
        this.position = position;
        this.id = id;
    }

    public Employee(String firstname, String lastname,
                    String thirdname, String sex,
                    String password, String username, String position) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.thirdname = thirdname;
        this.sex = sex;
        this.password = password;
        this.username = username;
        this.position = position;
    }

    public Employee(String firstname, String lastname,
                    String thirdname, String sex,
                    String password, String username, Position position, String id) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.thirdname = thirdname;
        this.sex = sex;
        this.password = password;
        this.username = username;
        this.positionReal = position;
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getThirdname() {
        return thirdname;
    }

    public void setThirdname(String thirdname) {
        this.thirdname = thirdname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPosition() {
        return position;
    }

    public String getPositionReal(){
        if( this.positionReal != null ) {
            return this.positionReal.toString();
        }
        return null;
    }

    public Position getPositionObject(){
        return this.positionReal;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString(){
        return this.lastname + " " + this.firstname + "(" + this.id + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
