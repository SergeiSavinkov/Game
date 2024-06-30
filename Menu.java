import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;
import static java.lang.System.out;

public class Menu {
    Image pic;

    Menu() {
        //Создание окна
        JFrame myFrame = new JFrame("More or less, less is more");
        myFrame.setSize(150, 350);
        myFrame.setLocationRelativeTo(null);
        myFrame.setLayout(null);
        myFrame.setResizable(false);

        //Создание метки с текстом
        JLabel gameName = new JLabel("More or less, less is more");
        gameName.setFont(new Font("", Font.ITALIC, 10));
        gameName.setBounds(5, 43, 150, 30);
        myFrame.add(gameName);

        //Создание поля для введения размера поля
        JLabel size = new JLabel("Set field size: ");
        size.setBounds(10, 80, 100,20);
        myFrame.add(size);

        //Создание поля для введения ответа 1
        JTextField firstInput = new JFormattedTextField();
        firstInput.setBounds(10, 100, 50, 30);
        myFrame.add(firstInput);

        //Создание поля для введения ответа 2
        JTextField secondInput = new JFormattedTextField();
        secondInput.setBounds(70, 100, 50, 30);
        myFrame.add(secondInput);

        //Создание поля для введения количества шагов
        JLabel move = new JLabel("Set number of moves: ");
        move.setBounds(10, 130, 250, 20);
        myFrame.add(move);

        //Создание поля для введения ответа 3
        JTextField thirdInput = new JFormattedTextField();
        thirdInput.setBounds(10, 150, 50, 30);
        myFrame.add(thirdInput);

        //Создание поля для введения цели
        JLabel target = new JLabel("Set target: ");
        target.setBounds(10, 180, 250,20);
        myFrame.add(target);

        //Создание поля для введения ответа 4
        JTextField forthInput = new JFormattedTextField();
        forthInput.setBounds(10, 200, 50, 30);
        myFrame.add(forthInput);

        //Создание кнопки для начала игры
        JButton start = new JButton("Start");
        start.setBounds(20, 240, 100, 30);
        start.setBackground(new Color(0xF5F5F5));
        myFrame.add(start);

        //Создание кнопки для загрузки игры
        JButton load = new JButton("Load");
        load.setBounds(20, 275, 100, 30);
        load.setBackground(new Color(0xF5F5F5));
        myFrame.add(load);

        //Действия для кнопки Start
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //firstInput is height
                int height = Integer.parseInt(firstInput.getText());
                //secondInput is row
                int row = Integer.parseInt(secondInput.getText());
                int move = Integer.parseInt(thirdInput.getText());
                int target = Integer.parseInt(forthInput.getText());
                int score = 0;
                int amove = move;
                myFrame.dispose();
                Engine game = new Engine(height, row, move, target, score, amove, false, new String[0][0]);
            }
        });

        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] settings = new String[0];
                String[][] field = new String[0][0];
                JFileChooser lchooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text file", "txt");
                lchooser.setFileFilter(filter);
                lchooser.setCurrentDirectory(new File("."));
                lchooser.setDialogTitle("Load");
                int res = lchooser.showSaveDialog(null);
                if(res == JFileChooser.APPROVE_OPTION) {
                    File file = lchooser.getSelectedFile();
                    String filePath = file.getAbsolutePath();
                    try {
                        File myObj = new File(filePath);
                        Scanner scanner = new Scanner(myObj);
                        settings = scanner.nextLine().split(",");
                        field = new String[Integer.parseInt(settings[0])][Integer.parseInt(settings[1])];
                        int i = 0;
                        while(scanner.hasNextLine()) {
                            field[i] = scanner.nextLine().split(",");
                            i++;
                        }
                        scanner.close();
                    } catch (FileNotFoundException ex) {
                        out.println("An error occured.");
                        ex.printStackTrace();
                    }
                }
                int height = Integer.parseInt(settings[0]);
                int row = Integer.parseInt(settings[1]);
                int target = Integer.parseInt(settings[2]);
                int move = Integer.parseInt(settings[3]);
                int score = 0;
                int amove = move;
                myFrame.dispose();
                Engine game = new Engine(height, row, move, target, score, amove, true, field);
            }
        });
        myFrame.setVisible(true);
        myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
