package view;

import controller.GameController;
import controller.IGameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: jahoefne
 * Creation Date: 03.11.13
 * Time: 13:42
 */
class GUI extends JFrame implements ActionListener {

    private static final int LENGTH_OF_BOARD = 8;

    private final JButton[][] fields = new JButton[LENGTH_OF_BOARD][LENGTH_OF_BOARD];
    private final IGameController controller = new GameController();// GameController.getInstance();
    private JLabel status;
    private final GridLayout layout = new GridLayout(9, 8);

    private Point selected = new Point(-1, -1);
    private Color tmp;
    private Point[] possibilities;
    private Color[] tmpColors;

    private static final int FILED_SIZE = 70;
    private static final int FONT_SIZE = 36;


    JPanel createContentPane() {
        JButton newGame;
        JButton quit;
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            // wow, an error occured!
        }

        JPanel totalGUI = new JPanel();
        JPanel mainPanel = new JPanel(layout);

        byte colorSwitch = 1;
        for (int i = 0; i < LENGTH_OF_BOARD; i++) {
            for (int x = 0; x < LENGTH_OF_BOARD; x++) {
                fields[x][i] = new JButton(controller.getUnicode(new Point(x, i)));
                if (controller.getID(new Point(x, i)) < 0) {
                    fields[x][i].setForeground(Color.black);
                } else {
                    fields[x][i].setForeground(Color.white);
                }
                fields[x][i].setBackground((colorSwitch > 0) ? Color.lightGray : Color.gray);
                fields[x][i].setFont(new Font("Dialog", Font.BOLD, FONT_SIZE));
                fields[x][i].setActionCommand(x + " " + i);
                fields[x][i].setPreferredSize(new Dimension(FILED_SIZE, FILED_SIZE));
                fields[x][i].setBorderPainted(true);
                fields[x][i].setOpaque(true);
                fields[x][i].addActionListener(this);
                fields[x][i].setFocusPainted(false);
                mainPanel.add(fields[x][i]);
                colorSwitch *= -1;
            }
            colorSwitch *= -1;
        }
        status = new JLabel("Whites turn!");
        mainPanel.add(status);
        mainPanel.add(new JLabel(""));
        mainPanel.add(new JLabel(""));

        newGame = new JButton("New Game!");
        newGame.setActionCommand("new");
        newGame.addActionListener(this);
        mainPanel.add(newGame);

        quit = new JButton("Quit!");
        quit.setActionCommand("quit");
        quit.addActionListener(this);
        mainPanel.add(quit);

        totalGUI.add(mainPanel);
        totalGUI.setOpaque(true);
        return totalGUI;
    }

    private static void createAndShowGUI() {

        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Chess");

        GUI gui = new GUI();
        frame.setContentPane(gui.createContentPane());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    // gets called when a button is clicked
    public void actionPerformed(ActionEvent actionEvent) {

        if (actionEvent.getActionCommand().equals("quit")) {
            System.exit(0);
        }

        if(actionEvent.getActionCommand().equals("new")){
            // TODO: start new game
            return;
        }

        String s[] = actionEvent.getActionCommand().split(" ");

        Point p = new Point(Integer.parseInt(s[0]), Integer.parseInt(s[1]));

        // if an empty field has been clicked and no figure to move was selected before
        // ignore
        if (fields[p.x][p.y].getText().equals("") && selected.x == -1) {
            return;
        }

        handleMovement(p);

        if(controller.isGameOver()){
          if (controller.whitesTurn()) {
             status.setText("Black Won!");
          } else {
             status.setText("White Won!");
        }
        }else{
            if (controller.whitesTurn()) {
                status.setText("Whites turn!");
        } else {
        status.setText("Blacks turn!");
        }
        }
        }

private void handleMovement(Point p) {

        if (selected.x == -1) {
        possibilities = controller.getPossibleMoves(p);

        tmpColors = new Color[possibilities.length];
        for (int i = 0; i < possibilities.length; i++) {
        tmpColors[i] = fields[possibilities[i].x][possibilities[i].y].getBackground();
        fields[possibilities[i].x][possibilities[i].y].setBackground(Color.RED);
        }
        tmp = fields[p.x][p.y].getBackground();
        fields[p.x][p.y].setBackground(Color.darkGray);
        selected = p;
        } else if (selected.x == p.x && selected.y == p.y) {
        fields[p.x][p.y].setBackground(tmp);
        tmp = null;
        selected = new Point(-1, -1);
        for (int i = 0; i < possibilities.length; i++) {
        fields[possibilities[i].x][possibilities[i].y].setBackground(tmpColors[i]);
        }
        } else {
        fields[selected.x][selected.y].setBackground(tmp);
        if (controller.move(selected, p)) {
        fields[p.x][p.y].setText(fields[selected.x][selected.y].getText());
        fields[selected.x][selected.y].setText("");

        if (controller.getID(p) < 0) {
        fields[p.x][p.y].setForeground(Color.black);
        } else {
        fields[p.x][p.y].setForeground(Color.white);
        }
        }
        for (int i = 0; i < possibilities.length; i++) {
        fields[possibilities[i].x][possibilities[i].y].setBackground(tmpColors[i]);
        }
        selected = new Point(-1, -1);
        }
        }
        }
