package GameStructures;

import Players.Player;

import java.util.ArrayList;

public class GameLoop {

    private Field field;

    private ArrayList<Player> players;

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

        while (!field.winCheck() && cycleCount < 9) {
            players.get(i).makeTurn();
            System.out.println("Ход игрока " + (i + 1) + ":");
            field.printField();
            i++;
            cycleCount++;
            i = i % 2;
        }

        field.printField();

        if (cycleCount == 9 && !field.winCheck()) {
            System.out.println("Ничья!");
        } else {
            System.out.println("Победил игрок играющий за - " + players.get((i + 1) % 2).getSymbol() + "!");
        }



    }



}
