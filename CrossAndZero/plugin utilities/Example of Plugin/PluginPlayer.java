import Exceptions.IncorrectSymbolsException;
import Exceptions.OutOfFiledException;
import GameStructures.Field;
import PluginSupport.IPlayerPlugin;
import PluginSupport.IPluginContext;

public class PluginPlayer implements IPlayerPlugin {

    private char symbol;
    private Field field;

    @Override
    public void makeTurn() {
        for (int i = 0; i < Field.WIDTH; i++) {
            for (int j = 0; j < Field.HEIGHT; j++) {
                try {
                    if (field.getCell(i, j) == ' ') {
                        field.addSymbol(symbol, i, j);
                        return;
                    }
                } catch (OutOfFiledException e) {
                    e.printStackTrace();
                }
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
        return "Дополнительный вид игрока";
    }

    @Override
    public void init(IPluginContext context) {
        symbol = 'X';
        field = context.getField();
    }
}
