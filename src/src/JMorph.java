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
    private JPanel controls, images, leftPanel, rightPanel, leftImageOptions, rightImageOptions, centralOptions, gridOptions, frameOptions;
    private JButton uploadLeft, uploadRight, quit, resetLeft, resetRight, animate, saveMorph, uploadMorph;
    private BufferedImage leftImage, rightImage;
    private JSlider timeSlider, frameSlider, rowSlider, colSlider, rightBrightnessSlider, leftBrightnessSlider;
    private JLabel extra, timeLabel, frameLabel, leftBrightnessLabel, rightBrightnessLabel;
    static int rows = 11, cols = 11, frame = 0, frames = 30, seconds = 3, frameCount = 0, animateCounter = 0;
    private Timer frameCounter;
    boolean timestart = false;
    TriangleGrid[] gridFrames;
    final JFileChooser fc = new JFileChooser("./img");





    public JMorph(){
        super("JMorph");
        setupGUI();
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

    private void setupGUI(){
        Container c = this.getContentPane();
        images = new JPanel();
        leftImageOptions = new JPanel();
        rightImageOptions = new JPanel();
        gridOptions = new JPanel();
        frameOptions = new JPanel();
        centralOptions = new JPanel();
        leftPanel = new JPanel();
        rightPanel = new JPanel();
        controls = new JPanel();
        uploadLeft = new JButton("Upload Left Image");
        uploadRight = new JButton("Upload Right Image");
        quit = new JButton("Quit");
        resetLeft = new JButton("Reset Left Points");
        resetRight = new JButton("Reset Right Points");
        animate = new JButton("Animate");
        saveMorph = new JButton("Save Morph");
        uploadMorph = new JButton("Upload Morph");
        timeSlider = new JSlider(1,5,3);
        frameSlider = new JSlider(0, 30, 30);
        leftBrightnessSlider = new JSlider(1, 10, 5);
        rightBrightnessSlider = new JSlider(1, 10, 5);
        rowSlider = new JSlider(1, 20, rows);
        colSlider = new JSlider(1, 20, cols);
        extra = new JLabel("");
        leftBrightnessLabel = new JLabel("Adjust Left Brightness");
        rightBrightnessLabel = new JLabel("Adjust Right Brightness");
        timeLabel = new JLabel("Adjust how many seconds the animation will run for");
        frameLabel = new JLabel("Adjust how many frames are shown per second during the animation");
        images.setLayout(new GridLayout(1,2));
        images.add(leftPanel);
        images.add(rightPanel);
        controls.setLayout(new GridLayout(5,3));
        leftImageOptions.setLayout(new GridLayout(1,2));
        leftImageOptions.add(uploadLeft);
        leftImageOptions.add(resetLeft);
        rightImageOptions.setLayout(new GridLayout(1,2));
        rightImageOptions.add(uploadRight);
        rightImageOptions.add(resetRight);
        centralOptions.setLayout(new GridLayout(1,2));
        centralOptions.add(saveMorph);
        centralOptions.add(uploadMorph);
        gridOptions.setLayout(new GridLayout(1,2));
        gridOptions.add(rowSlider);
        gridOptions.add(colSlider);
        frameOptions.setLayout(new GridLayout(1,2));
        frameOptions.add(frameSlider);
        frameOptions.add(timeSlider);
        resetRight.setEnabled(false);
        resetLeft.setEnabled(false);
        animate.setEnabled(false);
        JMorphListener manager = new JMorphListener();

        quit.addActionListener(manager);

        frameCounter = new Timer((1000/frames), e -> {
            if(frameCount<gridFrames.length) {
                previewGrid.setGrid(gridFrames[frameCount]);
                frameCount++;
            }
            else {
                frameCount = 0;
                frameCounter.stop();

            }
        });

        uploadRight.addActionListener(manager);

        animate.addActionListener(manager);


        uploadLeft.addActionListener(manager);

        resetLeft.addActionListener(manager);

        resetRight.addActionListener(manager);

        timeSlider.setMajorTickSpacing(1);
        timeSlider.setPaintTicks(true);
        timeSlider.setPaintLabels(true);
        frameSlider.setMinorTickSpacing(5);
        frameSlider.setMajorTickSpacing(10);
        frameSlider.setPaintTicks(true);
        frameSlider.setPaintLabels(true);
        leftBrightnessSlider.setMajorTickSpacing(1);
        leftBrightnessSlider.setPaintTicks(true);
        leftBrightnessSlider.setPaintLabels(true);
        rightBrightnessSlider.setMajorTickSpacing(1);
        rightBrightnessSlider.setPaintTicks(true);
        rightBrightnessSlider.setPaintLabels(true);


        timeSlider.addChangeListener(manager);

        frameSlider.addChangeListener(manager);
        rowSlider.addChangeListener(manager);
        colSlider.addChangeListener(manager);

        leftBrightnessSlider.addChangeListener(manager);

        rightBrightnessSlider.addChangeListener(manager);


        controls.add(leftBrightnessLabel);
        controls.add(rightBrightnessLabel);
        controls.add(leftBrightnessSlider);
        controls.add(rightBrightnessSlider);
        controls.add(uploadLeft);
        controls.add(uploadRight);
        controls.add(resetLeft);
        controls.add(resetRight);
        controls.add(leftImageOptions);
        controls.add(quit);
        controls.add(rightImageOptions);
        c.add(images, BorderLayout.NORTH);
        c.add(controls);

        images.setPreferredSize(new Dimension(1000, 500));
        controls.setPreferredSize(new Dimension(100, 250));

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
        TriangleGrid inter = new TriangleGrid(targ.getWidth(), targ.getHeight(), targ.getTrueWidth(), targ.getTrueWidth());
        for(int i = 0; i < inter.getWidth(); i++){
            for(int j = 0; j <inter.getHeight(); j++){
                inter.points[i][j].x = (int) (a * targ.points[i][j].x + (1-a) * orig.points[i][j].x);
                inter.points[i][j].y = (int) (a * targ.points[i][j].y + (1-a) * orig.points[i][j].y);
            }

        }
        return inter;
    }



    public static void main(String argv[]){
        JMorph morph = new JMorph();
    }

    public class JMorphListener implements ActionListener, ChangeListener{
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == animate) {
                gridFrames = animate();
                previewGrid = leftGrid;
                createPreview();
                previewGrid.setGrid(gridFrames[0]);
                frameCounter.start();
            } else if (e.getSource() == quit) {
                System.exit(0);
            }
            else if(e.getSource() == resetRight){
                rightGrid.reset();
            }
            else if(e.getSource() == resetLeft){
                leftGrid.reset();
            }
            else if(e.getSource() == uploadLeft) {
                leftPanel.removeAll();
                int returnVal = fc.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    String name = file.getAbsolutePath();
                    try {
                        leftImage = ImageIO.read(new File(name));
                    } catch (IOException e1) {
                    }
                    ;

                    leftImage = resize(leftImage, MAX_IMAGE_SIZE, MAX_IMAGE_SIZE);
                    //pls = new JLabel("", new ImageIcon(leftImage), JLabel.CENTER);
                    leftGrid = new GriddedImage(leftImage);
                    oldGrid = leftGrid.getTriangleGrid();
                    leftPanel.add(leftGrid);
                    leftPanel.revalidate();
                    leftPanel.repaint();
                    resetLeft.setEnabled(true);
                    animateCounter++;
                    if (animateCounter == 2) {
                        animate.setEnabled(true);
                    }
                }
            }
            else if(e.getSource() == uploadRight) {
                rightPanel.removeAll();
                int returnVal = fc.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    String name = file.getAbsolutePath();
                    try {
                        rightImage = ImageIO.read(new File(name));
                    } catch (IOException e1) {
                    }
                    ;

                    rightImage = resize(rightImage, MAX_IMAGE_SIZE, MAX_IMAGE_SIZE);

                    //rightPanel.add(pls, BorderLayout.CENTER);
                    rightGrid = new GriddedImage(rightImage);
                    newGrid = rightGrid.getTriangleGrid();
                    rightPanel.add(rightGrid);
                    rightPanel.revalidate();
                    rightGrid.repaint();
                    resetRight.setEnabled(true);
                    animateCounter++;
                    if (animateCounter == 2) {
                        animate.setEnabled(true);
                    }
                }
            }
        }
        public void stateChanged(ChangeEvent e){
            if(e.getSource() == timeSlider){
                seconds = timeSlider.getValue();
            }
            else if(e.getSource() == frameSlider){
                if (frameSlider.getValue() == 0) {frames = 1;}
                else {
                    frames = frameSlider.getValue();
                    frameCounter.setDelay(1000/frames);
                }
            }
            else if(e.getSource() == leftBrightnessSlider){
                float newBrightness;
                int newRed, newBlue, newGreen;
                for(int x = 0;x < leftImage.getWidth(); x++){
                    for(int y = 0;y < leftImage.getHeight();y++){
                        Color color = new Color(leftImage.getRGB(x, y));
                        newBrightness = leftBrightnessSlider.getValue() / (float)10;
                        newRed = (int)(color.getRed() + newBrightness) % 255;
                        newBlue = (int)(color.getBlue() + newBrightness) % 255;
                        newGreen = (int)(color.getGreen() + newBrightness) % 255;
                        color = new Color(newRed, newBlue, newGreen);
                        leftImage.setRGB(x, y, color.getRGB());
                        //alkjsdfh
                    }
                }
                repaint();
            }
            else if(e.getSource() == rightBrightnessSlider){

            }
            else if(e.getSource() == rowSlider){
                if(!rowSlider.getValueIsAdjusting()){
                    rows = rowSlider.getValue();
                }
            }
            else if(e.getSource() == colSlider){
                if(!colSlider.getValueIsAdjusting()){
                    cols = colSlider.getValue();
                }
            }
        }

    }


}
