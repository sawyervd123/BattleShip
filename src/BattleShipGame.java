import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BattleShipGame extends JFrame {
    private static final int SIZE = 10;
    private final JButton[][] buttons = new JButton[SIZE][SIZE];
    private Board board;
    private int missCount = 0;
    private int strikeCount = 0;
    private int totalMiss = 0;
    private int totalHit = 0;

    private JLabel lblMiss, lblStrike, lblTotalMiss, lblTotalHit;

    public BattleShipGame() {
        board = new Board(SIZE);
        setupUI();
        board.placeShips();
    }

    private void setupUI() {
        setTitle("Battleship Game");
        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(SIZE, SIZE));
        Font font = new Font("Arial", Font.BOLD, 14);
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                JButton button = new JButton("🌊");
                button.setFont(font);
                int finalRow = row;
                int finalCol = col;
                button.addActionListener(e -> handleClick(finalRow, finalCol, button));
                buttons[row][col] = button;
                gridPanel.add(button);
            }
        }

        JPanel infoPanel = new JPanel(new GridLayout(2, 4));
        lblMiss = new JLabel("Miss: 0");
        lblStrike = new JLabel("Strikes: 0");
        lblTotalMiss = new JLabel("Total Misses: 0");
        lblTotalHit = new JLabel("Total Hits: 0");

        JButton btnPlayAgain = new JButton("Play Again");
        JButton btnQuit = new JButton("Quit");

        btnPlayAgain.addActionListener(e -> resetGame());
        btnQuit.addActionListener(e -> System.exit(0));

        infoPanel.add(lblMiss);
        infoPanel.add(lblStrike);
        infoPanel.add(lblTotalMiss);
        infoPanel.add(lblTotalHit);
        infoPanel.add(btnPlayAgain);
        infoPanel.add(btnQuit);

        add(gridPanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);

        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void handleClick(int row, int col, JButton button) {
        if (!button.isEnabled()) return;

        Cell cell = board.getCell(row, col);
        button.setEnabled(false);

        if (cell.hasShip()) {
            button.setText("💥");
            button.setBackground(Color.RED); // HIT = red
            button.setOpaque(true);
            button.setBorderPainted(false);

            totalHit++;
            missCount = 0;
            cell.markHit();

            if (cell.getShip().isSunk()) {
                JOptionPane.showMessageDialog(this, "You sunk a ship!");
                if (board.allShipsSunk()) {
                    JOptionPane.showMessageDialog(this, "You win! Play again?");
                    resetGame();
                    return;
                }
            }

        } else {
            button.setText("💧");
            button.setBackground(Color.CYAN); // MISS = cyan
            button.setOpaque(true);
            button.setBorderPainted(false);

            missCount++;
            totalMiss++;
            if (missCount == 5) {
                strikeCount++;
                missCount = 0;

                if (strikeCount == 3) {
                    JOptionPane.showMessageDialog(this, "You lost! Out of strikes.");
                    resetGame();
                    return;
                }
            }
        }

        updateLabels();
    }

    private void updateLabels() {
        lblMiss.setText("Miss: " + missCount);
        lblStrike.setText("Strikes: " + strikeCount);
        lblTotalMiss.setText("Total Misses: " + totalMiss);
        lblTotalHit.setText("Total Hits: " + totalHit);
    }

    private void resetGame() {
        int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to restart?", "Restart", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            board = new Board(SIZE);
            board.placeShips();
            missCount = 0;
            strikeCount = 0;
            totalMiss = 0;
            totalHit = 0;

            updateLabels();
            for (int row = 0; row < SIZE; row++) {
                for (int col = 0; col < SIZE; col++) {
                    buttons[row][col].setText("🌊");
                    buttons[row][col].setEnabled(true);
                    buttons[row][col].setBackground(null); // Reset button background
                    buttons[row][col].setOpaque(true);
                    buttons[row][col].setBorderPainted(true);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BattleShipGame::new);
    }
}
