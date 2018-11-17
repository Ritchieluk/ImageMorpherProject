import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class JMorph extends JFrame {

    private int MAX_IMAGE_SIZE = 400;
    private GriddedImage leftGrid, rightGrid;
    private TriangleGrid oldGrid, newGrid;
    private JPanel panel, controls, images, leftPanel, rightPanel;
    private JButton uploadLeft, uploadRight, quit, resetLeft, resetRight, animate;
    private BufferedImage leftImage, rightImage;
    private JSlider timeSlider, frameSlider;
    private JLabel extra, timeLabel, frameLabel;
    static int rows = 11, cols = 11, frame = 0, frames = 30, seconds = 3;
    private Timer frameCounter;
    boolean timestart = false;




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
        resetLeft = new JButton("Reset Left Points");
        resetRight = new JButton("Reset Right Points");
        animate = new JButton("Animate");
        timeSlider = new JSlider(1,5,3);
        frameSlider = new JSlider(0, 30, 30);
        extra = new JLabel("");
        timeLabel = new JLabel("Adjust how many seconds the animation will run for.");
        frameLabel = new JLabel("Adjust how many frames are shown per second during the animation.");
        images.setLayout(new GridLayout(1,2));
        images.add(leftPanel);
        images.add(rightPanel);
        panel.setLayout(new GridLayout(2,1,5,20));
        controls.setLayout(new GridLayout(5,2));
        resetRight.setEnabled(false);
        resetLeft.setEnabled(false);

        quit.addActionListener(e -> System.exit(0));


        uploadRight.addActionListener(
                new ActionListener() {
                    public void actionPerformed (ActionEvent e) {
                        rightPanel.removeAll();
                        int returnVal = fc.showOpenDialog(null);
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            File file = fc.getSelectedFile();
                            String name = file.getAbsolutePath();
                            try {
                                rightImage = ImageIO.read(new File(name));
                            } catch (IOException e1){};

                            rightImage = resize(rightImage, MAX_IMAGE_SIZE, MAX_IMAGE_SIZE);

                            //rightPanel.add(pls, BorderLayout.CENTER);
                            rightGrid = new GriddedImage(rightImage);
                            newGrid = rightGrid.getTriangleGrid();
                            rightPanel.add(rightGrid);
                            rightPanel.revalidate();
                            rightGrid.repaint();
                            resetRight.setEnabled(true);

                        }
                    }
                }
        );

        animate.addActionListener(e -> {
            timestart = true;
        });
        uploadLeft.addActionListener(
                new ActionListener() {
                    public void actionPerformed (ActionEvent e) {
                        leftPanel.removeAll();
                        int returnVal = fc.showOpenDialog(null);
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            File file = fc.getSelectedFile();
                            String name = file.getAbsolutePath();
                            try {
                                leftImage = ImageIO.read(new File(name));
                            } catch (IOException e1){};

                            leftImage = resize(leftImage, MAX_IMAGE_SIZE, MAX_IMAGE_SIZE);
                            //pls = new JLabel("", new ImageIcon(leftImage), JLabel.CENTER);
                            leftGrid = new GriddedImage(leftImage);
                            oldGrid = leftGrid.getTriangleGrid();
                            leftPanel.add(leftGrid);
                            leftPanel.revalidate();
                            leftPanel.repaint();
                            resetLeft.setEnabled(true);
                        }
                    }
                }
        );

        resetLeft.addActionListener(e -> {
            leftGrid.reset();
        });

        resetRight.addActionListener(e -> {
            rightGrid.reset();
        });

        timeSlider.setMajorTickSpacing(1);
        timeSlider.setPaintTicks(true);
        timeSlider.setPaintLabels(true);
        frameSlider.setMinorTickSpacing(5);
        frameSlider.setMajorTickSpacing(10);
        frameSlider.setPaintTicks(true);
        frameSlider.setPaintLabels(true);

        timeSlider.addChangeListener(
                new ChangeListener()
                {
                    public void stateChanged( ChangeEvent e )
                    {
                        seconds = timeSlider.getValue();
                        System.out.println(seconds);
                    }
                }
        );

        frameSlider.addChangeListener(
                new ChangeListener()
                {
                    public void stateChanged( ChangeEvent e )
                    {
                        if (frameSlider.getValue() == 0) {frames = 1;}
                        else {frames = frameSlider.getValue();}
                        System.out.println(frames);

                    }
                }
        );


        controls.add(uploadLeft);
        controls.add(uploadRight);
        controls.add(resetLeft);
        controls.add(resetRight);
        controls.add(quit);
        controls.add(extra);
        controls.add(frameLabel);
        controls.add(timeLabel);
        controls.add(frameSlider);
        controls.add(timeSlider);
        c.add(images, BorderLayout.NORTH);
        c.add(controls);
        //add(c);

        images.setPreferredSize(new Dimension(1000, 500));
        controls.setPreferredSize(new Dimension(100, 250));




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
        float height, width, scale;
        height = image.getHeight();
        width = image.getWidth();

        if (height > width) {
            scale = newHeight / height;
            height = height * scale;
            width = width * scale;
        }
        else {
            scale = newWidth / width;
            width = width * scale;
            height = height * scale;
        }

        Image temp = image.getScaledInstance((int)width, (int)height, Image.SCALE_SMOOTH);
        BufferedImage newImage = new BufferedImage((int)width, (int)height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = newImage.createGraphics();
        g2d.drawImage(temp, 0, 0, null);
        g2d.dispose();

        return newImage;
    }


    private TriangleGrid[] animate() {

        TriangleGrid[] animatedGrid = new TriangleGrid[frames*seconds];
        for(int i = 0; i < frames * seconds; i++){
            float alpha = frame * 1 / (float) (frames * seconds - 1);
            TriangleGrid intermediateGrids = intermediateGrid(oldGrid, newGrid, alpha);
            animatedGrid[i] = intermediateGrids;
        }
        return animatedGrid;
    }

    private static TriangleGrid intermediateGrid(TriangleGrid orig, TriangleGrid targ, float a){
        TriangleGrid inter = new TriangleGrid(orig.getWidth(), orig.getHeight(), orig.getTrueWidth(), orig.getTrueWidth());
        for(int i = 0; i < inter.getWidth(); i++){
            for(int j = 0; j <inter.getHeight(); j++){
                inter.points[i][j].x = (int) (a * orig.points[i][j].x + (1-a) * targ.points[i][j].x);
                inter.points[j][j].y = (int) (a * orig.points[i][j].y + (1-a) * targ.points[i][j].y);
            }

        }
        return inter;
    }



    public static void main(String argv[]){
        JMorph please = new JMorph();
    }




}
