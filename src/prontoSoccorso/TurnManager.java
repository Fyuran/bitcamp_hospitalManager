package prontoSoccorso;

import java.util.ArrayList;
import java.util.List;

public class TurnManager {
    private List<Turn> turns;

    public TurnManager() {
        this.turns = new ArrayList<>();
    }

    public void addTurn(Turn turn) {
    	
        turns.add(turn);
    }

    public void removeTurn(int index) {
        if (index >= 0 && index < turns.size()) {
            turns.remove(index);
        }
    }

    public void removeTurnById(int turnId) {
        turns.removeIf(turn -> turn.getId() == turnId);
    }

    public List<Turn> getTurns() {
        return turns;
    }

    public void editTurn(int index, Turn newTurn) {
        if (index >= 0 && index < turns.size()) {
            turns.set(index, newTurn);
        }
    }

    public void printTurns() {
        for (Turn turn : turns) {
            System.out.println(turn);
        }
    }
}