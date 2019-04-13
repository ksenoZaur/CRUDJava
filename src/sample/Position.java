package sample;

import java.util.Objects;

public class Position {
    // Fields
    private String namePosition;
    private String idPosition;

    // Methods
    public Position(String idPosition, String namePosition) {
        this.namePosition = namePosition;
        this.idPosition = idPosition;
    }

    public Position(String namePosition) {
        this.namePosition = namePosition;
    }

    public String getNamePosition() {
        return this.namePosition;
    }

    public String getIdPosition() {
        return idPosition;
    }

    public void setNamePosition(String namePosition) {
        this.namePosition = namePosition;
    }

    public void setIdPosition(String idPosition) {
        this.idPosition = idPosition;
    }

    @Override
    public String toString(){
        return namePosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(idPosition, position.idPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPosition);
    }
}
