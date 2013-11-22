package view;

import controller.GameController;

public class TUI {
    private final static GameController controller = GameController.GetInstance();

    public static void main(String[] args) {
        System.out.println(controller.toString());
    }
}
