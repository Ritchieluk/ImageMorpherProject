import javafx.scene.shape.Circle;

import javax.swing.*;

public class GridPoint extends JComponent{

    int xValue, yValue, startY, startX;
    int selected = -1, radius = 5;
    boolean running = false;
    Circle point;

    public GridPoint(int x, int y){
        xValue = x;
        yValue = y;
        point = new Circle(x, y, radius);
    }

    public void startDrag(int x, int y){

    }

    public void doDrag(int x, int y){
        if(selected >-1){
            xValue = x;
        }
    }

    public void setxValue(int newX){
        xValue = newX;
    }
}
