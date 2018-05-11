package GameStructures;

import Exceptions.OutOfFiledException;

public class AdvancedField extends Field {

    public void removeCell(int i, int j) {
        field[i][j] = ' ';
    }

    public char advancedWinCheck() {
        for (int i = 0; i < WIDTH; i++) {
            if (field[i][0] == field[i][1] && field[i][1] == field[i][2] && field[i][1] != ' ' ) {
                return field[i][0];
            }
        }

        for (int i = 0; i < WIDTH; i++) {
            if (field[0][i] == field[1][i] && field[1][i] == field[2][i] && field[1][i] != ' ') {
                return field[0][i];
            }
        }

        if (field[0][0] == field[1][1] && field[1][1] == field[2][2] && field[1][1] != ' ') {
            return field[0][0];
        } else {
            if (field[0][2] == field[1][1] && field[1][1] == field[2][0] && field[1][1] != ' ') {
                return field[0][2];
            }
        }

        return ' ';
    }

    public void fillByField(Field map) {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                try {
                    field[i][j] = map.getCell(i, j);

                } catch (OutOfFiledException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
