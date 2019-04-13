package sample;

import java.util.Objects;

public class Contract {
    // Fields
    private String id;
    private String number;
    private String dateOfConclusion;
    private String dateStartRent;
    private String dateEndRent;
//    private String idClient;
//    private String idEmployee;
    private Double totalPrice;

    Client client;
    Employee employee;

    // Methods

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Contract(String number, String dateOfConclusion,
                    String dateStartRent, String dateEndRent,
                    Client client, Employee employee, Double totalPrice, String id) {
        this.id = id;
        this.number = number;
        this.dateOfConclusion = dateOfConclusion;
        this.dateStartRent = dateStartRent;
        this.dateEndRent = dateEndRent;
//        this.idClient = idClient;
//        this.idEmployee = idEmployee;
        this.totalPrice = totalPrice;
        this.client = client;
        this.employee = employee;
    }

    public Contract(String number, String dateOfConclusion,
                    String dateStartRent, String dateEndRent,
                    Client client, Employee employee, Double totalPrice) {
        this.number = number;
        this.dateOfConclusion = dateOfConclusion;
        this.dateStartRent = dateStartRent;
        this.dateEndRent = dateEndRent;
        this.client = client;
        this.employee = employee;
        this.totalPrice = totalPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDateOfConclusion() {
        return dateOfConclusion;
    }

    public void setDateOfConclusion(String dateOfConclusion) {
        this.dateOfConclusion = dateOfConclusion;
    }

    public String getDateStartRent() {
        return dateStartRent;
    }

    public void setDateStartRent(String dateStartRent) {
        this.dateStartRent = dateStartRent;
    }

    public String getDateEndRent() {
        return dateEndRent;
    }

    public void setDateEndRent(String dateEndRent) {
        this.dateEndRent = dateEndRent;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
