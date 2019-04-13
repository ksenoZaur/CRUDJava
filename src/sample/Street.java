package sample;

public class Street {
    // Fields
    private String idStreet;
    private String nameStreet;

    // Methods
    public Street(String nameStreet) {
        this.nameStreet = nameStreet;
    }

    public Street(String idStreet, String nameStreet) {
        this.idStreet = idStreet;
        this.nameStreet = nameStreet;
    }

    public String getIdStreet() {
        return idStreet;
    }

    public void setIdStreet(String idStreet) {
        this.idStreet = idStreet;
    }

    public String getNameStreet() {
        return nameStreet;
    }

    public void setNameStreet(String nameStreet) {
        this.nameStreet = nameStreet;
    }
}
