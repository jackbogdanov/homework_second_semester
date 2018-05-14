package GameStructures;

import Exceptions.OutOfFiledException;

public interface IField {
    void clearFiled();

    void printField();

    void addSymbol(char s, int i, int j) throws OutOfFiledException;

    boolean winCheck();

    char getCell(int i, int j) throws OutOfFiledException;
}
