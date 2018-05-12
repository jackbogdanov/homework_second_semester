package GameStructures;

import Exceptions.OutOfFiledException;

public class Field implements IField{

    public char[][] field;

    public static final int WIDTH = 3;
    public static final int HEIGHT = 3;

    public Field() {
        field = new char[WIDTH][HEIGHT];
        clearFiled();

    }

    public void clearFiled() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                field[i][j] = ' ';
            }
        }
    }

    public void printField() {

        for (int i = 0; i < WIDTH; i++) {
            System.out.println("-------");
            System.out.println("|" + field[i][0] + "|" + field[i][1] + "|" + field[i][2] + "|");
        }
        System.out.println("-------");

    }

    public void addSymbol(char s, int i, int j) throws OutOfFiledException {
        if (i < HEIGHT && i >= 0 && j < WIDTH && j >= 0) {
            field[i][j] = s;
        } else {
            System.out.println("Выход за границы поля!");
            throw new OutOfFiledException();
        }
    }

    public boolean winCheck() {

        for (int i = 0; i < WIDTH; i++) {
            if (field[i][0] == field[i][1] && field[i][1] == field[i][2] && field[i][1] != ' ' ) {
                return true;
            }
        }

        for (int i = 0; i < WIDTH; i++) {
            if (field[0][i] == field[1][i] && field[1][i] == field[2][i] && field[1][i] != ' ') {
                return true;
            }
        }

        if (field[0][0] == field[1][1] && field[1][1] == field[2][2] && field[1][1] != ' ') {
            return true;
        } else {
            return field[0][2] == field[1][1] && field[1][1] == field[2][0] && field[1][1] != ' ';
        }

    }

    public char getCell(int i, int j) throws OutOfFiledException {
        if (i < HEIGHT && i >= 0 && j < WIDTH && j >= 0) {
            return field[i][j];
        } else {
            System.out.println("Выход за границы поля!");
            throw new OutOfFiledException();
        }
    }
}
