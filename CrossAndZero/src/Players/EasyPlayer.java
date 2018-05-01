package Players;

import Exceptions.IncorrectSymbolsException;
import Exceptions.OutOfFiledException;
import GameStructures.Field;

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
            int x = (int) (3*Math.random());
            int y = (int) (3*Math.random());

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
        if (symbols.length == 2) {
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
