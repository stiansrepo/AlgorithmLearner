/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithmlearner;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author DesktopStian
 */
public class AlgorithmLearner {

    int targetx = 83;
    int targety = 60;
    int startx = 26;
    int starty = 15;
    int sizeX = 100;
    int sizeY = 100;
    int costhv = 10;
    int costdiag = 14;
    int cost;
    int[][] map = new int[sizeX][sizeY];
    boolean[][] booleanmap = new boolean[sizeX][sizeY];
    int[][] heuristicMap = new int[sizeX][sizeY];

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        AlgorithmLearner al = new AlgorithmLearner();
        al.buildMap();
        al.buildBlocks();
        al.buildHeuristic();
        al.drawMap();
    }
 
    public void buildBlocks() {
        Random rnd = new Random();
        for (int i = 0; i < 1000; i++) {
            int rx = rnd.nextInt(100);
            int ry = rnd.nextInt(100);
            /*if ((rx == startx && ry == starty) || (rx == targetx && ry == targety) ||
                    (rx == startx+1 && ry == starty) || (rx == targetx+1 && ry == targety)) {*/
            if (rx == startx || ry == starty || rx == targetx || ry == targety || rx == startx+1 || ry == starty+1
                    || ry == starty-1 || rx == startx+1 || rx == targetx+1 || ry == targetx+1) {
                System.out.println("bommert");
            } else {
                map[rx][ry] = 2;
            }
        }
    }

    public void buildMap() {

        for (int j = 0; j < sizeX; j++) {
            for (int i = 0; i < sizeY; i++) {
                map[i][j] = 0;
                booleanmap[i][j] = false;
            }
        }
        
    }

    public void buildHeuristic() {
        for (int i = 0;
                i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                int distancex;
                int distancey;
                distancex = Math.abs((targetx - i));
                distancey = Math.abs((targety - j));
                if (map[i][j] == 0) {
                    heuristicMap[i][j] = distancex + distancey;

                } else {
                    heuristicMap[i][j] = 99999;
                }
            }
        }
    }

    public ArrayList<Coordinate> findPathInt(int i, int j, int tox, int toy, int[][] map) {
        boolean checking = true;
        int cheapestnode = 99399;
        int cheapestx = 0;
        int cheapesty = 0;
        ArrayList<Coordinate> ac = new ArrayList<>(9000);

        while (checking) {
            // start på en node
            // sjekk de 8 rundt
            // velg den billigste
            // start fra denne
            // sjekk de 8 rundt
            // ...til man finder endepunktet
            if (!((i < 1 || i > sizeX - 2) || (j < 1) || (j > sizeY - 2))) {
                if (map[i][j + 1]< cheapestnode) {
                    cheapestnode = map[i][j + 1];
                    cheapestx = i;
                    cheapesty = j + 1;
                }
                if (map[i][j - 1] < cheapestnode) {
                    cheapestnode = map[i][j - 1];
                    cheapestx = i;
                    cheapesty = j - 1;
                }
                if (map[i + 1][j] < cheapestnode) {
                    cheapestnode = map[i + 1][j];
                    cheapestx = i + 1;
                    cheapesty = j;
                }
                if (map[i + 1][j + 1] < cheapestnode) {
                    cheapestnode = map[i + 1][j + 1];
                    cheapestx = i + 1;
                    cheapesty = j + 1;
                }
                if (map[i + 1][j - 1] < cheapestnode) {
                    cheapestnode = map[i + 1][j - 1];
                    cheapestx = i + 1;
                    cheapesty = j - 1;
                }
                if (map[i - 1][j] < cheapestnode) {
                    cheapestnode = map[i - 1][j];
                    cheapestx = i - 1;
                    cheapesty = j;
                }
                if (map[i - 1][j + 1] < cheapestnode) {
                    cheapestnode = map[i - 1][j + 1];
                    cheapestx = i - 1;
                    cheapesty = j + 1;
                }
                if (map[i - 1][j - 1]*14 < cheapestnode) {
                    cheapestnode = map[i - 1][j - 1];
                    cheapestx = i - 1;
                    cheapesty = j - 1;
                }

                if (cheapestx == tox && cheapesty == toy) {
                    checking = false;
                }

                if (!ac.contains(new Coordinate(cheapestx, cheapesty))) {
                    ac.add(new Coordinate(cheapestx, cheapesty));
                    i = cheapestx;
                    j = cheapesty;
                }

            }
        }
        return ac;
    }

    public Coordinate getCoordFromCellNumber(int cell) {
        Coordinate temp = null;
        int tempi = (cell % 10);
        int tempj = cell - (cell % 10);
        new Coordinate(tempi, tempj);
        return temp;
    }

    public int getCellNumberFromCoordinate(Coordinate c) {
        int cellnumber = (10 * c.y) + c.x;
        return cellnumber;
    }

    public int getCellNumberFromInts(int x, int y) {
        int cellnumber = (10 * y) + x;
        return cellnumber;
    }

    public void drawMap() {
        JFrame jf = new JFrame();
        Panel p = new Panel(this);
        JLabel jl;
        String mapString = "";

        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                if (findPathInt(startx, starty, targetx, targety, heuristicMap).contains(new Coordinate(i, j))) {
                    mapString = mapString + ' ' + "X" + ' ';
                } else {
                    mapString = mapString + ' ' + heuristicMap[i][j] + ' ';
                }
            }
            mapString = mapString + "<br>";
        }

        mapString = "<HTML>" + mapString + "</HTML>";

        jl = new JLabel(mapString);
        jl.setFont(new Font("Monospace", Font.PLAIN, 12));
        
        jf.add(p);
        jf.setSize(600, 600);
        p.setVisible(true);
        jf.setVisible(true);

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        p.repaint();

    }

}

class Coordinate {

    int x;
    int y;
    Coordinate parent;
    int h;
    int g;
    int f;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(int x, int y, int h) {
        this.x = x;
        this.y = y;
        this.h = h;
    }

    @Override
    public boolean equals(Object object) {
        boolean sameSame = false;
        Coordinate c = (Coordinate) object;

        if (c.x == this.x && c.y == this.y) {
            sameSame = true;
        }

        return sameSame;
    }

}

/*
 ArrayList<Coordinate> heuristicList = new ArrayList();

 for (int i = 0; i < heuristicList.size(); i++) {            
 heuristicList.get(i).x = targetx - heuristicList.get(i).x;
 heuristicList.get(i).y = targety - heuristicList.get(i).y;
 }


 */
/*
 map[i + 1][j]
 map[i - 1][j];
 map[i + 1][j + 1];
 map[i + 1][j - 1];
 map[i - 1][j + 1];
 map[i][j + 1];
 map[i][j - 1];
 map[i - 1][j - 1];
 */

/*
 regn ut x/y basert på cellenr.:
 int tempi = (i % 10);
 int tempj = i - (i % 10);

 */
