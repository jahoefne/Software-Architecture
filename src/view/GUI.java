package view;

import controller.GameController;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * User: jahoefne
 * Creation Date: 03.11.13
 * Time: 13:42
 */
public class GUI extends JFrame implements ActionListener{

    JButton[][] fields = new JButton[8][8];
    GameController controller = new GameController();
    JButton newGame;
    JButton quit;
    JLabel status;
    GridLayout layout = new GridLayout(9,8);


    Point selected = new Point(-1,-1);
    Color tmp;
    Point[] possibilities;
    Color[] tmpColors;


    public JPanel createContentPane (){
        try {
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel totalGUI = new JPanel();
        JPanel mainPanel = new JPanel(layout);

        byte colorSwitch=1;
        for(int i=0;i<8;i++){
            for(int x=0;x<8;x++){
                fields[x][i]= new JButton(controller.getUnicode(new Point(x, i)));
                if(controller.getID(new Point(x, i))<0)
                    fields[x][i].setForeground(Color.black);
                else
                    fields[x][i].setForeground(Color.white);
                fields[x][i].setBackground((colorSwitch>0) ? Color.lightGray : Color.gray);
                fields[x][i].setFont(new Font("Dialog", Font.BOLD, 36));
                fields[x][i].setActionCommand(x + " " + i);
                fields[x][i].setPreferredSize(new Dimension(70, 70));
                fields[x][i].setBorderPainted(false);
                fields[x][i].setOpaque(true);
                fields[x][i].addActionListener(this);
                fields[x][i].setFocusPainted(false);
                mainPanel.add(fields[x][i]);
                colorSwitch*=-1;
            }
            colorSwitch*=-1;
        }
        status = new JLabel("Whites turn!");
        mainPanel.add(status);
        mainPanel.add(new JLabel(""));
        mainPanel.add(new JLabel("")); // skip positions

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

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if(actionEvent.getActionCommand().equals("quit"))
            System.exit(0);

        String s[] = actionEvent.getActionCommand().split(" ");
        Point p = new Point(Integer.parseInt(s[0]),Integer.parseInt(s[1]));

        if(fields[p.x][p.y].getText().equals("") && selected.x==-1)
            return;

        if(selected.x==-1){
            possibilities = controller.getPossibleMoves(p);

            tmpColors= new Color[possibilities.length];
            for(int i=0;i<possibilities.length;i++) {
                tmpColors[i]=fields[possibilities[i].x][possibilities[i].y].getBackground();
                fields[possibilities[i].x][possibilities[i].y].setBackground(new Color(184,220,184));
            }
            tmp = fields[p.x][p.y].getBackground();
            fields[p.x][p.y].setBackground(Color.darkGray);
            selected = p;
        }else if(selected.x==p.x&&selected.y==p.y){
            fields[p.x][p.y].setBackground(tmp);
            tmp=null;
            selected=new Point(-1,-1);
            for(int i=0;i<possibilities.length;i++) {
                fields[possibilities[i].x][possibilities[i].y].setBackground(tmpColors[i]);
            }
        }else{
            fields[selected.x][selected.y].setBackground(tmp);
            if(controller.move(selected, p)){
                fields[p.x][p.y].setText(fields[selected.x][selected.y].getText());
                fields[selected.x][selected.y].setText("");

                if(controller.getID(p)<0)
                    fields[p.x][p.y].setForeground(Color.black);
                else
                    fields[p.x][p.y].setForeground(Color.white);
            }
            for(int i=0;i<possibilities.length;i++) {
                fields[possibilities[i].x][possibilities[i].y].setBackground(tmpColors[i]);
            }
            selected=new Point(-1,-1);
        }

        if(controller.whitesTurn())
            status.setText("Whites turn!");
        else
            status.setText("Blacks turn!");


    }
}
