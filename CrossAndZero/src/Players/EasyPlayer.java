package Players;


import Exceptions.IncorrectSymbolsException;
import Exceptions.OutOfFiledException;
import GameStructures.Field;

import static GameStructures.Field.HEIGHT;
import static GameStructures.Field.WIDTH;

public class EasyPlayer implements Player {

    private Field field;
    private char symbol;

    public EasyPlayer(Field field)  {
        this.field = field;
        this.symbol = 'X';

    }

    @Override
    public void makeTurn() {

        while (true) {
            int x = (int) (HEIGHT*Math.random());
            int y = (int) (WIDTH*Math.random());

            try {
                if (field.getCell(x, y) == ' ') {
                    field.addSymbol(symbol,x, y);
                    break;
                }
            } catch (OutOfFiledException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

    @Override
    public void setSymbols(char[] symbols) throws IncorrectSymbolsException {
        if (symbols.length == BASE_SYMBOL_LENGTH) {
            symbol = symbols[0];
        } else {
            throw new IncorrectSymbolsException(symbols);
        }
    }

    @Override
    public String toString() {
        return "Легкий игрок, сложность - " + "0";
    }

}
