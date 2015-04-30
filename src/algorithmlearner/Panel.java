package algorithmlearner;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 * @author DesktopStian
 */
public class Panel extends JPanel {

    AlgorithmLearner a;

    public Panel(AlgorithmLearner a) {
        this.a = a;
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_OFF);
        g2d.scale(10, 10);
        for (int i = 0; i < a.sizeX; i++) {
            for (int j = 0; j < a.sizeY; j++) {
                if (a.findPathInt(a.startx, a.starty, a.targetx, a.targety, a.heuristicMap).contains(new Coordinate(i, j))) {
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(i, j, 1, 1);
                } else {
                    if (a.map[i][j] == 2) {
                        g2d.setColor(Color.ORANGE);
                        g2d.fillRect(i, j, 1, 1);
                    } else {
                        g2d.setColor(Color.BLACK);
                        g2d.fillRect(i, j, 1, 1);
                    }
                }
            }

        }
    }

}
