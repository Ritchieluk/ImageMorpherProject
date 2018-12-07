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
    private GriddedImage leftGrid, rightGrid, previewGrid;
    private TriangleGrid oldGrid, newGrid, interGrid;
    private JPanel panel, controls, images, leftPanel, rightPanel;
    private JButton uploadLeft, uploadRight, quit, resetLeft, resetRight, animate;
    private BufferedImage leftImage, rightImage;
    private JSlider timeSlider, frameSlider;
    private JLabel extra, timeLabel, frameLabel;
    static int rows = 11, cols = 11, frame = 0, frames = 30, seconds = 3, frameCount = 0, animateCounter = 0, anirate = 0;
    private Timer frameCounter;
    boolean timestart = false;
    TriangleGrid[] gridFrames;





    public JMorph(){
        super("JMorph");
        Container c = this.getContentPane();

        final JFileChooser fc = new JFileChooser("./img");
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
        animate.setEnabled(false);

        quit.addActionListener(e -> System.exit(0));

        frameCounter = new Timer((1000/frames), e -> {
            if(frameCount<gridFrames.length) {
                previewGrid.setGrid(gridFrames[frameCount]);
                System.out.println(frameCount);
                frameCount++;
            }
            else {
                frameCount = 0;
                frameCounter.stop();

            }
        });

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
                            animateCounter++;
                            if (animateCounter == 2){animate.setEnabled(true);}

                        }
                    }
                }
        );

        animate.addActionListener(e -> {
            gridFrames = animate();
            previewGrid = leftGrid;
            createPreview();
            previewGrid.setGrid(gridFrames[0]);
            System.out.println("Frames: " + frames);
            System.out.println("Seconds: " + seconds);
            anirate =  10000/frames;
            System.out.println("Anirate: " + anirate);
            frameCounter.start();
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
                            animateCounter++;
                            if (animateCounter == 2){animate.setEnabled(true);}
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
        //ii

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
                    }
                }
        );

        frameSlider.addChangeListener(
                new ChangeListener()
                {
                    public void stateChanged( ChangeEvent e )
                    {
                        if (frameSlider.getValue() == 0) {frames = 1;}
                        else {frames = frameSlider.getValue();
                                frameCounter.setDelay(1000/frames);
                        }

                    }
                }
        );


        controls.add(uploadLeft);
        controls.add(uploadRight);
        controls.add(resetLeft);
        controls.add(resetRight);
        controls.add(quit);
        controls.add(animate);
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
    private void createPreview(){
       JFrame previewFrame = new JFrame("Animation");
       Container container = previewFrame.getContentPane();
       container.add(previewGrid, BorderLayout.CENTER);

       previewFrame.setSize(400,400);
       previewFrame.setVisible(true );
       previewFrame.pack();

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
        for(frame = 0; frame < frames * seconds; frame++){
            float alpha = frame * 1 / (float) (frames * seconds - 1);
            TriangleGrid intermediateGrids = intermediateGrid(oldGrid, newGrid, alpha);
            animatedGrid[frame] = intermediateGrids;
            System.out.println(frame);
        }
        frame = 0;
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
