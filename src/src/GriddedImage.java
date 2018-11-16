import javax.swing.*;
import javax.xml.ws.Dispatch;
import java.awt.image.BufferedImage;

public class GriddedImage extends JPanel {
    public String path = null;
    public BufferedImage img = null;
    private int width, height, midpointWidth, midpointHeight, sRow, sCol, rDiff, cDiff;
    public TriangleGrid grid;
    private JMorphListener runner;


    public GriddedImage(BufferedImage pic1, String imgPath, JMorphListener listener, TriangleGrid g){
        new GriddedImage(pic1, imgPath, listener);
        grid = g;
    }

    public GriddedImage(BufferedImage pic1, String imgPath, JMorphListener listener){
        super();
        path = imgPath;
        img = pic1;
        runner = listener;

        if(img.getWidth() == -1) {
            System.out.println("Unexpected image size, exiting...");
            System.exit(0);
        }
        width = img.getWidth();

        if(img.getHeight() == -1) {
            System.out.println("Unexpected image size, exiting...");
            System.exit(0);
        }
        height = img.getHeight();

        addMouseListener(runner);
        addMouseMotionListener(runner);

    }
}
