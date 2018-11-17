import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GriddedImage extends JPanel {
    public String path = null;
    public BufferedImage img = null;
    private int width, height, midpointWidth, midpointHeight, sRow, sCol, rDiff, cDiff, radius = 5;
    private Color circleColor = Color.BLACK, lineColor = Color.WHITE;
    public TriangleGrid tGrid;
    private JMorphListener runner;


    public GriddedImage(BufferedImage pic1, String imgPath, JMorphListener listener, TriangleGrid g){
        new GriddedImage(pic1, imgPath, listener);
        tGrid = g;
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

    public void createGrid(Graphics graphic, int xDiff, int yDiff){
        rDiff = xDiff;
        cDiff = yDiff;

        for(int i = 0; i < midpointWidth; i++){
            for(int j = 0; j<midpointHeight; j++){
                Point p = tGrid.points[i][j];

                graphic.setColor(lineColor);
                if(j != midpointHeight - 1){
                    Point nextPoint = tGrid.points[i][j+1];
                    graphic.drawLine(p.x + rDiff, p.y + cDiff, nextPoint.x + rDiff, nextPoint.y + cDiff);
                }
                else if(i != midpointWidth -1){
                    Point nextPoint = tGrid.points[i+1][j];
                    graphic.drawLine(p.x + rDiff, p.y + cDiff, nextPoint.x + rDiff, nextPoint.y + cDiff);
                }
                else if(i != midpointWidth -1 && j != midpointHeight - 1){
                    Point nextPoint = tGrid.points[i+1][j+1];
                    graphic.drawLine(p.x + rDiff, p.y + cDiff, nextPoint.x + rDiff, nextPoint.y + cDiff);
                }

                graphic.setColor(circleColor);
                graphic.fillOval(rDiff - radius + p.x, cDiff - radius + p.y, 2*radius, 2*radius);

            }
        }
    }

    public void paintComponent(Graphics graphic){
        rDiff = 20; cDiff = 20;
        graphic.drawImage(img, rDiff, cDiff, this);
        createGrid(graphic, rDiff, cDiff);
    }


    public void setCircleColor(Color cColor) {
        circleColor = cColor;
        repaint();
    }
    public void setLineColor(Color lColor){
        lineColor = lColor;
        repaint();
    }

    public void setRadius(int r){
        radius = r;
        repaint();
    }
}
