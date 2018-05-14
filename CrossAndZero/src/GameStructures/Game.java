package GameStructures;

import Exceptions.IncorrectSymbolsException;
import Players.*;
import PluginSupport.GamePluginContext;
import PluginSupport.IPlayerPlugin;
import PluginSupport.PluginLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Game {

    private static final String PLUGIN_PATH = System.getProperty("user.dir") + "/bin/plugins/";
    private static final int MENU_OFFSET = 3;

    private Player p1;
    private Player p2;
    private IField field;

    private ArrayList<Player> addedPlayers;

    private BufferedReader reader;


    public Game() {

        addedPlayers = new ArrayList<>();
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

                while (!line.matches("[1-3]")) {
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
        System.out.println("----Дополнительно----");
        printPluginPlayers();
        System.out.println();
        System.out.println("При выборе вторго пункта необходимо указать сложность(0 - 9) через пробел");

        String line = reader.readLine();
        String[] s = line.split(" ");

        while (true) {
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

            }

            if (s[0].matches(String.valueOf(MENU_OFFSET - 1 + addedPlayers.size()))){
                return addedPlayers.get(Integer.parseInt(s[0]) - MENU_OFFSET);
            }

            System.out.println("Неправильный ввод!");
            System.out.println("Повторите попытку");
            return createNewPlayer();
        }


    }

    private void loadPlugins() {
        PluginLoader loader = new PluginLoader(PLUGIN_PATH, ClassLoader.getSystemClassLoader());
        GamePluginContext context = new GamePluginContext(field);
        File dir = new File(PLUGIN_PATH);

        String[] plugins = dir.list();

        if (plugins != null) {
            try {
                for (String pl:plugins) {

                    Class loadedClass = loader.loadClass(pl.split(".class")[0]);

                    IPlayerPlugin loadedPlayer = (IPlayerPlugin) loadedClass.newInstance();
                    //(Player) loadedClass.getDeclaredConstructor(Field.class, int.class).newInstance(field, 0);

                    loadedPlayer.init(context);

                    addedPlayers.add( loadedPlayer);

                }
            } catch (ClassNotFoundException | IllegalAccessException |
                    InstantiationException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Нет ни одного плагина");
        }

    }

    private void printPluginPlayers() {
        for (int i = 0; i < addedPlayers.size(); i++) {
            System.out.println((MENU_OFFSET + i) + " - Загруженный игрок: " + addedPlayers.get(i).toString());
        }
    }

    public void setField(IField field) {
        this.field = field;
        p1 = new RealPlayer(field);
        p2 = new EasyPlayer(field);

        loadPlugins();
    }
}
