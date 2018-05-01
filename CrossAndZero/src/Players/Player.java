package Players;

import Exceptions.IncorrectSymbolsException;


public interface Player {

    void makeTurn();
    char getSymbol();
    void setSymbols(char[] symbols) throws IncorrectSymbolsException;
}
