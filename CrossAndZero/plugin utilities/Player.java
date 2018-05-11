package Players;

import Exceptions.IncorrectSymbolsException;
import GameStructures.Field;


public interface Player {

    int BASE_SYMBOL_LENGTH = 2;

    void makeTurn();
    char getSymbol();
    void setSymbols(char[] symbols) throws IncorrectSymbolsException;
}
