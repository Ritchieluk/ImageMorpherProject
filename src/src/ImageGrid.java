import javax.swing.*;

public class ImageGrid extends JPanel {
    int width, height;
    GridPoint[][] points;

    public ImageGrid(int w, int h){
        width = w;
        height = h;

        for(int i = 0; i < w; i++){
            for(int j = 0; j<h; j++){
                points[i][j] = new GridPoint(i*20, j*20);
            }
        }
    }
}
