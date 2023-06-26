module org.quizlab.quizlab_game {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    
    opens org.quizlab.quizlab_game to  javafx.graphics,javafx.fxml;
    opens org.quizlab.quizlab_game.game to  javafx.graphics,javafx.fxml;

    exports org.quizlab.quizlab_game;
    exports org.quizlab.quizlab_game.game;
    exports org.quizlab.quizlab_game.levels;
    exports org.quizlab.quizlab_game.menu;
}
