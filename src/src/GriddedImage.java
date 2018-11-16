import javax.swing.*;
import javax.xml.ws.Dispatch;
import java.awt.image.BufferedImage;

public class GriddedImage extends JPanel {
    public String path = null;
    public BufferedImage img = null;
    private int width, height, midpointWidth, midpointHeight, sRow, sCol, rDiff, cDiff;
    public TriangleGrid grid;


    public GriddedImage(BufferedImage pic1, String imgPath, MorphActionListener listener, TriangleGrid g){
        GriddedImage(pic1, imgPath);
        grid = g;
    }
    public GriddedImage(BufferedImage pic1, String imgPath){
        super();
        path = imgPath;
        img = pic1;

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

        addMorphActionListener(this);

    }
}
