import GameStructures.Game;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Main {

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("spring_configuration_file.xml");
        Game game = (Game) context.getBean("game");
        game.startMenuLoop();
    }
}
