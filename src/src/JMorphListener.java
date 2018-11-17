import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class JMorphListener implements MouseListener, MouseMotionListener {
    private GriddedImage pic;
    public JMorphListener(GriddedImage img){
        pic = img;
    }
    public void mousePressed(MouseEvent e){
        int xPos = e.getX() - pic.getrDiff();
        int yPos = e.getY() - pic.getcDiff();

        for(int i = 0; i < pic.getMidpointWidth(); i++){
            for(int j = 0; j < pic.getMidpointHeight(); j++){
                Point curPoint = pic.getTriangleGrid().points[i][j];
                int r = pic.getRadius();
                if(curPoint.distance(xPos, yPos) <= r){
                    if(i != 0 && i!=pic.getMidpointWidth() -1 && j!=0 && j!= pic.getMidpointHeight() -1){
                        pic.setsRow(i);
                        pic.setsCol(j);
                        return;
                    }
                }
            }
        }
    }
    public void mouseReleased(MouseEvent e){
        pic.setsRow(-1);
        pic.setsCol(-1);
    }
    public void mouseDragged(MouseEvent e){
        int xPos = e.getX() - pic.getrDiff();
        int yPos = e.getY() - pic.getcDiff();

        if(pic.getsRow()!=-1 && pic.getsCol() != -1){
            pic.getTriangleGrid().points[pic.getsRow()][pic.getsCol()].x = xPos;
            pic.getTriangleGrid().points[pic.getsRow()][pic.getsCol()].y = yPos;
            pic.repaint();
        }

    }
    public void mouseMoved(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
