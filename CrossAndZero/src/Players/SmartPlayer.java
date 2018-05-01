package Players;

import Exceptions.IncorrectSymbolsException;
import Exceptions.OutOfFiledException;
import GameStructures.AdvancedField;
import GameStructures.Field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SmartPlayer implements Player {

    protected class GoingCoords {

        int meetingsCounter;
        int x;
        int y;

        GoingCoords(int x, int y) {
            this.x = x;
            this.y = y;

            meetingsCounter = 0;
        }

        @Override
        public String toString() {
            return x + " " + y + " " + meetingsCounter;
        }
    }

    private final int BOT_SYMBOL = 0;
    private final int ENEMY_SYMBOL = 1;

    private int difficultyLevel;
    private char[] symbols;
    private GoingCoords goingCoords;
    private Field field;
    private AdvancedField copyOfField;
    private HashMap<Integer, ArrayList<GoingCoords>> winGames;

    public SmartPlayer(Field field, int difficulty) {
        difficultyLevel = difficulty;
        this.field = field;
        this.symbols = new char[]{'X', 'O'};

        goingCoords = new GoingCoords(0, 0);
        copyOfField = new AdvancedField();

    }

    @Override
    public void makeTurn() {
        preparingForTurn();

        GoingCoords highPriorityMovingCoordinates = null;


        if (difficultyLevel > 3) {
            highPriorityMovingCoordinates = enemyWinCheck();
        } else {
            if ((difficultyLevel * Math.random()) > 1.2) {
                highPriorityMovingCoordinates = enemyWinCheck();
            }
        }

        findingOptimum(copyOfField, 0);

        GoingCoords movingCoordinates = findBestMoving();

        try {
            if (highPriorityMovingCoordinates != null) {
                if (winGames.get(0).size() != 0) {
                    field.addSymbol(symbols[BOT_SYMBOL], movingCoordinates.x, movingCoordinates.y);

                } else {
                    field.addSymbol(symbols[BOT_SYMBOL], highPriorityMovingCoordinates.x, highPriorityMovingCoordinates.y);
                }
            } else {
                if (movingCoordinates != null) {
                    field.addSymbol(symbols[BOT_SYMBOL], movingCoordinates.x, movingCoordinates.y);
                } else {
                    while (true) {
                        int x = (int) (3*Math.random());
                        int y = (int) (3*Math.random());


                        if (field.getCell(x, y) == ' ') {
                            field.addSymbol(symbols[BOT_SYMBOL],x, y);
                            break;
                        }

                    }
                }
            }
        }catch (OutOfFiledException e) {e.printStackTrace();}

    }

    private char findingOptimum(AdvancedField field, int deep) {

        if (deep != difficultyLevel) {

            if (deep % 2 == 0) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        try {
                            if (field.getCell(i, j) == ' ') {

                                if (deep == 0) {
                                    goingCoords.x = i;
                                    goingCoords.y = j;
                                }

                                field.addSymbol(symbols[deep % 2], i, j);
                                char ch = field.advancedWinCheck();

                                if (ch != ' ') {
                                    if (ch == symbols[BOT_SYMBOL]) {
                                        ArrayList<GoingCoords> coords = winGames.get(deep);

                                        GoingCoords foundedCoords = findCoordsInList(coords, goingCoords);
                                        if(foundedCoords == null){
                                            coords.add(new GoingCoords(goingCoords.x, goingCoords.y));
                                        } else {
                                            foundedCoords.meetingsCounter++;
                                        }
                                    }
                                    field.removeCell(i ,j);
                                    return ch;

                                } else {
                                    findingOptimum(field, deep + 1);

                                    field.removeCell(i, j);
                                }


                            }

                        } catch (OutOfFiledException e) {
                            e.printStackTrace();
                        }
                    }
                }

            } else {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        try {
                            if (field.getCell(i, j) == ' '){
                                field.addSymbol(symbols[ENEMY_SYMBOL], i, j);

                                char secondWinCheck = field.advancedWinCheck();
                                if (secondWinCheck == symbols[ENEMY_SYMBOL]) {
                                    field.removeCell(i, j);
                                    return ' ';
                                }

                                char ch = findingOptimum(field, deep + 1);

                                if (ch != ' ') {
                                    field.removeCell(i, j);
                                    return ' ';
                                }

                                field.removeCell(i, j);
                            }

                        } catch (OutOfFiledException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }


        } else {
            return ' ';
        }


        return ' ';

    }

    private GoingCoords findCoordsInList(ArrayList<GoingCoords> list, GoingCoords coords) {
        for (GoingCoords g : list) {
            if (g.x == coords.x && g.y == coords.y) {
                return g;
            }
        }

        return null;
    }

    private void printWinGames() {
        for (Map.Entry<Integer, ArrayList<GoingCoords>> entry: winGames.entrySet()){
            System.out.println("Deep - " + entry.getKey() + ":");
            for (GoingCoords coords : entry.getValue()) {
                System.out.println(coords);
            }
        }
    }

    private GoingCoords findBestMoving() {
        int max = -1;
        GoingCoords bestMoving = null;

        for (Map.Entry<Integer, ArrayList<GoingCoords>> entry: winGames.entrySet()){
            ArrayList<GoingCoords> coordsArrayList = entry.getValue();
            if (coordsArrayList.size() != 0) {
                for (GoingCoords coords : coordsArrayList) {
                    int num = coords.meetingsCounter;
                    if (max < num) {
                        max = num;
                        bestMoving = coords;
                    }
                }

                return bestMoving;
            }
        }


        //System.out.println("RETURN NULL!!");
        return  bestMoving;
    }

    @Override
    public char getSymbol() {
        return symbols[BOT_SYMBOL];
    }

    private GoingCoords enemyWinCheck() {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                try {
                    if (copyOfField.getCell(i, j) == ' ') {
                        copyOfField.addSymbol(symbols[ENEMY_SYMBOL], i, j);
                        if (copyOfField.advancedWinCheck() == symbols[ENEMY_SYMBOL]) {
                            copyOfField.removeCell(i, j);
                            return new GoingCoords(i, j);
                        }

                        copyOfField.removeCell(i, j);
                    }


                } catch (OutOfFiledException e) {}
            }
        }

        return null;
    }

    private void initWinGames() {
        winGames = new HashMap<>();

        for (int i = 0; i < difficultyLevel; i += 2) {
            winGames.put(i, new ArrayList<>());
        }
    }

    private void preparingForTurn() {
        copyOfField.fillByField(field);
        initWinGames();
    }

    public void setSymbols(char[] symbols) throws IncorrectSymbolsException {
        if (symbols.length == 2) {
            this.symbols = symbols;
        } else {
            throw new IncorrectSymbolsException(symbols);
        }
    }

    @Override
    public String toString() {
        if (difficultyLevel > 3) {
            return "Сложный игрок, сложность - " + difficultyLevel;
        } else {
            return "Средний игрок, сложность - " + difficultyLevel;
        }
    }
}