import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class GriddedImage extends JPanel {

    public BufferedImage img = null;
    private int width, height, midpointWidth, midpointHeight, sRow = -1, sCol = -1, rDiff, cDiff, radius = 5;
    private Color circleColor = Color.BLACK, lineColor = Color.GREEN;
    public TriangleGrid tGrid;


    public GriddedImage(BufferedImage pic1, TriangleGrid g, JMorph.JMorphListener manager){
        new GriddedImage(pic1, manager);
        tGrid = g;
    }

    public GriddedImage(BufferedImage pic1, JMorph.JMorphListener manager){
        super();
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

        midpointWidth = JMorph.rows;
        midpointHeight = JMorph.cols;
        tGrid = new TriangleGrid(midpointWidth, midpointHeight, width, height);



        addMouseListener(manager);
        addMouseMotionListener(manager);
        setPreferredSize(new Dimension(width, height));
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
                if(i != midpointWidth -1){
                    Point nextPoint = tGrid.points[i+1][j];
                    graphic.drawLine(p.x + rDiff, p.y + cDiff, nextPoint.x + rDiff, nextPoint.y + cDiff);
                }
                if(i != midpointWidth -1 && j != midpointHeight - 1){
                    Point nextPoint = tGrid.points[i+1][j+1];
                    graphic.drawLine(p.x + rDiff, p.y + cDiff, nextPoint.x + rDiff, nextPoint.y + cDiff);
                }
                if(i > 0 && j > 0 && i < midpointWidth - 1 && j < midpointHeight - 1) {
                    graphic.setColor(circleColor);
                    graphic.fillOval(rDiff - radius + (int) p.getX(), cDiff - radius + (int) p.getY(), 2 * radius, 2 * radius);
                }
            }
        }
    }

    public void paintComponent(Graphics graphic){
        rDiff = 0; cDiff = 0;
        graphic.drawImage(img, rDiff, cDiff, this);
        createGrid(graphic, rDiff, cDiff);
    }

    public void reset(){
        tGrid.reset();
        repaint();
    }

    public int[] getXBounds() {
        int[] temp = {tGrid.points[sRow - 1][sCol - 1].x, tGrid.points[sRow][sCol - 1].x, tGrid.points[sRow + 1][sCol].x, tGrid.points[sRow + 1][sCol + 1].x, tGrid.points[sRow][sCol + 1].x, tGrid.points[sRow - 1][sCol].x};
        return temp;
    }
    public int[] getYBounds(){
        int[] temp = {tGrid.points[sRow - 1][sCol - 1].y, tGrid.points[sRow][sCol - 1].y, tGrid.points[sRow + 1][sCol].y, tGrid.points[sRow + 1][sCol + 1].y, tGrid.points[sRow][sCol + 1].y, tGrid.points[sRow - 1][sCol].y};
        return temp;
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
    public void setGrid(TriangleGrid g){
        tGrid = g;
        repaint();
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
