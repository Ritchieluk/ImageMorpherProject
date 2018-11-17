

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GriddedImage extends JPanel {

    private JLabel pls;
    public BufferedImage img = null;
    private int width, height, midpointWidth, midpointHeight, sRow = -1, sCol = -1, rDiff, cDiff, radius = 5;
    private Color circleColor = Color.BLACK, lineColor = Color.WHITE;
    public TriangleGrid tGrid;
    private JMorphListener runner = new JMorphListener(this);


    public GriddedImage(BufferedImage pic1, JMorphListener listener, TriangleGrid g){
        new GriddedImage(pic1, listener);
        tGrid = g;
    }

    public GriddedImage(BufferedImage pic1, JMorphListener listener){
        super();
        img = pic1;
        runner = listener;

        pls = new JLabel("", new ImageIcon(img), JLabel.CENTER);
        add(pls, BorderLayout.CENTER);



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

        midpointWidth = JMorph.rows;
        midpointHeight = JMorph.cols;
        tGrid = new TriangleGrid(midpointWidth, midpointHeight, width, height);



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

    public int getRadius(){
        return radius;
    }
    public int getrDiff(){
        return rDiff;
    }
    public int getcDiff(){
        return cDiff;
    }
    public int getMidpointWidth(){
        return midpointWidth;
    }
    public int getMidpointHeight(){
        return midpointHeight;
    }
    public TriangleGrid getTriangleGrid(){
        return tGrid;
    }
    public Point getTriangleGridPoint(int x, int y){
        return tGrid.points[x][y];
    }
    public int getsRow(){
        return sRow;
    }
    public int getsCol(){
        return sCol;
    }
    public void setsRow(int r){
        sRow = r;
    }
    public void setsCol(int c){
        sCol = c;
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
