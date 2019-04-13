package sample;

import java.util.Objects;

public class Client {
    // Fields
    private String id;
    private String firstname;
    private String lastname;
    private String thirdname;
    private String sex;
    private String phoneNumber;
    private String email;
    private String birthday;

    // Methods
    public Client( String firstname, String lastname,
                  String thirdname, String sex, String phoneNumber,
                  String email, String birthday, String id) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.thirdname = thirdname;
        this.sex = sex;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthday = birthday;
    }

    public Client(String firstname, String lastname, String thirdname,
                  String sex, String phoneNumber, String email,
                  String birthday) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.thirdname = thirdname;
        this.sex = sex;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthday = birthday;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString(){
        return this.lastname + " " + this.firstname + "(" + this.id + ")";
    }
}
