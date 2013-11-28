package view;

import controller.GameController;

public class TUI {
    private static final GameController Controller = GameController.getInstance();

    public static void main(String[] args) {
        Controller.resetGame();
    }
}
