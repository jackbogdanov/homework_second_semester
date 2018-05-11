package GameStructures;

import Players.Player;

import java.util.ArrayList;

import static GameStructures.Field.HEIGHT;
import static GameStructures.Field.WIDTH;

public class GameLoop {

    private Field field;

    private ArrayList<Player> players;

    private static final int NUM_OF_OPERATIONS = WIDTH * HEIGHT;

    public GameLoop(Player playerOne, Player playerTwo, Field field) {
        this.field = field;

        players = new ArrayList<>();
        players.add(playerOne);
        players.add(playerTwo);
    }

    public void startGame() {

        int i = 0;
        int cycleCount = 0;
        field.printField();

        while (!field.winCheck() && cycleCount < NUM_OF_OPERATIONS) {
            players.get(i).makeTurn();
            System.out.println("Ход игрока " + (i + 1) + ":");
            field.printField();
            i++;
            cycleCount++;
            i = i % 2;
        }

        field.printField();

        if (cycleCount == NUM_OF_OPERATIONS && !field.winCheck()) {
            System.out.println("Ничья!");
        } else {
            System.out.println("Победил игрок играющий за - " + players.get((i + 1) % 2).getSymbol() + "!");
        }

        field.clearFiled();

    }



}
