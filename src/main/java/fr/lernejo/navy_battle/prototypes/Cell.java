package fr.lernejo.navy_battle.prototypes;

public enum Cell {
    SUCCESSFUL_FIRE("X"),
    EMPTY("."),
    MISSED_FIRE("-"),
    BOAT("B");

    private final String letter;

    Cell(String letter) {
        this.letter = letter;
    }
    public String getLetter() {
        return letter;
    }
}
