package aplicacao;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage myStage) throws Exception{
        Controller Controle = new Controller();
        Controle.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
