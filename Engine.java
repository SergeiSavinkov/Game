import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Engine implements ActionListener {
    private int height;
    private int row;
    private int move;
    private int target;
    private int score;
    private int amove;
    private boolean load;
    private String[][] field;
    private int previous;
    private int current;
    private int maxNumber;
    JFrame myFrame = new JFrame("More or less, less is more");
    JButton[][] b;
    Random random = new Random();
    JLabel firstLabel;
    JLabel secondLabel;

    public Engine(int height, int row, int move, int target, int score, int amove, boolean load, String[][] field) {
        this.height = height;
        this.row = row;
        this.move = move + 1;
        this.target = target;
        this.score = score;
        this.amove = amove;
        this.load = load;
        this.field = field;
        this.previous = 1;
        this.current = 1;
        this.maxNumber = 0;

        // Set up the JFrame
        myFrame.setLocation(100, 200);
        myFrame.setSize(600, 600);
        myFrame.setLocationRelativeTo(null);
        myFrame.getContentPane().setBackground(Color.white);

        // Add score and target labels
        firstLabel = new JLabel("Score: " + score + " , Target value: " + target);
        myFrame.add(firstLabel, BorderLayout.NORTH);

        secondLabel = new JLabel("The number of moves: " + move);
        myFrame.add(secondLabel, BorderLayout.SOUTH);

        // Add menu options
        JMenuBar menuInside = new JMenuBar();
        JMenu menu = new JMenu("Options");
        JMenuItem restart = new JMenuItem("Restart");
        JMenuItem save = new JMenuItem("Save");
        menu.add(restart);
        menu.add(save);
        menuInside.add(menu);
        myFrame.setJMenuBar(menuInside);

        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myFrame.dispose();
                new Menu();
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser lchooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
                lchooser.setFileFilter(filter);
                lchooser.setDialogTitle("Save");
                int res = lchooser.showSaveDialog(null);
                if (res == JFileChooser.APPROVE_OPTION) {
                    File file = lchooser.getSelectedFile();
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                        writer.write(height + "," + row + "," + target + "," + amove + "\n");
                        for (int i = 0; i < height; i++) {
                            for (int j = 0; j < row; j++) {
                                writer.write(b[i][j].getText() + ",");
                            }
                            writer.newLine();
                        }
                        JOptionPane.showMessageDialog(null, "Game saved!", "Result", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // Create the game panel
        JPanel panel = new JPanel(new GridLayout(height, row));
        b = new JButton[height][row];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < row; j++) {
                int randomNumber = random.nextInt(9) + 1;
                if (load) {
                    b[i][j] = new JButton(field[i][j]);
                } else {
                    b[i][j] = new JButton(String.valueOf(randomNumber));
                }

                if (target == Integer.parseInt(b[i][j].getText())) {
                    b[i][j].setBackground(new Color(0x6BFD07));
                } else {
                    b[i][j].setBackground(Color.WHITE);
                }
                maxNumber = Math.max(maxNumber, Integer.parseInt(b[i][j].getText()));
                b[i][j].addActionListener(this);
                panel.add(b[i][j]);
            }
        }

        if (target > 9) {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < row; j++) {
                    if (maxNumber == Integer.parseInt(b[i][j].getText()))
                        b[i][j].setBackground(new Color(0x6BFD07));
                }
            }
        }
        myFrame.add(panel, BorderLayout.CENTER);
        myFrame.setVisible(true);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton z = (JButton) e.getSource();
        int value = Integer.parseInt(z.getText());
        calculator(value);
        Coloring(value);
    }

    public void Coloring(int value) {
        previous = current;
        current = value;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < row; j++) {
                if (i % current == 0 && j % previous == 0) {
                    b[i][j].setBackground(new Color(0xF5F5F5));
                    b[i][j].setEnabled(false);
                } else if (i % current == 0 || j % previous == 0) {
                    b[i][j].setBackground(new Color(0x78DA64));
                    b[i][j].setEnabled(true);
                } else {
                    b[i][j].setBackground(Color.WHITE);
                    b[i][j].setEnabled(true);
                }
            }
        }
    }

    public void calculator(int value) {
        move -= 1;
        secondLabel.setText("The number of moves: " + move);
        score += value;
        firstLabel.setText("Score: " + score + ", Target value: " + target);
        if (move == 0) {
            int result = Math.abs(target - score);
            JOptionPane.showMessageDialog(null, "You lost!\nYou run out of moves\nRemaining score: " + result, "GAME OVER", JOptionPane.WARNING_MESSAGE);
            //Возвращаем меню
            new Menu();
            myFrame.dispose();
        } else if (score > target) {
            JOptionPane.showMessageDialog(null, "You lost!\nYour score is bigger than the target", "GAME OVER", JOptionPane.WARNING_MESSAGE);
            //Возвращаем меню
            new Menu();
            myFrame.dispose();
        } else if (score == target) {
            JOptionPane.showMessageDialog(null, "YOU WIN\nCongratulations!", "GAME OVER", JOptionPane.INFORMATION_MESSAGE);
            //Возвращаем меню
            new Menu();
            myFrame.dispose();
        }
    }
}
