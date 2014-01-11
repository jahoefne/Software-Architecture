package view;

import controller.GameController;

public final class TUI {

    private static final GameController GAME_CONTROLLER = GameController.getInstance();

    // useless but to satisfy sonar..
    private TUI() {}

    public static void main(String[] args) {
        GAME_CONTROLLER.resetGame();
        //TODO: make tui work
    }
}
