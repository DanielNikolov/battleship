import org.battleshipgame.app.BattleshipApp;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("battleship_config.xml");
		BattleshipApp battleshipApp = (BattleshipApp) appContext.getBean("battleshipApp");
		battleshipApp.startApp();
	}

}
