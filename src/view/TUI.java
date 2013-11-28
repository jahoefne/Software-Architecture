package view;

import controller.GameController;

public class TUI {
    private static final GameController GAME_CONTROLLER = GameController.getInstance();

    public static void main(String[] args) {
        GAME_CONTROLLER.resetGame();
    }
}
