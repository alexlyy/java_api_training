package fr.lernejo.navy_battle.prototypes;
import java.util.Objects;

public class Coordinates {
    private final int x;
    private final int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Coordinates(String code) {
        if (code.length() < 2 || code.length() > 3 || !code.matches("^[A-J]([1-9]|10)$"))
            throw new RuntimeException("The code " + code + "is invalid!");

        y = Integer.parseInt(code.substring(1)) - 1;
        x = code.charAt(0) - 'A';
    }

    public int Get_Y() {
        return y;
    }
    public int Get_X() {
        return x;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return Character.toString('A' + x) + (y + 1);
    }
}
