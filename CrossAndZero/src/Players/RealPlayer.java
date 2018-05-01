package Players;

import Exceptions.IncorrectSymbolsException;
import Exceptions.OutOfFiledException;
import GameStructures.Field;
import Players.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RealPlayer implements Player {

    private BufferedReader reader;
    private char symbol;
    private Field field;

    public RealPlayer(Field field) {

        reader = new BufferedReader(new InputStreamReader(System.in));
        this.symbol = 'X';
        this.field = field;


    }


    @Override
    public void makeTurn() {
        System.out.println("Введите координаты клетки через пробел");

        try {
            String line = reader.readLine();
            String[] coords= line.split(" ");

            if (coords.length == 2 && coords[0].matches("[1-3]") && coords[1].matches("[1-3]")) {
                int x = Integer.parseInt(coords[0]) - 1;
                int y = Integer.parseInt(coords[1]) - 1;

                if (field.getCell(x, y) == ' '){
                    field.addSymbol(symbol, x, y);
                } else {
                    System.out.println("Это поле уже занято!");
                    makeTurn();
                }

            } else {
                System.out.println("Не корректный ввод");
                makeTurn();
            }


        } catch (IOException | OutOfFiledException e) {
            e.printStackTrace();
        }
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

    @Override
    public void setSymbols(char[] symbols) throws IncorrectSymbolsException {
        if (symbols.length == 2) {
            symbol = symbols[0];
        } else {
            throw new IncorrectSymbolsException(symbols);
        }
    }

    @Override
    public String toString() {
        return "Реальный игрок";
    }

}
