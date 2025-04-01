import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainPanel extends JPanel implements Runnable {

    JButton fileChooseButton;
    JFileChooser fileChooser;
    String filePath = "";
    ReadFile readFile;
    int posX, posY;

    public MainPanel() {
        initializeObjects();
        buttonConfig();
        this.setBackground(Color.lightGray);
        this.setLayout(new BorderLayout());
        addObjects();
        new Thread(this).start();

    }

    private void buttonConfig() {
        fileChooseButton.setMaximumSize(new Dimension(50, 30));
        fileChooseButton.setPreferredSize(new Dimension(50, 30));
        fileChooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int resultValue = fileChooser.showOpenDialog(MainPanel.this);
                if (resultValue == JFileChooser.APPROVE_OPTION) {
                    filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    System.out.println("File selected: " + fileChooser.getSelectedFile().getAbsolutePath());
                    readFile = new ReadFile(filePath);

                } else if (resultValue == JFileChooser.CANCEL_OPTION) {
                    System.out.println("File selection cancelled.");
                } else if (resultValue == JFileChooser.ERROR_OPTION) {
                    System.out.println("Error occurred while selecting file.");
                } else {
                    System.out.println("Unknown error occurred.");
                }

            }
        });

    }

    private void initializeObjects() {
        posX = 600;
        posY = 250;
        fileChooseButton = new JButton("Choose File");
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text files (*.txt)", "txt"));
    }

    private void addObjects() {
        this.add(fileChooseButton, BorderLayout.NORTH);
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Running.....");
            update();
            repaint();

        }
    }

    public void update() {
        if (posX > 0) {
            posX -= 1;
        } else {
            posX = 600;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("G", posX, posY);
    }
}
