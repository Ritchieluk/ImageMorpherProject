import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LineGrid extends JComponent {

    Color color = Color.BLACK;
    private ArrayList<Line> lines = new ArrayList<>();
    private static class Line{
        int x1;
        int y1;
        int x2;
        int y2;


        public Line(int xOne, int yOne, int xTwo, int yTwo){
            x1 = xOne;
            x2 = xTwo;
            y1 = yOne;
            y2 = yTwo;
        }
    }
    public void setColor(Color c){
        color = c;
        repaint();
    }


    public void addLine(int x1, int x2, int x3, int x4){
        lines.add(new Line(x1, x2, x3, x4));
        repaint();
    }
    public void addLine(GridPoint g1, GridPoint g2){
        lines.add(new Line(g1.getX(), g1.getY(), g2.getX(), g2.getY()));
    }

    public void clearLines(){
        lines.clear();
        repaint();
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        for(Line line : lines){
            g.setColor(color);
            g.drawLine(line.x1, line.y1, line.x2, line.y2);
        }
    }



}
