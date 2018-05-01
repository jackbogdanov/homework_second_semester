package GameStructures;

import Exceptions.IncorrectSymbolsException;
import Players.EasyPlayer;
import Players.Player;
import Players.RealPlayer;
import Players.SmartPlayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Game {

    private Player p1;
    private Player p2;
    private Field field;

    private BufferedReader reader;


    public Game() {
        field = new Field();

        p1 = new RealPlayer(field);
        p2 = new EasyPlayer(field);


        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void startMenuLoop() {
        boolean isExit = false;
        String line;

        try {
            while (!isExit) {
                printMenu();
                System.out.println("Выберите действие:");
                line = reader.readLine();

                if (line.matches("[1-3]")) {
                    isExit = callMenuMethod(Integer.parseInt(line));
                } else {
                    System.out.println("Некоретный ввод!");
                    System.out.println("Попробуйте ещё раз");
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void printMenu() {
        System.out.println();
        System.out.println("_____меню_____");
        System.out.println("1 - играть");
        System.out.println("2 - настроить игроков");
        System.out.println("3 - выход");
        System.out.println("--------------");
    }

    private void printSideOptionMenu() {
        printCurrentPlayers();
        System.out.println("Выберите сторуну первого игрока:");
        System.out.println("1 - Крестики");
        System.out.println("2 - Нолики");
    }

    private void printCurrentPlayers() {
        System.out.println();
        System.out.println("Текущие игроки:");
        System.out.println("1 - " + p1);
        System.out.println("2 - " + p2);
        System.out.println();
    }

    private boolean callMenuMethod(int i) throws IOException {
        String line;

        switch (i) {
            case 1:
                printSideOptionMenu();
                line = reader.readLine();

                while (!line.matches("[1-2]")) {
                    System.out.println("Неверный ввод!");
                    System.out.println("Попробуйте ещё раз");
                    line = reader.readLine();
                }
                startGameLoop(Integer.parseInt(line));

                field.clearFiled();
                break;
            case 2:
                playerChangeChose();
                break;
            case 3:
                reader.close();
                return true;
        }
        return false;
    }

    private void startGameLoop(int i) {

        try {
            if (i == 1) {
                p1.setSymbols(new char[]{'X', 'O'});
                p2.setSymbols(new char[]{'O', 'X'});
                new GameLoop(p1, p2, field).startGame();
            } else {
                p1.setSymbols(new char[]{'O', 'X'});
                p2.setSymbols(new char[]{'X', 'O'});
                new GameLoop(p2, p1, field).startGame();
            }
        } catch (IncorrectSymbolsException e) {
            e.getMessage();
        }

    }

    private void playerChangeChose() throws IOException {
        printCurrentPlayers();

        System.out.println("Выберите игрока для изменения");
        String line = reader.readLine();

        while (!line.matches("[1-2]")) {
            System.out.println("Неверный ввод!");
            System.out.println("Попробуйте ещё раз");
            line = reader.readLine();
        }

        changePlayers(Integer.parseInt(line));
    }

    private void changePlayers(int i) throws IOException {
        if (i == 1) {
            System.out.println();
            System.out.println("Выбран первый игрок");
            System.out.println();

            p1 = createNewPlayer();
        } else {
            System.out.println("Выбран второй игрок");

            p2 = createNewPlayer();
        }
    }

    private Player createNewPlayer() throws IOException {
        System.out.println("Возможный опции:");
        System.out.println("1 - заменить на реального игрока");
        System.out.println("2 - заменить на искуственный интеллект");
        System.out.println("При выборе вторго пункта необходимо указать сложность(0 - 9) через пробел");

        String line = reader.readLine();

        String[] s = line.split(" ");

        if ((s.length == 1 && s[0].matches("1")) || (s.length == 2 && s[0].matches("2") && s[1].matches("[0-9]"))) {

            if (s.length == 1) {
                return new RealPlayer(field);
            } else {
                int num = Integer.parseInt(s[1]);

                if (num == 0) {
                    return new EasyPlayer(field);
                } else {
                    return new SmartPlayer(field, num);
                }
            }

        } else {
            System.out.println("Неправильный ввод!");
            System.out.println("Повторите попытку");
            return createNewPlayer();
        }
    }
}
