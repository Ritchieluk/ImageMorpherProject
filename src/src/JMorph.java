import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class JMorph extends JFrame {

    private JPanel panel, controls, images, leftPanel, rightPanel;
    private JButton uploadLeft, uploadRight, quit, reset;
    private BufferedImage leftImage, rightImage;
    private JSlider speedSlider;
    private JLabel speedLabel;




    public JMorph(){
        Container c = this.getContentPane();

        final JFileChooser fc = new JFileChooser(".");
        panel  = new JPanel();
        images = new JPanel();
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        controls = new JPanel();
        uploadLeft = new JButton("Upload Left Image");
        uploadRight = new JButton("Upload Right Image");
        quit = new JButton("Quit");
        reset = new JButton("Reset");
        speedSlider = new JSlider(0,10,5);
        speedLabel = new JLabel("Adjust the speed  of the animation.    --->");

        leftPanel.setPreferredSize(new Dimension(200, 200));
        rightPanel.setPreferredSize(new Dimension(200, 200));
        images.add(leftPanel, BorderLayout.WEST);
        images.add(rightPanel, BorderLayout.EAST);
        panel.setLayout(new GridLayout(2,1,5,5));
        controls.setLayout(new GridLayout(3,2));

        quit.addActionListener(e -> System.exit(0));

        reset.addActionListener(e -> {});

        uploadRight.addActionListener(
                new ActionListener() {
                    public void actionPerformed (ActionEvent e) {
                        int returnVal = fc.showOpenDialog(null);
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            File file = fc.getSelectedFile();
                            String name = file.getAbsolutePath();
                            try {
                                rightImage = ImageIO.read(new File(name));
                            } catch (IOException e1){};

                            rightImage = resize(rightImage, 200, 200);
                        }
                    }
                }
        );
        uploadLeft.addActionListener(
                new ActionListener() {
                    public void actionPerformed (ActionEvent e) {
                        int returnVal = fc.showOpenDialog(null);
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            File file = fc.getSelectedFile();
                            String name = file.getAbsolutePath();
                            try {
                                leftImage = ImageIO.read(new File(name));
                            } catch (IOException e1){};

                            leftImage = resize(leftImage, 200, 200);
                        }
                    }
                }
        );

        speedSlider.setMinorTickSpacing(1);
        speedSlider.setMajorTickSpacing(5);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);


        controls.add(uploadLeft);
        controls.add(uploadRight);
        controls.add(reset);
        controls.add(quit);
        controls.add(speedLabel);
        controls.add(speedSlider);
        c.add(images, BorderLayout.NORTH);
        c.add(controls);
        //add(c);

        images.setPreferredSize(new Dimension(1000, 500));
        controls.setPreferredSize(new Dimension(100, 150));




        pack();
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        setResizable(false);
        setVisible(true);


    }

    public BufferedImage resize(BufferedImage image, int newWidth, int newHeight) {
        Image temp = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = newImage.createGraphics();
        g2d.drawImage(temp, 0, 0, null);
        g2d.dispose();

        return newImage;
    }



    public static void main(String argv[]){
        JMorph please = new JMorph();
    }




}
