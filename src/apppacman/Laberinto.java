package apppacman;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * @author IrvinACG
 * @version 1.0.0
 * https://github.com/IrvinACG
 */
public class Laberinto implements IPintar {

    public static final int TAM = 50;
    public static final int MAX_LEVEL = 3;
    private int laberinto[][];
    public static final Color color[] = {Color.GREEN, Color.CYAN, Color.MAGENTA};
    private int nivel;

    public Laberinto(int nivel) {
        this.nivel = nivel;
        this.laberinto = getLab();
    }

    @Override
    public void pintar(Graphics2D g) {
        int xb = 0, yb = TAM;
        for (int f = 0; f <= 13; f++) {
            xb = 0;
            for (int c = 0; c <= 20; c++) {
                xb = c * TAM;
                yb = f * TAM;
                switch (laberinto[f][c]) {
                    case 0: //Paredes de Laberinto
                        g.setColor(color[nivel - 1]);
                        g.drawRect(xb, yb, TAM, TAM);
                        //g.fillRect(xb, yb, tam, tam);
                        break;
                    case 1: //Pintar bolas
                        g.setColor(Color.white);
                        g.fillOval(xb + 20, yb + 20, 10, 10);
                        break;
                    case 2: //Pintar fondo de camino
                        g.setColor(Color.black);
                        g.fillOval(xb + 20, yb + 20, 10, 10);
                        break;
                    case 5: //Bolas grandes
                        g.setColor(Color.white);
                        g.fillOval(xb + 10, yb + 10, 20, 20);
                        break;
                    case 6: //Pintar puntos de lugar de fantasmas
                        g.setColor(Color.red);
                        g.fillOval(xb + 25, yb + 25, 5, 5);
                        break;
                    case 3:
                        break;
                    case 9: //Lugar por default de Pacman lugar seguro
                        g.setColor(color[nivel - 1]);
                        g.fillRect(xb, yb, TAM, TAM);
                        break;
                }

            }
        }
    }

    public int[][] getLab() {
        int aux[][] = null;
        if (nivel == 1) {
            aux = arrPacman;
        }
        if (nivel == 2) {
            aux = arrPacman2;
        }
        if (nivel == 3) {
            aux = arrPacman3;
        }
        return aux;
    }
    public int arrPacman[][] = {
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 1, 1, 1, 1, 1, 1, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
        {0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0},
        {0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 5, 1, 0, 0},
        {0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 5, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0},
        {0, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 1, 0},
        {0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 6, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0},
        {3, 1, 0, 0, 1, 0, 1, 1, 1, 6, 6, 6, 1, 1, 1, 0, 1, 0, 0, 1, 3},
        {0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 6, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0},
        {0, 1, 0, 1, 1, 1, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0},
        {0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0},
        {0, 5, 0, 1, 0, 1, 0, 1, 1, 0, 9, 0, 1, 0, 0, 1, 1, 1, 0, 1, 0},
        {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 5, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };
    public int arrPacman2[][] = {
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 1, 1, 1, 1, 0, 1, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
        {0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0},
        {0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 1, 1, 5, 1, 0, 0},
        {0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 5, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0},
        {0, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0},
        {0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 6, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0},
        {3, 1, 1, 1, 0, 0, 1, 1, 1, 6, 6, 6, 1, 1, 1, 0, 1, 1, 1, 1, 3},
        {0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 6, 0, 1, 0, 0, 1, 1, 1, 0, 1, 0},
        {0, 1, 0, 1, 1, 1, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0},
        {0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0},
        {0, 5, 0, 1, 0, 1, 0, 1, 1, 0, 9, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0},
        {0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };
    public int arrPacman3[][] = {
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
        {0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 5, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0},
        {0, 1, 0, 5, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0},
        {0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 5, 0},
        {0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0},
        {3, 1, 1, 1, 1, 1, 1, 1, 1, 0, 6, 0, 1, 1, 0, 1, 0, 0, 0, 1, 3},
        {0, 1, 1, 1, 1, 1, 1, 1, 1, 6, 6, 6, 1, 1, 0, 1, 0, 0, 0, 1, 0},
        {0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 6, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0},
        {0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0},
        {0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 5, 1, 1, 1, 1, 1, 0, 0},
        {0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 9, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0},
        {0, 5, 1, 1, 1, 1, 1, 1, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

}
